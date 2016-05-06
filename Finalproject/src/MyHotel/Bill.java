package MyHotel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Bill extends JDBCinfo{
	DecimalFormat dfInt=new DecimalFormat("00");
	private String[] day = new String[31];
	
	public void truncate(){
		try{
			String sql = "truncate bill";
			Statement myStmt = myConn.createStatement();
			myStmt.execute(sql);
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void ddd(String year, String mon){
		int yy = Integer.parseInt(year);
		int mm = Integer.parseInt(mon);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,yy);   
		calendar.set(Calendar.MONTH,mm-1);
		String month = dfInt.format(mm);
		int  maxDate = calendar.getActualMaximum(Calendar.DATE);
		for(int j = 0; j < maxDate; j++){
			day[j] = dfInt.format(j+1);
		}
		for(int i = 0; i < maxDate; i++){
			String dayday = year + "-" + month + "-" + day[i] + "%" ;
			try{
				//2. Create a statement
				String sql = "select * from checkoutlist where intime like ?";
	
				PreparedStatement myStmt = myConn.prepareStatement(sql);
				myStmt.setString(1,dayday);
				ResultSet myRs = myStmt.executeQuery();
				int count = 0;
				while (myRs.next()) {
					count++;
				}
				
				//2. Create a statement
				String sql2 = "select * from checkoutlist where outtime like ?";
	
				PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
				myStmt2.setString(1,dayday);
				ResultSet myRs2 = myStmt2.executeQuery();
				int count2 = 0;
				double total = 0;
				while (myRs2.next()) {
					count2++;
					double fee = myRs2.getDouble("totalfee");
					total += fee;
					//System.out.println(a);
				}
				
				String sql3 = "insert into bill (day,numofcheckin,numofcheckout,feeofday) values(?,?,?,?)";
				PreparedStatement myStmt3 = myConn.prepareStatement(sql3);
				myStmt3.setString(1,day[i]);
				myStmt3.setInt(2,count);
				myStmt3.setInt(3,count2);
				myStmt3.setDouble(4,total);
				//3. Execute SQL 
				myStmt3.executeUpdate();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		try{
			String sql2 = "SELECT * FROM bill";
			Statement myStmt2 = myConn.createStatement();
			ResultSet myRs2 = myStmt2.executeQuery(sql2);
			int numofci = 0;
			int numofco = 0;
			double totalmoney = 0.0;
			while (myRs2.next()) {
				int a1 = myRs2.getInt("numofcheckin");
				int a2 = myRs2.getInt("numofcheckout");
				double a3 = myRs2.getDouble("feeofday");
				numofci += a1;
				numofco += a2;
				totalmoney += a3;
				
			}
			
			String sql3 = "insert into bill (day,numofcheckin,numofcheckout,feeofday) values(?,?,?,?)";
			PreparedStatement myStmt3 = myConn.prepareStatement(sql3);
			myStmt3.setString(1,"");
			myStmt3.setInt(2,numofci);
			myStmt3.setInt(3,numofco);
			myStmt3.setDouble(4,totalmoney);
			//3. Execute SQL 
			myStmt3.executeUpdate();
		}catch (Exception exc) {
				exc.printStackTrace();
		}
	}
}
