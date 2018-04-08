package com.taotao.service;

import com.taotao.common.pojo.EuiDataGrid;

public interface ContentService {
	EuiDataGrid getContentList(long categoryId,Integer page,Integer rows);
}
