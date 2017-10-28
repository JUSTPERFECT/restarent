package com.org.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.org.restaurant.model.MaterialCategory;
import com.org.restaurant.model.PaymentCategory;
import com.org.restaurant.model.PaymentInfo;
import com.org.restaurant.model.PaymentType;
import com.org.restaurant.model.User;
import com.org.restaurant.util.DBUtil;

public class PaymentDAO {
	static Connection conn = DBUtil.getConnection();
	public List<PaymentInfo> getAllPaymentInfos(String date7, String date8) {
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from payment_info where active=1 and date(payment_date)>='"+date7+"' and date(payment_date)<='"+date8+"' order by payment_date desc ");
			while(rs.next()){
				PaymentInfo paymentInfo = new PaymentInfo();
				
				UserDAO userDao = new UserDAO();
				User user = userDao.getUser(rs.getInt("user_id"));
				
				MaterialDAO materialDao = new MaterialDAO();
				MaterialCategory materialCategory = materialDao.getMaterialCategory(rs.getInt("material_category_id"));
				PaymentType paymentType = getPaymentType(rs.getInt("payment_type_id"));
				PaymentCategory paymentCategory = getPaymentCategory(rs.getInt("payment_category_id"));
				
				paymentInfo.setPaymentInfoId(rs.getInt("payment_info_id"));
				paymentInfo.setPaymentDate(rs.getString("payment_date"));
				paymentInfo.setPaymentAmount(rs.getFloat("payment_amount"));
				paymentInfo.setPaymentBill(rs.getString("payment_bill"));
				paymentInfo.setUser(user);
				paymentInfo.setPaymentType(paymentType);
				paymentInfo.setPaymentCategory(paymentCategory);
				paymentInfo.setMaterialCategory(materialCategory);
				paymentInfo.setActive(1);
				paymentInfoList.add(paymentInfo);
			}
			return paymentInfoList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private PaymentCategory getPaymentCategory(int id) {
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from payment_category where active=1 and payment_category_id="+id);
			if(rs.next()){
				PaymentCategory paymentType = new PaymentCategory();
				paymentType.setPaymentCategoryId(rs.getInt("payment_category_id"));
				paymentType.setPaymentCategoryName(rs.getString("payment_category_name"));
				paymentType.setActive(1);
				return paymentType;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public PaymentType getPaymentType(int id) {
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from payment_type where active=1 and payment_type_id="+id);
			if(rs.next()){
				PaymentType paymentType = new PaymentType();
				paymentType.setPaymentTypeId(rs.getInt("payment_type_id"));
				paymentType.setPaymentTypeName(rs.getString("payment_type_name"));
				paymentType.setActive(1);
				return paymentType;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PaymentType> getCreditAmountBasedOnPaymentType(String date5, String date6){
		List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT payment_type_id,sum(amount) as credit FROM item_order ior left join item_payment ip on ior.order_id=ip.order_id where ior.active=1 and date(order_date)>='"+date5+"' and date(order_date)<='"+date6+"' group by payment_type_id");
			while(rs.next()){
				PaymentType paymentType = getPaymentType(rs.getInt("payment_type_id"));
				paymentType.setAmount(rs.getFloat("credit"));
				paymentTypeList.add(paymentType);
			}
			return paymentTypeList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PaymentType> getAllPaymentTypes() {
		List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from payment_type where active=1");
			while(rs.next()){
				PaymentType paymentType = new PaymentType();
				paymentType.setPaymentTypeId(rs.getInt("payment_type_id"));
				paymentType.setPaymentTypeName(rs.getString("payment_type_name"));
				paymentType.setAmount(rs.getFloat("amount"));
				paymentType.setActive(1);
				paymentTypeList.add(paymentType);
			}
			return paymentTypeList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PaymentCategory> getAllPaymentCategories() {
		List<PaymentCategory> paymentTypeList = new ArrayList<PaymentCategory>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from payment_category where active=1");
			while(rs.next()){
				PaymentCategory paymentType = new PaymentCategory();
				paymentType.setPaymentCategoryId(rs.getInt("payment_category_id"));
				paymentType.setPaymentCategoryName(rs.getString("payment_category_name"));
				paymentType.setActive(1);
				paymentTypeList.add(paymentType);
			}
			return paymentTypeList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateRemainingBalance(int paymentTypeId, float amount){
		String query = "update payment_type set amount= amount - ? where payment_type_id=?";
		
		try {
			PreparedStatement preparedStatement1 = conn.prepareStatement(query);
			preparedStatement1.setFloat(1, amount);
			preparedStatement1.setInt(2, paymentTypeId);
			preparedStatement1.executeUpdate();
			preparedStatement1.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addPaymentInfo(PaymentInfo paymentInfo) {
		PreparedStatement preparedStatement = null;
		String query2 = "INSERT INTO payment_info(user_id,material_category_id,payment_type_id,payment_category_id,payment_amount,payment_bill,payment_date) VALUES(?,?,?,?,?,?,?)";
		try {
			preparedStatement = conn.prepareStatement(query2);
			preparedStatement.setInt(1,paymentInfo.getUser().getUserId());
			preparedStatement.setInt(2,paymentInfo.getMaterialCategory().getMaterialCategoryId());
			preparedStatement.setInt(3,paymentInfo.getPaymentType().getPaymentTypeId());
			preparedStatement.setInt(4,paymentInfo.getPaymentCategory().getPaymentCategoryId());
			preparedStatement.setFloat(5,paymentInfo.getPaymentAmount());
			preparedStatement.setString(6,paymentInfo.getPaymentBill());
			preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatement.executeUpdate();
			
			updateRemainingBalance(paymentInfo.getPaymentType().getPaymentTypeId(),paymentInfo.getPaymentAmount());
			
			BalanceDAO balanceDao = new BalanceDAO();
			balanceDao.updateDebitAmount(paymentInfo.getPaymentAmount(), paymentInfo.getPaymentDate());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<PaymentType> getDebitAmountBasedOnPaymentType(String date9, String date10) {
		List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT payment_type_id,sum(payment_amount) as debit FROM payment_info where active=1 and date(payment_date)>='"+date9+"' and date(payment_date)<='"+date10+"' group by payment_type_id");
			while(rs.next()){
				PaymentType paymentType = getPaymentType(rs.getInt("payment_type_id"));
				paymentType.setAmount(rs.getFloat("debit"));
				paymentTypeList.add(paymentType);
			}
			return paymentTypeList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
