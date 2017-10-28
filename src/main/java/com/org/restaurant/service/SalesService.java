package com.org.restaurant.service;

import java.util.List;

import com.org.restaurant.model.BalanceSummary;
import com.org.restaurant.dao.BalanceDAO;
import com.org.restaurant.dao.ItemDAO;
import com.org.restaurant.model.Item;
import com.org.restaurant.model.OrderItem;
import com.org.restaurant.model.OrderSubcategory;
import com.org.restaurant.model.Sales;

public class SalesService {
	public static void main(String args[]){
		ItemDAO itemDao = new ItemDAO();
		List<Item> itemList = itemDao.getAllItems();
		System.out.println("ItemList:"+itemList);
	}
	
	public Sales getAllSales(String date1, String date2) {
		ItemDAO orderDao = new ItemDAO();
		List<OrderItem> orderedItems = orderDao.getItemsBasedOnCustomDate(date1,date2);
		List<OrderSubcategory> orderedSubcategories = orderDao.getSubcategoriesBasedOnCustomDate(date1,date2);
		Sales sales = new Sales();
		sales.setItems(orderedItems);
		sales.setSubcategories(orderedSubcategories);
		return sales;
	}

	public BalanceSummary getBalanceSummary(String date) {
		BalanceDAO balanceDao = new BalanceDAO();
		return balanceDao.getBalanceSummary(date);
	}

	public void addDayCloseInfo(BalanceSummary balanceSummary1) {
		BalanceDAO balanceDao = new BalanceDAO();
		balanceDao.addDayCloseInfo(balanceSummary1);
	}

	public float getCreditAmount(String date1) {
		// TODO Auto-generated method stub
		return 0;
	}
}
