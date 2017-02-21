package com.taotao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${BASE_URL_SSO}")
	private String BASE_URL_SSO;
	@Value("${GET_USER_BY_TOKEN}")
	private String GET_USER_BY_TOKEN;

	@Override
	public TbUser getUserByToken(String token) {
		String jsonData = HttpClientUtil.doGet(BASE_URL_SSO + GET_USER_BY_TOKEN + "/" + token);
		if(!StringUtils.isBlank(jsonData)){
			try {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbUser.class);
				if(taotaoResult != null && taotaoResult.getStatus() == 200){
					TbUser user = (TbUser) taotaoResult.getData();
					return user;					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return null;
	}

}
