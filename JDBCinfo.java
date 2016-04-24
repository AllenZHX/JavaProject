package test3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCinfo {
	protected static String url = "jdbc:mysql://172.20.10.3:3306/demo?useSSL=false";
	protected static String user = "root";
	protected static String password = "root";
	protected static Connection myConn = getconnection();
	public static Connection getconnection(){
		
		try {
			Connection myConn = DriverManager.getConnection(url, user, password);
			return myConn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}