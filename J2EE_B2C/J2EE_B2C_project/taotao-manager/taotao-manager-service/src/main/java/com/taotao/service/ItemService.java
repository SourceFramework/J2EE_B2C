package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	public TbItem getItemById(long id) throws Exception ; 
	
	public EasyUIDataGridResult getItemList(int page, int rows) throws Exception;
	
	public TaotaoResult createItem(TbItem item,String desc,String itemParams) throws Exception;
}
