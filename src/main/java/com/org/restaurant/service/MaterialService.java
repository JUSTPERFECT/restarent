package com.org.restaurant.service;

import java.util.List;

import com.org.restaurant.dao.MaterialDAO;
import com.org.restaurant.model.Material;
import com.org.restaurant.model.MaterialCategory;

public class MaterialService {

	public List<Material> getAllMaterials() {
		MaterialDAO materialDao = new MaterialDAO();
		return materialDao.getAllMaterials();
	}

	public List<MaterialCategory> getAllMaterialCategories() {
		MaterialDAO materialDao = new MaterialDAO();
		return materialDao.getAllMaterialCategories();
	}

}
