package com.org.restaurant.model;

import java.util.List;

public class MaterialOrder {
	int orderId;
	List<OrderMaterial> orderedMaterials;
	String orderDate;
	float totalAmount;
	int active;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public List<OrderMaterial> getOrderedMaterials() {
		return orderedMaterials;
	}
	public void setOrderedMaterials(List<OrderMaterial> orderedMaterials) {
		this.orderedMaterials = orderedMaterials;
	}
	@Override
	public String toString() {
		return "MaterialOrder [orderId=" + orderId + ", orderedMaterials=" + orderedMaterials + ", orderDate="
				+ orderDate + ", totalAmount=" + totalAmount + ", active=" + active + "]";
	}
}