package test3;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

class Popup_Rinfo implements ActionListener{
        JDialog jDialog1=null; 
        Font font =  new Font("Times new Roman", Font.BOLD, 20);
	    Popup_Rinfo(JFrame jFrame, int roomtype){
	       jDialog1=new JDialog(jFrame,"Room Introduction",true);
	       JTextArea jt1 = new JTextArea();
	       String[] textinfo = {"              Price: $ 220 /day\n      "
			                     + "   Free Wifi, air condition\n   "
			                     + "   Free Morning Call service\n   "
			                     + "      Hot water, toothbush\n     "
			                     + "        one king-size bed\n      "
			                     + "       one twin-size bed      ",
			                    "              Price: $ 160 /day\n      "
	                             + "    Free Wifi, air condition\n   "
	                             + "   Free Morning Call service\n   "
	                             + "       Hot water, toothbush\n     "
	                             + "     two Queen-size bed\n     ",
	                             "              Price: $ 90 /day\n      "
	                             + "    Free Wifi, air condition\n   "
	                             + "   Free Morning Call service\n   "
	                             + "       Hot water, toothbush\n     "
	                             + "     one Quence-size bed\n     "};
	       for (int i = 0; i < 3; i++){
	    	   if(roomtype == i){
	    		   jt1 = new JTextArea(textinfo[i]);
	    	   }
	       }
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

