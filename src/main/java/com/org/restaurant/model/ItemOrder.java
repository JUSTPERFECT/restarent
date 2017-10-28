package com.org.restaurant.model;

import java.util.List;

public class ItemOrder {
	int orderId;
	List<OrderItem> orderedItems;
	String orderDate;
	List<PaymentType> paymentTypes;
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
	public List<OrderItem> getOrderedItems() {
		return orderedItems;
	}
	public void setOrderedItems(List<OrderItem> itemquan) {
		this.orderedItems = itemquan;
	}
	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}
	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
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
	@Override
	public String toString() {
		return "ItemOrder [orderId=" + orderId + ", orderedItems=" + orderedItems + ", orderDate=" + orderDate
				+ ", paymentTypes=" + paymentTypes + ", totalAmount=" + totalAmount + ", active=" + active + "]";
	}
	
}