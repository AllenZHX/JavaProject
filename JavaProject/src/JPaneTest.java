import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;  
import java.util.Date;  
import java.util.Timer;  
import java.util.TimerTask;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.io.File;

public class JPaneTest extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JButton[] button1 = new JButton[1];
	JButton[] button2 = new JButton[5];
	JButton[] button3 = new JButton[3];
	JButton[] button5 = new JButton[4];
	JButton[] button6 = new JButton[3];
	JButton[] button22 = new JButton[27];
	JLabel[] label1 = new JLabel[3];
	JLabel[] label5 = new JLabel[5];
	JLabel[] label6 = new JLabel[5];
	JTextArea[] ta1 = new JTextArea[2];
	JTextArea[] ta5 = new JTextArea[4];
	JTextArea[] ta6 = new JTextArea[4];
	JTable table = new JTable();
	JScrollPane scrollPane = new JScrollPane(table);
	String[] buttonString1 = {"OK"};
	String[] buttonString2 = {"Family","Double","Single","Check Current Rooms' Status","Clear"};
	String[] buttonString22 = {"F1","F2","D1","D2","D3","D4","S1","S2","S3",
			                  "F3","F4","D5","D6","D7","D8","S4","S5","S6",
			                  "F5","F6","D9","D10","D11","D12","S7","S8","S9"};
	String[] buttonString3 = {"userinfo","service","payment"};
	String[] buttonString5 = {"Settle","confirm","Finish","Clear"};
	String[] buttonString6 = {"Settle","OK","Clear"};
	String[] labelString1 = {"Check-in", "Name:","ID:"};
	String[] labelString2 = {"Booking"};
	String[] labelString5 = {"Check-out","Room #: ","Total: ","Paid-up: ","Change: "};
	String[] labelString6 = {"ORDER","Room #: ","Item: ","Num: ","Total: "};
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	Font font1 =  new Font("Times new Roman", Font.BOLD, 34);
	Font font2 =  new Font("Times new Roman", Font.BOLD, 14);
	Font font3 =  new Font("Calibri", Font.PLAIN, 14);
	Font font4 =  new Font("Calibri", Font.PLAIN, 50);
	JLabel timeLabel;  
    JLabel displayArea;  
    String DEFAULT_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";  
    String time;  
    int ONE_SECOND = 1000;
    int Ok = 1;
    int Can = 1;
    int roomid = 99;
    int num = 0;
    int listnum = 0;
    String room = "";
    String status = "";
    Boolean[] roomstatus = {false,false,false,false,false,false,false,false,false,
    						false,false,false,false,false,false,false,false,false,
    						false,false,false,false,false,false,false,false,false};
    String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	String user = "root";
	String password = ",26187108hoog";
	Double[] roomprice = {220.0,160.0,90.0};
	Double feeofroom = 0.0;
	Double feeofservice = 0.0;
	Double totalfee = 0.0;
	JPanel p1 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel(); 
	
	
	public JPaneTest() {
		setTitle("Hotel management System");
		Container c = getContentPane();
		//c.setLayout(new GridLayout(2,3,10,10));
		c.setLayout(new FlowLayout(3,10,10));
		//******for Panel1(Check-in Part)******
		for(int i = 0; i < 1; i++) {
		    button1[i] = new JButton();
		    button1[i].setText(buttonString1[i]);
		    button1[i].setFont(font2);
		    button1[i].setContentAreaFilled(false);
		    button1[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button1[i].addActionListener(this);
		}
		for(int i = 0; i < 3; i++) {
		    label1[i] = new JLabel();
		    label1[i].setText(labelString1[i]);
		    label1[i].setFont(font);
		    if (i==0)
		    	label1[i].setFont(font1);
		}
		for(int i = 0; i < 2; i++) {
		    ta1[i] = new JTextArea();
		    ta1[i].setFont(font);
		    ta1[i].setBorder(BorderFactory.createLoweredBevelBorder());
		}
		p1.setLayout(null);
		button1[0].setBounds(40,220,100,40);
		label1[0].setBounds(270,10,160,50);
		label1[1].setBounds(20,70,80,50);
		label1[2].setBounds(20,140,80,50);
		ta1[0].setBounds(110,80,180,30);
		ta1[1].setBounds(110,150,180,30);
		for(int i = 0; i < 1; i++)
			p1.add(button1[i]);
		for(int i = 0; i < 3; i++) 
			p1.add(label1[i]);
		for(int i = 0; i < 2; i++) 
			p1.add(ta1[i]);
		// ******for Panel2(Booking Part)*******
		for(int i = 0; i < 5; i++) {
		    button2[i] = new JButton();
		    button2[i].setText(buttonString2[i]);
		    button2[i].setFont(font2);
		    button2[i].setContentAreaFilled(false);
		    button2[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button2[i].addActionListener(this);
		}
		p1.setLayout(null);
		button2[0].setBounds(340,80,90,80);
		button2[1].setBounds(445,80,90,80);
		button2[2].setBounds(550,80,90,80);
		button2[4].setBounds(200,220,100,40);
		button2[3].setBounds(340,180,300,70);

		for(int i = 0; i < 5; i++)
			p1.add(button2[i]);
		p1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p1.setPreferredSize(new Dimension(660, 270));
		p1.setOpaque(false);
		// ******for Panel3(show data list Part)******
		for(int i = 0; i < 3; i++) {
		    button3[i] = new JButton();
		    button3[i].setText(buttonString3[i]);
		    button3[i].setFont(font2);
		    button3[i].setContentAreaFilled(false);
		    button3[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button3[i].addActionListener(this);
		}
		p3.setLayout(null);
		button3[0].setBounds(50,200,100,30);
		button3[1].setBounds(355,200,100,30);
		button3[2].setBounds(680,200,100,30);
		for(int i = 0; i < 3; i++)
			p3.add(button3[i]);
		p3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p3.setPreferredSize(new Dimension(830,240));
		p3.setOpaque(false);
		// ******for Panel4(standard time Part)******
		
		timeLabel = new JLabel("CurrentTime: ");  
        displayArea = new JLabel();  
        timeLabel.setFont(font);
        displayArea.setFont(font);
        p4.setLayout(null);
        timeLabel.setBounds(120,50,140,50);
        displayArea.setBounds(100,120,180,50);
        configTimeArea();
		p4.add(timeLabel);
		p4.add(displayArea);
		p4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p4.setPreferredSize(new Dimension(380, 240));
		p4.setOpaque(false);
		// ******for Panel5(Check-out Part)******
		for(int i = 0; i < 4; i++) {
		    button5[i] = new JButton();
		    button5[i].setText(buttonString5[i]);
		    button5[i].setFont(font2);
		    button5[i].setContentAreaFilled(false);
		    button5[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button5[i].addActionListener(this);
		}
		for(int i = 0; i < 5; i++) {
		    label5[i] = new JLabel();
		    label5[i].setText(labelString5[i]);
		    label5[i].setFont(font);
		    if (i==0)
		    	label5[i].setFont(font1);
		}
		for(int i = 0; i < 4; i++) {
		    ta5[i] = new JTextArea();
		    ta5[i].setFont(font);
		    ta5[i].setBorder(BorderFactory.createLoweredBevelBorder());
		}
		p5.setLayout(null);
		button5[0].setBounds(120,170,100,40);
		button5[1].setBounds(400,170,100,40);
		button5[2].setBounds(120,220,100,40);
		button5[3].setBounds(400,220,100,40);
		label5[0].setBounds(200,10,160,50);
		label5[1].setBounds(20,70,80,50);
		label5[2].setBounds(20,110,80,50);
		label5[3].setBounds(300,70,80,50);
		label5[4].setBounds(300,110,80,50);
		ta5[0].setBounds(120,80,100,30);
		ta5[1].setBounds(120,120,100,30);
		ta5[2].setBounds(400,80,100,30);
		ta5[3].setBounds(400,120,100,30);
		for(int i = 0; i < 4; i++)
			p5.add(button5[i]);
		for(int i = 0; i < 5; i++) 
			p5.add(label5[i]);
		for(int i = 0; i < 4; i++) 
			p5.add(ta5[i]);
		p5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p5.setPreferredSize(new Dimension(550, 270));
		p5.setOpaque(false);
		// ******for Panel6(Order service Part)******
		for(int i = 0; i < 3; i++) {
		    button6[i] = new JButton();
		    button6[i].setText(buttonString6[i]);
		    button6[i].setFont(font2);
		    button6[i].setContentAreaFilled(false);
		    button6[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button6[i].addActionListener(this);
		}
		for(int i = 0; i < 5; i++) {
		    label6[i] = new JLabel();
		    label6[i].setText(labelString6[i]);
		    label6[i].setFont(font);
		    if (i==0)
		    	label6[i].setFont(font1);
		}
		for(int i = 0; i < 4; i++) {
		    ta6[i] = new JTextArea();
		    ta6[i].setFont(font);
		    ta6[i].setBorder(BorderFactory.createLoweredBevelBorder());
		}
		p6.setLayout(null);
		button6[0].setBounds(250,155,100,40);
		button6[1].setBounds(40,290,100,40);
		button6[2].setBounds(240,290,100,40);
		label6[0].setBounds(20,20,160,50);
		label6[1].setBounds(50,80,80,50);
		label6[2].setBounds(50,130,80,50);
		label6[3].setBounds(50,170,80,50);
		label6[4].setBounds(50,230,80,50);
		ta6[0].setBounds(140,90,100,30);
		ta6[1].setBounds(140,140,100,30);
		ta6[2].setBounds(140,180,100,30);
		ta6[3].setBounds(140,240,100,30);
		for(int i = 0; i < 3; i++)
			p6.add(button6[i]);
		for(int i = 0; i < 5; i++) 
			p6.add(label6[i]);
		for(int i = 0; i < 4; i++) 
			p6.add(ta6[i]);
		p6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p6.setPreferredSize(new Dimension(380, 345));
		p6.setOpaque(false);
		// for Panel7 (logo)
		JPanel p7 = new JPanel(); 
		JLabel label = new JLabel("Hotel Management System");
		p7.add(label,BorderLayout.CENTER);
		p7.setOpaque(false);
		((JPanel)getContentPane()).setOpaque(false);
		ImageIcon img = new ImageIcon("C:\\3.jpg");
	    JLabel hy = new JLabel(img);
	    getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
	    hy.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 
	    p7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p7.setPreferredSize(new Dimension(830, 345));
		/*p7 = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paint(Graphics g){
				ImageIcon icon = new ImageIcon("C:\\1.jpg");
				Image img = icon.getImage();
				g.drawImage(img, 0, 0, icon.getImageObserver());
			}
		};
		p7.setOpaque(false);
		JLabel label = new JLabel("Hotel Management System");
		label.setForeground(Color.white);
		p7.add(label,BorderLayout.CENTER);
		p7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p7.setPreferredSize(new Dimension(830, 345));*/
		/*JLabel label = new JLabel("Hotel Management System");
		label.setOpaque(true);
		URL url = JPaneTest.class.getResource("1.jpg");
		Icon icon = new ImageIcon(url);
		JLabel label_1 = new JLabel();
		label_1.setIcon(icon);
		p7.add(label_1,BorderLayout.CENTER);
		p7.add(label);
		p7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p7.setPreferredSize(new Dimension(830, 345));
		*/
		// add all of Panel to Container c
		c.add(p4);
		c.add(p3);
		c.add(p1);
		c.add(p5);
		c.add(p6);
		c.add(p7);
		setSize(1250,930);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == button1[0]) {
        	createANewUser();
        	Ok = 0;
        	Popup pp = new Popup(this);
        }
        if(ae.getSource() == button2[0]) {
			Popup_FRinfo pp1 = new Popup_FRinfo(this);    //family room info
        }
		if(ae.getSource() == button2[1]){
            Popup_DRinfo pp2 = new Popup_DRinfo(this);   // double room info
        }
		if(ae.getSource() == button2[2]){
            Popup_SRinfo pp3 = new Popup_SRinfo(this);   //single room info
        }
		if(ae.getSource() == button2[3]){
			getRoomStatus();      // search database, get room status (empty or fill)
            Popup_roomStatus hw = new Popup_roomStatus(this);   // show the current room status
        }
        if(ae.getSource() == button2[4]) {
        	Can = 0;
        	cancel();
        }
        if(ae.getSource() == button3[0]) {
        	listnum = 0;
        	showtable();
        }
        if(ae.getSource() == button3[1]) {
        	listnum = 1;
        	showtable();
        }
        if(ae.getSource() == button3[2]) {
        	listnum = 2;
        	showtable();
        }
        if(ae.getSource() == button5[0]) {
			calculateTotalFee();     //  get all of fees from database and sum them then show it on the interface
        }
		if(ae.getSource() == button5[1]){
            calculateChange();    // according to paid-up money, get the amount of the change
        }
		if(ae.getSource() == button5[2]){
            checkout();       // detele customer's info, update the room status
			Ok = 1;
			Popup pp = new Popup(this);
        }
		if(ae.getSource() == button5[3]){
            Can = 2;
			cancel();
        }
        if(ae.getSource() == button6[0]) {
        	calculateServPrice();     // according to what and how many food or drink customer order, show the money amount in the interface.
        }
        if(ae.getSource() == button6[1]) {
        	Ok = 2;
        	Popup pp = new Popup(this);
        	addServiceinfo();     // add service info to database
        }
        if(ae.getSource() == button6[2]) {
			Can = 3;
        	cancel();
        }
        //*******************************************************************
        
        
	}
	
	public void createANewUser() {
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "insert into checkin (name,idnum,room,status,roomid) values(?,?,?,?,?)";
			String sql2 = "insert into payment (roomnum,fee_room,fee_service,total) values(?,?,?,?)";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,ta1[0].getText());
			myStmt.setString(2,ta1[1].getText());
			myStmt.setString(3,room);
			myStmt.setString(4,status);
			myStmt.setInt(5,roomid);
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setString(1,room);
			myStmt2.setDouble(2,feeofroom);
			myStmt2.setDouble(3,0);
			myStmt2.setDouble(4,totalfee);
			//3. Execute SQL 
			myStmt.executeUpdate();
			myStmt2.executeUpdate();
			listnum = 0;
			showtable();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void getRoomStatus() {   
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "select * from checkin";
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL 
			ResultSet myRs = myStmt.executeQuery(sql);
			roomid = 99;
			for(int i = 0; i < 27; i++){
				roomstatus[i] = false;
			}
			while (myRs.next()) {
				int aa = myRs.getInt("roomid");
				roomstatus[aa] = true;
			}	
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void addServiceinfo() {
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "select * from payment";
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(sql);
			double fee_service = 0;
			String roomnum = ta6[0].getText();
			while (myRs.next()) {
				if(roomnum.equals(myRs.getString("roomnum"))){
					fee_service = myRs.getDouble("fee_service");
				}	
			}

			String sql2 = "update payment set fee_service = ? where roomnum = ?";
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setDouble(1,Double.parseDouble(ta6[3].getText()) + fee_service);
			myStmt2.setString(2,ta6[0].getText());
			//3. Execute SQL 
			myStmt2.executeUpdate();
			
			
			String sql3 = "select * from servicelist";
			Statement myStmt3 = myConn.createStatement();
			ResultSet myRs3 = myStmt3.executeQuery(sql3);
			int i = 1;
			int stock = 0;
			while (myRs3.next()) {
				int a = Integer.parseInt(ta6[1].getText());
				if(a == i){
					stock = myRs3.getInt("stock");
				}
				i++;
			}	
			String sql4 = "update servicelist set stock = ? where id = ?";
			PreparedStatement myStmt4 = myConn.prepareStatement(sql4);
			myStmt4.setInt(1,stock-num);
			myStmt4.setInt(2,Integer.parseInt(ta6[1].getText()));
			//3. Execute SQL 
			myStmt4.executeUpdate();
			
			listnum = 2;
			showtable();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public class customer {
		private int id1;
		private String name;
		private int idnum;
		private String room;
		private String status;
		private int id2;
		private String items;
		private double price;
		private int stock;
		private int id3;
		private String roomnum;
		private double fee_room;
		private double fee_service;
		private double total;
		public void setid1(int i1){id1 = i1;}
		public void setname(String nam){name = nam;}
		public void setidnum(int idnu){idnum = idnu;}
		public void setroom(String roo){room = roo;}
		public void setstatus(String statu){status = statu;}
		public int getid1(){return id1;}
		public String getname(){return name;}
		public int getidnum(){return idnum;}
		public String getroom(){return room;}
		public String getstatus(){return status;}
		
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
		
		public ArrayList selectAll1(){
			ArrayList<customer> ls = new ArrayList<customer>();
			try{
				Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from checkin";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					customer cc= new customer();
					cc.setid1(myRs.getInt("id"));
					cc.setname(myRs.getString("name"));
					cc.setidnum(myRs.getInt("idnum"));
					cc.setroom(myRs.getString("room"));
					cc.setstatus(myRs.getString("status"));
					ls.add(cc);
				}	
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			return ls;
	   }
		public ArrayList selectAll2(){
			ArrayList<customer> ls = new ArrayList<customer>();
			try{
				Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from servicelist";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					customer cc= new customer();
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
		public ArrayList selectAll3(){
			ArrayList<customer> ls = new ArrayList<customer>();
			try{
				Connection myConn = DriverManager.getConnection(url, user, password);
				String sql = "select * from payment";
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql);
				while (myRs.next()) {
					customer cc= new customer();
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
	
		
	}
	
	public void showtable(){
		
		DefaultTableModel defaultModel = (DefaultTableModel)table.getModel();
		defaultModel.setRowCount(0);
		if(listnum == 0){
			defaultModel.setColumnIdentifiers(new Object[]{"id","name","idnum","room","status"});
		}
		if(listnum == 1){
			defaultModel.setColumnIdentifiers(new Object[]{"id","items","price","stock"});
		}
		if(listnum == 2){
			defaultModel.setColumnIdentifiers(new Object[]{"id","roomnum","fee_room","fee_service","total"});
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(defaultModel);
		table.setFont(font3);
		table.setBorder(BorderFactory.createLoweredBevelBorder());
		//defaultModel.addRow(new Object[]{getId(),getItem(),getPrice(),getSrock()});
		if(listnum == 0){
			customer ccc = new customer();
			ArrayList list = ccc.selectAll1();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getid1(),ccc.getname(),
								ccc.getidnum(),ccc.getroom(),ccc.getstatus()});
			}			
		}
		if(listnum == 1){
			customer ccc = new customer();
			ArrayList list = ccc.selectAll2();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getid2(),ccc.getitems(),
								               ccc.getprice(),ccc.getstock()});
			}
		}
		if(listnum == 2){
			customer ccc = new customer();
			ArrayList list = ccc.selectAll3();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getid3(),ccc.getroomnum(),
						     ccc.getfee_room(),ccc.getfee_service(),ccc.gettotal()});
			}
		}
		scrollPane.setViewportView(table);
		p3.setLayout(null);
		scrollPane.setBounds(20,25,790,165);
		scrollPane.setFont(font);
		p3.add(scrollPane);
	}
	
	public void calculateServPrice() {
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "select * from servicelist";
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(sql);
			int i = 1;
			while (myRs.next()) {
				int a = Integer.parseInt(ta6[1].getText());
				if(a == i){
					num = Integer.parseInt(ta6[2].getText());
					double price = myRs.getDouble("price");
					double total = num * price;
					ta6[3].setText(Double.toString(total));
				}
				i++;
			}	
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void calculateTotalFee() {
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "select * from payment";
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(sql);
			double fee_service = 0;
			double fee_room = 0;
			double total = 0;
			String roomnum = ta5[0].getText();
			while (myRs.next()) {
				if(roomnum.equals(myRs.getString("roomnum"))){
					fee_service = myRs.getDouble("fee_service");
					fee_room = myRs.getDouble("fee_room");
					total = fee_service+fee_room;
					ta5[1].setText(Double.toString(total));
				}	
			}
			String sql2 = "update payment set total = ? where roomnum = ?";
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setDouble(1,total);
			myStmt2.setString(2,ta5[0].getText());
			//3. Execute SQL 
			myStmt2.executeUpdate();
			listnum = 2;
			showtable();
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}
	
	public void calculateChange() {
		double total = Double.parseDouble(ta5[1].getText());
		double paidup = Double.parseDouble(ta5[2].getText());
		double change = paidup - total;
		ta5[3].setText(Double.toString(change));
	}
	
	public void checkout(){
		try{
			Connection myConn = DriverManager.getConnection(url, user, password);
			String sql = "delete from checkin where room = ?";
			String sql2 = "delete from payment where roomnum = ?";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,ta5[0].getText());
			PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
			myStmt2.setString(1,ta5[0].getText());
			myStmt.executeUpdate();
			myStmt2.executeUpdate();
			listnum = 0;
			showtable();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void cancel() {
		if(Can == 0){
			for(int i = 0; i < 2; i++)
				ta1[i].setText("");	
		}
		if(Can == 1){
			//  cancel the booking.
		}
		if(Can == 2){
			for(int i = 0; i < 4; i++)
				ta5[i].setText("");
		}
		if(Can == 3){
			for(int i = 0; i < 4; i++)
				ta6[i].setText("");
		}
	}
	
	// for current time
	
	public void configTimeArea() {  
        Timer tmr = new Timer();  
        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);  
    }  
  
    public class JLabelTimerTask extends TimerTask {  
        SimpleDateFormat dateFormatter = new SimpleDateFormat(  
                DEFAULT_TIME_FORMAT);  
  
        @Override  
        public void run() {  
            time = dateFormatter.format(Calendar.getInstance().getTime());  
            displayArea.setText(time);  
        }  
    } 
	
    // for pop-up windows
    
    class Popup implements ActionListener{
        JDialog jDialog2=null; 
	    Popup(JFrame jFrame){
	       jDialog2=new JDialog(jFrame,"Notice",true);
	       JTextArea jt2 = new JTextArea();
	       String[] textinfo = {"      Successfully Check-in!","         Check-out Done!","     successfully purchase!"};
	       for (int i = 0; i < 3; i++){
	    	   if(Ok == i){
	    		   jt2 = new JTextArea(textinfo[i]);
	    	   }
	       }
	       jt2.setFont(font);
	       JButton jButton1=new JButton("close");
	       jButton1.addActionListener(this);
	       jDialog2.getContentPane().add(BorderLayout.SOUTH,jButton1);
	       jDialog2.getContentPane().add(jt2);
	       jDialog2.setSize(260,100);
	       jDialog2.setLocationRelativeTo(null);
	       jDialog2.setVisible(true);
	       jDialog2.setResizable(false);
	    }
	     public void actionPerformed(ActionEvent e){
	        if(e.getActionCommand().equals("close")){
	               jDialog2.dispose();
	        }
	     }	
	}
    
	class Popup_roomStatus implements ActionListener{
	        JDialog jDialog=null; 
		    Popup_roomStatus(JFrame jFrame){
			    JPanel p22 = new JPanel(new GridLayout(3,9,10,15));
			    for(int i = 0; i < 27; i++) {
			    	button22[i] = new JButton();
				    button22[i].setText(buttonString22[i]);
				    button22[i].setFont(font);
				    button22[i].setBackground(Color.GREEN);
				    if (roomstatus[i] == true){
				          button22[i].setBackground(Color.RED);
				    }
				    button22[i].addActionListener(this);
				}
			   for(int i = 0; i < 27; i++)
			    	p22.add(button22[i]);
		       jDialog=new JDialog(jFrame,"Room",true);
		       JButton jButton=new JButton("close");
		       jButton.addActionListener(this);
		       jDialog.getContentPane().add(BorderLayout.SOUTH,jButton);
		       jDialog.getContentPane().add(p22);
		       jDialog.setSize(850,350);
		       jDialog.setLocationRelativeTo(null);
		       jDialog.setVisible(true);
		       jDialog.setResizable(false);
		    }
		     public void actionPerformed(ActionEvent e){
		        if(e.getActionCommand().equals("close")){
		               jDialog.dispose();
		        }else{
		        	for(int i = 0; i<27; i++){
		        		if(e.getSource() == button22[i]){
		        			if(roomstatus[i] == false){
			        			button22[i].setBackground(Color.RED);
			        			if(roomid < 27){
			        				button22[roomid].setBackground(Color.GREEN);
			        				roomstatus[roomid] = false;
			        			}
		        		        room = buttonString22[i];
		        		        roomid = i;
		        		        status = "check-in!";
		        		        if(i == 0 || i == 1 || i == 9 || i == 10 || i == 18 || i == 19){
		        		        	feeofroom = roomprice[0];
		        		        }else if(i == 2 || i == 3 || i == 4 || i == 5 || i == 11 || i == 12 || i == 13 || i == 14 || i == 20 || i == 21 || i == 22 || i == 23){
		        		        	feeofroom = roomprice[1];
		        		        }else{
		        		        	feeofroom = roomprice[2];
		        		        }
		        		        roomstatus[i] = true;
		        			}else{
		        				
		        			}
		        		}
		        	}
		        	   
		        }
		     }
	}
	
	class Popup_FRinfo implements ActionListener{
        JDialog jDialog1=null; 
	    Popup_FRinfo(JFrame jFrame){
	       jDialog1=new JDialog(jFrame,"Room Introduction",true);
	       final JTextArea jt1 = new JTextArea("               Price: $ 220 /day\n      "
	       		                             + "    Free Wifi, air condition\n   "
	       		                             + "   Free Morning Call service\n   "
	       		                             + "      Hot water, toothbush\n     "
	       		                             + "        one king-size bed\n      "
	       		                             + "        one twin-size bed      ");
	       jt1.setFont(font);
	       JButton jButton1=new JButton("close");
	       jButton1.addActionListener(this);
	       jDialog1.getContentPane().add(BorderLayout.SOUTH,jButton1);
	       jDialog1.getContentPane().add(jt1);
	       jDialog1.setSize(300,250);
	       jDialog1.setLocationRelativeTo(null);
	       jDialog1.setVisible(true);
	       jDialog1.setResizable(false);
	    }
	     public void actionPerformed(ActionEvent e){
	        if(e.getActionCommand().equals("close")){
	               jDialog1.dispose();
	        }
	     }	
	}
	
	class Popup_DRinfo implements ActionListener{
        JDialog jDialog2=null; 
	    Popup_DRinfo(JFrame jFrame){
	       jDialog2=new JDialog(jFrame,"Room Introduction",true);
	       final JTextArea jt2 = new JTextArea("               Price: $ 160 /day\n      "
	       		                             + "    Free Wifi, air condition\n   "
	       		                             + "   Free Morning Call service\n   "
	       		                             + "      Hot water, toothbush\n     "
	       		                             + "        two Quence-size bed\n     ");
	       jt2.setFont(font);
	       JButton jButton1=new JButton("close");
	       jButton1.addActionListener(this);
	       jDialog2.getContentPane().add(BorderLayout.SOUTH,jButton1);
	       jDialog2.getContentPane().add(jt2);
	       jDialog2.setSize(300,250);
	       jDialog2.setLocationRelativeTo(null);
	       jDialog2.setVisible(true);
	       jDialog2.setResizable(false);
	    }
	     public void actionPerformed(ActionEvent e){
	        if(e.getActionCommand().equals("close")){
	               jDialog2.dispose();
	        }
	     }	
	}
	
	class Popup_SRinfo implements ActionListener{
        JDialog jDialog3=null; 
	    Popup_SRinfo(JFrame jFrame){
	       jDialog3=new JDialog(jFrame,"Room Introduction",true);
	       final JTextArea jt3 = new JTextArea("               Price: $ 90 /day\n      "
	       		                             + "    Free Wifi, air condition\n   "
	       		                             + "   Free Morning Call service\n   "
	       		                             + "      Hot water, toothbush\n     "
	       		                             + "        one Quence-size bed\n     ");
	       jt3.setFont(font);
	       JButton jButton1=new JButton("close");
	       jButton1.addActionListener(this);
	       jDialog3.getContentPane().add(BorderLayout.SOUTH,jButton1);
	       jDialog3.getContentPane().add(jt3);
	       jDialog3.setSize(300,250);
	       jDialog3.setLocationRelativeTo(null);
	       jDialog3.setVisible(true);
	       jDialog3.setResizable(false);
	    }
	     public void actionPerformed(ActionEvent e){
	        if(e.getActionCommand().equals("close")){
	               jDialog3.dispose();
	        }
	     }	
	}
	
	
	public static void main(String[] args){
		new JPaneTest();
	}

}
