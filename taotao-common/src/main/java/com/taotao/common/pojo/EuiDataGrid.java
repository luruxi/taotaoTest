package com.taotao.common.pojo;

import java.util.List;
/*
 * easyUI datagrid 数据表格的数据格式类
 */
public class EuiDataGrid {
	private long total;
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
