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
		
	
	JMenuItem jmi1;
	JMenuItem jmi2;
	
	public static void main(String[] args) 
	{
	   new Xsglxt();
	}
	
	
	public Xsglxt()
	{
		Container c=getContentPane();
//		c.setBackground(Color.WHITE);
		
		JMenuBar jmb=new JMenuBar();
		
		JMenu jm1=new JMenu("File");
		jm1.setFont(new Font("Times New Roman", 1,12));
		JMenu jm2=new JMenu("Help");
		jm2.setFont(new Font("Times New Roman",1,12));
		
		jmi1=new JMenuItem("Exit");
		jmi1.setFont(new Font("Times New Roman", 0,12));
		jmi1.addActionListener(this);
		jmi2=new JMenuItem("About the System");
		jmi2.setFont(new Font("Times New Roman",0,12));
		jmi2.addActionListener(this);
		
		jm1.add(jmi1);
		jm2.add(jmi2);
		jmb.add(jm1);
		jmb.add(jm2);
	
		
		mb1=new JPanel();
		bq1=new JLabel("Input name:");
		wbk1=new JTextField(20);
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
		bg1.setRowHeight(30);
		bg1.setAutoResizeMode(4);
		gd1=new JScrollPane(bg1);
		
		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("Search", new JButton("Search"));
		jtp.addTab("Add",new JButton("Add"));
		jtp.addTab("Modify", new JButton("Modify"));
		jtp.addTab("Remove", new JButton("Remove"));
		
		this.add(jtp,BorderLayout.WEST);
		this.add(gd1);
		this.add(mb1,"North");
		this.add(mb2,"South");
		
		this.setJMenuBar(jmb);
		
		this.setTitle("Student Management System");
//		this.setSize(500,400);
		this.setSize(570, 400);
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
//			String sql="select * from students where name='"+xingming+"'";
			String sql="select * from students where name like '"+xingming+"'";
//			String sql="select * from students where name like 'xingming%'";
//			String sql="select * from students where name like 'x%'";
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
		sb1.append("This is a Student Management System.\n\n");
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
	
	public void actionPerfomed(ActionEvent e){
		if(e.getSource()==jb1){
			this.dispose();
		}
	}
//	JDialog jd1=new JDialog(this, "About the System");
//	JPanel jp1=new JPanel();
//	
//	StringBuffer sb=new StringBuffer();
//	sb.append("This is a Student Management System.\n\n");
//	sb.append("It mainly involves Swing and Database.\n\n");
//	sb.append("Author: Xiang");
//	
//	
//	JTextArea jta=new JTextArea(5,21);
//	jta.setText(sb.toString());
//	jp1.add(jta);
//	getContentPane().add(jp1);
//	
//	jd1.setVisible(true);
//	jd1.setLocation(201,181);
//	jd1.setSize(200, 200);

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
