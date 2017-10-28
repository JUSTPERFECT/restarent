package com.org.restaurant.model;

public class Item {
	int itemId;
	String itemName;
	String itemDescription;
	ItemSubcategory subcategory;
	float price;
	int active;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public ItemSubcategory getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(ItemSubcategory subcategory) {
		this.subcategory = subcategory;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName
				+ ", itemDescription=" + itemDescription + ", subcategory="
				+ subcategory + ", price=" + price + ", active=" + active + "]";
	}
}