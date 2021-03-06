package MyHotel;
/*
 * @ author: Hongxiang Zheng
 * 
 * ***************************************************
 * **********   food&drink popup window   ************
 * ***************************************************
 * 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class Food extends JDBCinfo implements ActionListener {
	private JDialog jDialog=null; 
	private JButton[] button33 = new JButton[30];
	private JButton[] button333 = new JButton[3];
	private JTextArea[] ta333 = new JTextArea[2];
	private JLabel[] label333 = new JLabel[2];
	private String[] labelString = {"Room#: ", "Total: "};
    private String[] buttonString33 = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private String[] buttonString333 = {"settle","Ok","Clear"};
    private int[] stocknum = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private boolean[] selectstatus = {false,false,false,false,false,false,false,false,false,false,false,false,
							    	  false,false,false,false,false,false,false,false,false,false,false,false,
							    	  false,false,false,false,false,false,};
    private boolean clear = false;
    private int itemid = 99;
    private int[] idlist = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int[] stock = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private JTable table = new JTable();
	private JScrollPane scrollPane = new JScrollPane(table);
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	Customer ccc = new Customer();
	private String[] item = {"","","","","","","","","","","","","","",""};
	private double[] price = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
	private int temp = 0;
	
	public void getitemname(){
		Customer cccc = new Customer();
		@SuppressWarnings("rawtypes")
		ArrayList list = cccc.selectAll2();
		for(int i = 0; i < list.size(); i++){
			cccc = (Customer)list.get(i);
			buttonString33[i] = cccc.getitems();
			stocknum[i] = cccc.getstock();
		}
	}
	public void Popup_service(JFrame jFrame){
		  
		   JPanel p33 = new JPanel(new GridLayout(6,5,10,15));
		   for(int i = 0; i < 30; i++) {
		    	button33[i] = new JButton();
			    button33[i].setText(buttonString33[i]);
			    button33[i].setFont(font);
			    button33[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button33[i].setBackground(Color.GREEN);
			    if(stocknum[i] <= 3){
			    	button33[i].setEnabled(false);
			    }
			    button33[i].addActionListener(this);
		   }
		   for(int i = 0; i < 30; i++)
		    	p33.add(button33[i]);
		   p33.setBounds(5,5,950,300);
		   p33.setOpaque(false);
		   p33.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   ///////////////////////////////////////
		   JPanel p333 = new JPanel();
		   for(int i = 0; i < 3; i++) {
			    button333[i] = new JButton();
			    button333[i].setText(buttonString333[i]);
			    button333[i].setFont(font);
			    button333[i].setContentAreaFilled(false);
			    button333[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button333[i].addActionListener(this);
			}
			for(int i = 0; i < 2; i++) {
			    label333[i] = new JLabel();
			    label333[i].setText(labelString[i]);
			    label333[i].setFont(font);
			}
			for(int i = 0; i < 2; i++) {
			    ta333[i] = new JTextArea();
			    ta333[i].setFont(font);
			    ta333[i].setBorder(BorderFactory.createLoweredBevelBorder());
			}
			p333.setLayout(null);
			button333[0].setBounds(750,170,100,40);
			button333[1].setBounds(670,230,100,40);
			button333[2].setBounds(820,230,100,40);
			label333[0].setBounds(660,50,160,50);
			label333[1].setBounds(660,100,160,50);
			ta333[0].setBounds(750,60,100,30);
			ta333[1].setBounds(750,110,100,30);
			for(int i = 0; i < 3; i++)
				p333.add(button333[i]);
			for(int i = 0; i < 2; i++) 
				p333.add(label333[i]);
			for(int i = 0; i < 2; i++) 
				p333.add(ta333[i]);
			scrollPane.setViewportView(table);
			scrollPane.setBounds(20,25,600,250);
			scrollPane.setFont(font);
			p333.add(scrollPane);
			p333.setOpaque(false);
		   p333.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   p333.setBounds(5,310,950,300);
		   jDialog=new JDialog(jFrame,"Service List",true);
		   jDialog.getContentPane().setLayout(null);
		   jDialog.getContentPane().add(p33);
		   jDialog.getContentPane().add(p333);
		   ((JPanel)jDialog.getContentPane()).setOpaque(false);
			ImageIcon img = new ImageIcon("lib/7.jpg");  //add background picture
		    JLabel hy = new JLabel(img);
		    jDialog.getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
		    hy.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 
		   jDialog.setSize(980,660);
		   jDialog.setLocationRelativeTo(null);
		   jDialog.setVisible(true);
		   jDialog.setResizable(false);
	}
		
	 public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("settle")){
			calculateServPrice();
		}else if(e.getActionCommand().equals("Ok")){
			addServiceinfo(ta333[0].getText(),ta333[1].getText(),idlist);
			new Popup(new JFrame(),2);
			jDialog.dispose();
		}else if(e.getActionCommand().equals("Clear")){
			cancel();
		}else{
			for(int i = 0; i<30; i++){
        		if(e.getSource() == button33[i]){
        			if(selectstatus[i] == false){
	        			button33[i].setBackground(Color.RED);
	        			if(itemid < 30){
	        				button33[itemid].setBackground(Color.GREEN);
	        				selectstatus[itemid] = false;
	        			}
        		        itemid = i;
        		        selectstatus[i] = true;
        			}
        			temp++;
        			idlist[temp-1] = itemid+1;
        			clear = false;
        		    showtable();
        		}
        	}
        }
	 }
	 public void showtable(){
			
			DefaultTableModel defaultModel = (DefaultTableModel)table.getModel();
			defaultModel.setRowCount(0);
			defaultModel.setColumnIdentifiers(new Object[]{"Item","num","price"});
			table.getTableHeader().setReorderingAllowed(false);
			table.setModel(defaultModel);
			table.getColumnModel().getColumn(0).setPreferredWidth(15);
			table.getColumnModel().getColumn(1).setPreferredWidth(30);
			table.setFont(font);
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
			r.setHorizontalAlignment(JLabel.CENTER);   
			table.setDefaultRenderer(Object.class, r);
			
			ArrayList<?> list = ccc.selectAll2();
			for(int i = 0; i < list.size(); i++){
				ccc = (Customer)list.get(i);
				if(buttonString33[itemid].equals(ccc.getitems()) & clear == false){
					item[temp-1] = ccc.getitems();
					price[temp-1] = ccc.getprice();
					for(int j = 0; j < temp; j++){
						defaultModel.addRow(new Object[]{item[j],1,price[j]});
					}
				}
			}			
		}
	 
	 public void calculateServPrice(){
		    double sum = 0.0;
		    for(int i = 0; i < 15; i++){
		    	sum += price[i];
		    }
		    ta333[1].setText(Double.toString(sum));
		    
	 }
	 
	 public void addServiceinfo(String roomnum,String newfee_service, int[] idlist){
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
					myStmt2.setDouble(1,Double.parseDouble(newfee_service) + fee_service);
					myStmt2.setString(2,roomnum);
					//3. Execute SQL 
					myStmt2.executeUpdate();
					
					int i = 1;
					for(int n = 0; n < temp; n++){
						String sql3 = "select * from servicelist";
						Statement myStmt3 = myConn.createStatement();
						ResultSet myRs3 = myStmt3.executeQuery(sql3);
						while (myRs3.next()) {
							if(idlist[n] == i){
								stock[n] = myRs3.getInt("stock");
							}
							i++;
							if(i>30){i=1;}
						}	
						String sql4 = "update servicelist set stock = ? where id = ?";
						PreparedStatement myStmt4 = myConn.prepareStatement(sql4);
						myStmt4.setInt(1,stock[n]-1);
						myStmt4.setInt(2,idlist[n]);
						myStmt4.executeUpdate();
					}	
				
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
	}
	 
	 public void cancel(){
		 ta333[0].setText("");
		 ta333[1].setText("");
		 for(int i = 0; i < temp; i++){
			 idlist[i] = 0;
			 stock[i] = 0;
			 item[i] = "";
			 price[i] = 0.0;
		 }
		 clear = true;
		 temp = 0;
		 showtable();
	 }

}

