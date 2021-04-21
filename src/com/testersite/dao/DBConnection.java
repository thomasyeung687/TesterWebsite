package com.testersite.dao;
import java.sql.*;

public class DBConnection {
	static Connection con = null;
	
	private DBConnection() {
		
	}
	public static Connection getDBConnection() {
		try {
			if(con == null) {
				Class.forName("com.mysql.cj.jdbc.Driver"); // this might be error. delete cj
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","1938Fall76!");
			}
		}catch (Exception e) {
			System.out.print("Exception occured");
		}
		return con;
	}
	
}
