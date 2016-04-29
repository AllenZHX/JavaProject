package Client;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class manager extends JDBCinfo{
	public void createANewBookingUser(String name, String idnum, String room, int roomid, String from, String to, String status,String email) {
		try{
			//1. Get a connection to database
			
			//2. Create a statement
			String sql = "insert into booking (name,idnum,room,roomid,fromday,today,status,email) values(?,?,?,?,?,?,?,?)";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,name);
			myStmt.setString(2,idnum);
			myStmt.setString(3,room);
			myStmt.setInt(4,roomid);
			myStmt.setString(5,from);
			myStmt.setString(6,to);
			myStmt.setString(7,status);
			myStmt.setString(8,email);
			//3. Execute SQL 
			myStmt.executeUpdate();
			generateAndSendEmail(email,"booking",name,idnum,room,from,to);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	public void createANewRegisterUser(String name, String idnum, String email, String password) {
		try{
			//1. Get a connection to database
			
			//2. Create a statement
			String sql = "insert into register (name,idnum,email,password) values(?,?,?,?)";
			PreparedStatement myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1,name);
			myStmt.setString(2,idnum);
			myStmt.setString(3,email);
			myStmt.setString(4,password);
			
			
			//3. Execute SQL 
			myStmt.executeUpdate();
			//generateAndSendEmail(email,"booking",name,idnum,room,from,to);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public boolean canceling(String name,String id){
		try{
			String sql4 = "select * from booking";
			Statement myStmt4 = myConn.createStatement();
			ResultSet myRs4 = myStmt4.executeQuery(sql4);

			while (myRs4.next()) {
				if(name.equals(myRs4.getString("name"))){
					if(id.equals(myRs4.getString("idnum"))){
					String sql6 = "delete from booking where idnum = ?";
					PreparedStatement myStmt6 = myConn.prepareStatement(sql6);
					myStmt6.setString(1,id);
					myStmt6.executeUpdate();

					generateAndSendEmail(myRs4.getString("email"),"Cancel",myRs4.getString("name"),myRs4.getString("idnum"),myRs4.getString("room"),myRs4.getString("fromday"),myRs4.getString("today"));
					return true;
					}
				}	
			}
			

			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}
	public String[] search(String name,String id){
		try{
			//Connection myConn = DriverManager.getConnection(url, user, password);
			//3. Execute SQL 
			String sql4 = "select * from booking";
			Statement myStmt4 = myConn.createStatement();
			ResultSet myRs4 = myStmt4.executeQuery(sql4);
			String[] tmp=new String[6];
			tmp[0]="no";
			while (myRs4.next()) {
				if(name.equals(myRs4.getString("name"))){
					if(id.equals(myRs4.getString("idnum"))){
					tmp[0]=myRs4.getString("name");tmp[1]=myRs4.getString("idnum");tmp[2]=myRs4.getString("room");tmp[3]=myRs4.getString("roomid");tmp[4]=myRs4.getString("fromday");tmp[5]=myRs4.getString("today");
					return tmp;
					}
				}	
			}
			

			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
	public String[] getnameidemail(String email,String password){
		try{
			//Connection myConn = DriverManager.getConnection(url, user, password);
			//3. Execute SQL 
			String sql4 = "select * from register";
			Statement myStmt4 = myConn.createStatement();
			ResultSet myRs4 = myStmt4.executeQuery(sql4);
			String[] tmp=new String[3];
			while (myRs4.next()) {
				if(email.equals(myRs4.getString("email"))){
					if(password.equals(myRs4.getString("password"))){
					tmp[0]=myRs4.getString("name");tmp[1]=myRs4.getString("idnum");tmp[2]=myRs4.getString("email");
					return tmp;
					}
				}	
			}
			

			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
	public boolean searchregister(String name,String id){
		try{
			//Connection myConn = DriverManager.getConnection(url, user, password);
			//3. Execute SQL 
			String sql4 = "select * from register";
			Statement myStmt4 = myConn.createStatement();
			ResultSet myRs4 = myStmt4.executeQuery(sql4);
			//boolean tmp;
			while (myRs4.next()) {
				if(name.equals(myRs4.getString("email"))){
					if(id.equals(myRs4.getString("password"))){
					
					return true;
					}
				}	
			}
			

			
		}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	
		
	}
	 //gmail
		static Properties mailServerProperties;
		static Session getMailSession;
		static MimeMessage generateMailMessage;
	 
		public static void generateAndSendEmail(String emailaddress,String borc, String name, String idnum, String room, String fromday, String today) throws AddressException, MessagingException {
	 
			// Step1
			//System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			//System.out.println("Mail Server Properties have been setup successfully..");
	 
			// Step2
			//System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress));
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(emailaddress));
			generateMailMessage.setSubject("Successfully "+borc+" from Java810Hotel!");
			String emailBody = "This is your "+ borc +" information: "+"<br>Your name: "+name+"<br>ID: "+idnum+"<br>Room: "+room+"<br>From: "+fromday+"<br>To: "+today+ "<br>We are so glad to serve you."+"<br><br> Regards, <br>Java810Hotel";
			generateMailMessage.setContent(emailBody, "text/html");
			//System.out.println("Mail Session has been created successfully..");
	 
			// Step3
			//System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
	 
			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "java810hotel@gmail.com", "55webster");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
		}
		public static String vcode="";
		public  static String getvcode(){
		return vcode;
		}
		public static void VerificationEmail(String emailaddress, String vcode ) throws AddressException, MessagingException {
			 
			// Step1
			//System.out.println("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			//System.out.println("Mail Server Properties have been setup successfully..");
			
			
			// Step2
			//System.out.println("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress));
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(emailaddress));
			generateMailMessage.setSubject("Verification code from Java810Hotel!");
			String emailBody = "This is your Verification code: "+vcode+"<br>Thank you for your register."+"<br><br> Regards, <br>Java810Hotel";
			generateMailMessage.setContent(emailBody, "text/html");
			//System.out.println("Mail Session has been created successfully..");
	 
			// Step3
			//System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
	 
			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "java810hotel@gmail.com", "55webster");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
		}
	}


