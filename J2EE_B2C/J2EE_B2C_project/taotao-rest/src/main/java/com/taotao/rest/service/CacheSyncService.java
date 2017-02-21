package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * cache synchronization
 * @author mbc1996
 *
 */
public interface CacheSyncService {
	/**
	 * 同步缓存中门户网站广告位数据
	 * @param contentCategoryId
	 * @return
	 */
	public TaotaoResult syncContentList(long contentCategoryId);
	
	/**
	 * 同步缓存中门户网站的商品分类列表
	 * @return
	 */
	public TaotaoResult syncItemCategoryList();
}
