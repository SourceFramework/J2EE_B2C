package com.taotao.sso.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Object checkData(@PathVariable("param") String content, @PathVariable("type") Integer type,
			@RequestParam(required = false) String callback) {
		try {
			content = new String(content.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		TaotaoResult taotaoResult = null;
		// validate the parameters

		if (StringUtils.isBlank(content)) {
			taotaoResult = TaotaoResult.build(400, "the content couldn't be blank");
		} else if (type == null || type != 1 && type != 2 && type != 3) {
			taotaoResult = TaotaoResult.build(400, "the value of type should in (1,2,3)");
		} else {
			try {
				taotaoResult = userService.checkData(content, type);
			} catch (Exception e) {
				e.printStackTrace();
				taotaoResult = TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
			}
		}
		// if use jsonp
		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return taotaoResult;
		}

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult regist(TbUser user) {
		// verify data integrity
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(500, "Username or password shouldn't be null");
		}
		return userService.regist(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult loginOn(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		// verify data integrity
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return TaotaoResult.build(500, "Username or password shouldn't be null");
		}
		return userService.loginOn(username, password,request,response);
	}

	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		if (!StringUtils.isBlank(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return taotaoResult;
	}

	@RequestMapping(value = "/logout/{token}", method = RequestMethod.GET)
	@ResponseBody
	public Object loginOut(@PathVariable String token, String callback) {
		TaotaoResult taotaoResult = userService.loginOut(token);
		if (!StringUtils.isBlank(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return taotaoResult;
	}

}
