package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Value("${BASE_REST_URL}")
	private String BASE_REST_URL;
	@Value("${CACHE_SYNC_CONTENT_REST_URL}")
	private String CACHE_SYNC_CONTENT_REST_URL;

	@Override
	public EasyUIDataGridResult getContentListByCatId(int page, int rows, long categoryId) {
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> contentList = contentMapper.selectByExample(example);
		// 创建返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(contentList);
		PageInfo pageInfo = new PageInfo<>(contentList);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	@Override
	public TaotaoResult createContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		// 对内容进行更新操作时，需要同步缓存
		HttpClientUtil.doGet(BASE_REST_URL + CACHE_SYNC_CONTENT_REST_URL + content.getCategoryId());
		return TaotaoResult.ok();
	}

}
