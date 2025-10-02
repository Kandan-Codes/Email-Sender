package com.kandan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		
		final String URL = "jdbc:mysql://localhost:3306/patternsdb?useSSL=false&serverTimezone=UTC";
		final String USER = "root";
		final String PASSWORD = "mani535";
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(URL, USER, PASSWORD);
		
	}
}
