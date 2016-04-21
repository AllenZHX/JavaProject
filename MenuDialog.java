package com.mwq.frame.system;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.mwing.MTable;
import com.mwq.tool.Today;

public class MenuDialog extends JDialog {

	private JTextField numTextField;

	private JTextField nameTextField;

	private JTextField unitTextField;

	private JTextField codeTextField;

	private JComboBox sortComboBox;

	private JTextField unitPriceTextField;

	private MTable table;

	private final Vector tableColumnV = new Vector();

	private final DefaultTableModel tableModel = new DefaultTableModel();

	private final Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MenuDialog dialog = new MenuDialog();
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
	public MenuDialog() {
		super();
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("��Ʒ����");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		numLabel.setText("��  �ţ�");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 0;
		operatePanel.add(numLabel, gridBagConstraints_6);

		numTextField = new JTextField();
		numTextField.setText(getNextNum(dao.sMenuOfMaxNum()));
		numTextField.setHorizontalAlignment(SwingConstants.CENTER);
		numTextField.setEditable(false);
		numTextField.setColumns(12);
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 1;
		operatePanel.add(numTextField, gridBagConstraints_15);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("���ƣ�");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 15, 0, 0);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		operatePanel.add(nameLabel, gridBagConstraints);

		nameTextField = new JTextField();
		nameTextField.setName("����");
		nameTextField.setColumns(21);
		nameTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (nameTextField.getText().length() < 10) {
					if (e.getKeyChar() == ' ')
						e.consume();
				} else {
					e.consume();
				}
			}
		});
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_1.gridx = 3;
		gridBagConstraints_1.gridy = 0;
		operatePanel.add(nameTextField, gridBagConstraints_1);

		final JLabel unitLabel = new JLabel();
		unitLabel.setText("��λ��");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 4;
		operatePanel.add(unitLabel, gridBagConstraints_8);

		unitTextField = new JTextField();
		unitTextField.setName("��λ");
		unitTextField.setColumns(10);
		unitTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (unitTextField.getText().length() < 2) {
					if (e.getKeyChar() == ' ')
						e.consume();
				} else {
					e.consume();
				}
			}
		});
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 5;
		operatePanel.add(unitTextField, gridBagConstraints_11);

		final JLabel codeLabel = new JLabel();
		codeLabel.setText("�����룺");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_2.gridx = 0;
		gridBagConstraints_2.gridy = 1;
		operatePanel.add(codeLabel, gridBagConstraints_2);

		codeTextField = new JTextField();
		codeTextField.setName("������");
		codeTextField.setColumns(12);
		codeTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (codeTextField.getText().length() < 10) {
					if ("abcdefghijklmnopqrstuvwxyz".indexOf(e.getKeyChar()) < 0)
						e.consume();
				} else {
					e.consume();
				}
			}
		});
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_3.gridx = 1;
		gridBagConstraints_3.gridy = 1;
		operatePanel.add(codeTextField, gridBagConstraints_3);

		final JLabel sortLabel = new JLabel();
		sortLabel.setText("��ϵ��");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_4.gridy = 1;
		gridBagConstraints_4.gridx = 2;
		operatePanel.add(sortLabel, gridBagConstraints_4);

		sortComboBox = new JComboBox();
		sortComboBox.addItem("��ѡ��");
		Vector sortNameV = dao.sSortNames();
		for (int i = 0; i < sortNameV.size(); i++) {
			sortComboBox.addItem(sortNameV.get(i));
		}
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.anchor = GridBagConstraints.WEST;
		gridBagConstraints_7.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 3;
		operatePanel.add(sortComboBox, gridBagConstraints_7);

		final JLabel unitPriceLabel = new JLabel();
		unitPriceLabel.setText("���ۣ�");
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 4;
		operatePanel.add(unitPriceLabel, gridBagConstraints_9);

		unitPriceTextField = new JTextField();
		unitPriceTextField.setName("����");
		unitPriceTextField.setColumns(8);
		unitPriceTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				switch (unitPriceTextField.getText().length()) {
				case 1:
				case 2:
				case 3:
					if ("0123456789".indexOf(e.getKeyChar()) >= 0)
						break;
				case 0:
					if ("123456789".indexOf(e.getKeyChar()) >= 0)
						break;
				default:
					e.consume();
				}
			}
		});
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 1;
		gridBagConstraints_12.gridx = 5;
		operatePanel.add(unitPriceTextField, gridBagConstraints_12);

		final JLabel label = new JLabel();
		label.setText("Ԫ");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_5.gridy = 1;
		gridBagConstraints_5.gridx = 6;
		operatePanel.add(label, gridBagConstraints_5);

		final JPanel panel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		panel.setLayout(flowLayout_1);
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.anchor = GridBagConstraints.EAST;
		gridBagConstraints_14.insets = new Insets(5, 0, 10, 0);
		gridBagConstraints_14.gridwidth = 7;
		gridBagConstraints_14.gridy = 2;
		gridBagConstraints_14.gridx = 0;
		operatePanel.add(panel, gridBagConstraints_14);

		final JButton addButton = new JButton();
		addButton.setText("���");
		addButton.addActionListener(new AddButtonAL());
		panel.add(addButton);

		final JButton delButton = new JButton();
		delButton.setText("ɾ��");
		delButton.addActionListener(new DelButtonAL());
		panel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "�� ��", "�� ��", "�� ��", "������",
				"�� ϵ", "�� λ", "�� ��" };
		for (int i = 0; i < columnNames.length; i++) {
			tableColumnV.add(columnNames[i]);
		}

		tableModel.setDataVector(dao.sMenuV(), tableColumnV);

		table = new MTable(tableModel);
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		scrollPane.setViewportView(table);

		final JLabel label_1 = new JLabel();
		label_1.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(label_1, BorderLayout.WEST);

		final JLabel label_2 = new JLabel();
		label_2.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(label_2, BorderLayout.EAST);

		final JLabel label_3 = new JLabel();
		label_3.setPreferredSize(new Dimension(0, 10));
		getContentPane().add(label_3, BorderLayout.SOUTH);
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
		public void actionPerformed(ActionEvent e) {
			Field[] fields = MenuDialog.class.getDeclaredFields();// ͨ��Java�����ȡMenuDialog�����������
			for (int i = 0; i < fields.length; i++) {// ��������
				Field field = fields[i];// ���ָ������
				if (field.getType().equals(JTextField.class)) { // �鿴�������Ƿ�ΪJTextField����
					field.setAccessible(true); // ͨ���������˽������ʱ������Ϊtrue�������������
					JTextField textField = null;// ����һ��JTextField���͵Ķ���
					try {
						textField = (JTextField) field.get(MenuDialog.this); // ��ñ����е���Ӧ����
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					if (textField.getText().equals("")) { // �ı���Ϊ��
						JOptionPane.showMessageDialog(null, "����д��Ʒ��"
								+ textField.getName() + "����", "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);// ������ʾ
						textField.requestFocus(); // Ϊ���ı��������ý���
						return;
					}
				}
			}
			if (sortComboBox.getSelectedIndex() == 0) {// ������֤�����˵�
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ��������ϵ����", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				return;
			}
			//
			String menus[] = new String[6];// ���������Ʒ��Ϣ
			menus[0] = numTextField.getText();// ��ò�Ʒ���
			menus[1] = nameTextField.getText();// ��ò�Ʒ����
			menus[2] = codeTextField.getText();// ��ò�Ʒ������
			menus[3] = sortComboBox.getSelectedItem().toString();// ��ò�Ʒ������ϵ
			menus[4] = unitTextField.getText();// ��ò�Ʒ��λ
			menus[5] = unitPriceTextField.getText();// ��ò�Ʒ����
			Vector menuV = dao.sMenuByName(menus[2]);// �����ݿ��в�ѯ�ò�Ʒ
			if (menuV == null) { // ������
				dao.iMenu(menus);// �־û������ݿ�
			} else {// �Ѵ���
				if (menuV.get(6).equals("����")) {// ������������
					JOptionPane.showMessageDialog(null, "�ò�Ʒ�Ѿ����ڣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					nameTextField.requestFocus();// Ϊ�����ı��������ý���
					return;
				} else {// ������ͣ��
					dao.uMenuByName(menus);// �ָ�Ϊ����
				}
			}
			int rowCount = tableModel.getRowCount();// ��õ�ǰӵ�в�Ʒ������
			Vector rowV = new Vector();// �������������
			rowV.add(rowCount + 1);// ������
			for (int i = 0; i < menus.length; i++) {
				rowV.add(menus[i]);// ��Ӳ�Ʒ��Ϣ
			}
			tableModel.addRow(rowV);// ��ӵ������
			table.setRowSelectionInterval(rowCount);// ѡ�и���
			//
			numTextField.setText(getNextNum(menus[0]));// ��ʼ������ı���
			nameTextField.setText(null); // ��������ı���
			codeTextField.setText(null); // ����������ı���
			sortComboBox.setSelectedIndex(0); // ѡ�в�ϵ�б���еġ���ѡ����
			unitTextField.setText(null); // ��յ�λ�ı���
			unitPriceTextField.setText(null); // ��յ����ı���
		}
	}

private class DelButtonAL implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		int selectedRow = table.getSelectedRow();// ���ѡ����
		String name = table.getValueAt(selectedRow, 2).toString();// ��ò�Ʒ����
		int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����Ʒ��" + name
				+ "����", "������ʾ", JOptionPane.YES_NO_OPTION);// ����ȷ����ʾ
		if (i == 0) {// ȷ��ɾ��
			tableModel.removeRow(selectedRow);// �ӱ����ɾ���ò�Ʒ
			int rowCount = table.getRowCount();// ���ɾ����ӵ�еĲ�Ʒ��
			if (rowCount > 0) {// ��ӵ�в�Ʒ
				if (selectedRow < rowCount) { // ɾ���Ĳ��Ǳ�����һ�еĲ�Ʒ
					for (int row = selectedRow; row < rowCount; row++) {
						table.setValueAt(row + 1 + "", row, 0);// �޸�λ��ɾ����Ʒ֮������
					}
					table.setRowSelectionInterval(selectedRow);// ѡ������һ��
				} else { // ɾ�����Ǳ�����һ�еĲ�Ʒ
					table.setRowSelectionInterval(rowCount - 1);// ѡ�б������һ��
				}
			}
			dao.dMenuByName(name);// �����ݿ���ɾ���ò�Ʒ
		}
	}
}

}
