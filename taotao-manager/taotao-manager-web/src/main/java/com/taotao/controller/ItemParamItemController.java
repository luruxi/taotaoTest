/**
 * 
 */
package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.ItemParamItemService;

/**
 * 获取商品规格参数
 *
 */
@Controller
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping(value="/itemParam/{itemId}")
	public String showItemParam(@PathVariable long itemId,Model model,HttpServletRequest request) {
		String itemParamStr = itemParamItemService.getItemParamByItemId(itemId);
//		model.addAttribute("itemParam",itemParamStr);
		request.setAttribute("itemParam",itemParamStr);
		return "item-param";
	}
}
