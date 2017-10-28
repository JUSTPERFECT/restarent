package com.org.restaurant.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

	public static Connection getConnection()
	{
		Properties props = new Properties();
		Connection con = null;
    	try {
    		
    		InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("/db.properties");
			props.load(in);
			in.close();
	
			String driver = props.getProperty("driver");
			if (driver != null) {
			    Class.forName(driver) ;
			}
	
			String url = props.getProperty("url");
			String username = props.getProperty("user");
			String password = props.getProperty("password");
	
			con = DriverManager.getConnection(url, username, password);

	        System.out.println("Connection Successful");
	        return con;    
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
	    return con;
	}
}
