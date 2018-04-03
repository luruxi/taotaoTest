package com.taotao.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Override
	public List<TbItemParam> getItemParamList(int page,int rows) {
		// TODO Auto-generated method stub
		int start = (page-1)*rows;
		int end = page*rows;
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("start", start);
		map.put("end", end);
		List<TbItemParam> list = tbItemParamMapper.getItemParamList(map);
		return list;
	}
	@Override
	public long countAllList() {
		long count = tbItemParamMapper.countAllList();
		return count;
	}
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		Map<String,Long> paramMap = new HashMap<String,Long>();
		paramMap.put("cid", cid);
		List<TbItemParam> itemParamList = tbItemParamMapper.getItemParamListByCid(paramMap);
		if(null != itemParamList && itemParamList.size()>0) {
			return TaotaoResult.ok(itemParamList.get(0));
		}else {
			return TaotaoResult.ok();
		}
	}
	@Override
	public TaotaoResult insertItemParam(TbItemParam tbItemParam) {
		// TODO Auto-generated method stub
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		tbItemParamMapper.insert(tbItemParam);
		return TaotaoResult.ok();
	}

}

