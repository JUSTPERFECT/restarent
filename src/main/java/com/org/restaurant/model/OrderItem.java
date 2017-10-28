package com.org.restaurant.model;

public class OrderItem {
	Item item;
	int quantity;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "ItemQuantity [item=" + item + ", quantity=" + quantity + "]";
	}
}
