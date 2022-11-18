package com.examples.empapp.dao;
import java.sql.*;

public class ConnectionHelper {
	private static Connection conn=null;
	static final String url="jdbc:mysql://localhost:3307/testdb3";
	public static Connection createConnection() {
		if(conn==null)
		{
			try {
				conn=DriverManager.getConnection(url,"root","matricul1A*");
				System.out.println("Connection created");
				return conn;
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return conn;
	}

}
