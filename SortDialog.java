package com.mwq.frame.system;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.mwing.MTable;

public class SortDialog extends JDialog {

	private MTable table;

	private JTextField sortNameTextField;

	private final Vector columnNameV = new Vector();

	private final DefaultTableModel tableModel = new DefaultTableModel();

	private final Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			SortDialog dialog = new SortDialog();
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
	public SortDialog() {
		super();
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("菜系管理");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel sortNameLabel = new JLabel();
		operatePanel.add(sortNameLabel);
		sortNameLabel.setText("菜系名称：");

		sortNameTextField = new JTextField();
		sortNameTextField.setColumns(20);
		sortNameTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (sortNameTextField.getText().length() < 10) {
					if (e.getKeyChar() == ' ')
						e.consume();
				} else {
					e.consume();
				}
			}
		});
		operatePanel.add(sortNameTextField);

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(20, 0));
		operatePanel.add(label);

		final JButton addButton = new JButton();// 创建添加菜系名称按钮对象
		addButton.setText("添加");
		addButton.addActionListener(new AddButtonAL());
		operatePanel.add(addButton);

		final JButton delButton = new JButton();// 创建删除菜系名称按钮对象
		delButton.setText("删除");
		delButton.addActionListener(new DelButtonAL());
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		columnNameV.add("序    号");
		columnNameV.add("菜系名称");
		tableModel.setDataVector(dao.sSort(), columnNameV);

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

	private class AddButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = sortNameTextField.getText(); // 获得菜系名称
			if (name.equals("")) {// 查看是否输入了菜系名称
				JOptionPane.showMessageDialog(null, "请输入“菜系名称”！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				sortNameTextField.requestFocus();// 为菜系名称文本框请求获得焦点
				return;
			}
			int rowCount = tableModel.getRowCount();// 获得当前拥有菜系的个数
			Vector sortV = dao.sSortByName(name); // 从数据库中查询该菜系
			if (sortV == null) {// 不存在
				dao.iSort(name); // 持久化到数据库
				sortV = new Vector(); // 创建一个向量
				sortV.add(rowCount + 1);// 添加序号
				sortV.add(name);// 添加菜系名称
			} else { // 已存在
				if (sortV.get(2).toString().equals("销售")) { // 并且正在销售
					JOptionPane.showMessageDialog(null, "该菜系已经存在！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					return;
				} else { // 但是已停售
					dao.uSortByName(name); //恢复为销售
					sortV.set(0, rowCount + 1);// 设置序号
				}
			}
			tableModel.addRow(sortV);// 将新菜系添加到表格中
			table.setRowSelectionInterval(rowCount, rowCount);// 选中菜系
			//
			sortNameTextField.setText(null);// 清空菜系名称文本框
		}
	}

private class DelButtonAL implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		int selectedRow = table.getSelectedRow();// 获得选中行
		String name = table.getValueAt(selectedRow, 1).toString();// 获得菜系名称
		Vector<Vector> menusV = dao.sMenuVBySortName(name);// 从数据库查询该菜系所属的菜品
		String[] infos = null;// 提示信息
		if (menusV.size() > 0) {// 组织提示信息
			infos = new String[menusV.size() + 2];
			infos[0] = "如果删除菜系“" + name + "”，将同时删除它所包含的如下菜品：";
			for (int i = 0; i < infos.length; i++) {
				infos[i + 1] = menusV.get(i).get(1).toString();
			}
			infos[infos.length - 1] = "确定要删除菜系“" + name + "”？";
		} else {
			infos = new String[] { "确定要删除菜系“" + name + "”？" };
		}
		int i = JOptionPane.showConfirmDialog(null, infos, "友情提示",
				JOptionPane.YES_NO_OPTION);// 弹出确认提示
		if (i == 0) {// 确认删除
			tableModel.removeRow(selectedRow);// 从表格中删除该菜系
			int rowCount = table.getRowCount();// 获得删除后拥有的菜系数
			if (rowCount > 0) {// 还拥有菜系
				if (selectedRow < rowCount) {// 删除的不是表格最后一行的菜系
					for (int row = selectedRow; row < rowCount; row++) {
						table.setValueAt(row + 1 + "", row, 0);// 修改位于删除菜系之后的序号
					}
					table.setRowSelectionInterval(selectedRow);// 选中其后的一行
				} else {// 删除的是表格最后一行的菜系
					table.setRowSelectionInterval(rowCount - 1);// 选中表格的最后一行
				}
			}
			dao.dSortByName(name);// 从数据库中删除该菜系
		}
	}
}

}
