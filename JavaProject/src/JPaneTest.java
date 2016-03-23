import java.awt.*;
import javax.swing.*;

public class JPaneTest extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JButton[] button1 = new JButton[2];
	JLabel[] label1 = new JLabel[3];
	JTextArea[] ta1 = new JTextArea[2];
	String[] buttonString = {"OK", "Canel"};
	String[] labelString = {"Check-in", "Name:","ID:"};
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	Font font1 =  new Font("Times new Roman", Font.BOLD, 34);
	public JPaneTest() {
		setTitle("Hotel management System");
		Container c = getContentPane();
		c.setLayout(new GridLayout(2,3,10,10));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,15));
		JPanel p2 = new JPanel(new GridLayout(2,2,5,15));
		JPanel p3 = new JPanel(new GridLayout(2,1,5,15));
		JPanel p4 = new JPanel(new GridLayout(3,1,5,15));
		JPanel p5 = new JPanel(new GridLayout(2,2,5,15));
		JPanel p6 = new JPanel(new GridLayout(1,2,5,15));
		//for Panel1
		for(int i = 0; i < 2; i++) {
		    button1[i] = new JButton();
		    button1[i].setText(buttonString[i]);
		    button1[i].setFont(font);
		}
		for(int i = 0; i < 3; i++) {
		    label1[i] = new JLabel();
		    label1[i].setText(labelString[i]);
		    label1[i].setFont(font);
		    if (i==0)
		    	label1[i].setFont(font1);
		}
		for(int i = 0; i < 2; i++) {
		    ta1[i] = new JTextArea();
		    ta1[i].setFont(font);
		}
		p1.setLayout(null);
		button1[0].setBounds(40,320,100,50);
		button1[1].setBounds(240,320,100,50);
		label1[0].setBounds(120,30,160,50);
		label1[1].setBounds(40,120,80,50);
		label1[2].setBounds(40,190,80,50);
		ta1[0].setBounds(140,130,180,30);
		ta1[1].setBounds(140,200,180,30);
		for(int i = 0; i < 2; i++) {
			p1.add(button1[i]);
		}
		for(int i = 0; i < 3; i++) {
			p1.add(label1[i]);
		}
		for(int i = 0; i < 2; i++) {
			p1.add(ta1[i]);
		}
		// for Panel2
		p2.add(new JButton("1"));
		p2.add(new JButton("2"));
		p2.add(new JButton("3"));
		p2.add(new JButton("4"));
		p3.add(new JButton("1"));
		p3.add(new JButton("2"));
		p4.add(new JButton("1"));
		p4.add(new JButton("2"));
		p4.add(new JButton("3"));
		p5.add(new JButton("1"));
		p5.add(new JButton("2"));
		p5.add(new JButton("3"));
		p5.add(new JButton("4"));
		p6.add(new JButton("1"));
		p6.add(new JButton("2"));
		c.add(p1);
		c.add(p2);
		c.add(p3);
		c.add(p4);
		c.add(p5);
		c.add(p6);
		setSize(1200,800);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public static void main(String[] args){
		new JPaneTest();
	}

}
