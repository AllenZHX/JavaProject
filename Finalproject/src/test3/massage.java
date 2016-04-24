package test3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import test3.Popup;

public class massage extends JDBCinfo implements ActionListener {
	private JDialog jDialog=null; 
	private JButton[] button1 = new JButton[3];
	private JButton[] button2 = new JButton[3];
	private JButton[] button3 = new JButton[3];
	private JTextArea[] ta3 = new JTextArea[2];
	private JLabel[] label3 = new JLabel[2];
	private String[] labelString3 = {"Room#: ", "Total: "};
    private String[] buttonString1 = {"Foot Massage","Body Massage","Essential oils open back",""};
    private String[] buttonString2 = {"20 mins","30 mins","60 mins",""};
    private String[] buttonString3 = {"Ok","Clear"};
    private JTable table = new JTable();
	private JScrollPane scrollPane = new JScrollPane(table);
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	private double[] price = {10,15,25,30,40,60,35,50,80,0};
	int ii = 3; int jj = 3; int mm = 9;
	private int itemid = 99;private int itemid2 = 99;
	private boolean[] selectstatus = {false,false,false};
	private boolean[] selectstatus2 = {false,false,false};
	private boolean clear = false;
	
	
	public void Popup_service(JFrame jFrame){
		  
		   JPanel p1 = new JPanel(new GridLayout(3,1,10,15));
		   JPanel p2 = new JPanel(new GridLayout(3,1,10,15));
		   JPanel p3 = new JPanel();
		   for(int i = 0; i < 3; i++) {
		    	button1[i] = new JButton();
			    button1[i].setText(buttonString1[i]);
			    button1[i].setFont(font);
			    button1[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button1[i].setBackground(Color.GREEN);
			    button1[i].addActionListener(this);
		   }
		   for(int i = 0; i < 3; i++)
		    	p1.add(button1[i]);
		   p1.setBounds(5,10,380,180);
		   p1.setOpaque(false);
		   p1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   ///////////////////////////////////
		   for(int i = 0; i < 3; i++) {
		    	button2[i] = new JButton();
			    button2[i].setText(buttonString2[i]);
			    button2[i].setFont(font);
			    button2[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button2[i].setBackground(Color.GREEN);
			    button2[i].addActionListener(this);
		   }
		   for(int i = 0; i < 3; i++)
		    	p2.add(button2[i]);
		   p2.setBounds(5,205,380,180);
		   p2.setOpaque(false);
		   p2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   ///////////////////////////////////
		   for(int i = 0; i < 2; i++) {
		    	button3[i] = new JButton();
			    button3[i].setText(buttonString3[i]);
			    button3[i].setFont(font);
			    button3[i].setContentAreaFilled(false);
			    button3[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button3[i].addActionListener(this);
		   }
		   for(int i = 0; i < 2; i++) {
			    label3[i] = new JLabel();
			    label3[i].setText(labelString3[i]);
			    label3[i].setFont(font);
			}
			for(int i = 0; i < 2; i++) {
			    ta3[i] = new JTextArea();
			    ta3[i].setFont(font);
			    ta3[i].setBorder(BorderFactory.createLoweredBevelBorder());
			}
			p3.setLayout(null);
			button3[0].setBounds(360,260,100,35);
			button3[1].setBounds(360,310,100,35);
			label3[0].setBounds(70,250,160,50);
			label3[1].setBounds(70,300,160,50);
			ta3[0].setBounds(160,260,100,30);
			ta3[1].setBounds(160,310,100,30);
			
			for(int i = 0; i < 2; i++)
		    	p3.add(button3[i]);
			for(int i = 0; i < 2; i++) 
				p3.add(label3[i]);
			for(int i = 0; i < 2; i++) 
				p3.add(ta3[i]);
			scrollPane.setViewportView(table);
			scrollPane.setBounds(20,15,520,220);
			scrollPane.setFont(font);
			p3.add(scrollPane);
		   p3.setBounds(400,10,555,375);
		   p3.setOpaque(false);
		   p3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   ///////////////////////////////////////
		   jDialog=new JDialog(jFrame,"Service List",true);
		   jDialog.getContentPane().setLayout(null);
		   jDialog.getContentPane().add(p1);
		   jDialog.getContentPane().add(p2);
		   jDialog.getContentPane().add(p3);
		   ((JPanel)jDialog.getContentPane()).setOpaque(false);
			ImageIcon img = new ImageIcon("C:\\3.jpg");  //add background picture
		    JLabel hy = new JLabel(img);
		    jDialog.getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
		    hy.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 
		   jDialog.setSize(980,440);
		   jDialog.setLocationRelativeTo(null);
		   jDialog.setVisible(true);
		   jDialog.setResizable(false);
	}
		
	 public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Ok")){
			addMassageinfo(ta3[0].getText());
			new Popup(new JFrame(),2);
			jDialog.dispose();
		}else if(e.getActionCommand().equals("Clear")){
			clear();
		}else{
			for(int i = 0; i<3; i++){
        		if(e.getSource() == button1[i]){
        			if(selectstatus[i] == false){
	        			button1[i].setBackground(Color.RED);
	        			if(itemid < 3){
	        				button1[itemid].setBackground(Color.GREEN);
	        				selectstatus[itemid] = false;
	        			}
        		        ii = i;
        		        itemid = i;
        		        selectstatus[itemid] = true;
        			}
        			clear = false;
        			calculatePrice();
        		    showtable();
        		}
        	}
			for(int i = 0; i<3; i++){
        		if(e.getSource() == button2[i]){
        			if(selectstatus2[i] == false){
	        			button2[i].setBackground(Color.RED);
	        			if(itemid2 < 3){
	        				button2[itemid2].setBackground(Color.GREEN);
	        				selectstatus2[itemid2] = false;
	        			}
        		        jj = i;
        		        itemid2 = i;
        		        selectstatus2[i] = true;
        			}
        			clear = false;
        			calculatePrice();
        		    showtable();
        		}
        	}
        }
	 }
	 
	 public void showtable(){
			
			DefaultTableModel defaultModel = (DefaultTableModel)table.getModel();
			defaultModel.setRowCount(0);
			defaultModel.setColumnIdentifiers(new Object[]{"Item","time","price"});
			table.getTableHeader().setReorderingAllowed(false);
			table.setModel(defaultModel);
			table.getColumnModel().getColumn(0).setPreferredWidth(280);
			table.setFont(font);
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
			r.setHorizontalAlignment(JLabel.CENTER);   
			table.setDefaultRenderer(Object.class, r);
			
			if(clear == false){
			defaultModel.addRow(new Object[]{buttonString1[ii],buttonString2[jj],price[mm]});
			}
	 }	
	 
	 public void calculatePrice(){
		 
		 for(int i = 0; i < 3; i++){
			 for(int j = 0; j < 3; j++){
				 if(ii == i & jj == j){
					 mm = ii*3 + jj;
				 }	 
			 }
		 }
		 ta3[1].setText(Double.toString(price[mm]));
	 }
	 public void addMassageinfo(String roomnum){
			try{
				//1. Get a connection to database
				//Connection myConn = DriverManager.getConnection(url, user, password);
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
				myStmt2.setDouble(1,price[mm] + fee_service);
				myStmt2.setString(2,roomnum);
				//3. Execute SQL 
				myStmt2.executeUpdate();
			}	
			catch (Exception exc) {
				exc.printStackTrace();
			}
	 }
	 
	 public void clear(){
		 //ii = 3;
		 //jj = 3;
		 //mm = 9;
		 ta3[0].setText("");
		 ta3[1].setText("");
		 clear = true;
		 showtable();
	 }
}

