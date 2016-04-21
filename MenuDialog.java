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
		setTitle("菜品管理");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		numLabel.setText("编  号：");
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
		nameLabel.setText("名称：");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 15, 0, 0);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		operatePanel.add(nameLabel, gridBagConstraints);

		nameTextField = new JTextField();
		nameTextField.setName("名称");
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
		unitLabel.setText("单位：");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 4;
		operatePanel.add(unitLabel, gridBagConstraints_8);

		unitTextField = new JTextField();
		unitTextField.setName("单位");
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
		codeLabel.setText("助记码：");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_2.gridx = 0;
		gridBagConstraints_2.gridy = 1;
		operatePanel.add(codeLabel, gridBagConstraints_2);

		codeTextField = new JTextField();
		codeTextField.setName("助记码");
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
		sortLabel.setText("菜系：");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_4.gridy = 1;
		gridBagConstraints_4.gridx = 2;
		operatePanel.add(sortLabel, gridBagConstraints_4);

		sortComboBox = new JComboBox();
		sortComboBox.addItem("请选择");
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
		unitPriceLabel.setText("单价：");
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 4;
		operatePanel.add(unitPriceLabel, gridBagConstraints_9);

		unitPriceTextField = new JTextField();
		unitPriceTextField.setName("单价");
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
		label.setText("元");
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
		addButton.setText("添加");
		addButton.addActionListener(new AddButtonAL());
		panel.add(addButton);

		final JButton delButton = new JButton();
		delButton.setText("删除");
		delButton.addActionListener(new DelButtonAL());
		panel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "序 号", "编 号", "名 称", "助记码",
				"菜 系", "单 位", "单 价" };
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
			Field[] fields = MenuDialog.class.getDeclaredFields();// 通过Java反射获取MenuDialog类的所有属性
			for (int i = 0; i < fields.length; i++) {// 遍历属性
				Field field = fields[i];// 获得指定属性
				if (field.getType().equals(JTextField.class)) { // 查看该属性是否为JTextField类型
					field.setAccessible(true); // 通过反射访问私有属性时必须设为true，否则不允许访问
					JTextField textField = null;// 声明一个JTextField类型的对象
					try {
						textField = (JTextField) field.get(MenuDialog.this); // 获得本类中的相应对象
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					if (textField.getText().equals("")) { // 文本框为空
						JOptionPane.showMessageDialog(null, "请填写商品“"
								+ textField.getName() + "”！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);// 弹出提示
						textField.requestFocus(); // 为该文本框请求获得焦点
						return;
					}
				}
			}
			if (sortComboBox.getSelectedIndex() == 0) {// 单独验证下拉菜单
				JOptionPane.showMessageDialog(null, "请选择商品所属“菜系”！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				return;
			}
			//
			String menus[] = new String[6];// 用来保存菜品信息
			menus[0] = numTextField.getText();// 获得菜品编号
			menus[1] = nameTextField.getText();// 获得菜品名称
			menus[2] = codeTextField.getText();// 获得菜品助记码
			menus[3] = sortComboBox.getSelectedItem().toString();// 获得菜品所属菜系
			menus[4] = unitTextField.getText();// 获得菜品单位
			menus[5] = unitPriceTextField.getText();// 获得菜品单价
			Vector menuV = dao.sMenuByName(menus[2]);// 从数据库中查询该菜品
			if (menuV == null) { // 不存在
				dao.iMenu(menus);// 持久化到数据库
			} else {// 已存在
				if (menuV.get(6).equals("销售")) {// 并且正在销售
					JOptionPane.showMessageDialog(null, "该菜品已经存在！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					nameTextField.requestFocus();// 为名称文本框请求获得焦点
					return;
				} else {// 但是已停售
					dao.uMenuByName(menus);// 恢复为销售
				}
			}
			int rowCount = tableModel.getRowCount();// 获得当前拥有菜品的数量
			Vector rowV = new Vector();// 创建表格行向量
			rowV.add(rowCount + 1);// 添加序号
			for (int i = 0; i < menus.length; i++) {
				rowV.add(menus[i]);// 添加菜品信息
			}
			tableModel.addRow(rowV);// 添加到表格中
			table.setRowSelectionInterval(rowCount);// 选中该行
			//
			numTextField.setText(getNextNum(menus[0]));// 初始化编号文本框
			nameTextField.setText(null); // 清空名称文本框
			codeTextField.setText(null); // 清空助记码文本框
			sortComboBox.setSelectedIndex(0); // 选中菜系列表框中的“请选择”项
			unitTextField.setText(null); // 清空单位文本框
			unitPriceTextField.setText(null); // 清空单价文本框
		}
	}

private class DelButtonAL implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		int selectedRow = table.getSelectedRow();// 获得选中行
		String name = table.getValueAt(selectedRow, 2).toString();// 获得菜品名称
		int i = JOptionPane.showConfirmDialog(null, "确定要删除菜品“" + name
				+ "”？", "友情提示", JOptionPane.YES_NO_OPTION);// 弹出确认提示
		if (i == 0) {// 确认删除
			tableModel.removeRow(selectedRow);// 从表格中删除该菜品
			int rowCount = table.getRowCount();// 获得删除后拥有的菜品数
			if (rowCount > 0) {// 还拥有菜品
				if (selectedRow < rowCount) { // 删除的不是表格最后一行的菜品
					for (int row = selectedRow; row < rowCount; row++) {
						table.setValueAt(row + 1 + "", row, 0);// 修改位于删除菜品之后的序号
					}
					table.setRowSelectionInterval(selectedRow);// 选中其后的一行
				} else { // 删除的是表格最后一行的菜品
					table.setRowSelectionInterval(rowCount - 1);// 选中表格的最后一行
				}
			}
			dao.dMenuByName(name);// 从数据库中删除该菜品
		}
	}
}

}
