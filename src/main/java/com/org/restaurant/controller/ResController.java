package com.org.restaurant.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.restaurant.model.BalanceSummary;
import com.org.restaurant.model.Item;
import com.org.restaurant.model.ItemOrder;
import com.org.restaurant.model.Material;
import com.org.restaurant.model.MaterialCategory;
import com.org.restaurant.model.MaterialOrder;
import com.org.restaurant.model.PaymentCategory;
import com.org.restaurant.model.PaymentInfo;
import com.org.restaurant.model.PaymentType;
import com.org.restaurant.model.Sales;
import com.org.restaurant.model.User;
import com.org.restaurant.service.ItemService;
import com.org.restaurant.service.LoginService;
import com.org.restaurant.service.MaterialService;
import com.org.restaurant.service.OrderService;
import com.org.restaurant.service.PaymentService;
import com.org.restaurant.service.SalesService;

@WebServlet("/Res")

public class ResController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ItemService is = new ItemService();
	OrderService os = new OrderService();
	MaterialService ms = new MaterialService();
	SalesService ss = new SalesService();
	PaymentService ps = new PaymentService();
	LoginService ls = new LoginService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		String output = "";

		switch (action) {
		
			case "login":
				String userName = req.getParameter("userName");
				String password = req.getParameter("password");
				User user = ls.login(userName,password);
				if(user != null){
					output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(user) + "}";
				}
				else{
					output = output + "{\"status\":\"error\",\"result\":\"Invalid credentials\"}";
				}
				res.getWriter().write(output);
				break;
			
			case "logout":
				output = output + "{\"status\":\"success\",\"result\":\"Logout successfully\"}";
				res.getWriter().write(output);
				break;
		
			case "getAllItems":
				List<Item> itemList = is.getAllItems();
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(itemList) + "}";
				res.getWriter().write(output);
				break;
				
			case "getAllPaymentTypes":
				List<PaymentType> paymentTypeList = ps.getAllPaymentTypes();
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(paymentTypeList) + "}";
				res.getWriter().write(output);
				break;
				
			case "getAllPaymentCategories":
				List<PaymentCategory> paymentCategoryList = ps.getAllPaymentCategories();
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(paymentCategoryList) + "}";
				res.getWriter().write(output);
				break;
				
			case "getCreditAmountBasedOnPaymentType":
				String date5= req.getParameter("date1");
				String date6= req.getParameter("date2");
				List<PaymentType> paymentTypeList1 = ps.getCreditAmountBasedOnPaymentType(date5,date6);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(paymentTypeList1) + "}";
				res.getWriter().write(output);
				break;
				
			case "getDebitAmountBasedOnPaymentType":
				String date9= req.getParameter("date1");
				String date10= req.getParameter("date2");
				List<PaymentType> paymentTypeList3 = ps.getDebitAmountBasedOnPaymentType(date9,date10);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(paymentTypeList3) + "}";
				res.getWriter().write(output);
				break;
				
			case "getBalanceInfo":
				String date = req.getParameter("date");
				BalanceSummary balanceSummary = ss.getBalanceSummary(date);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(balanceSummary) + "}";
				res.getWriter().write(output);
				break;
				
			case "addDailyCloseInfo":
				StringBuilder sb5 = new StringBuilder();
				BufferedReader br5 = req.getReader();
				String str5 = null;
				while ((str5 = br5.readLine()) != null) {
					sb5.append(str5);
				}
				BalanceSummary balanceSummary1 = null;
				JSONObject name5;
				try {
					JSONObject jObj = new JSONObject(sb5.toString());
					name5 = jObj.getJSONObject("data");
	
					System.out.println("DailyClose"+ name5.toString());
	
					Gson gson = new GsonBuilder().create();
					balanceSummary1 = gson.fromJson(name5.toString(), BalanceSummary.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ss.addDayCloseInfo(balanceSummary1);
	
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write("Updated Daily close status");
				break;
				
			case "getAllMaterials":
				List<Material> materialList = ms.getAllMaterials();
				System.out.println("MaterialList:" + materialList);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(materialList) + "}";
				res.getWriter().write(output);
				break;
				
			case "placeItemOrder":
				StringBuilder sb = new StringBuilder();
				BufferedReader br = req.getReader();
				String str = null;
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
				ItemOrder itemOrder = null;
				JSONObject name;
				try {
					JSONObject jObj = new JSONObject(sb.toString());
					name = jObj.getJSONObject("data");
	
					Gson gson = new GsonBuilder().create();
					itemOrder = gson.fromJson(name.toString(), ItemOrder.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				os.placeItemOrder(itemOrder);
	
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write("Ordered successfully");
				break;
				
			case "placeMaterialOrder":
				StringBuilder sb1 = new StringBuilder();
				BufferedReader br1 = req.getReader();
				String str1 = null;
				while ((str1 = br1.readLine()) != null) {
					sb1.append(str1);
				}
				MaterialOrder materialOrder = null;
				JSONObject name1;
				try {
					JSONObject jObj = new JSONObject(sb1.toString());
					name1 = jObj.getJSONObject("data");
	
					String jsonText = name1.toString();
	
					System.out.println(jsonText);
	
					Gson gson = new GsonBuilder().create();
					materialOrder = gson.fromJson(name1.toString(), MaterialOrder.class);
					System.out.println("Order:" + materialOrder);
				} catch (Exception e) {
					e.printStackTrace();
				}
				os.placeMaterialOrder(materialOrder);
	
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write("Ordered successfully");
				break;
				
			case "getAllItemOrders":
				String date3= req.getParameter("date1");
				String date4= req.getParameter("date2");
				List<ItemOrder> itemOrderList = os.getAllItemOrders(date3,date4);
				System.out.println("OrderList:" + itemOrderList);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(itemOrderList) + "}";
				res.getWriter().write(output);
				break;
				
			case "getAllMaterialOrders":
				List<MaterialOrder> materialOrderList = os.getAllMaterialOrders();
				System.out.println("OrderList:" + materialOrderList);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(materialOrderList) + "}";
				res.getWriter().write(output);
				break;
				
			case "getAllSales":
				String date1= req.getParameter("date1");
				String date2= req.getParameter("date2");
				Sales sales = ss.getAllSales(date1,date2);
				System.out.println("sales:" + sales);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(sales) + "}";
				res.getWriter().write(output);
				break;
				
			case "getAllPaymentInfos":
				String date7= req.getParameter("date1");
				String date8= req.getParameter("date2");
				List<PaymentInfo> paymentInfoList = ps.getAllPaymentInfos(date7,date8);
				System.out.println("PaymentInfo list:" + paymentInfoList);
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(paymentInfoList) + "}";
				res.getWriter().write(output);
				break;
				
			case "addPaymentInfo":
				StringBuilder sb2 = new StringBuilder();
				BufferedReader br2 = req.getReader();
				String str2 = null;
				while ((str2 = br2.readLine()) != null) {
					sb2.append(str2);
				}
				PaymentInfo paymentInfo = null;
				JSONObject payment;
				try {
					JSONObject jObj = new JSONObject(sb2.toString());
					payment = jObj.getJSONObject("data");
	
					String jsonText = payment.toString();
	
					System.out.println(jsonText);
	
					Gson gson = new GsonBuilder().create();
					paymentInfo = gson.fromJson(payment.toString(), PaymentInfo.class);
					System.out.println("PaymentInfo:" + paymentInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ps.addPaymentInfo(paymentInfo);
	
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				res.getWriter().write("Add payment info successfully");
				break;
				
			case "getAllMaterialCategories":
				List<MaterialCategory> materialCategoryList = ms.getAllMaterialCategories();
				output = output + "{\"status\":\"success\",\"result\":" + new Gson().toJson(materialCategoryList) + "}";
				res.getWriter().write(output);
				break;
			}
	}
}