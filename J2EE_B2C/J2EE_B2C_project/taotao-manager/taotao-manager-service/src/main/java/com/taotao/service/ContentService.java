package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * CMS业务层
 * @author mbc1996
 *
 */
public interface ContentService {
	/**
	 * 根据内容分类id查找旗下内容
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	public EasyUIDataGridResult getContentListByCatId(int page,int rows,long categoryId);
	/**
	 * 添加内容条目
	 * @param content
	 * @return
	 */
	public TaotaoResult createContent(TbContent content);
}
