package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemBaseInfo;

public interface ItemService {
	/**
	 * get the base information of item by id
	 * @param id
	 * @return
	 */
	public ItemBaseInfo getItemBaseInfo(long id);
	/**
	 * get the description of item by id 
	 * @param id
	 * @return in the form of html fragment
	 */
	public String getItemDesc(long id);
	/**
	 * get the param of item by id 
	 * @param id
	 * @return in the form of html fragment
	 */
	public String getItemParam(long id);
}
