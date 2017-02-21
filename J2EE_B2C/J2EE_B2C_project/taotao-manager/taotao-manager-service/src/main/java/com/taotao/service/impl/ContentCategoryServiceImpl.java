package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example );
		
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for(TbContentCategory contentCategory : list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult createContentCategory(long parentId, String name) {
		//创建一个pojo,使用mapper代理插入数据库
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		contentCategory.setStatus(1);
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		Date date = new Date();
		contentCategory.setCreated(date);
		contentCategory.setUpdated(date);
		contentCategoryMapper.insert(contentCategory);
		//更新父结点状态
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(long id) {
		//1.查询得到待删除节点及其父节点
		TbContentCategory deletedItem = contentCategoryMapper.selectByPrimaryKey(id);
		long parentId = deletedItem.getParentId();
		TbContentCategory deletedItemParent = contentCategoryMapper.selectByPrimaryKey(parentId);
		//2.若待删除节点有子节点，则级联删除其所有子节点
		if(deletedItem.getIsParent()){
			TbContentCategoryExample children = new TbContentCategoryExample();
			Criteria criteria = children.createCriteria();
			criteria.andParentIdEqualTo(id);
			contentCategoryMapper.deleteByExample(children );
		}
		//3.删除目标节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		//4.获得被删除节点的兄弟节点
		TbContentCategoryExample brother = new TbContentCategoryExample();
		brother.createCriteria().andParentIdEqualTo(parentId);
		List<TbContentCategory> brotherList = contentCategoryMapper.selectByExample(brother );
		//5.若无兄弟节点，修改其父结点状态位
		if(brotherList.size() == 0){
			deletedItemParent.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(deletedItemParent);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult renameContentCategory(long id, String name) {
		TbContentCategory item = contentCategoryMapper.selectByPrimaryKey(id);
		item.setName(name);
		contentCategoryMapper.updateByPrimaryKey(item);
		return TaotaoResult.ok();
	}

}
