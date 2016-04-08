package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class manager {
	private String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String user = "root";
	private String password = ",26187108hoog";
	private int num;
	
	public void createANewUser(String name, String idnum, String room, String status, int roomid, String intime,double feeofroom) {
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "insert into checkin (name,idnum,room,status,roomid,intime) values(?,?,?,?,?,?)";
			String sql2 = "insert into payment (roomnum,fee_room,fee_service,total) values(?,?,?,?)";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,name);
			myStmt.setString(2,idnum);
			myStmt.setString(3,room);
			myStmt.setString(4,status);
			myStmt.setInt(5,roomid);
			myStmt.setString(6,intime);
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setString(1,room);
			myStmt2.setDouble(2,feeofroom);
			myStmt2.setDouble(3,0);
			myStmt2.setDouble(4,0);
			//3. Execute SQL 
			myStmt.executeUpdate();
			myStmt2.executeUpdate();
			//showtable(0);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public double calculateServPrice(String itemid, String stocknum) {
		double total = 0;
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "select * from servicelist";
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(sql);
			int i = 1;
			while (myRs.next()) {
				int a = Integer.parseInt(itemid);
				if(a == i){
					num = Integer.parseInt(stocknum);
					double price = myRs.getDouble("price");
					total = num * price;
					
					//ta6[3].setText(Double.toString(total));
				}
				i++;
			}
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return total;
	}
	
	public void addServiceinfo(String roomnum, String newfee_service, String itemid) {
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "select * from payment";
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(sql);
			double fee_service = 0;
			while (myRs.next()) {
				if(roomnum.equals(myRs.getString("roomnum"))){
					fee_service = myRs.getDouble("fee_service");
				}	
			}

			String sql2 = "update payment set fee_service = ? where roomnum = ?";
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setDouble(1,Double.parseDouble(newfee_service) + fee_service);
			myStmt2.setString(2,roomnum);
			//3. Execute SQL 
			myStmt2.executeUpdate();
			
			
			String sql3 = "select * from servicelist";
			Statement myStmt3 = myConn.createStatement();
			ResultSet myRs3 = myStmt3.executeQuery(sql3);
			int i = 1;
			int stock = 0;
			while (myRs3.next()) {
				int a = Integer.parseInt(itemid);
				if(a == i){
					stock = myRs3.getInt("stock");
				}
				i++;
			}	
			String sql4 = "update servicelist set stock = ? where id = ?";
			PreparedStatement myStmt4 = myConn.prepareStatement(sql4);
			myStmt4.setInt(1,stock-num);
			myStmt4.setInt(2,Integer.parseInt(itemid));
			//3. Execute SQL 
			myStmt4.executeUpdate();
		
			//showtable(2);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public double calculateTotalFee(String roomnum) {
		double total = 0;
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "select * from payment";
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(sql);
			double fee_service = 0;
			double fee_room = 0;
			while (myRs.next()) {
				if(roomnum.equals(myRs.getString("roomnum"))){
					fee_service = myRs.getDouble("fee_service");
					fee_room = myRs.getDouble("fee_room");
					total = fee_service+fee_room;
					//ta5[1].setText(Double.toString(total));
				}	
			}
			String sql2 = "update payment set total = ? where roomnum = ?";
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setDouble(1,total);
			myStmt2.setString(2,roomnum);
			//3. Execute SQL 
			myStmt2.executeUpdate();

			//showtable(2);
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return total;
	}
	
	public double calculateChange(String zongshu,String shifu) {
		double total = Double.parseDouble(zongshu);
		double paidup = Double.parseDouble(shifu);
		double change = paidup - total;
		return change;
	}
	
	public void checkout(String roo){
		try{
			Connection myConn = DriverManager.getConnection(url, user, password);
			String sql = "delete from checkin where room = ?";
			String sql2 = "delete from payment where roomnum = ?";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,roo);
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setString(1,roo);
			myStmt.executeUpdate();
			myStmt2.executeUpdate();
			//showtable(0);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
