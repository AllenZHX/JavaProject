package test3;
/*
 * @ author: Xiang Cao
 * 
 * ***************************************************
 * ********** Basic info for jDBC connect  ***********
 * ***************************************************
 * 
 */
import java.sql.*;

public class JDBCinfo {
	private static String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private static String user = "root";
	private static String password = ",26187108hoog";
	protected static Connection myConn = get();
	public static Connection get(){
		try {
			 myConn = DriverManager.getConnection(url, user, password);
			 return myConn;
		} catch (SQLException e) {
		}
		return null;
	}
}
