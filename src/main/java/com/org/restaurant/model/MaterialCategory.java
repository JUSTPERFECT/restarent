package com.org.restaurant.model;

public class MaterialCategory {
	int materialCategoryId;
	String materialCategoryName;
	int active;
	public int getMaterialCategoryId() {
		return materialCategoryId;
	}
	public void setMaterialCategoryId(int materialCategoryId) {
		this.materialCategoryId = materialCategoryId;
	}
	public String getMaterialCategoryName() {
		return materialCategoryName;
	}
	public void setMaterialCategoryName(String materialCategoryName) {
		this.materialCategoryName = materialCategoryName;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}