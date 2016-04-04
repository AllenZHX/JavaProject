import java.sql.*;
import java.util.ArrayList;

public class customer {
		private int id1;
		private String name;
		private int idnum;
		private String room;
		private String status;
		private String intime;
		private int id2;
		private String items;
		private double price;
		private int stock;
		private int id3;
		private String roomnum;
		private double fee_room;
		private double fee_service;
		private double total;
	    String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "root";
		String password = ",26187108hoog";
		public void setid1(int i1){id1 = i1;}
		public void setname(String nam){name = nam;}
		public void setidnum(int idnu){idnum = idnu;}
		public void setroom(String roo){room = roo;}
		public void setstatus(String statu){status = statu;}
		public void setintime(String intim){intime = intim;}
		public int getid1(){return id1;}
		public String getname(){return name;}
		public int getidnum(){return idnum;}
		public String getroom(){return room;}
		public String getstatus(){return status;}
		public String getintime(){return intime;}
		
		public void setid2(int i2){id2 = i2;}
		public void setitems(String item){items = item;}
		public void setprice(double pric){price = pric;}
		public void setstock(int stoc){stock = stoc;}
		public int getid2(){return id2;}
		public String getitems(){return items;}
		public double getprice(){return price;}
		public int getstock(){return stock;}
		
		public void setid3(int i3){id3 = i3;}
		public void setroomnum(String roomnu){roomnum = roomnu;}
		public void setfee_room(double fee_roo){fee_room = fee_roo;}
		public void setfee_service(double fee_servic){fee_service = fee_servic;}
		public void settotal(double tota){total = tota;}
		public int getid3(){return id3;}
		public String getroomnum(){return roomnum;}
		public double getfee_room(){return fee_room;}
		public double getfee_service(){return fee_service;}
		public double gettotal(){return total;}
		
		public ArrayList selectAll1(){
			ArrayList<customer> ls = new ArrayList<customer>();
			try{
				Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from checkin";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					customer cc= new customer();
					cc.setid1(myRs.getInt("id"));
					cc.setname(myRs.getString("name"));
					cc.setidnum(myRs.getInt("idnum"));
					cc.setroom(myRs.getString("room"));
					cc.setstatus(myRs.getString("status"));
					cc.setintime(myRs.getString("intime"));
					ls.add(cc);
				}	
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			return ls;
	   }
		public ArrayList selectAll2(){
			ArrayList<customer> ls = new ArrayList<customer>();
			try{
				Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from servicelist";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					customer cc= new customer();
					cc.setid2(myRs.getInt("id"));
					cc.setitems(myRs.getString("items"));
					cc.setprice(myRs.getDouble("price"));
					cc.setstock(myRs.getInt("stock"));
					ls.add(cc);
				}	
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			return ls;
	   }
		public ArrayList selectAll3(){
			ArrayList<customer> ls = new ArrayList<customer>();
			try{
				Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from payment";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					customer cc= new customer();
					cc.setid3(myRs.getInt("id"));
					cc.setroomnum(myRs.getString("roomnum"));
					cc.setfee_room(myRs.getDouble("fee_room"));
					cc.setfee_service(myRs.getDouble("fee_service"));
					cc.settotal(myRs.getDouble("total"));
					ls.add(cc);
				}	
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			return ls;
	   }
	
		
	}