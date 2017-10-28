package com.org.restaurant.model;

import java.util.List;

public class Sales {
	List<OrderItem> items;
	List<OrderSubcategory> subcategories;
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public List<OrderSubcategory> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<OrderSubcategory> subcategories) {
		this.subcategories = subcategories;
	}
	@Override
	public String toString() {
		return "Sales [items=" + items + ", subcategories=" + subcategories + "]";
	}
}