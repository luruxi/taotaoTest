package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.ArrayReferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EuiTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EuiTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EuiTreeNode> resultList = new ArrayList<>();
		for(TbContentCategory contentCategory:list) {
			EuiTreeNode node = new EuiTreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		//创建一个pojo--com.taotao.pojo.TbContentCategory 用于查询
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		//添加记录(同时实现了pojo-contentCategory的setId)
		contentCategoryMapper.insert(contentCategory);
		
		//查看父节点的isParent列是否是true不是改成true(查出父节点的数据记录)
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否是父节点
		if(!parentCat.getIsParent()) {//如果不是父节点
			parentCat.setIsParent(true);
			//更新父节点记录--数据库
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果--TaotaoResult
		//TaotaoResult result = new TaotaoResult();
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(long parentId, long id) {
		//删除当前节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		//判断父节点还有子节点没有
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
		//如果没有子节点更新isParent状态
		if(catList == null || catList.size()==0) {
			//查出父节点的数据记录
			TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
			parentCat.setIsParent(false);
			//更新父节点记录--数据库
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		//查出节点的数据记录
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		//更新节点记录--数据库
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok();
	}

}
