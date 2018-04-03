package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EuiTreeNode;

public interface ItemCatService {
	List<EuiTreeNode> getItemCatList(long parentId);
}
