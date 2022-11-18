package com.examples.empapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final public class ConnectionHelperJdbc {
	private static Connection conn = null ;
	final private static String dburl = "jdbc:mysql://localhost:3307/testdb3";
	private ConnectionHelperJdbc() {};
	public static Connection getJdbcConnection() {
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(dburl,"root","matricul1A*");
				System.out.println("Connection Created");
				return conn ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return conn ; 
	}
}
