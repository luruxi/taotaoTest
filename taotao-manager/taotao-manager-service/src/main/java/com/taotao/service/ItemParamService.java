package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	List<TbItemParam> getItemParamList(int page,int rows);
	long countAllList();
	TaotaoResult getItemParamByCid(long cid);
	TaotaoResult insertItemParam(TbItemParam tbItemParam);
}
