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
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
/*
 * 用户注册登录等
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private TbUserMapper userMapper;
	
	/*
	 * 注入jedis从spring容器中加载的配置文件
	 */
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;
	
	/*
	 * 用户注册信息校验 username phone email
	 */
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		switch (type) {
			case 1://校验-用户名
				criteria.andUsernameEqualTo(content);
			break;
			case 2://校验-电话
				criteria.andPasswordEqualTo(content);
			break;
			case 3://校验-邮件
				criteria.andEmailEqualTo(content);
			break;
			default:
			break;
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if(list==null || list.size()==0) {
			return TaotaoResult.ok(true);
		}
		return TaotaoResult.ok(false);
	}
	/*
	 * 用户注册添加新记录到数据库 
	 */
	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		//md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}
	/*
	 * 用户登录验证生成token存放缓存 
	 */
	@Override
	public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		//比对用户名
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
//		criteria.andPasswordEqualTo(password);
		List<TbUser> list = userMapper.selectByExample(example);
		if(list == null || list.size()==0) {
			return TaotaoResult.build(500, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		//比对密码
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(500, "用户名或密码错误");
		}
		//生成token
		String token = UUID.randomUUID().toString();
		//保存用户之前把用户的密码清空，增加安全性
		user.setPassword(null);
		//把token和user存入redis
		jedisClient.set(REDIS_USER_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
		//设置session过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token, SSO_SESSION_EXPIRE);
		//登录成功以后村cookie--添加写cookie的逻辑--cookie的有效期，关闭浏览器失效
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		return TaotaoResult.ok(token);
	}
	/*
	 * 通过token获取用户信息，是否过期，更新token过期时间
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(REDIS_USER_SESSION_KEY+":"+token);
		//过期
		if(StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "session过期重新登录");
		}
		//不过期，更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token, SSO_SESSION_EXPIRE);
		return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

}
