package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.CacheSyncService;

@Service
public class CacheSyncServiceImpl implements CacheSyncService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${HASH_KEY_FOR_CONTENT_LIST}")
	private String HASH_KEY_FOR_CONTENT_LIST;
	@Value("${KEY_FOR_CATEGORY_LIST}")
	private String KEY_FOR_CATEGORY_LIST;
	
	
	@Override
	public TaotaoResult syncContentList(long contentCategoryId) {
		try {
			jedisClient.hdel(HASH_KEY_FOR_CONTENT_LIST, contentCategoryId+"");			
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}


	@Override
	public TaotaoResult syncItemCategoryList() {
		try {
			jedisClient.del(KEY_FOR_CATEGORY_LIST);
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
