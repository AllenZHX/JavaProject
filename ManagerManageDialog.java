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
		setTitle("管理员管理");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel personnelLabel = new JLabel();
		personnelLabel.setText("员工：");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 0, 0, 0);
		operatePanel.add(personnelLabel, gridBagConstraints_8);

		personnelComboBox = new JComboBox();
		personnelComboBox.addItem("请选择");
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
		passwordLabel.setText("登录密码：");
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
				if (passwordTextField.getText().length() < 20) {// 登录密码的长度最多为20个字符
					if ("abcdefghigklmnopqrstuvwsyz_0123456789".indexOf(e
							.getKeyChar()) < 0)// 密码只能包含字母、数字和下划线
						e.consume(); // 销毁此次键盘事件
				} else {
					e.consume(); // 销毁此次键盘事件
				}
			}
		});
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 3;
		operatePanel.add(passwordTextField, gridBagConstraints_4);

		final JLabel purviewLabel = new JLabel();
		purviewLabel.setText("权限：");
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
		systemCheckBox.setText("系统维护");
		systemCheckBox.setSelected(true);
		purviewPanel.add(systemCheckBox);

		sellCheckBox = new JCheckBox();
		sellCheckBox.setText("销售统计");
		purviewPanel.add(sellCheckBox);

		personnelCheckBox = new JCheckBox();
		personnelCheckBox.setText("人员管理");
		purviewPanel.add(personnelCheckBox);

		initCheckBox = new JCheckBox();
		initCheckBox.setText("初始化系统    ");
		purviewPanel.add(initCheckBox);

		final JPanel buttonPanel = new JPanel();
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.anchor = GridBagConstraints.EAST;
		gridBagConstraints_7.gridwidth = 4;
		gridBagConstraints_7.gridy = 2;
		gridBagConstraints_7.gridx = 0;
		operatePanel.add(buttonPanel, gridBagConstraints_7);

		final JButton addButton = new JButton();
		addButton.setText("添加");
		addButton.addActionListener(new AddButtonAL());
		buttonPanel.add(addButton);

		final JButton delButton = new JButton();
		delButton.setText("删除");
		delButton.addActionListener(new DelButtonAL());
		buttonPanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		String[] columnNames = { "序号", "档案编号", "姓    名", "系统维护", "销售统计",
				"人员管理", "初始化系统" };
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
			String personnel = personnelComboBox.getSelectedItem().toString();// 获得员工选择框的选中项
			if (personnel.equals("请选择")) {// 查看是否选择了员工
				JOptionPane.showMessageDialog(ManagerManageDialog.this,
						"请选择设置为管理员的员工！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				return;
			}
			String password = passwordTextField.getText();// 获得登录密码
			if (password.length() == 0) {// 查看是否输入了登录密码
				JOptionPane.showMessageDialog(ManagerManageDialog.this,
						"请设置登录密码！", "友情提示", JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				return;
			}
			String[] managers = new String[6];// 用来存储管理员信息
			managers[0] = personnel.substring(0, 9);// 档案编号
			managers[1] = password;// 密码
			managers[2] = (systemCheckBox.isSelected() ? "√" : "");// 系统维护权限
			managers[3] = (sellCheckBox.isSelected() ? "√" : "");// 销售统计权限
			managers[4] = (personnelCheckBox.isSelected() ? "√" : "");// 人员管理权限
			managers[5] = (initCheckBox.isSelected() ? "√" : "");// 初始化系统权限
			personnelComboBox.removeItem(personnel);// 从员工选择框中移除该员工
			dao.iManager(managers);// 持久化到数据库
			Vector managerV = new Vector();// 创建管理员向量
			int rowCount = table.getRowCount();// 获得现有管理员数量
			managerV.add(rowCount + 1);// 添加序号
			for (int i = 0; i < managers.length; i++) {// 添加管理员信息
				managerV.add(managers[i]);
			}
			managerV.set(2, personnel.substring(10));// 将密码修改为姓名
			tableModel.addRow(managerV);// 添加到表格模型
			table.setRowSelectionInterval(rowCount);// 选中新添加的管理员
			//
			personnelComboBox.setSelectedIndex(0);// 默认选中员工列表框的“请选择”项
			systemCheckBox.setSelected(true);// 默认具有系统维护权限
			sellCheckBox.setSelected(false);// 默认不具有销售统计权限
			personnelCheckBox.setSelected(false);// 默认不具有人员管理权限
			initCheckBox.setSelected(false);// 默认不具有初始化系统权限
		}
	}

	private class DelButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();// 获得选中行
			String name = table.getValueAt(selectedRow, 2).toString();// 获得员工姓名
			int i = JOptionPane.showConfirmDialog(ManagerManageDialog.this,
					"确定要取消管理员“" + name + "”？", "友情提示",
					JOptionPane.YES_NO_OPTION);// 弹出确认框
			if (i == 0) {// 确认删除
				String num = table.getValueAt(selectedRow, 1).toString();// 获得档案编号
				dao.dManagerByRecordNum(num);// 从数据库中删除
				tableModel.removeRow(selectedRow);// 从表格模型中移除
			}
		}
	}

}
