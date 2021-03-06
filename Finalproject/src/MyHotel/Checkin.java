package MyHotel;
/*
 * @ author: Hongxiang Zheng, Xiang Cao, Yingbin Zheng
 * 
 * ***************************************************
 * **********   checkin popup window      ************
 * ***************************************************
 * 
 */
/*
 * Color meannings:
 * Red: someone in the room today;
 * Black: someone in the room today and have to check out today
 * Yellow: someone booked the room and need to check in today but he/she haven't
 * Blue: when u enter the right name and idnum, the blue one is the room you booked.
 * Green: room is available
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

public class Checkin extends JDBCinfo implements ActionListener{
	private int roomid;
	private int[] roomstatus = {0,0,0,0,0,0,0,0,0,
								0,0,0,0,0,0,0,0,0,
								0,0,0,0,0,0,0,0,0};
	private boolean[] buttonenable = {false,false,false,false,false,false,false,false,false,
										false,false,false,false,false,false,false,false,false,
										false,false,false,false,false,false,false,false,false,};
	private JDialog jDialog=null; 
	private JButton[] button22 = new JButton[27];
    private String[] buttonString22 = {"F1","F2","D1","D2","D3","D4","S1","S2","S3",
					           "F3","F4","D5","D6","D7","D8","S4","S5","S6",
					           "F5","F6","D9","D10","D11","D12","S7","S8","S9"};
    private Double[] roomprice = {220.0,160.0,90.0};
    private Double feeofroom = 0.0;
    private String room;
	private String status;
    
	public int getroomid(){return roomid;}
	public String getstatus(){return status;}
	public String getroom(){return room;}
	public Double getfeeofroom(){return feeofroom;}
	
	public String getDate(){
		Calendar ca = Calendar.getInstance();
		String date = ca.get(Calendar.YEAR)+"-"+(ca.get(Calendar.MONTH)+1)+"-"+ca.get(Calendar.DATE);
		DateFormat src = new SimpleDateFormat("yyyy-M-d");
		DateFormat tar = new SimpleDateFormat("yyyy-MM-dd");
		String tarDate = "";
		try {
			tarDate = tar.format(src.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tarDate;
	}
	
	public void getRoomStatus(String name, String idnum) {   
		try{
			roomid = 99;
			for(int i = 0; i < 27; i++){
				roomstatus[i] = 0;   // 0 means empty
				buttonenable[i] = false;
			}
			//2. Create a statement
			String sql = "select * from checkin";
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL 
			ResultSet myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				int aa = myRs.getInt("roomid");
				roomstatus[aa] = 1;  // 1 means checkined
			}		
			String current = getDate();
			int cyear = Integer.parseInt(current.substring(0,4));
			int cmon = Integer.parseInt(current.substring(5,7));
			int cday = Integer.parseInt(current.substring(8,10));
			JulianDate e = new JulianDate(cyear,cmon,cday);
			String sql2 = "select * from booking";
			Statement myStmt2 = myConn.createStatement();
			//3. Execute SQL 
			ResultSet myRs2 = myStmt2.executeQuery(sql2);
			while (myRs2.next()) {
				int aa = myRs2.getInt("roomid");
				if(roomstatus[aa] == 1){
					String dd = myRs2.getString("today");
					int tyear2 = Integer.parseInt(dd.substring(0,4));
					int tmon2 = Integer.parseInt(dd.substring(5,7));
					int tday2 = Integer.parseInt(dd.substring(8,10));
					JulianDate d = new JulianDate(tyear2,tmon2,tday2);
					if(e.getJulianDate() >= d.getJulianDate())
						roomstatus[aa] = 3;   // 3 means should check out today
				}
				if(roomstatus[aa] == 0){
					String cc = myRs2.getString("fromday");
					String bookname = myRs2.getString("name");
					String bookidnum = myRs2.getString("idnum");
					if(bookname.equals(name) & bookidnum.equals(idnum))
						buttonenable[aa] = true;
					int fyear2 = Integer.parseInt(cc.substring(0,4));
					int fmon2 = Integer.parseInt(cc.substring(5,7));
					int fday2 = Integer.parseInt(cc.substring(8,10));
					JulianDate c = new JulianDate(fyear2,fmon2,fday2);
					if(e.getJulianDate() > c.getJulianDate()){
						roomstatus[aa] = 0;  // cancel booking
						String sql3 = "delete from booking where name = ?";
						PreparedStatement myStmt3 = myConn.prepareStatement(sql3);
						myStmt3.setString(1,bookname);
						myStmt3.executeUpdate();
					}
					if(e.getJulianDate() == c.getJulianDate())
						roomstatus[aa] = 2; // 2 means someone has already booked a room but haven't check in
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
			    if (roomstatus[i] == 3){
			          button22[i].setBackground(Color.BLACK);
			          button22[i].setEnabled(false);
			    }
			    if (roomstatus[i] == 2){
			          if(buttonenable[i] == false){
			        	  button22[i].setBackground(Color.YELLOW);
			        	  button22[i].setEnabled(false);
			          }else{
			        	  button22[i].setBackground(Color.CYAN);
			          }
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
        			if(roomstatus[i] == 0 | roomstatus[i] == 2){
	        			button22[i].setBackground(Color.RED);
	        			if(roomid < 27){
	        				button22[roomid].setBackground(Color.GREEN);
	        				roomstatus[roomid] = 0;
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
        		        roomstatus[i] = 1;
        			}else{
        				
        			}
        		}
        	}
    }
	 public void createANewUser(String name, String idnum, String room, String status, int roomid, String intime,double feeofroom) {
			try{
				//2. Create a statement
				String sql = "insert into checkin (name,idnum,room,status,roomid,intime) values(?,?,?,?,?,?)";
				String sql2 = "insert into payment (roomnum,fee_room,fee_service,total) values(?,?,?,?)";
				PreparedStatement myStmt = myConn.prepareStatement(sql);
				myStmt.setString(1,name);
				myStmt.setString(2,idnum);
				myStmt.setString(3,room);
				myStmt.setString(4,status);
				myStmt.setInt(5,roomid);
				myStmt.setString(6,intime);
				PreparedStatement myStmt2 = myConn.prepareStatement(sql2);
				myStmt2.setString(1,room);
				myStmt2.setDouble(2,feeofroom);
				myStmt2.setDouble(3,0);
				myStmt2.setDouble(4,0);
				//3. Execute SQL 
				myStmt.executeUpdate();
				myStmt2.executeUpdate();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
}
