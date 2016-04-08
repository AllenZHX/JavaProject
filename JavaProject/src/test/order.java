package test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class order implements ActionListener {
	private String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String user = "root";
	private String password = ",26187108hoog";
	private JDialog jDialog=null; 
	private JButton[] button33 = new JButton[30];
	private JButton[] button333 = new JButton[1];
	private JTextArea[] ta333 = new JTextArea[2];
	private JLabel[] label333 = new JLabel[2];
	private String[] labelString = {"Room#: ", "Total: "};
    private String[] buttonString33 = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private int[] stocknum = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private boolean[] selectstatus = {false,false,false,false,false,false,false,false,false,false,false,false,
							    	  false,false,false,false,false,false,false,false,false,false,false,false,
							    	  false,false,false,false,false,false,};
    private int itemid = 99;
	private JTable table = new JTable();
	private JScrollPane scrollPane = new JScrollPane(table);
	Font font =  new Font("Times new Roman", Font.BOLD, 20);
	customer ccc = new customer();
	private String[] item = {"","","","","","","","","","","","","","",""};
	private double[] price = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
	private int temp = 0;
	
	public void getitemname(){
		customer cccc = new customer();
		@SuppressWarnings("rawtypes")
		ArrayList list = cccc.selectAll2();
		for(int i = 0; i < list.size(); i++){
			cccc = (customer)list.get(i);
			buttonString33[i] = cccc.getitems();
			stocknum[i] = cccc.getstock();
		}
	}
	public void Popup_service(JFrame jFrame){
		 
	       
		   JPanel p33 = new JPanel(new GridLayout(6,5,10,15));
		   for(int i = 0; i < 30; i++) {
		    	button33[i] = new JButton();
			    button33[i].setText(buttonString33[i]);
			    button33[i].setFont(font);
			    button33[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button33[i].setBackground(Color.GREEN);
			    if(stocknum[i] == 0){
			    	button33[i].setEnabled(false);
			    }
			    button33[i].addActionListener(this);
		   }
		   for(int i = 0; i < 30; i++)
		    	p33.add(button33[i]);
		   p33.setBounds(5,5,950,300);
		   p33.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   ///////////////////////////////////////
		   JPanel p333 = new JPanel();
		   for(int i = 0; i < 1; i++) {
			    button333[i] = new JButton();
			    button333[i].setText("settle");
			    button333[i].setFont(font);
			    button333[i].setContentAreaFilled(false);
			    button333[i].setBorder(BorderFactory.createRaisedBevelBorder());
			    button333[i].addActionListener(this);
			}
			for(int i = 0; i < 2; i++) {
			    label333[i] = new JLabel();
			    label333[i].setText(labelString[i]);
			    label333[i].setFont(font);
			}
			for(int i = 0; i < 2; i++) {
			    ta333[i] = new JTextArea();
			    ta333[i].setFont(font);
			    ta333[i].setBorder(BorderFactory.createLoweredBevelBorder());
			}
			p333.setLayout(null);
			button333[0].setBounds(750,200,100,40);
			label333[0].setBounds(660,80,160,50);
			label333[1].setBounds(660,130,160,50);
			ta333[0].setBounds(750,90,100,30);
			ta333[1].setBounds(750,140,100,30);
			for(int i = 0; i < 1; i++)
				p333.add(button333[i]);
			for(int i = 0; i < 2; i++) 
				p333.add(label333[i]);
			for(int i = 0; i < 2; i++) 
				p333.add(ta333[i]);
			scrollPane.setViewportView(table);
			scrollPane.setBounds(20,25,600,250);
			scrollPane.setFont(font);
			p333.add(scrollPane);
		   
		   p333.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		   p333.setBounds(5,310,950,300);
		   jDialog=new JDialog(jFrame,"Service List",true);
		   JButton jButton=new JButton("close");
		   jButton.setBounds(5,615,950,30);
		   jDialog.getContentPane().setLayout(null);
		   jDialog.getContentPane().add(jButton);
		   jDialog.getContentPane().add(p33);
		   jDialog.getContentPane().add(p333);
		   jDialog.setSize(980,700);
		   jDialog.setLocationRelativeTo(null);
		   jDialog.setVisible(true);
		   jDialog.setResizable(false);
	}
		
	 public void actionPerformed(ActionEvent e){
	    if(e.getActionCommand().equals("close")){
		        jDialog.dispose();
		}else if(e.getActionCommand().equals("settle")){
			// continue
		}else if(e.getActionCommand().equals("Ok")){
			//continue
		}else if(e.getActionCommand().equals("clear")){
			//continue
		}else{
			for(int i = 0; i<30; i++){
        		if(e.getSource() == button33[i]){
        			if(selectstatus[i] == false){
	        			button33[i].setBackground(Color.RED);
	        			if(itemid < 30){
	        				button33[itemid].setBackground(Color.GREEN);
	        				selectstatus[itemid] = false;
	        			}
        		        itemid = i;
        		        selectstatus[i] = true;
        			}
        			temp++;
        		    showtable();
        		}
        	}
        }
	 }
	 public void showtable(){
			
			DefaultTableModel defaultModel = (DefaultTableModel)table.getModel();
			defaultModel.setRowCount(0);
			defaultModel.setColumnIdentifiers(new Object[]{"Item","num","price"});
			table.getTableHeader().setReorderingAllowed(false);
			table.setModel(defaultModel);
			table.getColumnModel().getColumn(0).setPreferredWidth(15);
			table.getColumnModel().getColumn(1).setPreferredWidth(30);
			table.setFont(font);
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
			r.setHorizontalAlignment(JLabel.CENTER);   
			table.setDefaultRenderer(Object.class, r);
			
			ArrayList list = ccc.selectAll2();
			for(int i = 0; i < list.size(); i++){
				ccc = (customer)list.get(i);
				if(buttonString33[itemid].equals(ccc.getitems())){
					item[temp-1] = ccc.getitems();
					price[temp-1] = ccc.getprice();
					for(int j = 0; j < temp; j++){
						defaultModel.addRow(new Object[]{item[j],1,price[j]});
					}
				}
			}			
		}

}

