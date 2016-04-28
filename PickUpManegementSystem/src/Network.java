import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Network {
	public ArrayList arrayList;
	
	PreparedStatement ps=null;
    Connection ct=null;
	ResultSet rs=null;
	Statement sm=null;
	
	public Network(ArrayList list){
		this.arrayList=list;
		   try {
//			      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			      Class.forName("com.mysql.jdbc.Driver");
//				  ct=DriverManager.getConnection("jdbc:odbc:sql server","sa","ydyd4488321");
//				  ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/sms","root","1");
				  ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/ms","root","1");
			      String ss=("insert into client values (?,?,?,?,?)");
//			      INSERT INTO `sms`.`students` (`id`, `name`, `gender`, `age`, `fromwhere`, `department`) VALUES ('8', '8', '8', '8', '8', '8');
//			      String ss=("INSERT INTO `sms`.`students` (`id`, `name`, `gender`, `age`, `fromwhere`, `department`) VALUES ('?', '?', '?', бо?', '?', '?')");
//			      INSERT INTO `sms`.`students` (`id`, `name`, `gender`, `age`, `fromwhere`, `department`) VALUES ('34', '879', '879', '789', '789', '78');
				  ps=ct.prepareStatement(ss);	
//				  ps.setInt(1,Integer.parseInt(wbk1.getText()));
				  ps.setString(1,(String) arrayList.get(0));
//				  ps.setString(2,wbk2.getText());
				  ps.setString(2, (String) arrayList.get(1));
				  ps.setString(3, (String) arrayList.get(2));
//				  ps.setString(3,wbk3.getText());
//				  ps.setString(3,wbk3.getText());
//				  ps.setInt(4,Integer.parseInt(wbk4.getText()));
//				  ps.setString(4,wbk4.getText());
				  ps.setString(4, (String) arrayList.get(3));
				  ps.setString(5, (String) arrayList.get(4));
//				  ps.setString(5,wbk5.getText());
//				  ps.setString(6,wbk6.getText());
				  
				  ps.executeUpdate();
				  
//				  this.dispose();				  
			} catch (Exception e2){}
		    finally
		    {
		    	try {
		    		if(rs!=null)
					{
						rs.close();
					}
		    		if(ps!=null)
					{
						ps.close();
					}
					if(ct!=null)
					{
						ct.close();
					}
					
				} catch (Exception e3){}		
		    }
	}
}
