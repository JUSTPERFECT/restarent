package com.org.restaurant.service;

import java.util.List;

import com.org.restaurant.dao.PaymentDAO;
import com.org.restaurant.model.PaymentCategory;
import com.org.restaurant.model.PaymentInfo;
import com.org.restaurant.model.PaymentType;

public class PaymentService {

	public List<PaymentInfo> getAllPaymentInfos(String date7, String date8) {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getAllPaymentInfos(date7, date8);
	}

	public void addPaymentInfo(PaymentInfo paymentInfo) {
		PaymentDAO paymentDao = new PaymentDAO();
		paymentDao.addPaymentInfo(paymentInfo);
	}

	public List<PaymentType> getAllPaymentTypes() {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getAllPaymentTypes();
	}

	public List<PaymentType> getCreditAmountBasedOnPaymentType(String date5, String date6) {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getCreditAmountBasedOnPaymentType(date5,date6);
	}

	public List<PaymentCategory> getAllPaymentCategories() {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getAllPaymentCategories();
	}

	public List<PaymentType> getDebitAmountBasedOnPaymentType(String date9, String date10) {
		PaymentDAO paymentDao = new PaymentDAO();
		return paymentDao.getDebitAmountBasedOnPaymentType(date9,date10);
	}

}
