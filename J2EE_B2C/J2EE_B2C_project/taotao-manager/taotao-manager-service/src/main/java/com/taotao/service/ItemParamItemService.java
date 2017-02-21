package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItemParamItem;

/**
 * 商品规格参数
 * @author mbc1996
 *
 */
public interface ItemParamItemService {
	/**
	 * 获得商品规格参数列表，使用MyBatis的PageHelper分页插件
	 * @param page 显示的页码
	 * @param rows 每页显示记录数
	 * @return
	 */
	public EasyUIDataGridResult getItemParamItemList(int page,int rows);
	
	/**
	 * 根據商品ID查找商品規格參數
	 * @param itemId
	 * @return 
	 */
	public String getItemParamItemByItemId(Long itemId);
}
