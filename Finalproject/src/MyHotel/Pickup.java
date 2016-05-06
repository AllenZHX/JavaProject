package MyHotel;
/*
 * @ author: Hongxiang Zheng, Xiang Cao
 * 
 * ***************************************************
 * **********    pickup popup window      ************
 * ***************************************************
 * 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Pickup implements ActionListener {
	private JDialog jDialog=null; 
	private JButton[] button1 = new JButton[2];
	private JLabel[] label1 = new JLabel[5];
	private JTextArea[] ta1 = new JTextArea[5];
    private String[] buttonString1 = {"Ok","cancel"};
    private String[] labelString1 = {"Name:","Phone","Pick up Time:","From:","To:"};
    private String info = "";
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	
	public void Popup_service(JFrame jFrame){
		  
		   JPanel p1 = new JPanel();
		   for(int i = 0; i < 2; i++) {
		    	button1[i] = new JButton();
			    button1[i].setText(buttonString1[i]);
			    button1[i].setFont(font);
			    button1[i].setContentAreaFilled(false);
			    button1[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button1[i].addActionListener(this);
		   }
		   for(int i = 0; i < 5; i++) {
			    label1[i] = new JLabel();
			    label1[i].setText(labelString1[i]);
			    label1[i].setFont(font);
		   }
		   for(int i = 0; i < 5; i++) {
			    ta1[i] = new JTextArea();
			    ta1[i].setFont(font);
			    ta1[i].setBorder(BorderFactory.createLoweredBevelBorder());
		   }
		   p1.setLayout(null);
		   button1[0].setBounds(100,200,100,35);
		   button1[1].setBounds(280,200,100,35);
		   label1[0].setBounds(100,20,160,50);
		   label1[1].setBounds(100,80,80,50);
		   label1[2].setBounds(470,20,120,50);
		   label1[3].setBounds(470,80,80,50);
		   label1[4].setBounds(470,140,80,50);
		   ta1[0].setBounds(200,30,180,30);
		   ta1[1].setBounds(200,90,180,30);
		   ta1[2].setBounds(600,30,180,30);
		   ta1[3].setBounds(600,90,180,30);
		   ta1[4].setBounds(600,150,180,30);
		   for(int i = 0; i < 2; i++)
				p1.add(button1[i]);
		   for(int i = 0; i < 5; i++) 
				p1.add(label1[i]);
		   for(int i = 0; i < 5; i++) 
				p1.add(ta1[i]);
			
		   p1.setBounds(5,5,870,270);
		   p1.setOpaque(false);
		   p1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   ///////////////////////////////////////
		   jDialog=new JDialog(jFrame,"Service List",true);
		   jDialog.getContentPane().setLayout(null);
		   jDialog.getContentPane().add(p1);
		   ((JPanel)jDialog.getContentPane()).setOpaque(false);
			ImageIcon img = new ImageIcon("lib/7.jpg");  //add background picture
		    JLabel hy = new JLabel(img);
		    jDialog.getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
		    hy.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 
		   jDialog.setSize(900,330);
		   jDialog.setLocationRelativeTo(null);
		   jDialog.setVisible(true);
		   jDialog.setResizable(false);
	}
		
	 public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Ok")){
			sendInfoToServer();
		}
		if(e.getActionCommand().equals("cancel")){
			clear();
		}
	 }
	 public void sendInfoToServer(){
		 for(int i = 0; i < 5; i++){
			 info += ta1[i].getText();
			 if(i != 4){
				 info += "\n";
			 }
		 }	 
		 new Client(info);
	 }
	 public void clear(){
		 for(int i = 0; i < 5; i++){
			 ta1[i].setText("");
		 }
	 }
}


