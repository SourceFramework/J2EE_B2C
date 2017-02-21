package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * 商品规格模板
 * @author mbc1996
 *
 */
public interface ItemParamService {
	/**
	 * 根据商品分类查找商品规格模板
	 * @param cid
	 * @return
	 */
	public TaotaoResult getItemParamByCid(Long cid);
	/**
	 * 将用户自定义的商品规格参数模板存入数据表
	 * @param itemParam
	 * @return
	 */
	public TaotaoResult createItemParam(TbItemParam itemParam);
}
