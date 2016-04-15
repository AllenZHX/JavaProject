package test3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class pickup implements ActionListener {
	private JDialog jDialog=null; 
	private JButton[] button1 = new JButton[30];
    private String[] buttonString1 = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	
	public void Popup_service(JFrame jFrame){
		  
		   JPanel p1 = new JPanel(new GridLayout(6,5,10,15));
		   for(int i = 0; i < 30; i++) {
		    	button1[i] = new JButton();
			    button1[i].setText(buttonString1[i]);
			    button1[i].setFont(font);
			    button1[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button1[i].setBackground(Color.GREEN);
			    button1[i].addActionListener(this);
		   }
		   for(int i = 0; i < 30; i++)
		    	p1.add(button1[i]);
		   p1.setBounds(5,5,950,300);
		   p1.setOpaque(false);
		   p1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   ///////////////////////////////////////
		   jDialog=new JDialog(jFrame,"Service List",true);
		   jDialog.getContentPane().setLayout(null);
		   jDialog.getContentPane().add(p1);
		   ((JPanel)jDialog.getContentPane()).setOpaque(false);
			ImageIcon img = new ImageIcon("C:\\3.jpg");  //add background picture
		    JLabel hy = new JLabel(img);
		    jDialog.getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
		    hy.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 
		   jDialog.setSize(980,660);
		   jDialog.setLocationRelativeTo(null);
		   jDialog.setVisible(true);
		   jDialog.setResizable(false);
	}
		
	 public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("settle")){
			//calculateServPrice();
		}
	 }
}


