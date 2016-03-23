import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class JPaneTest extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JButton[] button1 = new JButton[2];
	JButton[] button2 = new JButton[5];
	JLabel[] label1 = new JLabel[3];
	JLabel[] label2 = new JLabel[4];
	JTextArea[] ta1 = new JTextArea[2];
	String[] buttonString1_6 = {"OK", "Canel"};
	String[] buttonString2 = {"Family Room","Double Room","Sinigle Room","OK", "Canel"};
	String[] labelString1 = {"Check-in", "Name:","ID:"};
	String[] labelString2 = {"Booking"};
	String[] buttonString22 = {"F1","F2","D1","D2","D3","D4","S1","S2","S3",
			                  "F3","F4","D5","D6","D7","D8","S4","S5","S6",
			                  "F5","F6","D9","D10","D11","D12","S7","S8","S9"};
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	Font font1 =  new Font("Times new Roman", Font.BOLD, 34);
	Font font2 =  new Font("Times new Roman", Font.BOLD, 14);
	
	public JPaneTest() {
		setTitle("Hotel management System");
		Container c = getContentPane();
		c.setLayout(new GridLayout(2,3,10,10));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,15));
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel(new GridLayout(2,1,5,15));
		JPanel p4 = new JPanel(new GridLayout(3,1,5,15));
		JPanel p5 = new JPanel(new GridLayout(2,2,5,15));
		JPanel p6 = new JPanel(new GridLayout(1,2,5,15));
		
		//******for Panel1(Check-in Part)******
		for(int i = 0; i < 2; i++) {
		    button1[i] = new JButton();
		    button1[i].setText(buttonString1_6[i]);
		    button1[i].setFont(font);
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
		}
		p1.setLayout(null);
		button1[0].setBounds(40,320,100,40);
		button1[1].setBounds(240,320,100,40);
		label1[0].setBounds(120,30,160,50);
		label1[1].setBounds(40,120,80,50);
		label1[2].setBounds(40,190,80,50);
		ta1[0].setBounds(140,130,180,30);
		ta1[1].setBounds(140,200,180,30);
		for(int i = 0; i < 2; i++)
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
		}
		JButton button222 = new JButton("Check Current Rooms' Status");
		button222.addActionListener(this);
		    label2[0] = new JLabel();
		    label2[0].setText(labelString2[0]);
		    label2[0].setFont(font1);
		p2.setLayout(null);
		button2[0].setBounds(50,120,80,80);
		button2[1].setBounds(155,120,80,80);
		button2[2].setBounds(260,120,80,80);
		button2[3].setBounds(40,320,100,40);
		button2[4].setBounds(240,320,100,40);
		button222.setBounds(72,230,240,60);
		label2[0].setBounds(120,30,160,50);

		for(int i = 0; i < 5; i++)
			p2.add(button2[i]);
		p2.add(label2[0]);
		p2.add(button222);
		
		
		// ******for Panel3(show data list Part)******
		p3.add(new JButton("1"));
		p3.add(new JButton("2"));
		
		// ******for Panel4(standard time Part)******
		p4.add(new JButton("1"));
		p4.add(new JButton("2"));
		p4.add(new JButton("3"));
		
		// ******for Panel5(Check-out Part)******
		p5.add(new JButton("1"));
		p5.add(new JButton("2"));
		p5.add(new JButton("3"));
		p5.add(new JButton("4"));
		
		// ******for Panel3(Order service Part)******
		p6.add(new JButton("1"));
		p6.add(new JButton("2"));
		
		// add all of Panel to Container c
		c.add(p1);
		c.add(p2);
		c.add(p3);
		c.add(p4);
		c.add(p5);
		c.add(p6);
		setSize(1200,800);
		setVisible(true);
		setResizable(false);
		setLocation(350,150);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("Check Current Rooms' Status")){
            Popup hw = new Popup(this);
	}
	}
		
	class Popup implements ActionListener{
	    JButton[] button22 = new JButton[27];
	    JDialog jDialog1=null; 
		    Popup(JFrame jFrame){
			    JPanel p22 = new JPanel(new GridLayout(3,9,10,15));
			    for(int i = 0; i < 27; i++) {
				    button22[i] = new JButton();
				    button22[i].setText(buttonString22[i]);
				    button22[i].setFont(font);
				    button22[i].setBackground(Color.RED);
				}
			    for(int i = 0; i < 27; i++)
			    	p22.add(button22[i]);
		       jDialog1=new JDialog(jFrame,"Room",true);
		       JButton jButton1=new JButton("close");
		       jButton1.addActionListener(this);
		       jDialog1.getContentPane().add(BorderLayout.SOUTH,jButton1);
		       jDialog1.getContentPane().add(p22);
		       jDialog1.setSize(850,350);
		       jDialog1.setLocation(450,450);
		       jDialog1.setVisible(true);
		       jDialog1.setResizable(false);
		    }
		     public void actionPerformed(ActionEvent e){
		        if(e.getActionCommand().equals("close")){
		               jDialog1.dispose();
		        }
		     }
	}
	public static void main(String[] args){
		new JPaneTest();
	}

}
