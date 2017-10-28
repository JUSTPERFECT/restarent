package com.org.restaurant.model;

public class Material {
	int materialId;
	String materialName;
	String materialDesc;
	MaterialCategory materialCategory;
	float quantity;
	QuantityType quantityType;
	float packCost;
	int active;
	public int getMaterialId() {
		return materialId;
	}
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public QuantityType getQuantityType() {
		return quantityType;
	}
	public void setQuantityType(QuantityType quantityType) {
		this.quantityType = quantityType;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public MaterialCategory getMaterialCategory() {
		return materialCategory;
	}
	public void setMaterialCategory(MaterialCategory materialCategory) {
		this.materialCategory = materialCategory;
	}
	public float getPackCost() {
		return packCost;
	}
	public void setPackCost(float packCost) {
		this.packCost = packCost;
	}
	@Override
	public String toString() {
		return "Material [materialId=" + materialId + ", materialName=" + materialName + ", materialDesc="
				+ materialDesc + ", materialCategory=" + materialCategory + ", quantity=" + quantity + ", quantityType="
				+ quantityType + ", packCost=" + packCost + ", active=" + active + "]";
	}
}
