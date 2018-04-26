package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
/*
 * 通过token获取用户信息，调用sso系统的服务
 */
@Service
public class UserServiceImpl implements UserService {
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;//这里用public 其他地方注入这个类可以调用这个属性变量
	@Value("${SSO_LOGIN_URL}")
	public String SSO_LOGIN_URL;//这里用public 其他地方注入这个类可以调用这个属性变量
	@Value("${SSO_TOKEN_URL}")
	private String SSO_TOKEN_URL;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			//从sso单点获取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL+SSO_TOKEN_URL+"/"+token);
			//返回信息数据处理
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
			//判断并返回结果
			if(result.getStatus()==200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
