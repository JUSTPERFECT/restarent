package com.org.restaurant.service;

import java.util.List;

import com.org.restaurant.dao.ItemDAO;
import com.org.restaurant.model.Item;

public class ItemService {
	public static void main(String args[]){
		ItemDAO itemDao = new ItemDAO();
		List<Item> itemList = itemDao.getAllItems();
		System.out.println("ItemList:"+itemList);
	}
	public List<Item> getAllItems(){
		ItemDAO itemDao = new ItemDAO();
		List<Item> itemList = itemDao.getAllItems();
		return itemList;
	}
}
