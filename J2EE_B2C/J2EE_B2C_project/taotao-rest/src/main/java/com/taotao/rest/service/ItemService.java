package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {
	/**
	 * get the base information of specific item by id
	 * @param id
	 * @return
	 */
	public TaotaoResult getItemBaseInfo(long id);
	/**
	 * get the description of specific item by id
	 * @param id
	 * @return
	 */
	public TaotaoResult getItemDesc(long id);
	/**
	 * get the param of specific item by id
	 * @param id
	 * @return
	 */
	public TaotaoResult getItemParam(long id);
}
