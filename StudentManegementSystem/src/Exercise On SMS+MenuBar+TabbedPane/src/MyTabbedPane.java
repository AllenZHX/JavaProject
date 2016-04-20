import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MyTabbedPane extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyTabbedPane(){
		Container c=getContentPane();
		c.setLayout(new BorderLayout());
		c.setBackground(Color.WHITE);
		
		JPanel jp1=new JPanel(new GridLayout(2,2,10,10));
		JButton jb1_1=new JButton("add");
		JButton jb1_2=new JButton("cancel");
		jp1.add(jb1_1);
		jp1.add(jb1_2);
		
		JPanel jp2=new JPanel(new BorderLayout());
		JButton jb2_1=new JButton("delete");
		JButton jb2_2=new JButton("Cancel");
		jp2.add(jb2_1, BorderLayout.WEST);
		jp2.add(jb2_2, BorderLayout.EAST);
		
		JPanel jp3=new JPanel(new BorderLayout());
		JButton jb3_1=new JButton("modify");
		JButton jb3_2=new JButton("cancel");
		jp3.add(jb3_1,BorderLayout.NORTH);
		jp3.add(jb3_2,BorderLayout.SOUTH);
		
		JTabbedPane jtp=new JTabbedPane(JTabbedPane.TOP);
		jtp.add("Check In", jp2);
		jtp.add("Food Service", jp3);
		jtp.add("Check Out", new JButton("click!"));
		jtp.add("Help", jp1);
		
		JMenuBar jmb=new JMenuBar();
//		JMenuBar jmb2=new JMenuBar();
		setJMenuBar(jmb);
//		setJMenuBar(jmb2);
		JMenu jm1=new JMenu("File");
		JMenu jm2=new JMenu("Help");
		jmb.add(jm1);
		jmb.add(jm2);
		
		JMenuItem jmi1_1=new JMenuItem("son 1 of File");
		JMenuItem jmi1_2=new JMenuItem("son 2 of File");
		jmi1_1.addActionListener(new ItemListener());
		jmi1_2.addActionListener(new ItemListener());
		jm1.add(jmi1_1);	jm1.add(jmi1_2);
		
		JMenuItem jmi2_1=new JMenuItem("son 1 of Help");
		JMenuItem jmi2_2=new JMenuItem("son 2 of Help");
		jmi2_1.addActionListener(new ItemListener());
		jmi2_2.addActionListener(new ItemListener());
		jm2.add(jmi2_1);	jm2.add(jmi2_2);
			
		c.add(jtp, BorderLayout.CENTER);
		
		setSize(400,300);
		setVisible(true);
		setTitle("MyTabbedPane");
	}
	
	public static void main(String[] args){
		new MyTabbedPane();
	}
}



class ItemListener implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
	JMenuItem jmi=(JMenuItem)e.getSource();
		System.out.println(jmi.getText());
	}
}