package test3;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class roominfo implements ActionListener{
	private int roomid;
	private Boolean[] roomstatus = {false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false,
									false,false,false,false,false,false,false,false,false};
	private String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String user = "root";
	private String password = ",26187108hoog";
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
	public void Popup_roomStatus(JFrame jFrame){
		 
	     Font font =  new Font("Times new Roman", Font.BOLD, 20);
	
		 JPanel p22 = new JPanel(new GridLayout(3,9,10,15));
		    for(int i = 0; i < 27; i++) {
		    	button22[i] = new JButton();
			    button22[i].setText(buttonString22[i]);
			    button22[i].setFont(font);
			    button22[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button22[i].setBackground(Color.GREEN);
			    if (roomstatus[i] == true){
			          button22[i].setBackground(Color.RED);
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
