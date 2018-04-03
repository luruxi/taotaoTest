package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EuiTreeNode;
import com.taotao.service.ItemCatService;

/*
 * 商品分类
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EuiTreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0") long parentId) {
		
		List<EuiTreeNode> result = itemCatService.getItemCatList(parentId);
		return result;
	}
}
