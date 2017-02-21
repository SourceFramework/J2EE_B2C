package com.taotao.common.pojo;

import java.util.List;

/**
 * 使用EasyUI的DataGrid控件要求提供JSON数据，格式如下
 * “{total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]}”
 * 此类为返回数据的包装类
 * @author mbc1996
 *
 */
public class EasyUIDataGridResult {
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
