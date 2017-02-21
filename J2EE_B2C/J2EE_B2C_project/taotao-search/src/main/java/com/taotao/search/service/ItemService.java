package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {
	/**
	 * import all items which come from database to solr
	 * @return
	 */
	public TaotaoResult importAllItems();
	/**
	 * import an item which come from database to solr by itemId
	 * @param id
	 * @return
	 */
	public TaotaoResult importOneItem(String id);
}
