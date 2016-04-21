package com.mwq.frame.stat;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.mwing.MTable;
import com.mwq.tool.Today;

public class MonthDialog extends JDialog {

	private JTable table;

	private Vector<String> tableColumnV;

	private Vector tableDataV;

	private DefaultTableModel tableModel;

	private JComboBox monthComboBox;

	private JComboBox yearComboBox;

	private Dao dao = Dao.getInstance();

	private int daysOfMonth[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MonthDialog dialog = new MonthDialog();
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
	public MonthDialog() {
		super();
		setModal(true);
		setTitle("������ͳ��");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		int year = Today.getYEAR();
		int month = Today.getMONTH();

		yearComboBox = new JComboBox();
		yearComboBox.setMaximumRowCount(10);
		String minDatetime = dao.sOrderFormOfMinDatetime();
		if (minDatetime == null) {
			yearComboBox.addItem(year);
		} else {
			int minYear = Integer.valueOf(minDatetime.substring(0, 4));
			for (int y = minYear; y <= year; y++) {
				yearComboBox.addItem(y);
			}
		}
		yearComboBox.setSelectedItem(year);
		judgeLeapYear(year);
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();
				judgeLeapYear(year);
			}
		});
		panel.add(yearComboBox);

		final JLabel yearLabel = new JLabel();
		yearLabel.setText("��");
		panel.add(yearLabel);

		monthComboBox = new JComboBox();
		monthComboBox.setMaximumRowCount(12);
		for (int m = 1; m < 13; m++) {
			monthComboBox.addItem(m);
		}
		monthComboBox.setSelectedItem(month);
		panel.add(monthComboBox);

		final JLabel monthLabel = new JLabel();
		monthLabel.setText("��    ");
		panel.add(monthLabel);

final JButton submitButton = new JButton();
submitButton.setText("ȷ��");
submitButton.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		tableDataV.removeAllElements(); // ��ձ������
		int year = (Integer) yearComboBox.getSelectedItem();
		int month = (Integer) monthComboBox.getSelectedItem();
		String monthStr;
		if (month < 10)
			monthStr = "0" + month;
		else
			monthStr = "" + month;
		for (int day = 1; day <= daysOfMonth[month]; day++) {// ����ָ���µ�������
			Vector rowV = new Vector();// ����ж���
			rowV.add(day);// �����
			String num = (day < 10 ? year + monthStr + "0" + day : year
					+ monthStr + day).toString();
			String[] values = dao.monthCheckOut(num);// ��ѯͳ������
			for (int i = 0; i < values.length; i++) {
				rowV.add(values[i]);// ��ӵ������
			}
			tableDataV.add(rowV); // ��ӵ����������
		}
		Vector totalRowV = new Vector();// ͳ���ж���
		totalRowV.add("�ܼ�");// ������
		String[] values = dao.monthCheckOut(year + monthStr);// ��ѯͳ������
		for (int i = 0; i < values.length; i++) {
			totalRowV.add(values[i]);// ��ӵ�ͳ����
		}
		tableDataV.add(totalRowV);// ��ӵ����������
		tableModel.setDataVector(tableDataV, tableColumnV);// ˢ�±��ģ��
	}
});
		panel.add(submitButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		tableColumnV = new Vector<String>();
		tableColumnV.add("����");
		tableColumnV.add("��̨����");
		tableColumnV.add("�����ܶ�");
		tableColumnV.add("ƽ�����Ѷ�");
		tableColumnV.add("������Ѷ�");
		tableColumnV.add("��С���Ѷ�");

		tableDataV = new Vector();

		tableModel = new DefaultTableModel(tableDataV, tableColumnV);

		table = new MTable(tableModel);
		scrollPane.setViewportView(table);
		//
	}

	private void judgeLeapYear(int year) {
		if (year % 100 == 0) {
			if (year % 400 == 0)
				daysOfMonth[2] = 29;
			else
				daysOfMonth[2] = 28;
		} else {
			if (year % 4 == 0)
				daysOfMonth[2] = 29;
			else
				daysOfMonth[2] = 28;
		}
	}

}
