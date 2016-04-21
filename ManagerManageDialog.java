package com.mwq.frame.personnel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.mwing.MTable;

public class ManagerManageDialog extends JDialog {

	private JTextField passwordTextField;

	private JComboBox personnelComboBox;

	private MTable table;

	private DefaultTableModel tableModel;

	private Dao dao = Dao.getInstance();

	private JCheckBox systemCheckBox;

	private JCheckBox sellCheckBox;

	private JCheckBox personnelCheckBox;

	private JCheckBox initCheckBox;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			ManagerManageDialog dialog = new ManagerManageDialog();
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
	public ManagerManageDialog() {
		super();
		setModal(true);
		setTitle("����Ա����");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel personnelLabel = new JLabel();
		personnelLabel.setText("Ա����");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 0, 0, 0);
		operatePanel.add(personnelLabel, gridBagConstraints_8);

		personnelComboBox = new JComboBox();
		personnelComboBox.addItem("��ѡ��");
		Vector nameV = dao.sRecordOfNumAndName();
		for (Iterator it = nameV.iterator(); it.hasNext();) {
			personnelComboBox.addItem(it.next());
		}

		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 15);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		operatePanel.add(personnelComboBox, gridBagConstraints);

		final JLabel passwordLabel = new JLabel();
		passwordLabel.setText("��¼���룺");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 2;
		operatePanel.add(passwordLabel, gridBagConstraints_2);

		passwordTextField = new JTextField();
		passwordTextField.setColumns(40);
		passwordTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (passwordTextField.getText().length() < 20) {// ��¼����ĳ������Ϊ20���ַ�
					if ("abcdefghigklmnopqrstuvwsyz_0123456789".indexOf(e
							.getKeyChar()) < 0)// ����ֻ�ܰ�����ĸ�����ֺ��»���
						e.consume(); // ���ٴ˴μ����¼�
				} else {
					e.consume(); // ���ٴ˴μ����¼�
				}
			}
		});
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 3;
		operatePanel.add(passwordTextField, gridBagConstraints_4);

		final JLabel purviewLabel = new JLabel();
		purviewLabel.setText("Ȩ�ޣ�");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_5.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_5.gridy = 1;
		gridBagConstraints_5.gridx = 0;
		operatePanel.add(purviewLabel, gridBagConstraints_5);

		final JPanel purviewPanel = new JPanel();
		purviewPanel.setLayout(new GridLayout(0, 4));
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_6.gridwidth = 3;
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.gridx = 1;
		operatePanel.add(purviewPanel, gridBagConstraints_6);

		systemCheckBox = new JCheckBox();
		systemCheckBox.setText("ϵͳά��");
		systemCheckBox.setSelected(true);
		purviewPanel.add(systemCheckBox);

		sellCheckBox = new JCheckBox();
		sellCheckBox.setText("����ͳ��");
		purviewPanel.add(sellCheckBox);

		personnelCheckBox = new JCheckBox();
		personnelCheckBox.setText("��Ա����");
		purviewPanel.add(personnelCheckBox);

		initCheckBox = new JCheckBox();
		initCheckBox.setText("��ʼ��ϵͳ    ");
		purviewPanel.add(initCheckBox);

		final JPanel buttonPanel = new JPanel();
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.anchor = GridBagConstraints.EAST;
		gridBagConstraints_7.gridwidth = 4;
		gridBagConstraints_7.gridy = 2;
		gridBagConstraints_7.gridx = 0;
		operatePanel.add(buttonPanel, gridBagConstraints_7);

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

		String[] columnNames = { "���", "�������", "��    ��", "ϵͳά��", "����ͳ��",
				"��Ա����", "��ʼ��ϵͳ" };
		Vector columnNameV = new Vector();
		for (int i = 0; i < columnNames.length; i++) {
			columnNameV.add(columnNames[i]);
		}

		tableModel = new DefaultTableModel(dao.sManagerVExceptPassword(),
				columnNameV);
		tableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.DELETE) {
					int deleteRow = e.getFirstRow();
					int rowCount = tableModel.getRowCount();
					if (rowCount > 0) {
						if (deleteRow == rowCount) {
							table.setRowSelectionInterval(--rowCount);
						} else {
							table.setRowSelectionInterval(deleteRow);
							for (int row = deleteRow; row < rowCount; row++) {
								tableModel.setValueAt(row + 1, row, 0);
							}
						}
					}
				}
			}
		});

		table = new MTable(tableModel);
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0);
		scrollPane.setViewportView(table);

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(label, BorderLayout.WEST);

		final JLabel label_1 = new JLabel();
		label_1.setPreferredSize(new Dimension(0, 10));
		getContentPane().add(label_1, BorderLayout.SOUTH);

		final JLabel label_2 = new JLabel();
		label_2.setPreferredSize(new Dimension(10, 0));
		getContentPane().add(label_2, BorderLayout.EAST);
		//
	}

	private class AddButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String personnel = personnelComboBox.getSelectedItem().toString();// ���Ա��ѡ����ѡ����
			if (personnel.equals("��ѡ��")) {// �鿴�Ƿ�ѡ����Ա��
				JOptionPane.showMessageDialog(ManagerManageDialog.this,
						"��ѡ������Ϊ����Ա��Ա����", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				return;
			}
			String password = passwordTextField.getText();// ��õ�¼����
			if (password.length() == 0) {// �鿴�Ƿ������˵�¼����
				JOptionPane.showMessageDialog(ManagerManageDialog.this,
						"�����õ�¼���룡", "������ʾ", JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				return;
			}
			String[] managers = new String[6];// �����洢����Ա��Ϣ
			managers[0] = personnel.substring(0, 9);// �������
			managers[1] = password;// ����
			managers[2] = (systemCheckBox.isSelected() ? "��" : "");// ϵͳά��Ȩ��
			managers[3] = (sellCheckBox.isSelected() ? "��" : "");// ����ͳ��Ȩ��
			managers[4] = (personnelCheckBox.isSelected() ? "��" : "");// ��Ա����Ȩ��
			managers[5] = (initCheckBox.isSelected() ? "��" : "");// ��ʼ��ϵͳȨ��
			personnelComboBox.removeItem(personnel);// ��Ա��ѡ������Ƴ���Ա��
			dao.iManager(managers);// �־û������ݿ�
			Vector managerV = new Vector();// ��������Ա����
			int rowCount = table.getRowCount();// ������й���Ա����
			managerV.add(rowCount + 1);// ������
			for (int i = 0; i < managers.length; i++) {// ��ӹ���Ա��Ϣ
				managerV.add(managers[i]);
			}
			managerV.set(2, personnel.substring(10));// �������޸�Ϊ����
			tableModel.addRow(managerV);// ��ӵ����ģ��
			table.setRowSelectionInterval(rowCount);// ѡ������ӵĹ���Ա
			//
			personnelComboBox.setSelectedIndex(0);// Ĭ��ѡ��Ա���б��ġ���ѡ����
			systemCheckBox.setSelected(true);// Ĭ�Ͼ���ϵͳά��Ȩ��
			sellCheckBox.setSelected(false);// Ĭ�ϲ���������ͳ��Ȩ��
			personnelCheckBox.setSelected(false);// Ĭ�ϲ�������Ա����Ȩ��
			initCheckBox.setSelected(false);// Ĭ�ϲ����г�ʼ��ϵͳȨ��
		}
	}

	private class DelButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();// ���ѡ����
			String name = table.getValueAt(selectedRow, 2).toString();// ���Ա������
			int i = JOptionPane.showConfirmDialog(ManagerManageDialog.this,
					"ȷ��Ҫȡ������Ա��" + name + "����", "������ʾ",
					JOptionPane.YES_NO_OPTION);// ����ȷ�Ͽ�
			if (i == 0) {// ȷ��ɾ��
				String num = table.getValueAt(selectedRow, 1).toString();// ��õ������
				dao.dManagerByRecordNum(num);// �����ݿ���ɾ��
				tableModel.removeRow(selectedRow);// �ӱ��ģ�����Ƴ�
			}
		}
	}

}
