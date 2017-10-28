package com.org.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.org.restaurant.model.Item;
import com.org.restaurant.model.ItemOrder;
import com.org.restaurant.model.Material;
import com.org.restaurant.model.MaterialOrder;
import com.org.restaurant.model.OrderItem;
import com.org.restaurant.model.OrderMaterial;
import com.org.restaurant.model.PaymentType;
import com.org.restaurant.util.DBUtil;
public class OrderDAO {
	static Connection conn = DBUtil.getConnection();
	
	public boolean placeItemOrder(ItemOrder order){
		PreparedStatement preparedStatement = null;
		String query2 = "INSERT INTO item_order(order_date,total_amount) VALUES(?,?)";
		int orderId;
		try {
			preparedStatement = conn.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			
			preparedStatement.setFloat(2,order.getTotalAmount());
			preparedStatement.executeUpdate();
			
			ResultSet rs;
			rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                orderId = rs.getInt(1);
                order.setOrderId(orderId);
            }
			
			addItems(order);
			
			addItemPayments(order);
			
			BalanceDAO balanceDao = new BalanceDAO();
			balanceDao.updateCreditAmount(order.getTotalAmount(), order.getOrderDate());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean placeMaterialOrder(MaterialOrder order){
		PreparedStatement preparedStatement = null;
		String query2 = "INSERT INTO material_order(order_date,total_amount) VALUES(?,?)";
		int orderId;
		try {
			preparedStatement = conn.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setFloat(2,order.getTotalAmount());
			preparedStatement.executeUpdate();
			
			ResultSet rs;
			rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                orderId = rs.getInt(1);
                order.setOrderId(orderId);
            }
			
			addMaterials(order);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	private void addItems(ItemOrder order) {
		List<OrderItem> itemQuanList = order.getOrderedItems();
		for(int i=0;i<itemQuanList.size();i++){
			OrderItem itemQuantity = itemQuanList.get(i);
			PreparedStatement preparedStatement = null;
			String query2 = "INSERT INTO order_item(order_id,item_id,item_quantity) VALUES(?,?,?)";
			try {
				preparedStatement = conn.prepareStatement(query2);
				preparedStatement.setInt(1,order.getOrderId());
				preparedStatement.setInt(2,itemQuantity.getItem().getItemId());
				preparedStatement.setInt(3,itemQuantity.getQuantity());
				preparedStatement.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
			MaterialDAO mtDao = new MaterialDAO();
			List<Material> matList = mtDao.getMaterials(itemQuantity.getItem().getItemId());
			for(int j=0;j<matList.size();j++){
				Material mat = matList.get(j);
				String query = "update material set quantity= ? where material_id=?";
				
				try {
					PreparedStatement preparedStatement1 = conn.prepareStatement(query);
					float itemMaterialQuan = getItemMaterialQuan(itemQuantity.getItem().getItemId(),mat.getMaterialId());
					preparedStatement1.setFloat(1, mat.getQuantity()-itemMaterialQuan*itemQuantity.getQuantity());
					preparedStatement1.setInt(2, mat.getMaterialId());
					preparedStatement1.executeUpdate();
					preparedStatement1.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void addItemPayments(ItemOrder order) {
		List<PaymentType> paymentTypeList = order.getPaymentTypes();
		for(int i=0;i<paymentTypeList.size();i++){
			PaymentType paymentType = paymentTypeList.get(i);
			PreparedStatement preparedStatement = null;
			String query2 = "INSERT INTO item_payment(order_id,payment_type_id,amount) VALUES(?,?,?)";
			try {
				preparedStatement = conn.prepareStatement(query2);
				preparedStatement.setInt(1,order.getOrderId());
				preparedStatement.setInt(2,paymentType.getPaymentTypeId());
				preparedStatement.setFloat(3,paymentType.getAmount());
				preparedStatement.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String query = "update payment_type set amount= amount + ? where payment_type_id=?";
			
			try {
				PreparedStatement preparedStatement1 = conn.prepareStatement(query);
				preparedStatement1.setFloat(1, paymentType.getAmount());
				preparedStatement1.setInt(2, paymentType.getPaymentTypeId());
				preparedStatement1.executeUpdate();
				preparedStatement1.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addMaterials(MaterialOrder order) {
		List<OrderMaterial> materialQuanList = order.getOrderedMaterials();
		for(int i=0;i<materialQuanList.size();i++){
			OrderMaterial materialQuantity = materialQuanList.get(i);
			PreparedStatement preparedStatement = null;
			String query2 = "INSERT INTO order_material(order_id,material_id,material_quantity) VALUES(?,?,?)";
			try {
				preparedStatement = conn.prepareStatement(query2);
				preparedStatement.setInt(1,order.getOrderId());
				preparedStatement.setInt(2,materialQuantity.getMaterial().getMaterialId());
				preparedStatement.setInt(3,materialQuantity.getQuantity());
				preparedStatement.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public float getItemMaterialQuan(int itemId, int materialId){
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select quantity from item_material where active=1 and item_id="+itemId+" and material_id="+materialId);
			if(rs.next()){
				return rs.getFloat("quantity");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public List<ItemOrder> getAllItemOrders(String date3, String date4) {
		List<ItemOrder> orderList = new ArrayList<ItemOrder>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from item_order where active=1 and date(order_date)>='"+date3+"' and date(order_date)<='"+date4+"' order by order_date desc");
			while(rs.next()){
				ItemOrder order = new ItemOrder();
				List<OrderItem> orderItemList = getOrderItemList(rs.getInt("order_id"));
				List<PaymentType> paymentTypeList = getPaymentTypeList(rs.getInt("order_id"));
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getString("order_date"));
				order.setTotalAmount(rs.getFloat("total_amount"));
				order.setPaymentTypes(paymentTypeList);
				order.setOrderedItems(orderItemList);
				order.setActive(1);
				orderList.add(order);
			}
			return orderList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private List<PaymentType> getPaymentTypeList(int id) {
		List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from item_payment where active=1 and order_id="+id);
			while(rs.next()){
				PaymentDAO paymentDAO = new PaymentDAO();
				PaymentType paymentType = paymentDAO.getPaymentType(rs.getInt("payment_type_id"));
				paymentType.setAmount(rs.getFloat("amount"));
				paymentTypeList.add(paymentType);
			}
			return paymentTypeList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<MaterialOrder> getAllMaterialOrders() {
		List<MaterialOrder> orderList = new ArrayList<MaterialOrder>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from material_order where active=1 order by order_date desc ");
			while(rs.next()){
				MaterialOrder order = new MaterialOrder();
				List<OrderMaterial> orderMaterialList = getOrderMaterialList(rs.getInt("order_id"));
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getString("order_date"));
				order.setTotalAmount(rs.getFloat("total_amount"));
				order.setOrderedMaterials(orderMaterialList);
				order.setActive(1);
				orderList.add(order);
			}
			return orderList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private List<OrderItem> getOrderItemList(int id) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from order_item where active=1 and order_id="+id);
			while(rs.next()){
				OrderItem orderItem = new OrderItem();
				ItemDAO itemDao = new ItemDAO();
				Item item = itemDao.getItem(rs.getInt("item_id"));
				orderItem.setItem(item);
				orderItem.setQuantity(rs.getInt("item_quantity"));
				orderItemList.add(orderItem);
			}
			return orderItemList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private List<OrderMaterial> getOrderMaterialList(int id) {
		List<OrderMaterial> orderMaterialList = new ArrayList<OrderMaterial>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from order_material where active=1 and order_id="+id);
			while(rs.next()){
				OrderMaterial orderMaterial = new OrderMaterial();
				MaterialDAO materialDao = new MaterialDAO();
				Material material = materialDao.getMaterial(rs.getInt("material_id"));
				orderMaterial.setMaterial(material);
				orderMaterial.setQuantity(rs.getInt("material_quantity"));
				orderMaterialList.add(orderMaterial);
			}
			return orderMaterialList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}	
}