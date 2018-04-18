package com.taotao.portal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * 商品详情页展示
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/*
	 * 商品基本信息
	 */
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable long itemId,Model model) {
		ItemInfo tbItem = itemService.getItemById(itemId);
		model.addAttribute("item",tbItem);
		return "item";
	}
	
	/*
	 * 商品描述信息
	 */
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemDesc(@PathVariable long itemId,Model model) {
		String itemDesc = itemService.getItemDesc(itemId);
		return itemDesc;
	}
	
	/*
	 * 商品描述信息
	 */
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemParam(@PathVariable long itemId,Model model) {
		String itemParam = itemService.getItemParam(itemId);
		return itemParam;
	}
	
}
