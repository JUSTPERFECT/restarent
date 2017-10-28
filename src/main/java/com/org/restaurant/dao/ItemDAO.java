package com.org.restaurant.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.org.restaurant.model.ItemCategory;
import com.org.restaurant.model.Item;
import com.org.restaurant.model.ItemSubcategory;
import com.org.restaurant.model.OrderItem;
import com.org.restaurant.model.OrderSubcategory;
import com.org.restaurant.util.DBUtil;

public class ItemDAO {
	static Connection conn = DBUtil.getConnection();
	public List<Item> getAllItems(){
		List<Item> itemList = new ArrayList<Item>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from item where active=1");
			while(rs.next()){
				Item item = new Item();
				ItemSubcategory subcategory = getSubcategory(rs.getInt("subcategory_id"));
				item.setItemId(rs.getInt("item_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDescription(rs.getString("item_description"));
				item.setSubcategory(subcategory);
				item.setPrice(rs.getFloat("price"));
				item.setActive(1);
				itemList.add(item);
			}
			return itemList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public ItemSubcategory getSubcategory(int id){
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from item_subcategory where active=1 and subcategory_id="+id);
			if(rs.next()){
				ItemSubcategory subcategory = new ItemSubcategory();
				subcategory.setSubcategoryId(rs.getInt("subcategory_id"));
				ItemCategory category = getCategory(rs.getInt("category_id"));
				subcategory.setSubcategoryName(rs.getString("subcategory_name"));
				subcategory.setSubcategoryDescription(rs.getString("subcategory_description"));
				subcategory.setCategory(category);
				subcategory.setActive(1);
				return subcategory;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public ItemCategory getCategory(int id){
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from item_category where active=1 and category_id="+id);
			if(rs.next()){
				ItemCategory category = new ItemCategory();
				category.setCategoryId(rs.getInt("category_id"));
				category.setCategoryName(rs.getString("category_name"));
				category.setCategoryDescription(rs.getString("category_description"));
				category.setActive(1);
				return category;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Item getItem(int id) {
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from item where active=1 and item_id="+id);
			if(rs.next()){
				Item item = new Item();
				item.setItemId(rs.getInt("item_id"));
				item.setItemName(rs.getString("item_name"));
				item.setItemDescription(rs.getString("item_description"));
				item.setPrice(rs.getFloat("price"));
				item.setActive(1);
				return item;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<OrderItem> getItemsBasedOnCustomDate(String date1, String date2){
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT item_id,sum(item_quantity) as item_count FROM item_order ior left join order_item oit on ior.order_id=oit.order_id where date(order_date)>='"+date1+"' and date(order_date)<='"+date2+"' group by item_id");
			while(rs.next()){
				OrderItem orderItem = new OrderItem();
				Item item = getItem(rs.getInt("item_id"));
				orderItem.setItem(item);
				orderItem.setQuantity(rs.getInt("item_count"));
				
				orderItemList.add(orderItem);
			}
			return orderItemList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<OrderSubcategory> getSubcategoriesBasedOnCustomDate(String date1, String date2){
		List<OrderSubcategory> orderSubcategoryList = new ArrayList<OrderSubcategory>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT isu.subcategory_id,sum(item_quantity) as subcategory_count,sum(it.price*item_quantity) as total FROM item_order ior left join order_item oit on ior.order_id=oit.order_id left join item it on it.item_id=oit.item_id left join item_subcategory isu on it.subcategory_id=isu.subcategory_id where date(order_date)>='"+date1+"' and date(order_date)<='"+date2+"' group by isu.subcategory_id;");
			while(rs.next()){
				OrderSubcategory orderSubcategory = new OrderSubcategory();
				ItemSubcategory subcategory = getSubcategory(rs.getInt("subcategory_id"));
				orderSubcategory.setSubCategory(subcategory);
				orderSubcategory.setQuantity(rs.getInt("subcategory_count"));
				orderSubcategory.setTotalAmount(rs.getInt("total"));
				orderSubcategoryList.add(orderSubcategory);
			}
			return orderSubcategoryList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}