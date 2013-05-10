package cn.edu.shiep.view;

public class MenuEntity {

	private int groupId;
	private int itemId;
	private int order;
	private String title;
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public MenuEntity(int groupId, int itemId, int order, String title) {
		super();
		this.groupId = groupId;
		this.itemId = itemId;
		this.order = order;
		this.title = title;
	}
	public MenuEntity() {
		super();
	}
	
	
}
