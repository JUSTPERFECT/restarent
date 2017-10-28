package com.org.restaurant.model;

public class OrderMaterial {
	Material material;
	int quantity;
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "OrderMaterial [material=" + material + ", quantity=" + quantity + "]";
	}
}