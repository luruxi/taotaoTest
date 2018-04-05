package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {
		//递归查询商品列表-不用循环是不确定层级数
		CatResult catResult = new CatResult();
		//查询分类列表
		catResult.setData(getCatList(0));
		return catResult;
	}
	//查询分类列表的方法
	private List<?> getCatList(long parentId){
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//根据条件查询返回list
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		System.out.println(list.toString());
		//创建返回值列表
		List resultList = new ArrayList<>();
		//由于前台空间有限，只能存放一级分类14个，所以加计数器截断
		int count = 0;
		//向list中添加节点
		for(TbItemCat tbItemCat:list) {
			//判断是否是父节点
			if(tbItemCat.getIsParent()) {//是父亲节点
				CatNode catNode = new CatNode();
				if(parentId==0) {//判断是否是一级节点
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else {
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				count++;
				if(parentId==0 && count>=14) {//限制一级分类
					break;
				}
			}else {
				resultList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return resultList;
	}

}
