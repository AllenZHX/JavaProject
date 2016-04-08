package test;

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

public class testclass extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	roominfo rr = new roominfo();
	manager mm = new manager();
	JButton[] button1 = new JButton[1];
	JButton[] button2 = new JButton[5];
	JButton[] button3 = new JButton[3];
	JButton[] button5 = new JButton[4];
	JButton[] button6 = new JButton[3];
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
	String[] buttonString3 = {"userinfo","service","payment"};
	String[] buttonString5 = {"Settle","confirm","Finish","Clear"};
	String[] buttonString6 = {"Settle","OK","Clear"};
	String[] labelString1 = {"Check-in", "Name:","ID:"};
	String[] labelString2 = {"Booking"};
	String[] labelString5 = {"Check-out","Room #: ","Total: ","Paid-up: ","Change: "};
	String[] labelString6 = {"Order","Room #: ","Item: ","Num: ","Total: "};
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	Font font1 =  new Font("Times new Roman", Font.BOLD, 34);
	Font font2 =  new Font("Times new Roman", Font.BOLD, 14);
	Font font3 =  new Font("Calibri", Font.PLAIN, 14);
	Font font4 =  new Font("Calibri", Font.PLAIN, 64);
	JLabel timeLabel;  
    JLabel displayArea;  
    String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";  
    String time;  
    int ONE_SECOND = 1000;
    int num = 0;
    String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	String user = "root";
	String password = ",26187108hoog";
	Double feeofservice = 0.0;
	Double toalfee = 0.0;
	JPanel p1 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel(); 
	JPanel p7 = new JPanel(); 
	JPanel p8 = new JPanel(); 
	JPanel p9 = new JPanel(); 
	JPanel p10 = new JPanel(); 
	
	public testclass() {
		setTitle("Hotel management System");
		Container c = getContentPane();
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
		label1[0].setBounds(20,10,160,50);
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
		label5[0].setBounds(20,10,160,50);
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
		JLabel label = new JLabel("Hotel Management System");
		p7.setLayout(null);
		label.setFont(font4);
		label.setBounds(60,100,720,100);
		p7.add(label);
		p7.setOpaque(false);
	    p7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p7.setPreferredSize(new Dimension(830, 345));
		
		//for Panel8 
		p8.setLayout(null);
		p8.setOpaque(false);
	    p8.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p8.setPreferredSize(new Dimension(295, 240));
		//for Panel9
		p9.setLayout(null);
		p9.setOpaque(false);
	    p9.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p9.setPreferredSize(new Dimension(295, 270));
		//for Panel10
		p10.setLayout(null);
		p10.setOpaque(false);
	    p10.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p10.setPreferredSize(new Dimension(295, 345));
		// add all of Panel to Container c
		c.add(p4);
		c.add(p3);
		c.add(p8);
		c.add(p1);
		c.add(p5);
		c.add(p9);
		c.add(p6);
		c.add(p7);
		c.add(p10);
		
		((JPanel)getContentPane()).setOpaque(false);
		ImageIcon img = new ImageIcon("C:\\3.jpg");  //add background picture
	    JLabel hy = new JLabel(img);
	    getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
	    hy.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 
		setSize(1550,930);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == button1[0]) {
        	mm.createANewUser(ta1[0].getText(),ta1[1].getText(),rr.getroom(),rr.getstatus(),rr.getroomid(),displayArea.getText(),rr.getfeeofroom());
        	new Popup(this,0);
        }
        if(ae.getSource() == button2[0]) {
			new Popup_Rinfo(this,0);    //family room info
        }
		if(ae.getSource() == button2[1]){
            new Popup_Rinfo(this,1);   // double room info
        }
		if(ae.getSource() == button2[2]){
            new Popup_Rinfo(this,2);   //single room info
        }
		if(ae.getSource() == button2[3]){
			rr.getRoomStatus();      // search database, get room status (empty or fill)
			rr.Popup_roomStatus(this);    // show the current room status
        }
        if(ae.getSource() == button2[4]) {
        	cancel(0);
        }
        if(ae.getSource() == button3[0]) {
        	showtable(0);
        }
        if(ae.getSource() == button3[1]) {
        	showtable(1);
        }
        if(ae.getSource() == button3[2]) {
        	showtable(2);
        }
        if(ae.getSource() == button5[0]) {
			double total = mm.calculateTotalFee(ta5[0].getText());     //  get all of fees from database and sum them then show it on the interface
			ta5[1].setText(Double.toString(total));
        }
		if(ae.getSource() == button5[1]){
            double change = mm.calculateChange(ta5[1].getText(),ta5[2].getText());    // according to paid-up money, get the amount of the change
            ta5[3].setText(Double.toString(change));
        }
		if(ae.getSource() == button5[2]){
            mm.checkout(ta5[0].getText());       // delete customer's info, update the room status
			new Popup(this,1);
        }
		if(ae.getSource() == button5[3]){
			cancel(2);
        }
        if(ae.getSource() == button6[0]) {
        	double total = mm.calculateServPrice(ta6[1].getText(),ta6[2].getText());     // according to what and how many food or drink customer order, show the money amount in the interface.
        	ta6[3].setText(Double.toString(total));
        }
        if(ae.getSource() == button6[1]) {
        	new Popup(this,2);
        	mm.addServiceinfo(ta6[0].getText(),ta6[3].getText(),ta6[1].getText());     // add service info to database
        }
        if(ae.getSource() == button6[2]) {
        	cancel(3);
        }
	}

	@SuppressWarnings("rawtypes")
	public void showtable(int listnum){
		
		DefaultTableModel defaultModel = (DefaultTableModel)table.getModel();
		defaultModel.setRowCount(0);
		if(listnum == 0){
			defaultModel.setColumnIdentifiers(new Object[]{"id","name","idnum","room","status","intime"});
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

		if(listnum == 0){
			customer ccc = new customer();
			ArrayList list = ccc.selectAll1();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getid1(),ccc.getname(),
								ccc.getidnum(),ccc.getroom(),ccc.getstatus(),ccc.getintime()});
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
	/////////////
	
	public void cancel(int Can) {
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
	
	public static void main(String[] args){
		new testclass();
	}

}

