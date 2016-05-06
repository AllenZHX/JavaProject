package MyHotel;
/*
 * @ author: Hongxiang Zheng, Xiang Cao
 * 
 * ***************************************************
 * **********      important operations     **********
 * **********  (booking, checkin, checkout) **********
 * ***************************************************
 * 
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Checkout extends JDBCinfo{
	private int day = 1;
	
	public double calculateTotalFee(String roomnum,String outtime) {
		double total = 0;
		
		try{
			//2. Create a statement
			String sql4 = "select * from checkin";
			Statement myStmt4 = myConn.createStatement();
			ResultSet myRs4 = myStmt4.executeQuery(sql4);
			String intime = "";
			while (myRs4.next()) {
				if(roomnum.equals(myRs4.getString("room"))){
					intime = myRs4.getString("intime");
				}	
			}
			int checkinyear = Integer.parseInt(intime.substring(0,4));
			int checkinmon = Integer.parseInt(intime.substring(5,7));
			int checkinday = Integer.parseInt(intime.substring(8,10));
			int checkoutyear = Integer.parseInt(outtime.substring(0,4));
			int checkoutmon = Integer.parseInt(outtime.substring(5,7));
			int checkoutday = Integer.parseInt(outtime.substring(8, 10));
			int checkouthour = Integer.parseInt(outtime.substring(11, 13));
			JulianDate in = new JulianDate(checkinyear,checkinmon,checkinday);
			JulianDate out = new JulianDate(checkoutyear,checkoutmon,checkoutday);
			int tempday = out.getJulianDate() - in.getJulianDate();
			if(tempday == 0){
				day = 1;
			}else{
				if(checkouthour < 12){
					day = tempday;
				}else{
					day = tempday + 1;
				}
			}
			String sql = "select * from payment";
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(sql);
			double fee_service = 0;
			double fee_room = 0;
			while (myRs.next()) {
				if(roomnum.equals(myRs.getString("roomnum"))){
					fee_service = myRs.getDouble("fee_service");
					fee_room = myRs.getDouble("fee_room") * day;
					total = fee_service+fee_room;
				}	
			}
			String sql2 = "update payment set fee_room = ? where roomnum = ?";
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setDouble(1,fee_room);
			myStmt2.setString(2,roomnum);
			//3. Execute SQL 
			myStmt2.executeUpdate();
			
			String sql3 = "update payment set total = ? where roomnum = ?";
			PreparedStatement myStmt3 = myConn.prepareStatement(sql3);
			myStmt3.setDouble(1,total);
			myStmt3.setString(2,roomnum);
			//3. Execute SQL 
			myStmt3.executeUpdate();
			
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
	
	public void checkout(String roo,String outtime){
		try{
			String sql3 = "select * from payment";
			Statement myStmt3 = myConn.createStatement();
			ResultSet myRs3 = myStmt3.executeQuery(sql3);
			double total = 0;
			while (myRs3.next()) {
				if(roo.equals(myRs3.getString("roomnum"))){
					total = myRs3.getDouble("total");
				}	
			}
			
			String sql4 = "select * from checkin";
			Statement myStmt4 = myConn.createStatement();
			ResultSet myRs4 = myStmt4.executeQuery(sql4);
			String name = "";
			String idnum = "";
			String intime = "";
			while (myRs4.next()) {
				if(roo.equals(myRs4.getString("room"))){
					name = myRs4.getString("name");
					idnum = myRs4.getString("idnum");
					intime = myRs4.getString("intime");
				}	
			}

			String sql5 = "insert into checkoutlist (name,idnum,room,intime,outtime,totalfee) values(?,?,?,?,?,?)";
			PreparedStatement myStmt5 = myConn.prepareStatement(sql5);
			myStmt5.setString(1,name);
			myStmt5.setString(2,idnum);
			myStmt5.setString(3,roo);
			myStmt5.setString(4,intime);
			myStmt5.setString(5,outtime);
			myStmt5.setDouble(6,total);
			
			//3. Execute SQL 
			myStmt5.executeUpdate();
			
			String sql = "delete from checkin where room = ?";
			String sql2 = "delete from payment where roomnum = ?";
			String sql6 = "delete from booking where name = ?";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,roo);
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setString(1,roo);
			PreparedStatement myStmt6 = myConn.prepareStatement(sql6);
			myStmt6.setString(1,name);
			myStmt.executeUpdate();
			myStmt2.executeUpdate();
			myStmt.executeUpdate();
			myStmt6.executeUpdate();

			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}

