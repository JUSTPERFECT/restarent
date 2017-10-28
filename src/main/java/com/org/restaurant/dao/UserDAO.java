package com.org.restaurant.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.org.restaurant.model.User;
import com.org.restaurant.model.UserRole;
import com.org.restaurant.util.DBUtil;

public class UserDAO {
	static Connection conn = DBUtil.getConnection();

	public User getUser(int userId) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user where active=1 and user_id=" + userId);
			if (rs.next()) {
				User user = new User();
				UserRole userRole = getUserRole(rs.getInt("role_id"));
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setRole(userRole);
				user.setActive(1);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private UserRole getUserRole(int roleId) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user_role where active=1 and role_id=" + roleId);
			if (rs.next()) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(rs.getInt("role_id"));
				userRole.setRoleName(rs.getString("role_name"));
				userRole.setActive(1);
				return userRole;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User login(String userName, String password) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user where active=1 and user_name='"+userName+"' and password='"+password+"'");
			if (rs.next()) {
				User user = new User();
				UserRole userRole = getUserRole(rs.getInt("role_id"));
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setRole(userRole);
				user.setActive(1);
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}