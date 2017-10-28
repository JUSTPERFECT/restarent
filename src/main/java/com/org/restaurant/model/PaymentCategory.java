package com.org.restaurant.model;

public class PaymentCategory {
	int paymentCategoryId;
	String paymentCategoryName;
	int active;
	public int getPaymentCategoryId() {
		return paymentCategoryId;
	}
	public void setPaymentCategoryId(int paymentCategoryId) {
		this.paymentCategoryId = paymentCategoryId;
	}
	public String getPaymentCategoryName() {
		return paymentCategoryName;
	}
	public void setPaymentCategoryName(String paymentCategoryName) {
		this.paymentCategoryName = paymentCategoryName;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}