package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	@Value("${BASE_URL_SSO}")
	private String BASE_URL_SSO;
	@Value("${SSO_LOGIN_PATH}")
	private String SSO_LOGIN_PATH;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//check if the user had logined
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		TbUser user = userService.getUserByToken(token);
		//返回值决定handler是否执行。true：执行，false：不执行
		if(user != null){
			return true;
		}else{     
			//Can't get the user information.Possibly token invalid or expired
			response.sendRedirect(BASE_URL_SSO + SSO_LOGIN_PATH + "?redirect=" + request.getRequestURL());
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
