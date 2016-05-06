package MyHotel;
/*
 * @ author: Hongxiang Zheng
 * 
 * ***************************************************
 * **********  Store all info for table   ************
 * ***************************************************
 * 
 */
import java.sql.*;
import java.util.ArrayList;

public class Customer extends JDBCinfo {
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
		private String outtime;
		private String email;
		private String fromday;
		private String today;
		private String day;
		private int numofcheckin;
		private int numofcheckout;
		private double feeofday;
		
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
		
		public void setouttime(String outtim){outtime = outtim;}
		public String getouttime(){return outtime;}
		public void setfromday(String fromda){fromday = fromda;}
		public String getfromday(){return fromday;}
		public void settoday(String toda){today = toda;}
		public String gettoday(){return today;}
		public void setemail(String emai){email = emai;}
		public String getemail(){return email;}
		
		public void setday(String da){day = da;}
		public void setnumofcheckin(int numofchecki){numofcheckin = numofchecki;}
		public void setnumofcheckout(int numofcheckou){numofcheckout = numofcheckou;}
		public void setfeeofday(double feeofda){feeofday = feeofda;}
		public String getday(){return day;}
		public int getnumofcheckin(){return numofcheckin;}
		public int getnumofcheckout(){return numofcheckout;}
		public double getfeeofday(){return feeofday;}
		@SuppressWarnings("rawtypes")
		public ArrayList selectAll1(){
			ArrayList<Customer> ls = new ArrayList<Customer>();
			try{
				//Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from checkin";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					Customer cc= new Customer();
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
		@SuppressWarnings("rawtypes")
		public ArrayList selectAll2(){
			ArrayList<Customer> ls = new ArrayList<Customer>();
			try{
				//Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from servicelist";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					Customer cc= new Customer();
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
		@SuppressWarnings("rawtypes")
		public ArrayList selectAll3(){
			ArrayList<Customer> ls = new ArrayList<Customer>();
			try{
				//Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from payment";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					Customer cc= new Customer();
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
		@SuppressWarnings("rawtypes")
		public ArrayList selectAll4(){
			ArrayList<Customer> ls = new ArrayList<Customer>();
			try{
				//Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from checkoutlist";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					Customer cc= new Customer();
					cc.setid1(myRs.getInt("id"));
					cc.setname(myRs.getString("name"));
					cc.setidnum(myRs.getInt("idnum"));
					cc.setroom(myRs.getString("room"));
					cc.setintime(myRs.getString("intime"));
					cc.setouttime(myRs.getString("outtime"));
					cc.settotal(myRs.getDouble("totalfee"));
					ls.add(cc);
				}	
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			return ls;
	   }
		public ArrayList selectAll5(){
			ArrayList<Customer> ls = new ArrayList<Customer>();
			try{
				//Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from booking";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					Customer cc= new Customer();
					cc.setid1(myRs.getInt("id"));
					cc.setname(myRs.getString("name"));
					cc.setidnum(myRs.getInt("idnum"));
					cc.setroom(myRs.getString("room"));
					cc.setemail(myRs.getString("email"));
					cc.setfromday(myRs.getString("fromday"));
					cc.settoday(myRs.getString("today"));
					ls.add(cc);
				}	
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			return ls;
	   }
		public ArrayList selectAll6(){
			ArrayList<Customer> ls = new ArrayList<Customer>();
			try{
				//Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from bill";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					Customer cc= new Customer();
					cc.setday(myRs.getString("day"));
					cc.setnumofcheckin(myRs.getInt("numofcheckin"));
					cc.setnumofcheckout(myRs.getInt("numofcheckout"));
					cc.setfeeofday(myRs.getDouble("feeofday"));
					ls.add(cc);
				}	
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			return ls;
	   }
	}
