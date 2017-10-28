package com.org.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.org.restaurant.model.BalanceSummary;
import com.org.restaurant.model.PaymentType;
import com.org.restaurant.util.DBUtil;

public class BalanceDAO {
	static Connection conn = DBUtil.getConnection();

	public BalanceSummary getBalanceSummary(String date) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from balance_summary where active=1 and date='"+ date+"'");
			if (rs.next()) {
				BalanceSummary balanceSummary = new BalanceSummary();
				balanceSummary.setBalanceSummaryId(rs.getInt("balance_summary_id"));
				balanceSummary.setCreditAmount(rs.getFloat("credit_amount"));
				balanceSummary.setDebitAmount(rs.getFloat("debit_amount"));
				balanceSummary.setDiscurbance(rs.getFloat("discurbance"));
				balanceSummary.setCounterCash(rs.getFloat("counter_cash"));
				balanceSummary.setActive(1);
				return balanceSummary;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addDayCloseInfo(BalanceSummary balanceSummary) {
		boolean check = checkBalanceSummary(balanceSummary.getDate());
		if (check) {
			String query = "update balance_summary set counter_cash = ?, discurbance = ? where date = ?";
			int balanceSummaryId;
			try {
				PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

				preparedStatement.setFloat(1, balanceSummary.getCounterCash());
				preparedStatement.setFloat(2, balanceSummary.getDiscurbance());
				preparedStatement.setString(3, balanceSummary.getDate());
				preparedStatement.executeUpdate();
				
				ResultSet rs;
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					balanceSummaryId = rs.getInt(1);
					balanceSummary.setBalanceSummaryId(balanceSummaryId);
				}
				addBalancePayments(balanceSummary);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			PreparedStatement preparedStatement = null;
			String query2 = "INSERT INTO balance_summary(date,counter_cash,discurbance) VALUES(?,?,?)";
			int balanceSummaryId;
			try {
				preparedStatement = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, balanceSummary.getDate());
				preparedStatement.setFloat(2, balanceSummary.getCounterCash());
				preparedStatement.setFloat(3, balanceSummary.getDiscurbance());
				preparedStatement.executeUpdate();

				ResultSet rs;
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					balanceSummaryId = rs.getInt(1);
					balanceSummary.setBalanceSummaryId(balanceSummaryId);
				}

				addBalancePayments(balanceSummary);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addBalancePayments(BalanceSummary balanceSummary) {
		List<PaymentType> paymentTypeList = balanceSummary.getPaymentTypes();
		for (int i = 0; i < paymentTypeList.size(); i++) {
			PaymentType paymentType = paymentTypeList.get(i);
			PreparedStatement preparedStatement = null;
			String query2 = "INSERT INTO balance_payment(balance_summary_id,payment_type_id,amount) VALUES(?,?,?)";
			try {
				preparedStatement = conn.prepareStatement(query2);
				preparedStatement.setInt(1, balanceSummary.getBalanceSummaryId());
				preparedStatement.setInt(2, paymentType.getPaymentTypeId());
				preparedStatement.setFloat(3, paymentType.getAmount());
				preparedStatement.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checkBalanceSummary(String date) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from balance_summary where active=1 and date='" + date+"'");
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void updateCreditAmount(float amount, String date) {
		boolean check = checkBalanceSummary(date);
		if (check) {
			String query = "update balance_summary set credit_amount= credit_amount + ? where date = ?";

			try {
				PreparedStatement preparedStatement = conn.prepareStatement(query);

				preparedStatement.setFloat(1, amount);
				preparedStatement.setString(2, date);
				preparedStatement.executeUpdate();
				preparedStatement.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			PreparedStatement preparedStatement = null;
			String query2 = "INSERT INTO balance_summary(date,credit_amount) VALUES(?,?)";
			try {
				preparedStatement = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, date);
				preparedStatement.setFloat(2, amount);
				preparedStatement.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void updateDebitAmount(float amount, String date) {
		boolean check = checkBalanceSummary(date);
		if (check) {
			String query = "update balance_summary set debit_amount= debit_amount + ? where date =?";

			try {
				PreparedStatement preparedStatement = conn.prepareStatement(query);

				preparedStatement.setFloat(1, amount);
				preparedStatement.setString(2, date);
				preparedStatement.executeUpdate();
				preparedStatement.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			PreparedStatement preparedStatement = null;
			String query2 = "INSERT INTO balance_summary(date,debit_amount) VALUES(?,?)";
			try {
				preparedStatement = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, date);
				preparedStatement.setFloat(2, amount);
				preparedStatement.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}