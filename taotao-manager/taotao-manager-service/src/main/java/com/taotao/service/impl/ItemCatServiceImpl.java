package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EuiTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	//@Autowired注入mapper
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EuiTreeNode> getItemCatList(long parentId) {
		// 根据parentId查询，parentId不是主键需要通过查询条件查询--创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 根据查询条件查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 把TbItemCat列表 转换成 EuiTreeNode列表
		List<EuiTreeNode> euiList = new ArrayList<>();
		for(TbItemCat itemCat : list) {
			EuiTreeNode node = new EuiTreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent()?"closed":"open");
			euiList.add(node);
		}
		return euiList;
	}

}
