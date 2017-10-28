package com.org.restaurant.model;

public class OrderSubcategory {
	ItemSubcategory subCategory;
	int quantity;
	int totalAmount;
	public ItemSubcategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(ItemSubcategory subCategory) {
		this.subCategory = subCategory;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "OrderSubcategory [subCategory=" + subCategory + ", quantity=" + quantity + "]";
	}
}