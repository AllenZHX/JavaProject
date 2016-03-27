import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;  
import java.util.Timer;  
import java.util.TimerTask;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class JPaneTest extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JButton[] button1 = new JButton[1];
	JButton[] button2 = new JButton[5];
	JButton[] button5 = new JButton[4];
	JButton[] button6 = new JButton[3];
	JButton[] button22 = new JButton[27];
	JLabel[] label1 = new JLabel[3];
	JLabel[] label2 = new JLabel[4];
	JLabel[] label5 = new JLabel[5];
	JLabel[] label6 = new JLabel[5];
	JTextArea[] ta1 = new JTextArea[2];
	JTextArea[] ta5 = new JTextArea[4];
	JTextArea[] ta6 = new JTextArea[4];
	JTable table = new JTable();
	ScrollPane scrollPane = new ScrollPane();
	String[] buttonString1 = {"OK"};
	String[] buttonString2 = {"Family Room","Double Room","Single Room","Check Current Rooms' Status","Canel"};
	String[] buttonString22 = {"F1","F2","D1","D2","D3","D4","S1","S2","S3",
			                  "F3","F4","D5","D6","D7","D8","S4","S5","S6",
			                  "F5","F6","D9","D10","D11","D12","S7","S8","S9"};
	String[] buttonString5 = {"Settle","confirm","Finish","Canel"};
	String[] buttonString6 = {"Settle","OK","Canel"};
	String[] labelString1 = {"Check-in", "Name:","ID:"};
	String[] labelString2 = {"Booking"};
	String[] labelString5 = {"Check-out","Room #: ","Total: ","Paid-up: ","Change: "};
	String[] labelString6 = {"ORDER","Room #: ","Item: ","Num: ","Total: "};
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	Font font1 =  new Font("Times new Roman", Font.BOLD, 34);
	Font font2 =  new Font("Times new Roman", Font.BOLD, 14);
	
	JLabel timeLabel;  
    JLabel displayArea;  
    String DEFAULT_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";  
    String time;  
    int ONE_SECOND = 1000;
    int Ok = 1;
    int Can = 1;
    String room = "";
    String status = "";
    Boolean[] roomstatus = {false,false,false,false,false,false,false,false,false,
    						false,false,false,false,false,false,false,false,false,
    						false,false,false,false,false,false,false,false,false};
    
	
	public JPaneTest() {
		setTitle("Hotel management System");
		Container c = getContentPane();
		c.setLayout(new GridLayout(2,3,10,10));
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel(new GridLayout(2,1,5,15));
		JPanel p4 = new JPanel(new GridLayout(3,1,5,15));
		JPanel p5 = new JPanel(new GridLayout(2,2,5,15));
		JPanel p6 = new JPanel(new GridLayout(1,2,5,15));
		
		//******for Panel1(Check-in Part)******
		for(int i = 0; i < 1; i++) {
		    button1[i] = new JButton();
		    button1[i].setText(buttonString1[i]);
		    button1[i].setFont(font2);
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
		}
		p1.setLayout(null);
		button1[0].setBounds(250,320,100,40);
		label1[0].setBounds(120,30,160,50);
		label1[1].setBounds(40,120,80,50);
		label1[2].setBounds(40,190,80,50);
		ta1[0].setBounds(140,130,180,30);
		ta1[1].setBounds(140,200,180,30);
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
		    button2[i].addActionListener(this);
		}
		    label2[0] = new JLabel();
		    label2[0].setText(labelString2[0]);
		    label2[0].setFont(font1);
		p2.setLayout(null);
		button2[0].setBounds(50,120,80,80);
		button2[1].setBounds(155,120,80,80);
		button2[2].setBounds(260,120,80,80);
		button2[4].setBounds(100,320,100,40);
		button2[3].setBounds(72,230,240,60);
		label2[0].setBounds(120,30,160,50);

		for(int i = 0; i < 5; i++)
			p2.add(button2[i]);
		p2.add(label2[0]);
		
		// ******for Panel3(show data list Part)******
		
		DefaultTableModel defaultModel = (DefaultTableModel) table.getModel();
		defaultModel.setRowCount(0);
		defaultModel.setColumnIdentifiers(new Object[]{"items","storage","price"});
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(defaultModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setViewportView(table);
		p3.setLayout(null);
		scrollPane.setBounds(30,50,340,300);
		scrollPane.setFont(font);
		p3.add(scrollPane);
		
		// ******for Panel4(standard time Part)******
		
		timeLabel = new JLabel("CurrentTime: ");  
        displayArea = new JLabel();  
        timeLabel.setFont(font);
        displayArea.setFont(font);
        p4.setLayout(null);
        timeLabel.setBounds(120,100,140,50);
        displayArea.setBounds(100,200,180,50);
        configTimeArea();
		p4.add(timeLabel);
		p4.add(displayArea);
		
		// ******for Panel5(Check-out Part)******
		for(int i = 0; i < 4; i++) {
		    button5[i] = new JButton();
		    button5[i].setText(buttonString5[i]);
		    button5[i].setFont(font2);
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
		}
		p5.setLayout(null);
		button5[0].setBounds(260,105,80,40);
		button5[1].setBounds(250,215,100,40);
		button5[2].setBounds(40,310,100,40);
		button5[3].setBounds(240,310,100,40);
		label5[0].setBounds(120,20,160,50);
		label5[1].setBounds(40,80,80,50);
		label5[2].setBounds(40,120,80,50);
		label5[3].setBounds(40,190,80,50);
		label5[4].setBounds(40,230,80,50);
		ta5[0].setBounds(140,90,100,30);
		ta5[1].setBounds(140,200,100,30);
		ta5[2].setBounds(140,130,100,30);
		ta5[3].setBounds(140,240,100,30);
		for(int i = 0; i < 4; i++)
			p5.add(button5[i]);
		for(int i = 0; i < 5; i++) 
			p5.add(label5[i]);
		for(int i = 0; i < 4; i++) 
			p5.add(ta5[i]);
		
		// ******for Panel6(Order service Part)******
		for(int i = 0; i < 3; i++) {
		    button6[i] = new JButton();
		    button6[i].setText(buttonString6[i]);
		    button6[i].setFont(font2);
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
		}
		p6.setLayout(null);
		button6[0].setBounds(250,155,100,40);
		button6[1].setBounds(40,310,100,40);
		button6[2].setBounds(240,310,100,40);
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
		
		// add all of Panel to Container c
		c.add(p1);
		c.add(p2);
		c.add(p3);
		c.add(p4);
		c.add(p5);
		c.add(p6);
		setSize(1200,800);
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
			getRoomStatus();      // search database, get room status (empty or fill) (use Boolean[] status = new Boolean[27] to store the info)
            Popup_roomStatus hw = new Popup_roomStatus(this);   // show the current room status
        }
        if(ae.getSource() == button2[4]) {
        	Can = 0;
        	cancel();
        }
        if(ae.getSource() == button5[0]) {
			// calculateTotalFee();     //  get all of fees from database and sum them then show it on the interface
        }
		if(ae.getSource() == button5[1]){
            // calculateChange();    // according to paid-up money, get the amount of the change
        }
		if(ae.getSource() == button5[2]){
            // checkout();       // detele customer's info, update the room status
			Ok = 2;
			Popup pp = new Popup(this);
        }
		if(ae.getSource() == button5[3]){
            Can = 2;
			cancel();
        }
        if(ae.getSource() == button6[0]) {
        	// calculateServPrice();     // according to what and how many food or drink customer order, show the money amount in the interface.
        }
        if(ae.getSource() == button6[1]) {
        	Ok = 3;
        	Popup pp = new Popup(this);
        	// addServiceinfo();     // add service info to database
        }
        if(ae.getSource() == button6[2]) {
			Can = 3;
        	cancel();
        }
        //*******************************************************************
        
        
	}
	
	public void createANewUser() {
		String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "root";
		String password = ",26187108hoog";
		try{
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql = "insert into checkin (name,idnum,room,status) values(?,?,?,?)";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,ta1[0].getText());
			myStmt.setString(2,ta1[1].getText());
			myStmt.setString(3,room);
			myStmt.setString(4,status);
			//3. Execute SQL 
			myStmt.executeUpdate();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void getRoomStatus() {   //should use JDBC to get the data, not this method!
		for(int i = 0; i < 27; i++){
			button22[i] = new JButton();
			if(roomstatus[i] == true){
				button22[i].setBackground(Color.RED);
			}
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
	       String[] textinfo = {"      Successfully Check-in!","Successfully booked a room!",
	    		                "         Check-out Done!","     successfully purchase!"};
	       for (int i = 0; i < 4; i++){
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
				    button22[i].setText(buttonString22[i]);
				    button22[i].setFont(font);
				    /*
				    if (status[i] == true){
				          button22[i].setBackground(Color.RED);
				    }
				    */
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
		        		        room = buttonString22[i];
		        		        status = "check-in!";
			        		    roomstatus[i] = true;
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
