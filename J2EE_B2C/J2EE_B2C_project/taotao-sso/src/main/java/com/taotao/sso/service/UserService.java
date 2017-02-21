package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
	/**
	 * Access database to check if the data is exist
	 * 
	 * @param content
	 *            the data to be checked
	 * @param type
	 *            includes "1": username ; "2":phone ; "3":email
	 * @return
	 */
	public TaotaoResult checkData(String content, Integer type);

	/**
	 * This method supports user registration. it inserts a piece of user record
	 * into database. The password should be encrypted using MD5.
	 * 
	 * @param user
	 * @return
	 */
	public TaotaoResult regist(TbUser user);

	/**
	 * This method supports user login on. Get the MD5 encrypted password by
	 * username and compare it with the password provided by form . Once a user
	 * login on successfully,the token will be effective in 30min in redis.
	 * Also,the token will be added to cookie
	 * 
	 * @param username
	 * @param password
	 * @param request
	 *            in order to add the token to cookie
	 * @param response
	 *            in order to add the token to cookie
	 * @return TaotaoResult with a token when success
	 */
	public TaotaoResult loginOn(String username, String password, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * Get the user info by specified token.
	 * 
	 * @param token
	 * @return TaotaoResult with an user object if the token is effective and
	 *         not expired
	 */
	public TaotaoResult getUserByToken(String token);

	/**
	 * This method supports user login out
	 * 
	 * @param token
	 * @return
	 */
	public TaotaoResult loginOut(String token);
}
