import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.*;

class Xsxx extends AbstractTableModel 
{
	Vector ziduan,jilu;
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
//    Vector<String> v=new Vector();
    
	public int getRowCount()
	{
		return this.jilu.size();
	}
    public int getColumnCount()
    {
		return this.ziduan.size();
	}
	public Object getValueAt(int hang, int lie)
	{
		return ((Vector)this.jilu.get(hang)).get(lie);
	}
	
	public Xsxx()
	{
		this.sqlyj("select * from students");
	}
	
	public Xsxx(String ss)
	{
		this.sqlyj(ss);
	}
	
	public String getColumnName(int e)
	{
		return (String)this.ziduan.get(e);
	}
	
	
	public void sqlyj(String sql)
	{
		ziduan=new Vector();
		ziduan.add("ID");
		ziduan.add("Name");
		ziduan.add("Gender");
		ziduan.add("Age");
		ziduan.add("From");
		ziduan.add("Department");	
		ziduan.add("EditionDate");
		
        jilu=new Vector();
		
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 System.out.println("Loaded Successfully");
			 
			 ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/sms","root","1");
//	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//			  ct=DriverManager.getConnection("jdbc:odbc:sql server","sa","ydyd4488321");
			  ps=ct.prepareStatement(sql);
			  System.out.println("Successfully connectted to database!");
			  rs=ps.executeQuery();
			  
			  while(rs.next())
			  {
				  System.out.println(rs.getString(5)+"\t");
				  Vector hang=new Vector();
//					hang.add(rs.getString(1));
				  
					hang.add(rs.getInt(1));
					hang.add(rs.getString(2));
					hang.add(rs.getString(3));
					hang.add(rs.getInt(4));
					hang.add(rs.getString(5));
					hang.add(rs.getString(6));
					hang.add(rs.getString(7));
//				  	hang.add(rs.getInt(1));
//				  	hang.add(rs.getString(2));
//				  	hang.add(rs.getString(3));
				  jilu.add(hang);
			  }
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
