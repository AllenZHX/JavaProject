package MyHotel;
import java.awt.Color;
/*
 * @ author: Hongxiang Zheng
 * 
 * ***************************************************
 * ********** draw the table & show the data *********
 * ***************************************************
 * 
 */
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Table {
	Font font3 =  new Font("Calibri", Font.PLAIN, 14);
public Table(int listnum,JTable table){
		
		DefaultTableModel defaultModel = (DefaultTableModel)table.getModel();
		defaultModel.setRowCount(0);
		if(listnum == 0){
			defaultModel.setColumnIdentifiers(new Object[]{"Name","Idnum","Room","Status","Check-in time"});
		}
		if(listnum == 1){
			defaultModel.setColumnIdentifiers(new Object[]{"id","Items","Price","Stock"});
		}
		if(listnum == 2){
			defaultModel.setColumnIdentifiers(new Object[]{"Roomnum","Fee_room","Fee_service","Total"});	
		}
		if(listnum == 3){
			defaultModel.setColumnIdentifiers(new Object[]{"Name","Idnum","Room","Check-in time","Check-out time","Totalfee"});
		}
		if(listnum == 4){
			defaultModel.setColumnIdentifiers(new Object[]{"Name","Idnum","Email","Room","Fromday","Today"});
		}
		if(listnum == 5){
			defaultModel.setColumnIdentifiers(new Object[]{"Day","Num of Checkin","Num of Checkout","Total"});
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(defaultModel);
		//table.getColumnModel().getColumn(0).setPreferredWidth(15);
		//table.getColumnModel().getColumn(1).setPreferredWidth(30);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(defaultModel);  
        table.setRowSorter(sorter); 
		table.setFont(font3);
		table.setBackground(Color.CYAN);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class, r);
		//table.setBorder(BorderFactory.createLoweredBevelBorder());

		if(listnum == 0){
			Customer ccc = new Customer();
			ArrayList list = ccc.selectAll1();
			for(int i = 0; i < list.size(); i++){
				ccc = (Customer)list.get(i);
				defaultModel.addRow(new Object[]{ ccc.getname(),
								ccc.getidnum(),ccc.getroom(),ccc.getstatus(),ccc.getintime()});
			}			
		}
		if(listnum == 1){
			Customer ccc = new Customer();
			ArrayList list = ccc.selectAll2();
			for(int i = 0; i < list.size(); i++){
				ccc = (Customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getid2(),ccc.getitems(),
								               ccc.getprice(),ccc.getstock()});
			}
		}
		if(listnum == 2){
			Customer ccc = new Customer();
			ArrayList list = ccc.selectAll3();
			for(int i = 0; i < list.size(); i++){
				ccc = (Customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getroomnum(),
						     ccc.getfee_room(),ccc.getfee_service(),ccc.gettotal()});
			}
		}
		if(listnum == 3){
			Customer ccc = new Customer();
			ArrayList list = ccc.selectAll4();
			for(int i = 0; i < list.size(); i++){
				ccc = (Customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getname(),
								ccc.getidnum(),ccc.getroom(),ccc.getintime(),ccc.getouttime(),ccc.gettotal()});
			}	
		}
		if(listnum == 4){
			Customer ccc = new Customer();
			ArrayList list = ccc.selectAll5();
			for(int i = 0; i < list.size(); i++){
				ccc = (Customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getname(),
								ccc.getidnum(),ccc.getemail(), ccc.getroom(),ccc.getfromday(),ccc.gettoday()});
			}	
		}
		if(listnum == 5){
			Customer ccc = new Customer();
			ArrayList list = ccc.selectAll6();
			for(int i = 0; i < list.size(); i++){
				ccc = (Customer)list.get(i);
				defaultModel.addRow(new Object[]{ccc.getday(),
								ccc.getnumofcheckin(),ccc.getnumofcheckout(),ccc.getfeeofday()});
			}	
		}
	}
}
