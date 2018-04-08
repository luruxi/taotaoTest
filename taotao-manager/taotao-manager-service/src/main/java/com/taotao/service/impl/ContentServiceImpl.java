package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EuiDataGrid getContentList(long categoryId, Integer page, Integer rows) {
		//创建查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//设置分页过滤
		PageHelper.startPage(page, rows);
		//数据列表查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//创建返回值pojo--EuiDataGrid
		EuiDataGrid result = new EuiDataGrid();
		//设置返回值的数据列表
		result.setRows(list);
		//根据查询结果列表获取分页基本信息（包含总条数）
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		//设置返回值的总条数
		result.setTotal(pageInfo.getTotal());
		return result;
	}

}
