package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

/**
 * 内容分类（广告位）业务层
 * @author mbc1996
 *
 */
public interface ContentCategoryService {

	/**
	 * 根据父节点ID检索其孩子节点
	 * @param parentId
	 * @return
	 */
	public  List<EasyUITreeNode> getContentCategoryList(long parentId);
	
	/**
	 * 生成一个内容分类
	 * @param parentId
	 * @param name
	 * @return
	 */
	public TaotaoResult createContentCategory(long parentId,String name);
	/**
	 * 删除一个内容分类节点
	 * @param parentId
	 * @param id
	 * @return
	 */
	public TaotaoResult deleteContentCategory(long id);
	/**
	 * 重命名内容分类节点
	 * @param id
	 * @param name
	 * @return
	 */
	public TaotaoResult renameContentCategory(long id, String name);
}
