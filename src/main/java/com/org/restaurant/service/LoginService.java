package com.org.restaurant.service;

import com.org.restaurant.dao.UserDAO;
import com.org.restaurant.model.User;

public class LoginService {

	public User login(String userName, String password) {
		UserDAO userDao = new UserDAO();
		return userDao.login(userName, password);
	}
	
}
