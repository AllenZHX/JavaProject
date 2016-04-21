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
import javax.swing.SwingUtilities;

import com.mwq.dao.Dao;
import com.mwq.mwing.FixedColumnTablePanel;
import com.mwq.tool.Today;

public class DayDialog extends JDialog {

	private Vector<String> tableColumnV;

	private Vector<Vector<Object>> tableDataV;

	private JComboBox dayComboBox;

	private JComboBox monthComboBox;

	private JComboBox yearComboBox;

	private int daysOfMonth[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };

	private Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DayDialog dialog = new DayDialog();
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
	public DayDialog() {
		super();
		setModal(true);
		setTitle("������ͳ��");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		int year = Today.getYEAR();
		int month = Today.getMONTH();
		int day = Today.getDAY();

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
				int year = (Integer) yearComboBox.getSelectedItem();// ���ѡ�е����
				judgeLeapYear(year);// �ж��Ƿ�Ϊ���꣬��ȷ��2�·ݵ�����
				int month = (Integer) monthComboBox.getSelectedItem();// ���ѡ�е��·�
				if (month == 2) {// ���ѡ�е�Ϊ2��
					int itemCount = dayComboBox.getItemCount();// ����������˵���ǰ������
					if (itemCount != daysOfMonth[2]) {// ����������˵���ǰ������������2�·ݵ�����
						if (itemCount == 28)// ����������˵���ǰ������Ϊ28��
							dayComboBox.addItem(29);// �����Ϊ29��
						else
							// �����������˵���ǰ��������Ϊ29��
							dayComboBox.removeItem(29);// �����Ϊ28��
					}
				}
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
		monthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int month = (Integer) monthComboBox.getSelectedItem();// ���ѡ�е��·�
				int itemCount = dayComboBox.getItemCount();// ����������˵���ǰ������
				while (itemCount != daysOfMonth[month]) {// ����������˵���ǰ������������ѡ���·ݵ�����
					if (itemCount > daysOfMonth[month]) {// �������ѡ���·ݵ�����
						dayComboBox.removeItem(itemCount);// ���Ƴ����һ��ѡ����
						itemCount--;// �����������˵���ǰ��������1
					} else {// ����С��ѡ���·ݵ�����
						itemCount++;// ���������˵���ǰ��������1
						dayComboBox.addItem(itemCount);// �����Ϊѡ����
					}
				}
			}
		});
		panel.add(monthComboBox);

		final JLabel monthLabel = new JLabel();
		monthLabel.setText("��");
		panel.add(monthLabel);

		dayComboBox = new JComboBox();
		dayComboBox.setMaximumRowCount(10);
		int days = daysOfMonth[month];
		for (int d = 1; d <= days; d++) {
			dayComboBox.addItem(d);
		}
		dayComboBox.setSelectedItem(day);
		panel.add(dayComboBox);

		final JLabel dayLabel = new JLabel();
		dayLabel.setText("��    ");
		panel.add(dayLabel);

		final JButton submitButton = new JButton();
		submitButton.setText("ȷ��");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();
				int month = (Integer) monthComboBox.getSelectedItem();
				int day = (Integer) dayComboBox.getSelectedItem();
				int columnCount = tableColumnV.size();
				Vector orderFormsV = dao.sOrderFormOfDay(year + "-" + month
						+ "-" + day);// ��ѯָ�����ڵ��������ѵ�
				tableDataV.removeAllElements();// ��ձ������
				for (int row = 0; row < orderFormsV.size(); row++) {// ������ѵ���Ϣ

					Vector<Object> rowV = new Vector<Object>();// ����ж���

					Vector orderFormV = (Vector) orderFormsV.get(row);// ���ѵ�����
					String orderFormNum = orderFormV.get(1).toString();// ������ѵ����
					rowV.add(orderFormNum);// ���
					rowV.add(orderFormV.get(2));// ̨��
					rowV.add(orderFormV.get(3).toString().substring(11, 19));// ��̨ʱ��
					rowV.add(orderFormV.get(4));// ���ѽ��
					for (int column = 4; column < columnCount; column++) {
						rowV.add("����");// ��Ʒ����������Ĭ��δ����
					}
					Vector orderItemsV = dao
							.sOrderItemAndMenuByOrderFormNum(orderFormNum);// ��ѯָ�����ѵ�������������Ŀ
					for (int i = 0; i < orderItemsV.size(); i++) {// ����������Ŀ
						Vector orderItemV = (Vector) orderItemsV.get(i);// ������Ŀ����
						String menuName = orderItemV.get(3).toString();// ���������Ŀ����
						for (int column = 4; column < columnCount; column++) {// ���������
							if (tableColumnV.get(column).equals(menuName)) {// �鿴���������������Ŀ���Ƿ���ͬ
								int amount = (Integer) orderItemV.get(4);// ��ø���Ŀ����������
								Object cellValue = rowV.get(column);// ��ñ��Ԫ���ֵ
								if (cellValue.equals("����"))// û�����ѹ�
									rowV.set(column, amount);
								else
									// �Ѿ����ѹ�
									rowV.set(column, (Integer) cellValue
											+ amount);
								break;
							}
						}
					}
					tableDataV.add(rowV);// ��ӵ����������
				}
				Vector<Object> totalRowV = new Vector<Object>();// ͳ���ж���
				totalRowV.add("�ܼ�");// �����
				totalRowV.add("����");// ̨����
				totalRowV.add("����");// ��̨ʱ����
				int rowCount = tableDataV.size();// ���ѵ�����
				for (int column = 3; column < columnCount; column++) {// ������
					int total = 0;// Ĭ����������Ϊ0
					for (int row = 0; row < rowCount; row++) {// ������
						Object cellValue = tableDataV.get(row).get(column);// ��ñ��Ԫ���ֵ
						if (!cellValue.equals("����"))// �鿴�Ƿ����ѹ�
							total += (Integer) cellValue;// �ۼ���������
					}
					totalRowV.add(total);// ��ӵ������
				}
				tableDataV.add(totalRowV);// ��ӵ����������
				Container contentPane = getContentPane();// �������
				contentPane.remove(1);// �Ƴ�ԭ���
				contentPane.add(new FixedColumnTablePanel(tableColumnV,
						tableDataV, 4), BorderLayout.CENTER);// ����±��
				SwingUtilities.updateComponentTreeUI(contentPane);// ˢ�´���
			}
		});
		panel.add(submitButton);

		tableColumnV = new Vector<String>();
		tableColumnV.add("���");
		tableColumnV.add("̨��");
		tableColumnV.add("��̨ʱ��");
		tableColumnV.add("���ѽ��");
		Vector<Vector<Object>> menuV = dao.sMenu();
		for (int i = 0; i < menuV.size(); i++) {
			tableColumnV.add(menuV.get(i).get(3).toString());
		}

		tableDataV = new Vector<Vector<Object>>();

		getContentPane().add(
				new FixedColumnTablePanel(tableColumnV, tableDataV, 4),
				BorderLayout.CENTER);
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
