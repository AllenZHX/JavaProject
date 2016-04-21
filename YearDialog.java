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
		setTitle("������ͳ��");
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
		yearLabel.setText("��    ");
		panel.add(yearLabel);

		final JButton submitButton = new JButton();
		submitButton.setText("ȷ��");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableDataV.removeAllElements(); // ��ձ������
				int year = (Integer) yearComboBox.getSelectedItem();
				tableDataV.addAll(dao.yearCheckOut(year));
				Container contentPane = getContentPane(); // �������a
				contentPane.remove(1); // �Ƴ�ԭ���
				getContentPane().add(
						new FixedColumnTablePanel(tableColumnV, tableDataV, 1),
						BorderLayout.CENTER);// ����±��
				SwingUtilities.updateComponentTreeUI(contentPane); // ˢ�´���
			}
		});
		panel.add(submitButton);

		tableColumnV = new Vector<String>();
		tableColumnV.add("����");
		for (int i = 1; i <= 12; i++) {
			tableColumnV.add(i + " ��");
		}
		tableColumnV.add("�ܼ�");

		tableDataV = new Vector<Vector<Object>>();

		getContentPane().add(
				new FixedColumnTablePanel(tableColumnV, tableDataV, 1),
				BorderLayout.CENTER);
		//
	}

}
