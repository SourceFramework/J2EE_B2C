package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${BASE_KEY_FOR_ITEM_INFO}")
	private String BASE_KEY_FOR_ITEM_INFO;
	@Value("${KEY_EXPIRE_TIME}")
	private Integer KEY_EXPIRE_TIME;
	
	@Override
	public TaotaoResult getItemBaseInfo(long id) {
		TbItem item = null;
		try {
			String json = jedisClient.get(BASE_KEY_FOR_ITEM_INFO+":"+id+":baseInfo");
			if(!StringUtils.isBlank(json)){
				item = JsonUtils.jsonToPojo(json, TbItem.class);
				return TaotaoResult.ok(item);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		item = itemMapper.selectByPrimaryKey(id);
		try {
			//添加缓存逻辑,设置缓存过期时间
			String jsonData = JsonUtils.objectToJson(item);
			jedisClient.set(BASE_KEY_FOR_ITEM_INFO+":"+id+":baseInfo", jsonData);
			jedisClient.expire(BASE_KEY_FOR_ITEM_INFO+":"+id+":baseInfo", KEY_EXPIRE_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	@Override
	public TaotaoResult getItemDesc(long id) {
		TbItemDesc itemDesc = null;
		try {
			String json = jedisClient.get(BASE_KEY_FOR_ITEM_INFO+":"+id+":desc");
			if(!StringUtils.isBlank(json)){
				itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return TaotaoResult.ok(itemDesc);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		itemDesc = itemDescMapper.selectByPrimaryKey(id);
		try {
			String jsonData = JsonUtils.objectToJson(itemDesc);
			jedisClient.set(BASE_KEY_FOR_ITEM_INFO+":"+id+":desc", jsonData);
			jedisClient.expire(BASE_KEY_FOR_ITEM_INFO+":"+id+":desc", KEY_EXPIRE_TIME);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult getItemParam(long id) {
		TbItemParamItem itemParamItem = null;
		try {
			String json = jedisClient.get(BASE_KEY_FOR_ITEM_INFO+":"+id+":param");
			if(!StringUtils.isBlank(json)){
				itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return TaotaoResult.ok(itemParamItem);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(id);
		List<TbItemParamItem> paramItemList = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(paramItemList != null && paramItemList.size() > 0){
			itemParamItem = paramItemList.get(0);
		}
		try {
			String jsonData = JsonUtils.objectToJson(itemParamItem);
			jedisClient.set(BASE_KEY_FOR_ITEM_INFO+":"+id+":param", jsonData);
			jedisClient.expire(BASE_KEY_FOR_ITEM_INFO+":"+id+":param", KEY_EXPIRE_TIME);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemParamItem);
	}

}
