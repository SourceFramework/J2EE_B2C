package com.taotao.search.dao;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import com.taotao.search.pojo.SearchResult;

public interface SearchDao {
	public SearchResult search(SolrQuery solrQuery) throws SolrServerException;
}
