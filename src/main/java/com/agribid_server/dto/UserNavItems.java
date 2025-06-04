package com.agribid_server.dto;

public class UserNavItems {

	private String itemName;
	private String itemIcon;
	private String itemUsage;
	private String path;
	private String userType;
	private int position;

	public UserNavItems(String itemName, String itemIcon, String itemUsage, String userTypee, String path, int position) {
		super();
		this.itemName = itemName;
		this.itemIcon = itemIcon;
		this.itemUsage = itemUsage;
		this.userType = userTypee;
		this.path = path;
		this.position = position;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(String itemIcon) {
		this.itemIcon = itemIcon;
	}

	public String getItemUsage() {
		return itemUsage;
	}

	public void setItemUsage(String itemUsage) {
		this.itemUsage = itemUsage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
