package com.org.restaurant.model;

public class PaymentInfo {
	int paymentInfoId;
	User user;
	MaterialCategory materialCategory;
	PaymentType paymentType;
	PaymentCategory paymentCategory;
	float paymentAmount;
	String paymentBill;
	String paymentDate;
	int active;
	public int getPaymentInfoId() {
		return paymentInfoId;
	}
	public void setPaymentInfoId(int paymentInfoId) {
		this.paymentInfoId = paymentInfoId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public MaterialCategory getMaterialCategory() {
		return materialCategory;
	}
	public void setMaterialCategory(MaterialCategory materialCategory) {
		this.materialCategory = materialCategory;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public PaymentCategory getPaymentCategory() {
		return paymentCategory;
	}
	public void setPaymentCategory(PaymentCategory paymentCategory) {
		this.paymentCategory = paymentCategory;
	}
	public float getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentBill() {
		return paymentBill;
	}
	public void setPaymentBill(String paymentBill) {
		this.paymentBill = paymentBill;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}