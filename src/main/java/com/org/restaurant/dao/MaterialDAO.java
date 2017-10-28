package com.org.restaurant.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.org.restaurant.model.Material;
import com.org.restaurant.model.MaterialCategory;
import com.org.restaurant.model.QuantityType;
import com.org.restaurant.util.DBUtil;

public class MaterialDAO {
	static Connection conn = DBUtil.getConnection();
	public List<Material> getAllMaterials(){
		List<Material> materialList = new ArrayList<Material>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from material where active=1");
			while(rs.next()){
				Material material = new Material();
				QuantityType quantityType = getQuantityType(rs.getInt("quantity_type_id"));
				MaterialCategory materialCategory = getMaterialCategory(rs.getInt("material_category_id"));
				material.setMaterialId(rs.getInt("material_id"));
				material.setMaterialName(rs.getString("material_name"));
				material.setMaterialDesc(rs.getString("material_description"));
				material.setQuantityType(quantityType);
				material.setQuantity(rs.getFloat("quantity"));
				material.setPackCost(rs.getFloat("pack_cost"));
				material.setMaterialCategory(materialCategory);
				material.setActive(1);
				materialList.add(material);
			}
			return materialList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Material getMaterial(int materialId){
		Material material = null;
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from material where active=1 and material_id="+materialId);
			if(rs.next()){
				material = new Material();
				QuantityType quantityType = getQuantityType(rs.getInt("quantity_type_id"));
				material.setMaterialId(rs.getInt("material_id"));
				material.setMaterialName(rs.getString("material_name"));
				material.setMaterialDesc(rs.getString("material_description"));
				material.setQuantityType(quantityType);
				material.setQuantity(rs.getFloat("quantity"));
				material.setPackCost(rs.getFloat("pack_cost"));
				material.setActive(1);
			}
			return material;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public QuantityType getQuantityType(int id){
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from quantity_type where active=1 and quantity_type_id="+id);
			if(rs.next()){
				QuantityType quantityType = new QuantityType();
				quantityType.setQuantityTypeId(rs.getInt("quantity_type_id"));
				quantityType.setQuantityTypeName(rs.getString("quantity_type_name"));
				quantityType.setActive(1);
				return quantityType;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public MaterialCategory getMaterialCategory(int id){
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from material_category where active=1 and material_category_id="+id);
			if(rs.next()){
				MaterialCategory materialCategory = new MaterialCategory();
				materialCategory.setMaterialCategoryId(rs.getInt("material_category_id"));
				materialCategory.setMaterialCategoryName(rs.getString("material_category_name"));
				materialCategory.setActive(1);
				return materialCategory;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Material> getMaterials(int itemId){
		List<Material> materialList = new ArrayList<Material>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from item_material where active=1 and item_id="+itemId);
			while(rs.next()){
				Material material = getMaterial(rs.getInt("material_id"));
				materialList.add(material);
			}
			return materialList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<MaterialCategory> getAllMaterialCategories() {
		List<MaterialCategory> materialCategoryList = new ArrayList<MaterialCategory>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from material_category where active=1");
			while(rs.next()){
				MaterialCategory material = new MaterialCategory();
				material.setMaterialCategoryId(rs.getInt("material_category_id"));
				material.setMaterialCategoryName(rs.getString("material_category_name"));
				material.setActive(1);
				materialCategoryList.add(material);
			}
			return materialCategoryList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}