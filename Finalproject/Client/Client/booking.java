package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class booking extends JDBCinfo implements ActionListener{
	private int roomid;
	private int[] roomstatus = {0,0,0,0,0,0,0,0,0,
								0,0,0,0,0,0,0,0,0,
								0,0,0,0,0,0,0,0,0};
	private JDialog jDialog=null; 
	private JButton[] button22 = new JButton[27];
    private String[] buttonString22 = {"F1","F2","D1","D2","D3","D4","S1","S2","S3",
					           "F3","F4","D5","D6","D7","D8","S4","S5","S6",
					           "F5","F6","D9","D10","D11","D12","S7","S8","S9"};
    private String[] roominfo = {"","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private String room;
	private String status;
	private String from;
	private String to;
    
	public int getroomid(){return roomid;}
	public String getstatus(){return status;}
	public String getroom(){return room;}
	public String getfrom(){return from;}
	public String getto(){return to;}
	
	public void getRoomStatus(String fy, String fm, String fd, String ty, String tm, String td) {   
		int fyear = Integer.parseInt(fy);
		int fmon = Integer.parseInt(fm);
		int fday = Integer.parseInt(fd);
		int tyear = Integer.parseInt(ty);
		int tmon = Integer.parseInt(tm);
		int tday = Integer.parseInt(td);
		JulianDate a = new JulianDate(fyear,fmon,fday);
		JulianDate b = new JulianDate(tyear,tmon,tday);
		from = fy+"-"+fm+"-"+fd;
		to = ty+"-"+tm+"-"+td;
		try{
			roomid = 99;
			for(int i = 0; i < 27; i++){
				roomstatus[i] = 0;   // 0 means empty
			}
			//1. Get a connection to database
			//Connection myConn = DriverManager.getConnection(url, user, password);
			//2. Create a statement
			String sql2 = "select * from booking";
			Statement myStmt2 = myConn.createStatement();
			//3. Execute SQL 
			ResultSet myRs2 = myStmt2.executeQuery(sql2);
			while (myRs2.next()) {
				int aa = myRs2.getInt("roomid");
				String booker = myRs2.getString("name");
				String cc = myRs2.getString("fromday");
				String dd = myRs2.getString("today");
				int fyear2 = Integer.parseInt(cc.substring(0,4));
				int fmon2 = Integer.parseInt(cc.substring(5,7));
				int fday2 = Integer.parseInt(cc.substring(8,10));
				int tyear2 = Integer.parseInt(dd.substring(0,4));
				int tmon2 = Integer.parseInt(dd.substring(5,7));
				int tday2 = Integer.parseInt(dd.substring(8, 10));
				JulianDate c = new JulianDate(fyear2,fmon2,fday2);
				JulianDate d = new JulianDate(tyear2,tmon2,tday2);
				if(a.getJulianDate() >= c.getJulianDate() & a.getJulianDate() <= d.getJulianDate()){
					roomstatus[aa] = 2;  // 2 means booking
					roominfo[aa] = booker + ": " + cc + " ~ " + dd.substring(5,10);
				}
				if(b.getJulianDate() >= c.getJulianDate() & b.getJulianDate() <= d.getJulianDate()){
					roomstatus[aa] = 2; 
					roominfo[aa] = booker + ": " + cc + " ~ " + dd.substring(5,10);
				}
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	public void Popup_roomStatus(JFrame jFrame){
	     Font font =  new Font("Times new Roman", Font.BOLD, 20);
	
		 JPanel p22 = new JPanel(new GridLayout(3,9,10,15));
		    for(int i = 0; i < 27; i++) {
		    	button22[i] = new JButton();
			    button22[i].setText(buttonString22[i]);
			    button22[i].setFont(font);
			    button22[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button22[i].setBackground(Color.GREEN);
			    if (roomstatus[i] == 1){
			          button22[i].setBackground(Color.RED);
			          button22[i].setEnabled(false);
			    }
			    if (roomstatus[i] == 2){
			          button22[i].setBackground(Color.YELLOW);
			          button22[i].setEnabled(false);
			          button22[i].setToolTipText(roominfo[i]);
			    }
			    button22[i].addActionListener(this);
			}
		   for(int i = 0; i < 27; i++)
		    	p22.add(button22[i]);
		   p22.setBounds(5,5,820,320);
		   
		   jDialog=new JDialog(jFrame,"Room",true);
		   jDialog.getContentPane().setLayout(null);
		   jDialog.getContentPane().add(p22);
		   jDialog.setSize(850,380);
		   jDialog.setLocationRelativeTo(null);
		   jDialog.setVisible(true);
		   jDialog.setResizable(false);
	}
		
	 public void actionPerformed(ActionEvent e){
        	for(int i = 0; i<27; i++){
        		if(e.getSource() == button22[i]){
        			if(roomstatus[i] == 0){
	        			button22[i].setBackground(Color.RED);
	        			if(roomid < 27){
	        				button22[roomid].setBackground(Color.GREEN);
	        				roomstatus[roomid] = 0;
	        			}
        		        room = buttonString22[i];
        		        roomid = i;
        		        status = "Booked!";
        		        roomstatus[i] = 2;
        			}else{
        				
        			}
        		}
        	}
    }
}
