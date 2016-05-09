package  Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;  
import java.util.Date;  
import java.util.Timer;  
import java.util.TimerTask;  
import javax.swing.*;


public class testclass3 extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	Roominfo rr = new Roominfo();
	Manager mm = new Manager();
	Booking bb = new Booking();
	private static String name, idnum,email;
	JButton[] button5 = new JButton[4];
	JButton[] button9 = new JButton[3];
	JLabel[] label1 = new JLabel[3];
	JLabel[] label5 = new JLabel[5];
	JLabel[] label9 = new JLabel[8];   //////
	JTextArea[] ta5 = new JTextArea[3];
	JTextArea[] ta9 = new JTextArea[9];   /////////
	JComboBox[] jcb = new JComboBox[6];
	JTable table = new JTable();
	JScrollPane scrollPane = new JScrollPane(table);
	String[] buttonString5 = {"Confirm","Clear","Search"};
	String[] buttonString9 = {"Ok", "Clear","Check Current Rooms' Status"};
	String[] labelString2 = {"Booking"};
	String[] labelString5 = {"Canceling","Name: ","ID: ","Paid-up: "};
	String[] labelString9 = {"E-mail: ","Name: ","ID: ","From: ","To: ","Year","Month","Day"};
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
    JPanel p3 = new JPanel(); 
	JPanel p4 = new JPanel(); 
	JPanel p5 = new JPanel();
	JPanel p7 = new JPanel(); 
	JPanel p8 = new JPanel(); 
	JPanel p9 = new JPanel(); 
	
	public testclass3(String name, String idnum,String email) {
		this.name=name;
		this.idnum=idnum;
		this.email=email;
		setTitle("Hotel Order System");
		Container c = getContentPane();
		c.setLayout(new FlowLayout(3,10,10));

		/////////////////////(Booking Part)//////////////////////////////

		for(int i = 0; i < 3; i++) {
		    button9[i] = new JButton();
		    button9[i].setText(buttonString9[i]);
		    button9[i].setFont(font2);
		    button9[i].setContentAreaFilled(false);
		    button9[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button9[i].addActionListener(this);
		}
		for(int i =3; i < 8; i++) {
		    label9[i] = new JLabel();
		    label9[i].setText(labelString9[i]);
		    label9[i].setFont(font);
		    
		}
		for(int i = 0; i < 9; i++) {
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
		button9[0].setBounds(570,100,100,40);
		button9[1].setBounds(570,200,100,40);
		button9[2].setBounds(150,180,330,80);
		jcb[0].setBounds(200,70,80,30);
		jcb[1].setBounds(300,70,80,30);
		jcb[2].setBounds(400,70,80,30);
		jcb[3].setBounds(200,130,80,30);
		jcb[4].setBounds(300,130,80,30);
		jcb[5].setBounds(400,130,80,30);
		label9[3].setBounds(120,60,80,50);
		label9[4].setBounds(120,120,80,50);
		label9[5].setBounds(320,15,80,50);
		label9[6].setBounds(415,15,80,50);
		label9[7].setBounds(220,15,80,50);

		for(int i = 0; i < 3; i++){
	    	p9.add(button9[i]);
		}
		for(int i = 3; i < 8; i++) 
			p9.add(label9[i]);
		for(int i = 2; i < 8; i++) 
			p9.add(ta9[i]);
		for(int i = 0; i < 6; i++) 
			p9.add(jcb[i]);
		p9.setOpaque(false);
		//show detail panel
		p3.setPreferredSize(new Dimension(610,255));
		p3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p3.setOpaque(false);
		// ******for Panel4(standard time Part)******
		timeLabel = new JLabel("CurrentTime: ");  
        displayArea = new JLabel();  
        timeLabel.setFont(font);
        displayArea.setFont(font);
        p4.setLayout(null);
        timeLabel.setBounds(120,60,140,50);
        displayArea.setBounds(100,130,180,50);
        configTimeArea();
		p4.add(timeLabel);
		p4.add(displayArea);
		p4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p4.setPreferredSize(new Dimension(380, 255));
		p4.setOpaque(false);
		
		// ******for Panel5(Cancel Part)******
		for(int i = 0; i < 3; i++) {
		    button5[i] = new JButton();
		    button5[i].setText(buttonString5[i]);
		    button5[i].setFont(font2);
		    button5[i].setContentAreaFilled(false);
		    button5[i].setBorder(BorderFactory.createRaisedBevelBorder());
		    button5[i].addActionListener(this);
		}
		for(int i = 0; i < 1; i++) {
		    label5[i] = new JLabel();
		    label5[i].setText(labelString5[i]);
		    label5[i].setFont(font);
		    if (i==0)
		    	label5[i].setFont(font1);
		}
		
		p5.setLayout(null);
		button5[0].setBounds(210,220,100,40);
		button5[1].setBounds(520,220,100,40);
		button5[2].setBounds(370,220,100,40);
		label5[0].setBounds(200,10,160,50);
		
		for(int i = 0; i < 3; i++)
			p5.add(button5[i]);
		for(int i = 0; i < 1; i++) 
			p5.add(label5[i]);
		p5.setOpaque(false);

		// for Panel7 (logo)
		JLabel label = new JLabel("Welcome To Java810 Hotel");
		p7.setLayout(null);
		label.setFont(font4);
		label.setBounds(140,5,720,100);
		p7.add(label);
		p7.setOpaque(false);
	    p7.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p7.setPreferredSize(new Dimension(1000, 100));
		
		// for Panel8
		UIManager.put("TabbedPane.contentOpaque", false);
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Booking",p9);
	    jtp.addTab("Cancel", p5);
	    jtp.setFont(font5);
	    jtp.setBackground(new Color(0,0,0,0));
	    jtp.setOpaque(false);
	    jtp.setTabPlacement(JTabbedPane.TOP);
	    jtp.setOpaque(false);
	    p8.setLayout(null);
	    jtp.setBounds(5,10,985,315);
		p8.add(jtp);
		p8.setOpaque(false);
	    p8.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p8.setPreferredSize(new Dimension(1000, 335));
		
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
		setSize(1025,760);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {


        

		if(ae.getSource() == button5[0]){
			
            boolean t=mm.canceling(name,idnum);       // delete customer's info, update the room status
            new Popup(this,1);
			
		}
		if(ae.getSource() == button5[1]){
			cancel(2);
        }
		if(ae.getSource() == button5[2]){
			 String[] t=mm.search(name,idnum); 
			//if(t[0].equals("no")){
				//new Popup(this,7);
				
			//}
			//else{
				new Popup(this,3);
				TextArea text=p3();
				text.setText("Finded your booking information: \n\n"+"Name: "+t[0]+"\n"+"ID: "+t[1]+"\n"+"Room: "+t[2]+"\n"+"From: "+t[4]+"\n"+"To: "+t[5]);
	    		p3.add(text);
			//}
        }
        if(ae.getSource() == button9[2]) {
        	bb.getRoomStatus(jcbString_year[jcb[0].getSelectedIndex()],jcbString_mon[jcb[1].getSelectedIndex()],
        			jcbString_day[jcb[2].getSelectedIndex()],jcbString_year[jcb[3].getSelectedIndex()],
        			jcbString_mon[jcb[4].getSelectedIndex()],jcbString_day[jcb[5].getSelectedIndex()]);    
			bb.Popup_roomStatus(this);    // show the current room status
        	
        }
        if(ae.getSource() == button9[0]) {
			TextArea text=p3();
        	mm.createANewBookingUser(name,idnum,bb.getroom(),bb.getroomid(),bb.getfrom(),bb.getto(),bb.getstatus(),email);
    		text.setText("This is your booking information: \n\n"+"Name: "+name+"\n"+"ID: "+idnum+"\n"+"Room: "+bb.getroom()+"\n"+"From: "+bb.getfrom()+"\n"+"To: "+bb.getto());
    		p3.add(text);
    		new Popup(this,0);
        	cancel(1);
			
        }
        
        if(ae.getSource() == button9[1]) {
        	cancel(1);
        }
        
	}
	
	public void cancel(int Can) {
		
		if(Can == 1){
			ta9[8].setText("");
			for(int i = 0; i < 2; i++)
				ta9[i].setText("");	
		}
		if(Can == 2){
			for(int i = 0; i < 2; i++)
				ta5[i].setText("");
		}
	}
	public TextArea p3(){
		TextArea text=new TextArea();
		text.setPreferredSize(new Dimension(590,240));
		text.setEditable(false);
		text.setFont(font);

		return text;
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
		//new testclass3();
	}

}


