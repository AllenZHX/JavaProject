package com.mwq.frame.stat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.mwing.FixedColumnTablePanel;
import com.mwq.tool.Today;

public class YearDialog extends JDialog {

	private JTable table;

	private Vector<String> tableColumnV;

	private Vector<Vector<Object>> tableDataV;

	private DefaultTableModel tableModel;

	private JComboBox yearComboBox;

	private Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			YearDialog dialog = new YearDialog();
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog
	 */
	public YearDialog() {
		super();
		setModal(true);
		setTitle("年销售统计");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		yearComboBox = new JComboBox();
		yearComboBox.setMaximumRowCount(10);
		String minDatetime = dao.sOrderFormOfMinDatetime();
		int year = Today.getYEAR();
		if (minDatetime == null) {
			yearComboBox.addItem(year);
		} else {
			int minYear = Integer.valueOf(minDatetime.substring(0, 4));
			for (int y = minYear; y <= year; y++) {
				yearComboBox.addItem(y);
			}
		}
		yearComboBox.setSelectedItem(year);
		panel.add(yearComboBox);

		final JLabel yearLabel = new JLabel();
		yearLabel.setText("年    ");
		panel.add(yearLabel);

		final JButton submitButton = new JButton();
		submitButton.setText("确定");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableDataV.removeAllElements(); // 清空表格数据
				int year = (Integer) yearComboBox.getSelectedItem();
				tableDataV.addAll(dao.yearCheckOut(year));
				Container contentPane = getContentPane(); // 获得容器a
				contentPane.remove(1); // 移除原表格
				getContentPane().add(
						new FixedColumnTablePanel(tableColumnV, tableDataV, 1),
						BorderLayout.CENTER);// 添加新表格
				SwingUtilities.updateComponentTreeUI(contentPane); // 刷新窗体
			}
		});
		panel.add(submitButton);

		tableColumnV = new Vector<String>();
		tableColumnV.add("日期");
		for (int i = 1; i <= 12; i++) {
			tableColumnV.add(i + " 月");
		}
		tableColumnV.add("总计");

		tableDataV = new Vector<Vector<Object>>();

		getContentPane().add(
				new FixedColumnTablePanel(tableColumnV, tableDataV, 1),
				BorderLayout.CENTER);
		//
	}

}
