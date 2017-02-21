package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;
/**
 * 商品功能业务层
 * @author mbc1996
 *
 */
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(long id) throws Exception {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}
	
	/**
	 * 返回商品分页列表
	 * @param page:显示的页码，rows:每页显示的记录数
	 */
	public EasyUIDataGridResult getItemList(int page, int rows) throws Exception {
		TbItemExample example = new TbItemExample();
		//在查询的SQL语句执行前，执行
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 实现添加商品的业务
	 * 完善Tbitem的属性，通过mapper插入数据库
	 */
	@Override
	public TaotaoResult createItem(TbItem item,String desc,String itemParams) throws Exception {
		//保存商品基本信息
		item.setId(IDUtils.generateItemId());
		item.setStatus((byte)1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);
		//保存商品详细描述
		createItemDesc(item.getId(), desc);
		//保存商品规格参数
		createItemParam(item.getId(), itemParams);
		return TaotaoResult.ok();
	}
	/**
	 * 保存商品规格参数到数据库
	 * @param itemId
	 * @param itemParams
	 * @return
	 */
	private TaotaoResult createItemParam(Long itemId,String itemParams){
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		Date date = new Date();
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}
	
	/**
	 * 保存商品详细描述到数据库
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaotaoResult createItemDesc(Long itemId, String desc){
		TbItemDesc record = new TbItemDesc();
		record.setItemId(itemId);
		record.setItemDesc(desc);
		Date date = new Date();
		record.setCreated(date);
		record.setUpdated(date);
		itemDescMapper.insert(record);
		return TaotaoResult.ok();
	}
	
}
