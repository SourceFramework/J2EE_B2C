package com.taotao.common.pojo;

/**
 * 满足EasyUI的tree控件数据格式的Pojo 
 * @author mbc1996
 *
 */
public class EasyUITreeNode {
	private long id;
	private String text;
	private String state;  //是否父结点,若为父节点，显示closed，否则为open
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
