package com.taotao.rest.service;

import com.taotao.rest.pojo.CategoryResult;
/**
 * 商品分类模块业务层
 * @author mbc1996
 *
 */
public interface ItemCatService {
	/**
	 * 返回商品的所有分类信息，实现前台商品分类显示功能
	 * @return 自定义pojo
	 */
	public CategoryResult getItemCatList();
}
