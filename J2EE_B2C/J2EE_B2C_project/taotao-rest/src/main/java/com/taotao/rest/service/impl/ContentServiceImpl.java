package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

/**
 * 内容模块 业务层
 * @author mbc1996
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${HASH_KEY_FOR_CONTENT_LIST}")
	private String HASH_KEY_FOR_CONTENT_LIST;
	
	@Override
	public List<TbContent> getContentListByCatId(long contentCategoryId) {
		List<TbContent> contentList = null;
		//检查缓存是否有相关数据
		try {
			String jsonValue = jedisClient.hget(HASH_KEY_FOR_CONTENT_LIST, contentCategoryId+"");
			if(!StringUtils.isBlank(jsonValue)){
				contentList = JsonUtils.jsonToList(jsonValue, TbContent.class);
				return contentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(contentCategoryId);
		contentList = contentMapper.selectByExample(example);
		//向缓存保存数据
		try {
			String jsonData = JsonUtils.objectToJson(contentList);
			jedisClient.hset(HASH_KEY_FOR_CONTENT_LIST, contentCategoryId+"", jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentList;
	}

}
