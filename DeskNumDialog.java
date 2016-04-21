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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.dao.JDBC;
import com.mwq.mwing.MTable;

public class DeskNumDialog extends JDialog {

	private JTable table;

	private JTextField seatingTextField;

	private JTextField numTextField;

	private JTable openingDeskTable;

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
			DeskNumDialog dialog = new DeskNumDialog(null);
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
	public DeskNumDialog(JTable rightTable) {
		super();
		setModal(true);
		setTitle("台号管理");
		setResizable(false);
		setBounds(100, 100, 500, 375);
		getContentPane().setLayout(new BorderLayout());

		this.openingDeskTable = rightTable;

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		operatePanel.add(numLabel);
		numLabel.setText("台  号：");

		numTextField = new JTextField();
		numTextField.setColumns(8);
		numTextField.setHorizontalAlignment(SwingConstants.CENTER);
		numTextField.addKeyListener(new KeyAdapter() {

			String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";

			public void keyTyped(KeyEvent e) {
				if (numTextField.getText().length() < 6) {
					String input = "" + e.getKeyChar();
					if (chars.indexOf(input.toUpperCase()) < 0)
						e.consume();
				} else {
					e.consume();
				}
			}
		});
		operatePanel.add(numTextField);

		final JLabel seatingLabel = new JLabel();
		operatePanel.add(seatingLabel);
		seatingLabel.setText("  座位数：");

		seatingTextField = new JTextField();
		seatingTextField.setColumns(8);
		seatingTextField.setHorizontalAlignment(SwingConstants.CENTER);
		seatingTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				switch (seatingTextField.getText().length()) {
				case 1:
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
		operatePanel.add(seatingTextField);

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(20, 0));
		operatePanel.add(label);

		final JButton addButton = new JButton();// 创建添加台号按钮对象
		addButton.setText("添加");
		addButton.addActionListener(new AddButtonAL());
		operatePanel.add(addButton);

		final JButton delButton = new JButton();// 创建删除台号按钮对象
		delButton.setText("删除");
		delButton.addActionListener(new DelButtonAL());
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "序  号", "台  号", "座位数" };
		for (int i = 0; i < columnNames.length; i++) {
			columnNameV.add(columnNames[i]);
		}

		tableModel.setDataVector(dao.sDesk(), columnNameV);

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
			String num = numTextField.getText();// 获取台号
			if (num.equals("")) {// 查看是否输入了台号
				JOptionPane.showMessageDialog(null, "请输入“台号”！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				numTextField.requestFocus();// 为台号文本框请求获得焦点
				return;
			}
			String seating = seatingTextField.getText();// 获取座位数
			if (seating.equals("")) {// 查看是否输入了座位数
				JOptionPane.showMessageDialog(null, "请输入“座位数”！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				seatingTextField.requestFocus();// 为座位数文本框请求获得焦点
				return;
			}
			int rowCount = table.getRowCount();// 获得当前拥有台号的个数
			Vector deskV = dao.sDeskByNum(num);// 从数据库中查询该台号
			if (deskV == null) {// 不存在
				dao.iDesk(num, seating);// 持久化到数据库
				deskV = new Vector();// 创建一个向量
				deskV.add(rowCount + 1);// 添加序号
				deskV.add(num);// 添加台号
				deskV.add(seating);// 添加座位数
			} else {// 已存在
				if (deskV.get(2).toString().equals("可用")) {// 并且可用
					JOptionPane.showMessageDialog(null, "该台号已经存在！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					return;
				} else {// 但是已撤消
					dao.uDeskByNum(num, seating);// 恢复为可用
					deskV.insertElementAt(rowCount + 1, 0);// 添加序号
				}
			}
			tableModel.addRow(deskV);// 将台号添加到表格中
			table.setRowSelectionInterval(rowCount, rowCount);// 选中台号
			//
			numTextField.setText(null);// 将台号文本框设置为空
			seatingTextField.setText(null);// 将座位数文本框设置为空
			numTextField.requestFocus();// 为台号文本框请求获得焦点
		}
	}

private class DelButtonAL implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		int selectedRow = table.getSelectedRow();// 获得选中行
		if (selectedRow < 0) {// 未选中任何行
			JOptionPane.showMessageDialog(null, "请选择要删除的餐台！", "友情提示",
					JOptionPane.INFORMATION_MESSAGE);// 弹出提示
			return;
		}
		String deskNum = table.getValueAt(selectedRow, 1).toString();// 获得台号
		for (int row = 0; row < openingDeskTable.getRowCount(); row++) {// 遍历正在被使用的餐台
			if (deskNum.equals(openingDeskTable.getValueAt(row, 1))) {// 查看该餐台是否正在被使用
				JOptionPane.showMessageDialog(null, "该餐台正在使用，不能删除！",
						"友情提示", JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				return;
			}
		}
		String infos[] = new String[] {// 组织确认信息
		"确定要删除餐台：", "    台  号：" + deskNum,
				"    座位数：" + table.getValueAt(selectedRow, 2) };
		int i = JOptionPane.showConfirmDialog(null, infos, "友情提示",
				JOptionPane.YES_NO_OPTION);// 弹出确认提示
		if (i == 0) {// 确认删除
			dao.dDeskByNum(deskNum);// 从数据库中删除
			tableModel.setDataVector(dao.sDesk(), columnNameV);// 刷新表格
			int rowCount = table.getRowCount();// 获得删除后拥有的餐台数
			if (rowCount > 0) {// 还拥有餐台
				if (selectedRow == rowCount)// 删除的为最后一个餐台
					selectedRow -= 1;// 将选中的餐台前移一行
				table.setRowSelectionInterval(selectedRow, selectedRow);// 设置当前选中的餐台
			}
		}
	}
}

}
