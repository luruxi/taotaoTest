package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;
/*
 * 搜索服务索引库维护
 */
@Controller
@RequestMapping("/manager")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/importAll")
	@ResponseBody
	public TaotaoResult importAllItems() {
		return itemService.importAllItem();
	}
}
