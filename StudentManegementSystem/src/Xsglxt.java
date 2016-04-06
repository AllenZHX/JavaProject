import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Xsglxt extends JFrame implements ActionListener
{
	JPanel mb1,mb2;
	JLabel bq1;
	JTextField wbk1;
	JButton an1,an2,an3,an4;	
	JTable bg1;	
	JScrollPane gd1;
	Xsxx xsxx2;
		
	public static void main(String[] args) 
	{
	   new Xsglxt();
	}
	
	
	public Xsglxt()
	{
		mb1=new JPanel();
		bq1=new JLabel("Input name:");
		wbk1=new JTextField(20);
//		wbk1.setText("xxx");
		an1=new JButton("search");
		an1.addActionListener(this);/////////////
		an1.setActionCommand("chaxun");//////////////////////////////
		mb1.add(bq1); mb1.add(wbk1); mb1.add(an1);
		
		mb2=new JPanel(new GridLayout(1,3,15,15));
		an2=new JButton("add");
		an2.addActionListener(this);
		an2.setActionCommand("tianjia");
		an3=new JButton("modify");
		an3.addActionListener(this);
		an3.setActionCommand("xiugai");
		an4=new JButton("delete");
		an4.addActionListener(this);
		an4.setActionCommand("shanchu");
		mb2.add(an2); mb2.add(an3); mb2.add(an4);
		
		xsxx2=new Xsxx();
		bg1=new JTable(xsxx2);
		gd1=new JScrollPane(bg1);
		
		this.add(gd1);
		this.add(mb1,"North");
		this.add(mb2,"South");
		
		this.setTitle("Student Management System");
		this.setSize(500,400);
		this.setLocation(201,181);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);			
	}
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("chaxun"))
		{
			
			String xingming=this.wbk1.getText().trim();
			String sql="select * from students where name='"+xingming+"'";
			xsxx2=new Xsxx(sql);
			bg1.setModel(xsxx2);
		}
		else if(e.getActionCommand().equals("tianjia"))
		{
			Tianjia tj=new Tianjia(this,"Add Student Information",true);
			xsxx2=new Xsxx();
			bg1.setModel(xsxx2);
			
		}
		else if(e.getActionCommand().equals("xiugai"))
		{
			int ii=this.bg1.getSelectedRow();
			if(ii==-1)
			{
				JOptionPane.showMessageDialog(this,"Choose the row needed to be revised");
				return;
			}
			new Xiugai(this,"Modify Student's Information",true,xsxx2,ii);
			
			xsxx2=new Xsxx();
			
			bg1.setModel(xsxx2);
		}
		else if(e.getActionCommand().equals("shanchu"))
		{
			int ii=this.bg1.getSelectedRow();
			if(ii==-1)
			{
				JOptionPane.showMessageDialog(this,"Choose the row needed to be deleted");
				return;
			}
//			   String st=(String)xsxx2.getValueAt(ii,0);
			  int st=(int)xsxx2.getValueAt(ii,0);
			   PreparedStatement ps=null;
			   Connection ct=null;
			   ResultSet rs=null;
			   Statement sm=null;
			   
			   try {
//				      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				   	  Class.forName("com.mysql.jdbc.Driver");
					  ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/sms","root","1");
					  ps=ct.prepareStatement("delete from students where id=?");
					  ps.setInt(1,st);
					  ps.executeUpdate();					 
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
			      xsxx2=new Xsxx();
				  bg1.setModel(xsxx2);
		}
	}
	
	
}
