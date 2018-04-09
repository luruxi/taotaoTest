package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.service.ContentService;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/index")
	public String showIndex(Model model) {
		String adJson = contentService.getContentService();
		model.addAttribute("ad1", adJson);
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
	
	@RequestMapping(value="/httpclient/post3" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE+";chartset=utf-8")
	@ResponseBody
	public TaotaoResult testPost3(String username,String password) {
		HashMap map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		System.out.println(map.toString());
		return TaotaoResult.ok(map);
	}
}
