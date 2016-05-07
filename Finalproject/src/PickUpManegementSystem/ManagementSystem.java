package PickUpManegementSystem;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class ManagementSystem extends JFrame implements ActionListener
{
	JPanel jp1,jp2;
	JLabel jl1;
	JTextField jtf1;
	JButton jb1,jb2,jb3,jb4;	
	JTable table;	
	JScrollPane jsp1;
	MyTableModel tableModel;
	JTextArea jta;
	//static ManagementSystem mm;
	
	JMenuItem jmi1;
	JMenuItem jmi2;
	//addWindowListener(new WindowAdapter){
	/*public void windowClosing(WindowEvent windowEvent){
		 //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  }*/
	public static void main(String[] args) throws Exception 
	
	{
	   Server ss = new Server();
	}
	
	
	public ManagementSystem()
	{
		Container c=getContentPane();
//		c.setBackground(Color.WHITE);
		
		JMenuBar jmb=new JMenuBar();
		
		JMenu jm1=new JMenu("File");
		jm1.setFont(new Font("Times New Roman", 1,18));
		JMenu jm2=new JMenu("Help");
		jm2.setFont(new Font("Times New Roman",1,18));
		
		jmi1=new JMenuItem("Exit");
		jmi1.setFont(new Font("Times New Roman", 1,12));
		jmi1.addActionListener(this);
		jmi2=new JMenuItem("About the System");
		jmi2.setFont(new Font("Times New Roman",1,12));
		jmi2.addActionListener(this);
		
		jm1.add(jmi1);
		jm2.add(jmi2);
		jmb.add(jm1);
		jmb.add(jm2);
	
		
		jp1=new JPanel();
		jp1.setLayout(new BorderLayout());
		JPanel mb_new=new JPanel();
		
		jl1=new JLabel("Input name:");
		jl1.setFont(new Font("Times New Roman",0,20));
		jtf1=new JTextField(20);
		jb1=new JButton("search");
		jb1.setFont(new Font("Times New Roman",1,20));
		jb1.setBackground(Color.PINK);
		jb1.addActionListener(this);/////////////
		jb1.setActionCommand("chaxun");//////////////////////////////
		jta=new JTextArea();
		JScrollPane jsp_new=new JScrollPane(jta);
//		jsp_new.
		
		jta.setBackground(Color.WHITE);
		jta.setSize(1000,400);
		jta.setText("Waiting info from client...");
		jta.setFont(new Font("Times New Roman",0,18));
//		jta.setText(arrayList.get(0));
//		mb1.add(bq1); mb1.add(wbk1); mb1.add(an1);
		mb_new.add(jl1); mb_new.add(jtf1); mb_new.add(jb1);
		jp1.add(BorderLayout.NORTH,mb_new);
		jp1.add(BorderLayout.CENTER,jsp_new);
		
		
		
		jp2=new JPanel(new GridLayout(1,3,15,15));
		jb2=new JButton("add");
		jb2.setFont(new Font("Times New Roman",1,20));
		jb2.addActionListener(this);
		jb2.setActionCommand("tianjia");
		jb3=new JButton("modify");
		jb3.setFont(new Font("Times New Roman",1,20));
		jb3.addActionListener(this);
		jb3.setActionCommand("xiugai");
		jb4=new JButton("delete");
		jb4.setFont(new Font("Times New Roman",1,20));
		jb4.addActionListener(this);
		jb4.setActionCommand("shanchu");
		jp2.add(jb2); jp2.add(jb3); jp2.add(jb4);
		
		tableModel=new MyTableModel();
	
		table=new JTable(tableModel);
		table.setRowHeight(30);
		table.setFont(new Font("Times New Roman",0,17));
		table.setAutoResizeMode(4);
		jsp1=new JScrollPane(table);
		JPanel jpCenter=new JPanel();
		jpCenter.setLayout(new GridLayout(2,1));
	
		jpCenter.add(jsp_new);
		jpCenter.add(jsp1);
		
		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("Search", new JButton("Search"));
		jtp.addTab("Add",new JButton("Add"));
		jtp.addTab("Modify", new JButton("Modify"));
		jtp.addTab("Remove", new JButton("Remove"));
		
//		this.add(jtp,BorderLayout.WEST);
		this.add(jpCenter, BorderLayout.CENTER);
		this.add(jp1,"North");
		this.add(jp2,"South");
		
		this.setJMenuBar(jmb);
		
		this.setTitle("Management System");
		this.setBackground(Color.GREEN);
//		this.setSize(500,400);
		this.setSize(1000, 800);
		this.setLocation(201,181);
//		this.setResizable(false);
		this.dispose();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);		
	}
	
	public void update(ArrayList arrayList){
		String s="Info from client:";
		tableModel=new MyTableModel();
		table.setModel(tableModel);
		
			String name="Name: "+arrayList.get(0);
			String phone="Phone :"+arrayList.get(1);
			String date="Date : "+arrayList.get(2);
			String from="FromWhere :"+arrayList.get(3);
			String to="ToWhere: "+arrayList.get(4);
			s=name+"\n"+phone+"\n"+date+"\n"+from+"\n"+to+"\n";
			jta.setText(s);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("chaxun"))
		{
			String xingming=this.jtf1.getText().trim();
//			String sql="select * from students where name='"+xingming+"'";
			String sql="select * from client where Name like '"+xingming+"'";
//			String sql="select * from students where name like 'xingming%'";
//			String sql="select * from students where name like 'x%'";
			tableModel=new MyTableModel(sql);
			table.setModel(tableModel);
		}
		else if(e.getActionCommand().equals("tianjia"))
		{
			Add tj=new Add(this,"Add Client Information",true);
			tableModel=new MyTableModel();
			table.setModel(tableModel);
		}
		else if(e.getActionCommand().equals("xiugai"))
		{
			int ii=this.table.getSelectedRow();
			if(ii==-1)
			{
				JOptionPane.showMessageDialog(this,"Choose the row needed to be revised");
				return;
			}
			new Modify(this,"Modify Client's Information",true,tableModel,ii);
			
			tableModel=new MyTableModel();
			
			table.setModel(tableModel);
		}
		else if(e.getActionCommand().equals("shanchu"))
		{
			int ii=this.table.getSelectedRow();
			if(ii==-1)
			{
				JOptionPane.showMessageDialog(this,"Choose the row needed to be deleted");
				return;
			}
//			   String st=(String)xsxx2.getValueAt(ii,0);
			  String st=(String)tableModel.getValueAt(ii,1);
			   PreparedStatement ps=null;
			   Connection ct=null;
			   ResultSet rs=null;
			   Statement sm=null;
			   
			   try {
//				      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				   	  //Class.forName("com.mysql.jdbc.Driver");
					  ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/ms?useSSL=false","root",",26187108hoog");
					  ps=ct.prepareStatement("delete from client where phone=?");
					  ps.setString(1,st);
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
			      tableModel=new MyTableModel();
				  table.setModel(tableModel);
		}
		else if(e.getSource()==jmi1){
			System.exit(0);
		}
		else if(e.getSource()==jmi2){
			new MyJDialog(this,"About the System");
		}
	}
}

class MyJDialog extends JDialog implements ActionListener{
	JButton jb1;
	MyJDialog(JFrame frame, String title){
		super(frame,title);
		Container c=getContentPane();
		c.setBackground(Color.RED);
		
		JPanel jp1=new JPanel();
		jp1.setBackground(Color.red);
		
		StringBuffer sb1=new StringBuffer();
		sb1.append("This is a Management System.\n\n");
		sb1.append("It mainly involves Swing and Database.\n\n");
		sb1.append("Author: Xiang");
		
		JTextArea jta1=new JTextArea(5,20);
		jta1.setText(sb1.toString());
		jta1.setFont(new Font("Times New Roman",Font.BOLD,14));
		jb1=new JButton("OK");
		jb1.addActionListener(this);
		
		jp1.add(jta1,BorderLayout.CENTER);
		jp1.add(jb1, BorderLayout.SOUTH);
		
		
		c.add(jp1);
		
		setVisible(true);
		setSize(260,200);
		setLocation(201,181);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jb1){
			this.dispose();
		}
	}
}
