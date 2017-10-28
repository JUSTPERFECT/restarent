package com.org.restaurant.model;

public class QuantityType {
	int quantityTypeId;
	String quantityTypeName;
	int active;
	public int getQuantityTypeId() {
		return quantityTypeId;
	}
	public void setQuantityTypeId(int quantityTypeId) {
		this.quantityTypeId = quantityTypeId;
	}
	public String getQuantityTypeName() {
		return quantityTypeName;
	}
	public void setQuantityTypeName(String quantityTypeName) {
		this.quantityTypeName = quantityTypeName;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "QuantityType [quantityTypeId=" + quantityTypeId + ", quantityTypeName=" + quantityTypeName + ", active="
				+ active + "]";
	}
}
