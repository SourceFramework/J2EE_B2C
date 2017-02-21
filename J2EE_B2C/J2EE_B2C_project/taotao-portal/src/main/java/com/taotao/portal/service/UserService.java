package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

public interface UserService {
	/**
	 * Get user by token
	 * @param token
	 * @return User pojo
	 */
	public TbUser getUserByToken(String token);
}
