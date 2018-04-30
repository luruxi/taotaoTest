package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserServiceImpl userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//在handker执行之前
		//判断用户是否登录
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//根据token换取用户信息，调取sso接口
		TbUser user = userService.getUserByToken(token);
		//判断用户信息，取不到跳转到登录页面带着用户请求的url传递过去，返回false
		if(null==user) {
			response.sendRedirect(userService.SSO_BASE_URL+userService.SSO_LOGIN_URL
					+"?redirect="+request.getRequestURL());
			return false;
		}
		//取到用户信息返回true，放行
		//把用户信息放到request中
		request.setAttribute("user", user);
		//返回值决定handler是否执行 true 执行 false 不执行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handler执行之后，返回ModelAndView之前
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//返回ModelAndView之后
	}

}
