package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CategoryNode;
import com.taotao.rest.pojo.CategoryResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${KEY_FOR_CATEGORY_LIST}")
	private String KEY_FOR_CATEGORY_LIST;
	/**
	 * 取出所有商品分类信息，封装 成CategoryResult返回
	 * 使用redis缓存
	 * 需求：门户网站分类详情显示
	 */
	@Override
	public CategoryResult getItemCatList() {
		CategoryResult categoryResult = null ;
		//判断缓存中是否有数据，有则直接取出
		try {
			String jsonData = jedisClient.get(KEY_FOR_CATEGORY_LIST);
			if(!StringUtils.isBlank(jsonData)){
				categoryResult = JsonUtils.jsonToPojo(jsonData, CategoryResult.class);				
				return categoryResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		categoryResult = new CategoryResult();
		categoryResult.setData(getItemCatList(0));
		//写缓存
		try {
			String jsonData = JsonUtils.objectToJson(categoryResult);
			jedisClient.set(KEY_FOR_CATEGORY_LIST, jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryResult;
	}

	/**
	 * 采用递归返回商品所有分类信息 查找某分类的所有子分类
	 * 
	 * @param parentId
	 * @return List<CategoryNode> when it is a parent category,List<String> when
	 *         it is a child category
	 */
	 private List getItemCatList(long parentId){
		 TbItemCatExample example = new TbItemCatExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andParentIdEqualTo(parentId);
		 List<TbItemCat> list = itemCatMapper.selectByExample(example);
		 List result = new ArrayList<>();
		 int count = 0; //计数器，因为前台版式设计原因，空间只够显示14个一级分类
		 for(TbItemCat itemCat : list ){
			 //如果该分类还有子分类
			 if(itemCat.getIsParent()){
				 CategoryNode node = new CategoryNode();
				 node.setUrl("/products/"+itemCat.getId()+".html");
				 //如果该分类是一级分类
				 if(itemCat.getParentId() == 0){
					 node.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
					 count++; //针对一级分类计数
				 }else{
					 node.setName(itemCat.getName());
				 }
				 node.setItems(getItemCatList(itemCat.getId()));
				 result.add(node);
				 if(count >= 14){ //查找满14个一级分类后就不再查找，不够显示了,这是前台设计的锅
					 break;
				 }
			 } else {
				 result.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
			
			 }
		 }
		 return result;
	 }

}
