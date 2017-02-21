package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${BASE_URL_SEARCH}")
	private String BASE_URL_SEARCH;

	@Override
	public SearchResult search(String queryString, int page) {
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page + "");
		
		String jsonData = HttpClientUtil.doGet(BASE_URL_SEARCH, param);
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, SearchResult.class);
		
		SearchResult searchResult = (SearchResult) result.getData();
		return searchResult;
	}

}
