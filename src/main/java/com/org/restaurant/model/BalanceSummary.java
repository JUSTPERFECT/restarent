package com.org.restaurant.model;

import java.util.List;

public class BalanceSummary {
	int balanceSummaryId;
	String date;
	float counterCash;
	float creditAmount;
	float debitAmount;
	List<PaymentType> paymentTypes;
	float discurbance;
	int active;
	public int getBalanceSummaryId() {
		return balanceSummaryId;
	}
	public void setBalanceSummaryId(int balanceSummaryId) {
		this.balanceSummaryId = balanceSummaryId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getCounterCash() {
		return counterCash;
	}
	public void setCounterCash(float counterCash) {
		this.counterCash = counterCash;
	}
	public float getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(float creditAmount) {
		this.creditAmount = creditAmount;
	}
	public float getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(float debitAmount) {
		this.debitAmount = debitAmount;
	}
	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}
	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
	public float getDiscurbance() {
		return discurbance;
	}
	public void setDiscurbance(float discurbance) {
		this.discurbance = discurbance;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "BalanceSummary [balanceSummaryId=" + balanceSummaryId + ", date=" + date + ", counterCash="
				+ counterCash + ", creditAmount=" + creditAmount + ", debitAmount=" + debitAmount + ", discurbance="
				+ discurbance + ", active=" + active + "]";
	}
}