package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${BASE_KEY_FOR_TOKEN}")
	private String BASE_KEY_FOR_TOKEN;
	@Value("${TOKEN_KEY_EXPIRE_TIME}")
	private Integer TOKEN_KEY_EXPIRE_TIME;

	@Override
	public TaotaoResult checkData(String content, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		switch (type) {
		case 1:
			criteria.andUsernameEqualTo(content);
			break;
		case 2:
			criteria.andPhoneEqualTo(content);
			break;
		case 3:
			criteria.andEmailEqualTo(content);
			break;
		}
		List<TbUser> userList = userMapper.selectByExample(example);
		if (userList == null || userList.size() == 0) {
			return TaotaoResult.ok(true);
		}
		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult regist(TbUser user) {
		// complete the form data
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// encrypt the password
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		// insert into database
		try {
			userMapper.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(400,
					"Fail to create a user and the cause are:\n" + ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult loginOn(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbUser> userList = userMapper.selectByExample(example);
		if (userList != null && userList.size() > 0) {
			TbUser user = userList.get(0);
			// if the password is correct.
			if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
				try {
					// clear the password field,it should not be stored in redis
					// for safety
					user.setPassword(null);
					// generate token
					String token = UUID.randomUUID().toString();
					jedisClient.set(BASE_KEY_FOR_TOKEN + ":" + token, JsonUtils.objectToJson(user));
					jedisClient.expire(BASE_KEY_FOR_TOKEN + ":" + token, TOKEN_KEY_EXPIRE_TIME);
					//add the token to cookie
					CookieUtils.setCookie(request, response, "TT_TOKEN", token);
					return TaotaoResult.ok(token);
				} catch (Exception e) {
					e.printStackTrace();
					return TaotaoResult.build(400, "Fail to login in. Some errors happened when store the token.");
				}
			}
		}
		return TaotaoResult.build(500, "The username is no exist or the password is no correct");
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		try {
			String jsonData = jedisClient.get(BASE_KEY_FOR_TOKEN + ":" + token);
			if (!StringUtils.isBlank(jsonData)) {
				// relocking
				jedisClient.expire(BASE_KEY_FOR_TOKEN + ":" + token, TOKEN_KEY_EXPIRE_TIME);
				return TaotaoResult.ok(JsonUtils.jsonToPojo(jsonData, TbUser.class));
			} else {
				return TaotaoResult.build(500, "Token invalid or expired");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
		}
	}

	@Override
	public TaotaoResult loginOut(String token) {
		try {
			jedisClient.del(BASE_KEY_FOR_TOKEN + ":" + token);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(400, "Fail to login out.\n" + ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
