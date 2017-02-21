package com.taotao.rest.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {
	/**
	 * 根据内容分类ID 获得内容
	 * @param contentCategoryId
	 * @return
	 */
	public List<TbContent> getContentListByCatId(long contentCategoryId);
}
