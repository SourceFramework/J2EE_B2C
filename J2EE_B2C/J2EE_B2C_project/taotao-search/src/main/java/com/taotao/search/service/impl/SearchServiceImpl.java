package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws SolrServerException {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(queryString);
		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);
		// set the default search field
		solrQuery.set("df", "item_keywords");
		// set the highlight
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:green\">");
		solrQuery.setHighlightSimplePost("</em>");
		//excute the query operation
		SearchResult searchResult = searchDao.search(solrQuery);
		//set the other properties
		long recordCount = searchResult.getRecordCount();
		long totalPage = recordCount / rows;
		if(recordCount % rows != 0 ){
			totalPage++;
		}
		searchResult.setTotalPage(totalPage);
		searchResult.setCurPage(page);
		return searchResult;
	}

}
