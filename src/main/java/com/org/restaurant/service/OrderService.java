package com.org.restaurant.service;

import java.util.List;

import com.org.restaurant.dao.OrderDAO;
import com.org.restaurant.model.ItemOrder;
import com.org.restaurant.model.MaterialOrder;
import com.org.restaurant.model.PaymentType;

public class OrderService {

	public boolean placeItemOrder(ItemOrder order) {
		OrderDAO orderDao = new OrderDAO();
		return orderDao.placeItemOrder(order);
	}
	
	public boolean placeMaterialOrder(MaterialOrder order) {
		OrderDAO orderDao = new OrderDAO();
		return orderDao.placeMaterialOrder(order);
	}

	public List<ItemOrder> getAllItemOrders(String date3, String date4) {
		OrderDAO orderDao = new OrderDAO();
		return orderDao.getAllItemOrders(date3, date4);
	}
	
	public List<MaterialOrder> getAllMaterialOrders() {
		OrderDAO orderDao = new OrderDAO();
		return orderDao.getAllMaterialOrders();
	}

}
