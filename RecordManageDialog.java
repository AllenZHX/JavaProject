package com.mwq.frame.personnel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.mwing.MDateField;
import com.mwq.mwing.MTable;
import com.mwq.tool.Today;

public class RecordManageDialog extends JDialog {

	private ButtonGroup sexButtonGroup = new ButtonGroup();

	private MTable table;

	private DefaultTableModel tableModel;

	private JTextField addressTextField;

	private JTextField idCardTextField;

	private JTextField nameTextField;

	private MDateField birthdayField;

	private Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			RecordManageDialog dialog = new RecordManageDialog();
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
	public RecordManageDialog() {
		super();
		setModal(true);
		setTitle("��������");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("��    ����");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.insets = new Insets(10, 0, 0, 0);
		operatePanel.add(nameLabel, gridBagConstraints_14);

		nameTextField = new JTextField();
		nameTextField.setColumns(17);
		nameTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (nameTextField.getText().length() < 4) {// �����ĳ������Ϊ4������
					if (e.getKeyChar() == ' ')// ����������ո�
						e.consume();// ���ٴ˴μ����¼�
				} else {
					e.consume();// ���ٴ˴μ����¼�
				}
			}
		});
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 1;
		operatePanel.add(nameTextField, gridBagConstraints_1);

		final JLabel sexLabel = new JLabel();
		sexLabel.setText("��    ��");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 2;
		operatePanel.add(sexLabel, gridBagConstraints_2);

		final JRadioButton manRadioButton = new JRadioButton();
		sexButtonGroup.add(manRadioButton);
		manRadioButton.setText("��");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(10, 0, 0, 5);
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 3;
		operatePanel.add(manRadioButton, gridBagConstraints_3);

		final JRadioButton womanRadioButton = new JRadioButton();
		sexButtonGroup.add(womanRadioButton);
		womanRadioButton.setText("Ů");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 4;
		operatePanel.add(womanRadioButton, gridBagConstraints_4);

		final JLabel idCardLabel = new JLabel();
		idCardLabel.setText("���֤�ţ�");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_5.gridy = 0;
		gridBagConstraints_5.gridx = 5;
		operatePanel.add(idCardLabel, gridBagConstraints_5);

		idCardTextField = new JTextField();
		idCardTextField.setColumns(15);
		idCardTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (idCardTextField.getText().length() < 20) { // ���֤�ŵĳ������Ϊ20λ
					if ("0123456789".indexOf(e.getKeyChar()) < 0)// ���֤��ֻ����Ϊ����
						e.consume(); // ���ٴ˴μ����¼�
				} else {
					e.consume(); // ���ٴ˴μ����¼�
				}
			}
		});
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 6;
		operatePanel.add(idCardTextField, gridBagConstraints_6);

		final JLabel birthdayLabel = new JLabel();
		birthdayLabel.setText("�������ڣ�");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		operatePanel.add(birthdayLabel, gridBagConstraints);

		birthdayField = new MDateField(false);
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 1;
		operatePanel.add(birthdayField, gridBagConstraints_7);

		final JLabel addressLabel = new JLabel();
		addressLabel.setText("��ͥסַ��");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.gridy = 1;
		gridBagConstraints_11.gridx = 2;
		operatePanel.add(addressLabel, gridBagConstraints_11);

		addressTextField = new JTextField();
		addressTextField.setColumns(40);
		addressTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (addressTextField.getText().length() >= 50)// ��ͥסַ�ĳ������Ϊ50������
					e.consume(); // ���ٴ˴μ����¼�
			}
		});
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.gridwidth = 4;
		gridBagConstraints_12.gridy = 1;
		gridBagConstraints_12.gridx = 3;
		operatePanel.add(addressTextField, gridBagConstraints_12);

		final JPanel buttonPanel = new JPanel();
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.anchor = GridBagConstraints.EAST;
		gridBagConstraints_13.gridwidth = 7;
		gridBagConstraints_13.gridy = 2;
		gridBagConstraints_13.gridx = 0;
		operatePanel.add(buttonPanel, gridBagConstraints_13);

		final JButton addButton = new JButton();
		addButton.setText("���");
		addButton.addActionListener(new AddButtonAL());
		buttonPanel.add(addButton);

		final JButton delButton = new JButton();
		delButton.setText("ɾ��");
		delButton.addActionListener(new DelButtonAL());
		buttonPanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		String[] columnNames = { "���", "�������", "����", "�Ա�", "��������", "���֤��",
				"��ͥסַ" };
		Vector columnNameV = new Vector();
		for (int i = 0; i < columnNames.length; i++) {
			columnNameV.add(columnNames[i]);
		}
		Vector<Vector> tableDataV = dao.sRecord();
		for (int row = 0; row < tableDataV.size(); row++) {
			String birthday = tableDataV.get(row).get(4).toString();
			tableDataV.get(row).set(4, birthday.substring(0, 10));
		}

		tableModel = new DefaultTableModel(tableDataV, columnNameV);
		tableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.DELETE) {// �鿴�Ƿ�����ɾ���д�����
					int deleteRow = e.getFirstRow();// ��ô����е�����
					int rowCount = tableModel.getRowCount();// ��ñ��ǰӵ�е�����
					if (rowCount > 0) {
						if (deleteRow == rowCount) {// ɾ����Ϊ���һ��
							table.setRowSelectionInterval(--rowCount);// ѡ����ǰ���һ��
						} else {// ɾ���Ĳ������һ��
							table.setRowSelectionInterval(deleteRow);// ѡ��������һ��
							for (int row = deleteRow; row < rowCount; row++) {// �������������
								tableModel.setValueAt(row + 1, row, 0);// �޸������
							}
						}
					}
				}
			}
		});

		table = new MTable(tableModel);
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		scrollPane.setViewportView(table);

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(label, BorderLayout.WEST);

		final JLabel label_1 = new JLabel();
		label_1.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(label_1, BorderLayout.EAST);

		final JLabel label_2 = new JLabel();
		label_2.setPreferredSize(new Dimension(0, 10));
		getContentPane().add(label_2, BorderLayout.SOUTH);
		//
	}

	private String getNextNum(String maxNum) {
		String date = Today.getDateOfNum().substring(2);
		if (maxNum == null) {
			maxNum = date + "001";
		} else {
			if (maxNum.subSequence(0, 6).equals(date)) {
				maxNum = maxNum.substring(6);
				int nextNum = Integer.valueOf(maxNum) + 1;
				if (nextNum < 10)
					maxNum = date + "00" + nextNum;
				else if (nextNum < 100)
					maxNum = date + "0" + nextNum;
				else
					maxNum = date + nextNum;
			} else {
				maxNum = date + "001";
			}
		}
		return maxNum;
	}

	private class AddButtonAL implements ActionListener {

		private String[] records = new String[6];

		private final String[] infos = { "", "����", "�Ա�", "��������", "���֤��", "��ͥסַ" };

		public void actionPerformed(ActionEvent e) {
			records[1] = nameTextField.getText();
			records[2] = "";
			records[3] = birthdayField.getDateAndInit(true);
			records[4] = idCardTextField.getText();
			records[5] = addressTextField.getText().trim();
			Enumeration<AbstractButton> elements = sexButtonGroup.getElements();
			while (elements.hasMoreElements()) {
				JRadioButton element = (JRadioButton) elements.nextElement();
				if (element.isSelected())
					records[2] = element.getText();
			}
			for (int i = 1; i < records.length; i++) {
				if (records[i].equals("")) {
					JOptionPane.showMessageDialog(RecordManageDialog.this,
							"����д��" + infos[i] + "����", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			records[0] = getNextNum(dao.sRecordOfMaxNum());
			dao.iRecord(records);
			int rowCount = table.getRowCount();
			Vector rowV = new Vector();
			rowV.add(rowCount + 1);
			for (int i = 0; i < records.length; i++) {
				rowV.add(records[i]);
			}
			tableModel.addRow(rowV);
			table.setRowSelectionInterval(rowCount, rowCount);
			nameTextField.setText("");
			idCardTextField.setText("");
			addressTextField.setText("");
		}
	}

	private class DelButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();
			String name = table.getValueAt(selectedRow, 2).toString();
			int i = JOptionPane
					.showConfirmDialog(RecordManageDialog.this, "ȷ��Ҫ���Ա����"
							+ name + "����", "������ʾ", JOptionPane.YES_NO_OPTION);
			if (i == 0) {
				String num = table.getValueAt(selectedRow, 1).toString();
				if (dao.sManagerByRecordNum(num) != null)
					dao.dManagerByRecordNum(num);
				tableModel.removeRow(selectedRow);
				dao.dRecordByNum(num);
			}
		}
	}

}
