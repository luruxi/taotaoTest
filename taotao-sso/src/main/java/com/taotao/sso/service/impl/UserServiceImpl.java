package com.taotao.sso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;
/*
 * 用户注册信息校验 username phone email
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private TbUserMapper userMapper;
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

}
