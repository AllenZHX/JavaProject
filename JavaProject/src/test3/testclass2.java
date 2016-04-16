package test3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;  
import java.util.Date;  
import java.util.Timer;  
import java.util.TimerTask;  
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class testclass2 extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	roominfo rr = new roominfo();
	manager mm = new manager();
	booking bb = new booking();
	JButton[] button1 = new JButton[1];
	JButton[] button2 = new JButton[5];
	JButton[] button3 = new JButton[5];
	JButton[] button5 = new JButton[4];
	JButton[] button6 = new JButton[3];
	JButton[] button8 = new JButton[3];
	JButton[] button9 = new JButton[3];
	JLabel[] label1 = new JLabel[3];
	JLabel[] label5 = new JLabel[5];
	JLabel[] label9 = new JLabel[8];   //////
	JTextArea[] ta1 = new JTextArea[2];
	JTextArea[] ta5 = new JTextArea[4];
	JTextArea[] ta9 = new JTextArea[8];   /////////
	JComboBox[] jcb = new JComboBox[6];
	JTable table = new JTable();
	JScrollPane scrollPane = new JScrollPane(table);
	String[] buttonString1 = {"OK"};
	String[] buttonString2 = {"Family","Double","Single","Check Current Rooms' Status","Clear"};
	String[] buttonString3 = {"userinfo","service","payment","checkout","booking"};
	String[] buttonString5 = {"Settle","confirm","Finish","Clear"};
	String[] buttonString6 = {"Food and Drink","Massage","Pick-up"};
	String[] buttonString8 = {"food", "massage","Pick-up"};
	String[] buttonString9 = {"Ok", "Clear","Check Current Rooms' Status"};
	String[] labelString1 = {"Check-in", "Name:","ID:"};
	String[] labelString2 = {"Booking"};
	String[] labelString5 = {"Check-out","Room #: ","Total: ","Paid-up: ","Change: "};
	String[] labelString6 = {"Order","Room #: ","Item: ","Num: ","Total: "};
	String[] labelString9 = {"Booking","Name: ","ID:","From: ","To: ","Year","Month","Day"};
	String[] jcbString_year = {"2016","2017","2018","2019","2020"};
	String[] jcbString_mon = {"01","02","03","04","05","06","07","08","09","10","11","12"};
	String[] jcbString_day = {"01","02","03","04","05","06","07","08","09","10",
							  "11","12","13","14","15","16","17","18","19","20",
							  "21","22","23","24","25","26","27","28","29","30","31"};
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	Font font1 =  new Font("Times new Roman", Font.BOLD, 34);
	Font font2 =  new Font("Times new Roman", Font.BOLD, 14);
	Font font3 =  new Font("Calibri", Font.PLAIN, 14);
	Font font4 =  new Font("Calibri", Font.PLAIN, 64);
	Font font5 =  new Font("Calibri", Font.PLAIN, 28);
	JLabel timeLabel;  
    JLabel displayArea;  
    String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";  
    String time;  
    int ONE_SECOND = 1000;
	JPanel p1 = new JPanel();
	JPanel p9 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel(); 
	JPanel p7 = new JPanel();  
	JPanel p8 = new JPanel(); 
	
	public testclass2() {
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
		button1[0].setBounds(240,220,100,40);
		label1[0].setBounds(220,10,160,50);
		label1[1].setBounds(220,70,80,50);
		label1[2].setBounds(220,140,80,50);
		ta1[0].setBounds(310,80,180,30);
		ta1[1].setBounds(310,150,180,30);
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
		button2[0].setBounds(540,80,90,80);
		button2[1].setBounds(645,80,90,80);
		button2[2].setBounds(750,80,90,80);
		button2[4].setBounds(400,220,100,40);
		button2[3].setBounds(540,180,300,70);

		for(int i = 0; i < 5; i++)
			p1.add(button2[i]);
		p1.setOpaque(false);
		///////////////////////////////////////////////////

		for(int i = 0; i < 3; i++) {
		    button9[i] = new JButton();
		    button9[i].setText(buttonString9[i]);
		    button9[i].setFont(font2);
		    button9[i].setContentAreaFilled(false);
		    button9[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button9[i].addActionListener(this);
		}
		for(int i = 0; i < 8; i++) {
		    label9[i] = new JLabel();
		    label9[i].setText(labelString9[i]);
		    label9[i].setFont(font);
		    if (i==0)
		    	label9[i].setFont(font1);
		}
		for(int i = 0; i < 8; i++) {
		    ta9[i] = new JTextArea();
		    ta9[i].setFont(font);
		    ta9[i].setBorder(BorderFactory.createLoweredBevelBorder());
		}
		for(int i = 0; i < 6; i++) {
			jcb[i] = new JComboBox<String>();
			jcb[i].setEnabled(true);
			if(i == 0 | i == 3){
				for(int j = 0; j < 5; j++)
					jcb[i].addItem(jcbString_year[j]);
			}else if(i==1 | i==4){
				for(int j = 0; j < 12; j++)
					jcb[i].addItem(jcbString_mon[j]);
			}else{
				for(int j = 0; j < 31; j++)
					jcb[i].addItem(jcbString_day[j]);
			}
			if(i==0|i==1){
			jcb[i].addItemListener(new ItemListener(){
                 public void itemStateChanged(ItemEvent event){
                	 if(event.getStateChange() == ItemEvent.SELECTED){
                		 int y = jcb[0].getSelectedIndex();
         				 int m = jcb[1].getSelectedIndex();
         				 int yy = Integer.parseInt(jcbString_year[y]);
         				 int mm = Integer.parseInt(jcbString_mon[m]);
         				 Calendar calendar = Calendar.getInstance();
         				 calendar.set(Calendar.YEAR,yy);   
         				 calendar.set(Calendar.MONTH,mm-1);   
         				 int  maxDate = calendar.getActualMaximum(Calendar.DATE);
         				 for(int j = 0; j < maxDate; j++){
         					jcb[2].addItem(jcbString_day[j]);
         				 }
         				 int count = jcb[2].getItemCount() - maxDate;
         				 for(int j = 0; j < count; j++){
         					jcb[2].removeItemAt(1);
         				 }
                     }
                 }
             });
			}
			if(i==3|i==4){
				jcb[i].addItemListener(new ItemListener(){
	                 public void itemStateChanged(ItemEvent event){
	                	 if(event.getStateChange() == ItemEvent.SELECTED){
	                		 int y = jcb[3].getSelectedIndex();
	         				 int m = jcb[4].getSelectedIndex();
	         				 int yy = Integer.parseInt(jcbString_year[y]);
	         				 int mm = Integer.parseInt(jcbString_mon[m]);
	         				 Calendar calendar = Calendar.getInstance();
	         				 calendar.set(Calendar.YEAR,yy);   
	         				 calendar.set(Calendar.MONTH,mm-1);   
	         				 int  maxDate = calendar.getActualMaximum(Calendar.DATE);
	         				 for(int j = 0; j < maxDate; j++){
	         					jcb[5].addItem(jcbString_day[j]);
	         				 }
	         				 int count = jcb[5].getItemCount() - maxDate;
	         				 for(int j = 0; j < count; j++){
	         					jcb[5].removeItemAt(1);
	         				 }
	                     }
	                 }
	             });
			}
		    jcb[i].setFont(font);
		    jcb[i].setBorder(BorderFactory.createLoweredBevelBorder());
		}
		
		p9.setLayout(null);
		button9[0].setBounds(210,220,100,40);
		button9[1].setBounds(370,220,100,40);
		button9[2].setBounds(620,180,330,80);
		label9[0].setBounds(200,10,160,50);
		label9[1].setBounds(200,70,80,50);
		label9[2].setBounds(200,140,80,50);
		jcb[0].setBounds(670,70,80,30);
		jcb[1].setBounds(770,70,80,30);
		jcb[2].setBounds(870,70,80,30);
		jcb[3].setBounds(670,130,80,30);
		jcb[4].setBounds(770,130,80,30);
		jcb[5].setBounds(870,130,80,30);
		label9[3].setBounds(590,60,80,50);
		label9[4].setBounds(590,120,80,50);
		label9[5].setBounds(690,15,80,50);
		label9[6].setBounds(785,15,80,50);
		label9[7].setBounds(890,15,80,50);
		ta9[0].setBounds(290,80,180,30);
		ta9[1].setBounds(290,150,180,30);
		for(int i = 0; i < 3; i++){
	    	p9.add(button9[i]);
		}
		for(int i = 0; i < 8; i++) 
			p9.add(label9[i]);
		for(int i = 0; i < 8; i++) 
			p9.add(ta9[i]);
		for(int i = 0; i < 6; i++) 
			p9.add(jcb[i]);
		p9.setOpaque(false);
		
		
		// ******for Panel3(show data list Part)******
		for(int i = 0; i < 5; i++) {
		    button3[i] = new JButton();
		    button3[i].setText(buttonString3[i]);
		    button3[i].setFont(font2);
		    button3[i].setContentAreaFilled(false);
		    button3[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button3[i].addActionListener(this);
		}
		p3.setLayout(null);
		button3[4].setBounds(30,250,100,30);
		button3[0].setBounds(195,250,100,30);
		button3[1].setBounds(540,250,100,30);
		button3[2].setBounds(700,250,100,30);
		button3[3].setBounds(360,250,100,30);
		
		for(int i = 0; i < 5; i++)
			p3.add(button3[i]);
		p3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p3.setPreferredSize(new Dimension(830,295));
		p3.setOpaque(false);
		scrollPane.setViewportView(table);
		p3.setLayout(null);
		scrollPane.setBounds(20,25,790,210);
		scrollPane.setFont(font);
		p3.add(scrollPane);
		
		// ******for Panel4(standard time Part)******
		timeLabel = new JLabel("CurrentTime: ");  
        displayArea = new JLabel();  
        timeLabel.setFont(font);
        displayArea.setFont(font);
        p4.setLayout(null);
        timeLabel.setBounds(120,80,140,50);
        displayArea.setBounds(100,150,180,50);
        configTimeArea();
		p4.add(timeLabel);
		p4.add(displayArea);
		p4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p4.setPreferredSize(new Dimension(380, 295));
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
		button5[0].setBounds(370,170,100,40);
		button5[1].setBounds(650,170,100,40);
		button5[2].setBounds(370,220,100,40);
		button5[3].setBounds(650,220,100,40);
		label5[0].setBounds(270,10,160,50);
		label5[1].setBounds(270,70,80,50);
		label5[2].setBounds(270,110,80,50);
		label5[3].setBounds(550,70,80,50);
		label5[4].setBounds(550,110,80,50);
		ta5[0].setBounds(370,80,100,30);
		ta5[1].setBounds(370,120,100,30);
		ta5[2].setBounds(650,80,100,30);
		ta5[3].setBounds(650,120,100,30);
		for(int i = 0; i < 4; i++)
			p5.add(button5[i]);
		for(int i = 0; i < 5; i++) 
			p5.add(label5[i]);
		for(int i = 0; i < 4; i++) 
			p5.add(ta5[i]);
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
		p6.setLayout(null);
		button6[0].setBounds(150,50,150,200);
		button6[1].setBounds(480,50,150,200);
		button6[2].setBounds(780,50,150,200);
		for(int i = 0; i < 3; i++){
	    	p6.add(button6[i]);
		}
		p6.setOpaque(false);
		
		// for Panel7 (logo)
		JLabel label = new JLabel("Hotel Management System");
		p7.setLayout(null);
		label.setFont(font4);
		label.setBounds(240,5,720,100);
		p7.add(label);
		p7.setOpaque(false);
	    p7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p7.setPreferredSize(new Dimension(1220, 100));
		
		// for Panel8
		UIManager.put("TabbedPane.contentOpaque", false);
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Booking",p9);
		jtp.addTab("Check in",p1);
	    jtp.addTab("Check out", p5);
	    jtp.addTab("Order", p6);
	    jtp.setFont(font5);
	    jtp.setBackground(new Color(0,0,0,0));
	    jtp.setOpaque(false);
	    jtp.setTabPlacement(JTabbedPane.LEFT);
	    jtp.setOpaque(false);
	    p8.setLayout(null);
	    jtp.setBounds(5,10,1205,275);
		p8.add(jtp);
		p8.setOpaque(false);
	    p8.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p8.setPreferredSize(new Dimension(1220, 295));
		
		// add all of Panel to Container c
		c.add(p7);
		c.add(p8);
		c.add(p4);
		c.add(p3);
		
		((JComponent) c).setOpaque(false);
		ImageIcon img = new ImageIcon("C:\\3.jpg");  //add background picture
	    JLabel hy = new JLabel(img);
	    getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
	    hy.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 
		setSize(1245,760);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == button1[0]) {
        	if(ta1[1].getText().length() == 8){
	        	mm.createANewUser(ta1[0].getText(),ta1[1].getText(),rr.getroom(),rr.getstatus(),rr.getroomid(),displayArea.getText(),rr.getfeeofroom());
	        	new Popup(this,0);
	        	showtable(0);
	        	cancel(0);
        	}else{
        		new Popup(this,3);
        	}
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
			rr.getRoomStatus(ta1[0].getText(),ta1[1].getText());      // search database, get room status (empty or fill)
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
        if(ae.getSource() == button3[3]) {
        	showtable(3);
        }
        if(ae.getSource() == button3[4]) {
        	showtable(4);
        }
        if(ae.getSource() == button5[0]) {
			double total = mm.calculateTotalFee(ta5[0].getText(),displayArea.getText());     //  get all of fees from database and sum them then show it on the interface
			ta5[1].setText(Double.toString(total));
			showtable(2);
        }
		if(ae.getSource() == button5[1]){
            double change = mm.calculateChange(ta5[1].getText(),ta5[2].getText());    // according to paid-up money, get the amount of the change
            ta5[3].setText(Double.toString(change));
        }
		if(ae.getSource() == button5[2]){
            mm.checkout(ta5[0].getText(),displayArea.getText());       // delete customer's info, update the room status
			new Popup(this,1);
			showtable(0);
			cancel(2);
        }
		if(ae.getSource() == button5[3]){
			cancel(2);
        }
        if(ae.getSource() == button6[0]) {
        	order oo = new order();
        	oo.getitemname();
        	oo.Popup_service(this);
        }
        if(ae.getSource() == button6[1]) {
        	massage mmm = new massage();
        	mmm.Popup_service(this);
        }
        if(ae.getSource() == button6[2]) {
        	pickup pp = new pickup();
        	pp.Popup_service(this);
        }
        if(ae.getSource() == button9[2]) {
        	bb.getRoomStatus(jcbString_year[jcb[0].getSelectedIndex()],jcbString_mon[jcb[1].getSelectedIndex()],
        			jcbString_day[jcb[2].getSelectedIndex()],jcbString_year[jcb[3].getSelectedIndex()],
        			jcbString_mon[jcb[4].getSelectedIndex()],jcbString_day[jcb[5].getSelectedIndex()]);    
			bb.Popup_roomStatus(this);    // show the current room status
        }
        if(ae.getSource() == button9[0]) {
        	mm.createANewBookingUser(ta9[0].getText(),ta9[1].getText(),bb.getroom(),bb.getroomid(),bb.getfrom(),bb.getto(),bb.getstatus());
        	new Popup(this,4);
        	cancel(1);
        	showtable(4);
        }
        if(ae.getSource() == button9[1]) {
        	cancel(1);
        }
        
	}

	@SuppressWarnings("rawtypes")
	public void showtable(int listnum){
		
		DefaultTableModel defaultModel = (DefaultTableModel)table.getModel();
		defaultModel.setRowCount(0);
		if(listnum == 0){
			defaultModel.setColumnIdentifiers(new Object[]{"Name","Idnum","Room","Status","Check-in time"});
		}
		if(listnum == 1){
			defaultModel.setColumnIdentifiers(new Object[]{"id","Items","Price","Stock"});
		}
		if(listnum == 2){
			defaultModel.setColumnIdentifiers(new Object[]{"Roomnum","Fee_room","Fee_service","Total"});	
		}
		if(listnum == 3){
			defaultModel.setColumnIdentifiers(new Object[]{"Name","Idnum","Room","Check-in time","Check-out time","Totalfee"});
		}
		if(listnum == 4){
			defaultModel.setColumnIdentifiers(new Object[]{"Name","Idnum","Room","Fromday","Today"});
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(defaultModel);
		//table.getColumnModel().getColumn(0).setPreferredWidth(15);
		//table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.setFont(font3);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class, r);
		//table.setBorder(BorderFactory.createLoweredBevelBorder());

		if(listnum == 0){
			customer ccc = new customer();
			ArrayList list = ccc.selectAll1();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				defaultModel.addRow(new Object[]{ ccc.getname(),
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
				defaultModel.addRow(new Object[]{ccc.getroomnum(),
						     ccc.getfee_room(),ccc.getfee_service(),ccc.gettotal()});
			}
		}
		if(listnum == 3){
			customer ccc = new customer();
			ArrayList list = ccc.selectAll4();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getname(),
								ccc.getidnum(),ccc.getroom(),ccc.getintime(),ccc.getouttime(),ccc.gettotal()});
			}	
		}
		if(listnum == 4){
			customer ccc = new customer();
			ArrayList list = ccc.selectAll5();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getname(),
								ccc.getidnum(),ccc.getroom(),ccc.getfromday(),ccc.gettoday()});
			}	
		}
	}
	/////////////
	
	public void cancel(int Can) {
		if(Can == 0){
			for(int i = 0; i < 2; i++)
				ta1[i].setText("");	
		}
		if(Can == 1){
			for(int i = 0; i < 2; i++)
				ta9[i].setText("");	
		}
		if(Can == 2){
			for(int i = 0; i < 4; i++)
				ta5[i].setText("");
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
		new testclass2();
	}

}


