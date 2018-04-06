package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EuiTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EuiTreeNode> getCategoryCatList(@RequestParam(value="id",defaultValue="0")long parentId){
		List<EuiTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createCategory(long parentId,String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteCategory(long parentId,long id) {//parentId有时候有值，有时候undefined（就剩下一个子节点，还要删除这个节点时候）
		System.out.println("parentId:"+parentId+"--id:"+id);
//		TaotaoResult result = contentCategoryService.deleteContentCategory(parentId, id);
//		return result;
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateCategory(long id,String name) {
		TaotaoResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}

}
