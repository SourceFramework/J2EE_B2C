package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{
	@Value("${HTTPCLIENT_BASE_URL}")
	private String HTTPCLIENT_BASE_URL;
	@Value("${HOMEPAGE_AD1}")
	private String HOMEPAGE_AD1;
	
	@Override
	public String getHomePageAd1() {
		//使用httpclient访问taotao-rest服务
		String remoteJsonResult = HttpClientUtil.doGet(HTTPCLIENT_BASE_URL+HOMEPAGE_AD1);
		TaotaoResult taotaoResult = TaotaoResult.formatToList(remoteJsonResult, TbContent.class);
		//取出大广告位的数据
		List<TbContent> contentList = (List<TbContent>) taotaoResult.getData();
		List<Map> mapList = new ArrayList<>();
		//生成前端所需的json数据格式
		for(TbContent tbContent : contentList){
			Map map = new HashMap<>();
			map.put("src", tbContent.getPic());
			map.put("height", 240);
			map.put("width", 670);
			map.put("srcB", tbContent.getPic2());
			map.put("widthB", 550);
			map.put("heightB", 240);
			map.put("href", tbContent.getUrl());
			map.put("alt", tbContent.getSubTitle());
			mapList.add(map);
		}
		String jsonResult = JsonUtils.objectToJson(mapList);
		return jsonResult;
	}

}
