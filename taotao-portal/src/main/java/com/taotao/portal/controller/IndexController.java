package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;

@Controller
public class IndexController {
	@RequestMapping(value="/index")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping(value="/httpclient/post" , method = RequestMethod.POST)
	@ResponseBody
	public String testPost() {
		return "ok";
	}
	
	@RequestMapping(value="/httpclient/post2" , method = RequestMethod.POST)
	@ResponseBody
	public String testPost2(String username,String password) {
		HashMap map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		return map.toString();
	}
}
