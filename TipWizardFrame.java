package com.mwq.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.mwq.DrinkeryManage;
import com.mwq.dao.Dao;
import com.mwq.frame.personnel.HandoverDialog;
import com.mwq.frame.personnel.ManagerManageDialog;
import com.mwq.frame.personnel.RecordManageDialog;
import com.mwq.frame.personnel.UpdatePasswordDialog;
import com.mwq.frame.stat.DayDialog;
import com.mwq.frame.stat.MonthDialog;
import com.mwq.frame.stat.YearDialog;
import com.mwq.frame.system.DeskNumDialog;
import com.mwq.frame.system.MenuDialog;
import com.mwq.frame.system.SortDialog;
import com.mwq.mwing.MButton;
import com.mwq.mwing.MTable;
import com.mwq.tool.Today;

public class TipWizardFrame extends JFrame {

	private JTextField changeTextField;

	private JTextField realWagesTextField;

	private JTextField expenditureTextField;

	private JTextField priceTextField;

	private JTextField amountTextField;

	private JTextField unitTextField;

	private JTextField nameTextField;

	private JTextField codeTextField;

	private JComboBox numComboBox;

	private JLabel timeLabel;

	private JLabel menuLabel;

	public JLabel operatorLabel;

	private PopupMenuListener popupMenuListener;

	private JPopupMenu popupMenu;

	private ButtonGroup buttonGroup = new ButtonGroup();

	private Vector<Vector<Vector<Object>>> itemOfDeskV;

	private MTable rightTable;

	private DefaultTableModel rightTableModel;

	private Vector<String> rightTableColumnV;

	private Vector<Vector<Object>> rightTableDataV;

	private MTable leftTable;

	private DefaultTableModel leftTableModel;

	private Vector<String> leftTableColumnV;

	private Vector<Vector<Object>> leftTableDataV;

	public Vector managerV;

	private final Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	private final Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			TipWizardFrame frame = new TipWizardFrame(null);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public TipWizardFrame(Vector managerV) {
		super();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);// 不执行任何操作
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {// 将在关闭窗体之前被执行
				if (rightTable.getRowCount() > 0)// 查看是否有正在消费的餐台
					JOptionPane.showMessageDialog(null, "还有未结账的餐台，当前不能退出系统！",
							"友情提示", JOptionPane.WARNING_MESSAGE);// 弹出提示
				else
					System.exit(0);// 退出系统
			}
		});
		setTitle("明日科技");
		setBounds(100, 100, 1024, 768);
		setExtendedState(TipWizardFrame.MAXIMIZED_BOTH);

		this.managerV = managerV;

		final JLabel iconLabel = new JLabel();
		iconLabel.setPreferredSize(new Dimension(0, 100));
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		iconLabel.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/main_top.png")));
		getContentPane().add(iconLabel, BorderLayout.NORTH);

		final JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		splitPane.setDividerLocation(754);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		final JTabbedPane leftTabbedPane = new JTabbedPane();
		splitPane.setLeftComponent(leftTabbedPane);

		final JScrollPane leftScrollPane = new JScrollPane();
		leftTabbedPane.addTab("签单列表", null, leftScrollPane, null);

		itemOfDeskV = new Vector<Vector<Vector<Object>>>();

		leftTableColumnV = new Vector<String>();
		String leftTableColumns[] = { "  ", "序    号", "商品编号", "商品名称", "单    位",
				"数    量", "单    价", "金    额" };
		for (int i = 0; i < leftTableColumns.length; i++) {
			leftTableColumnV.add(leftTableColumns[i]);
		}

		leftTableDataV = new Vector<Vector<Object>>();

		leftTableModel = new DefaultTableModel(leftTableDataV, leftTableColumnV);
		leftTableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) { // 当添加或移除表格行时被执行
				int rowCount = leftTable.getRowCount();// 获得“签单列表”中的行数
				int expenditure = 0;// 默认消费 0 元
				for (int row = 0; row < rowCount; row++) {// 通过循环统计消费金额
					expenditure += Integer.valueOf(leftTable.getValueAt(row, 7)
							.toString());// 累加消费金额
				}
				expenditureTextField.setText(expenditure + "");// 更新“消费金额”文本框
			}
		});

		leftTable = new MTable(leftTableModel);
		leftScrollPane.setViewportView(leftTable);

		final JTabbedPane rightTabbedPane = new JTabbedPane();
		splitPane.setRightComponent(rightTabbedPane);

		final JScrollPane rightScrollPane = new JScrollPane();
		rightTabbedPane.addTab("开台列表", null, rightScrollPane, null);

		rightTableColumnV = new Vector<String>();
		rightTableColumnV.add("序    号");
		rightTableColumnV.add("台    号");
		rightTableColumnV.add("开台时间");

		rightTableDataV = new Vector<Vector<Object>>();

		rightTableModel = new DefaultTableModel(rightTableDataV,
				rightTableColumnV);

		rightTable = new MTable(rightTableModel);
		rightTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRow = rightTable.getSelectedRow();// 获得“开台列表”中的选中行
				leftTableDataV.removeAllElements(); // 清空“签单列表”中的现有项目
				leftTableDataV.addAll(itemOfDeskV.get(selectedRow));// 向“签单列表”添加新选中餐台的消费项目
				leftTableModel.setDataVector(leftTableDataV, leftTableColumnV);// 刷新“签单列表”表格模型的数据
				leftTable.setRowSelectionInterval(0);// 选中“签单列表”中的第一行
				numComboBox.setSelectedItem(rightTable.getValueAt(selectedRow,
						1));// 同步选中“台号”选择框中的该台号
			}
		});
		rightScrollPane.setViewportView(rightTable);

		final JPanel operatePanel = new JPanel();
		operatePanel.setPreferredSize(new Dimension(0, 200));
		operatePanel.setLayout(new BorderLayout());
		getContentPane().add(operatePanel, BorderLayout.SOUTH);

		final JPanel orderDishesPanel = new JPanel();
		operatePanel.add(orderDishesPanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel(); // 创建“台号”标签
		numLabel.setText("台号："); // 设置标签文本
		orderDishesPanel.add(numLabel);

		numComboBox = new JComboBox(); // 创建台号选择框
		numComboBox.setNextFocusableComponent(codeTextField); // 设置下一个将要获得焦点的组件
		initNumComboBox(); // 初始化台号选择框
		numComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = rightTable.getRowCount(); // 获得已开台数
				if (rowCount == 0) // 没有开台
					return; // 不执行任何操作
				Object selectedDeskNum = numComboBox.getSelectedItem(); // 获得“台号”选择框中的选中项
				int needSelectedRow = -1; // 默认选中的台号未开台
				for (int row = 0; row < rowCount; row++) { // 查看选中的台号是否已经开台
					if (selectedDeskNum.equals(rightTable.getValueAt(row, 1))) { // 已经开台
						needSelectedRow = row; // 保存其在“开台列表” 中的所在行
						break; // 跳出循环
					}
				}
				if (needSelectedRow == -1) { // 选中的台号尚未开台，即将要开台
					rightTable.clearSelection(); // 取消选择“开台列表”中的选中行
					leftTableDataV.removeAllElements(); // 清空“签单列表”中的所有行
					leftTableModel.setDataVector(leftTableDataV,
							leftTableColumnV); // 刷新“签单列表”表格模型的数据
				} else { // 选中的台号已经开台，即将要添加消费项目
					rightTable.setRowSelectionInterval(needSelectedRow);// 在“开台列表”中选中该台号
					leftTableDataV.removeAllElements(); // 清空“签单列表”中的现有项目
					leftTableDataV.addAll(itemOfDeskV.get(needSelectedRow)); // 向“签单列表”添加新选中餐台的消费项目
					leftTableModel.setDataVector(leftTableDataV,
							leftTableColumnV); // 刷新“签单列表”表格模型的数据
					leftTable.setRowSelectionInterval(0); // 选中“签单列表”中的第一行
				}
			}
		});
		orderDishesPanel.add(numComboBox);

		final JPanel codePanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		codePanel.setLayout(flowLayout);
		orderDishesPanel.add(codePanel);

		final JLabel codeALabel = new JLabel();
		codeALabel.setText("    商品（");
		codePanel.add(codeALabel);

		final JRadioButton numRadioButton = new JRadioButton();
		numRadioButton.setFocusable(false);
		buttonGroup.add(numRadioButton);
		numRadioButton.setText("编号");
		codePanel.add(numRadioButton);

		final JLabel codeBLabel = new JLabel();
		codeBLabel.setText("/");
		codePanel.add(codeBLabel);

		final JRadioButton codeRadioButton = new JRadioButton();
		codeRadioButton.setFocusable(false);
		buttonGroup.add(codeRadioButton);
		codeRadioButton.setSelected(true);
		codeRadioButton.setText("助记码");
		codePanel.add(codeRadioButton);

		final JLabel codeCLabel = new JLabel();
		codeCLabel.setText("）：");
		codePanel.add(codeCLabel);

		codeTextField = new JTextField();
		codeTextField.setColumns(16);
		codeTextField.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {// 通过键盘监听器实现控制文本框的输入内容
				if ("abcdefghijklmnopqrstuvwxyz0123456789".indexOf(e
						.getKeyChar()) < 0)// 只允许输入字母和数字
					e.consume();// 销毁此次键盘事件
			}

			public void keyReleased(KeyEvent e) {// 通过键盘监听器实现智能获取菜品
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {// 按下回车键
					makeOutAnInvoice();// 开单
				} else {
					String input = codeTextField.getText();// 获得输入内容
					Vector menuV = null;// 符合条件的菜品
					if (input.length() > 0) {// 确认输入了内容
						if (codeRadioButton.isSelected()) {// 按助记码查询
							Vector menusV = dao.sMenuByCode(input);// 查询符合条件的菜品
							if (menusV.size() > 0) {// 存在符合条件的菜品
								menuV = (Vector) menusV.get(0);// 获得第一个符合条件的菜品
								menuV.remove(0);// 移除菜品的序号
							}
						} else {// 按编号查询
							if (input.length() == 9)// 确认编号是否为9位
								menuV = dao.sMenuByNum(input);// 查询符合条件的菜品
						}
					}
					if (menuV == null) {// 不存在符合条件的菜品
						nameTextField.setText(null);// 设置“商品名称”文本框为空
						unitTextField.setText(null);// 设置“单位”文本框为空
						priceTextField.setText(null);// 设置“单价”文本框为空
					} else {// 存在符合条件的菜品
						nameTextField.setText(menuV.get(2).toString());// 设置“商品名称”文本框为符合条件的菜品名称
						unitTextField.setText(menuV.get(4).toString());// 设置“单位”文本框为符合条件的菜品单位
						priceTextField.setText(menuV.get(5).toString());// 设置“单价”文本框为符合条件的菜品单价
					}
				}
			}
		});
		orderDishesPanel.add(codeTextField);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("    商品名称：");
		orderDishesPanel.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setColumns(24);
		nameTextField.setEditable(false);
		nameTextField.setFocusable(false);// 设置“商品名称”文本框不可以获得焦点
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		orderDishesPanel.add(nameTextField);

		final JLabel unitLabel = new JLabel();
		unitLabel.setText("    单位：");
		orderDishesPanel.add(unitLabel);

		unitTextField = new JTextField();
		unitTextField.setColumns(6);
		unitTextField.setEditable(false);
		unitTextField.setFocusable(false); // 设置“单位”文本框不可以获得焦点
		unitTextField.setHorizontalAlignment(SwingConstants.CENTER);
		orderDishesPanel.add(unitTextField);

		final JLabel priceLabel = new JLabel();
		priceLabel.setText("    单价：");
		orderDishesPanel.add(priceLabel);

		priceTextField = new JTextField();
		priceTextField.setColumns(6);
		priceTextField.setEditable(false);
		priceTextField.setFocusable(false); // 设置“单价”文本框不可以获得焦点
		priceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		orderDishesPanel.add(priceTextField);

		final JLabel amountLabel = new JLabel();
		amountLabel.setText("元    数量：");
		orderDishesPanel.add(amountLabel);

		amountTextField = new JTextField();// 创建“数量”文本框
		amountTextField.setColumns(6);
		amountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		amountTextField.setNextFocusableComponent(codeTextField);
		amountTextField.setText("1");// 默认数量为1
		amountTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {// 当文本框获得焦点时执行
				amountTextField.setText(null);// 设置“数量”文本框为空
			}

			public void focusLost(FocusEvent e) {// 当文本框失去焦点时执行
				String amount = amountTextField.getText();// 获得输入的数量
				if (amount.length() == 0)// 未输入数量
					amountTextField.setText("1"); // 恢复为默认数量1
			}
		});
		amountTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int length = amountTextField.getText().length();// 获取当前数量的位数
				if (length < 2) {// 位数小于两位
					String num = (length == 0 ? "123456789" : "0123456789"); // 将允许输入的字符定义成字符串
					if (num.indexOf(e.getKeyChar()) < 0)// 查看按键字符是否包含在允许输入的字符中
						e.consume(); // 如果不包含在允许输入的字符中则销毁此次按键事件
				} else {
					e.consume(); // 如果不小于数量允许的最大位数则销毁此次按键事件
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					makeOutAnInvoice();// 开单
					codeTextField.requestFocus();// 为用来输入助记码的文本框请求焦点
				}
			}
		});
		orderDishesPanel.add(amountTextField);

		final JPanel functionPanel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setVgap(0);
		functionPanel.setLayout(flowLayout_1);
		functionPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		operatePanel.add(functionPanel);

		final JLabel adLabel = new JLabel();
		adLabel.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/main_ad.png")));
		functionPanel.add(adLabel);

		final JPanel checkOutPanel = new JPanel();
		checkOutPanel.setPreferredSize(new Dimension(260, 150));
		checkOutPanel.setLayout(new GridBagLayout());
		functionPanel.add(checkOutPanel);

		final JLabel expenditureLabel = new JLabel();
		expenditureLabel.setFont(new Font("", Font.BOLD, 16));
		expenditureLabel.setText("消费金额：");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.gridx = 0;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.insets = new Insets(0, 10, 0, 0);
		checkOutPanel.add(expenditureLabel, gridBagConstraints_13);

		expenditureTextField = new JTextField();
		expenditureTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		expenditureTextField.setText("0");
		expenditureTextField.setForeground(new Color(255, 0, 0));
		expenditureTextField.setFont(new Font("", Font.BOLD, 15));
		expenditureTextField.setColumns(7);
		expenditureTextField.setEditable(false);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 1;
		checkOutPanel.add(expenditureTextField, gridBagConstraints_6);

		final JLabel expenditureUnitLabel = new JLabel();
		expenditureUnitLabel.setForeground(new Color(255, 0, 0));
		expenditureUnitLabel.setFont(new Font("", Font.BOLD, 15));
		expenditureUnitLabel.setText(" 元");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 2;
		checkOutPanel.add(expenditureUnitLabel, gridBagConstraints_14);

		final JLabel realWagesLabel = new JLabel();
		realWagesLabel.setFont(new Font("", Font.BOLD, 16));
		realWagesLabel.setText("实收金额：");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 0;
		checkOutPanel.add(realWagesLabel, gridBagConstraints_7);

		realWagesTextField = new JTextField();
		realWagesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		realWagesTextField.setText("0");
		realWagesTextField.setForeground(new Color(0, 128, 0));
		realWagesTextField.setFont(new Font("", Font.BOLD, 15));
		realWagesTextField.setColumns(7);
		realWagesTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				realWagesTextField.setText("");
			}

			public void focusLost(FocusEvent e) {
				if (realWagesTextField.getText().length() == 0)
					realWagesTextField.setText("0");
			}
		});
		realWagesTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int length = realWagesTextField.getText().length();// 获取当前数量的位数
				if (length < 8) {// 位数小于两位
					String num = (length == 4 ? "123456789" : "0123456789"); // 将允许输入的字符定义成字符串
					if (num.indexOf(e.getKeyChar()) < 0)// 查看按键字符是否包含在允许输入的字符中
						e.consume(); // 如果不包含在允许输入的字符中则销毁此次按键事件
				} else {
					e.consume(); // 如果不小于数量允许的最大位数则销毁此次按键事件
				}
			}
		});
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 1;
		checkOutPanel.add(realWagesTextField, gridBagConstraints_8);

		final JLabel realWagesUnitLabel = new JLabel();
		realWagesUnitLabel.setForeground(new Color(0, 128, 0));
		realWagesUnitLabel.setFont(new Font("", Font.BOLD, 15));
		realWagesUnitLabel.setText(" 元");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_15.gridy = 1;
		gridBagConstraints_15.gridx = 2;
		checkOutPanel.add(realWagesUnitLabel, gridBagConstraints_15);

		final JButton checkOutButton = new JButton();
		checkOutButton.setText("结 账");
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();
				if (selectedRow < 0) {// 未选中任何餐台
					JOptionPane.showMessageDialog(null, "请选择要结账的餐台！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					return;
				}
				int rowCount = leftTable.getRowCount();// 获得结账餐台的点菜数量
				if (leftTable.getValueAt(rowCount - 1, 0).equals("NEW")) {// 存在未签单菜品
					JOptionPane.showMessageDialog(null, "请先确定未签单商品的处理方式！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					return;
				}
				int expenditure = Integer.valueOf(expenditureTextField
						.getText());// 获得消费金额
				int realWages = Integer.valueOf(realWagesTextField.getText());// 获得实收金额
				if (realWages < expenditure) {// 查看实收金额是否小于消费金额
					if (realWages == 0)// 未输入实收金额
						JOptionPane.showMessageDialog(null, "请输入实收金额！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					else
						// 实收金额小于消费金额
						JOptionPane.showMessageDialog(null, "实收金额不能小于消费金额！",
								"友情提示", JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					realWagesTextField.requestFocus();// 为“实收金额”文本框请求获得焦点
					return;
				}
				changeTextField.setText((realWages - expenditure) + "");// 计算并设置“找零金额”
				String[] orderFormData = {
						getNum(),
						rightTable.getValueAt(selectedRow, 1).toString(),
						Today.getDate() + " "
								+ rightTable.getValueAt(selectedRow, 2),
						expenditureTextField.getText(),
						TipWizardFrame.this.managerV.get(0).toString() };// 组织消费单信息
				dao.iOrderForm(orderFormData);// 持久化到数据库
				String[] orderItemData = new String[4];// 用来存储消费项目信息
				orderItemData[0] = dao.sOrderFormOfMaxId();// 获得消费单编号
				for (int row = 0; row < rowCount; row++) {// 通过循环持久化消费项目信息
					orderItemData[1] = leftTable.getValueAt(row, 2).toString();// 获得菜品编号
					orderItemData[2] = leftTable.getValueAt(row, 5).toString();// 获得菜品数量
					orderItemData[3] = leftTable.getValueAt(row, 7).toString();// 获得菜品消费金额
					dao.iOrderItem(orderItemData);// 持久化到数据库
				}
				JOptionPane.showMessageDialog(null, rightTable.getValueAt(
						selectedRow, 1)
						+ " 结账完成！", "友情提示", JOptionPane.INFORMATION_MESSAGE);// 弹出提示
				//
				rightTableModel.removeRow(selectedRow);// 从“开台列表”中移除结账餐台
				leftTableDataV.removeAllElements();// 清空“签单列表”
				leftTableModel.setDataVector(leftTableDataV, leftTableColumnV);// 刷新“签单列表”
				realWagesTextField.setText("0");// 清空“实收金额”文本框
				changeTextField.setText("0");// 清空“找零金额”文本框
				itemOfDeskV.remove(selectedRow);// 从数据集中移除结账餐台
			}
		});
		checkOutButton.setMargin(new Insets(2, 14, 2, 14));
		checkOutButton.setFont(new Font("", Font.BOLD, 12));
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.anchor = GridBagConstraints.EAST;
		gridBagConstraints_10.gridwidth = 2;
		gridBagConstraints_10.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 1;
		checkOutPanel.add(checkOutButton, gridBagConstraints_10);

		final JLabel changeLabel = new JLabel();
		changeLabel.setFont(new Font("", Font.BOLD, 16));
		changeLabel.setText("找零金额：");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_11.gridy = 3;
		gridBagConstraints_11.gridx = 0;
		checkOutPanel.add(changeLabel, gridBagConstraints_11);

		changeTextField = new JTextField();
		changeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		changeTextField.setText("0");
		changeTextField.setForeground(new Color(255, 0, 255));
		changeTextField.setFont(new Font("", Font.BOLD, 15));
		changeTextField.setEditable(false);
		changeTextField.setColumns(7);
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 3;
		gridBagConstraints_12.gridx = 1;
		checkOutPanel.add(changeTextField, gridBagConstraints_12);

		final JLabel changeUnitLabel = new JLabel();
		changeUnitLabel.setForeground(new Color(255, 0, 255));
		changeUnitLabel.setFont(new Font("", Font.BOLD, 15));
		changeUnitLabel.setText(" 元");
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_16.gridy = 3;
		gridBagConstraints_16.gridx = 2;
		checkOutPanel.add(changeUnitLabel, gridBagConstraints_16);

		final JLabel aLineLabel = new JLabel();
		aLineLabel.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/main_vl.png")));
		functionPanel.add(aLineLabel);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(230, 150));
		functionPanel.add(buttonPanel);

		final JPanel orderDishesButtonPanel = new JPanel();
		orderDishesButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(orderDishesButtonPanel);

		final JButton addButton = new MButton();
		addButton.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/main_add.png")));
		addButton.setRolloverIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/main_add_on.png")));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeOutAnInvoice();
				codeTextField.requestFocus();
			}
		});
		orderDishesButtonPanel.add(addButton);

		final JButton subButton = new MButton();// 创建按钮
		subButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_sub.png")));// 按钮默认时显示的图片
		subButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_sub_on.png")));// 光标移入时显示的图片
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();// 获得“开台列表”中的选中行
				if (selectedRow < 0) {// 未选中任何行
					JOptionPane.showMessageDialog(null, "请选择要签单的台号！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					int lastRowIndex = leftTable.getRowCount() - 1;// 获得最后一个消费项目的索引
					Object lastRowSign = leftTable.getValueAt(lastRowIndex, 0);// 获得最后一个消费项目的标记
					if (lastRowSign.equals("NEW")) {// 为未签单项目
						Object firstRowSign = leftTable.getValueAt(0, 0);// 获得第一个消费项目的标记
						if (firstRowSign.equals("NEW")) {// 为未签单项目，即为新开台签单
							for (int row = lastRowIndex; row >= 0; row--) {
								leftTable.setValueAt("", row, 0);// 签单
							}
						} else {// 为已签单项目，即为新添加菜品签单
							for (int row = lastRowIndex; row >= 0; row--) {
								if (leftTable.getValueAt(row, 0).equals("NEW"))// 为未签单项目
									leftTable.setValueAt("", row, 0);// 签单
								else
									// 为已签单项目，即为新添加菜品签单完成
									break;
							}
						}
					}
				}
			}
		});
		orderDishesButtonPanel.add(subButton);

		final JButton delButton = new MButton();// 创建按钮
		delButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_del.png")));// 按钮默认时显示的图片
		delButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_del_on.png")));// 光标移入时显示的图片
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int leftSelectedRow = leftTable.getSelectedRow();// 获得“签单列表”中的选中行，即要取消的消费项目
				if (leftSelectedRow < 0) {// 未选中任何行
					JOptionPane.showMessageDialog(null, "请选择要取消的商品！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					return;
				}
				if (leftTable.getValueAt(leftSelectedRow, 0).equals("")) {// 已签单商品不允许取消
					JOptionPane.showMessageDialog(null, "很抱歉，该商品已经不能取消！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);// 弹出提示
					return;
				}
				int rightSelectedRow = rightTable.getSelectedRow();// 获得“开台列表”中的选中行，即要取消菜品的餐台
				int i = JOptionPane.showConfirmDialog(null, "确定要取消“"
						+ rightTable.getValueAt(rightSelectedRow, 1) + "”中的商品“"
						+ leftTable.getValueAt(leftSelectedRow, 3) + "”？",
						"友情提示", JOptionPane.YES_NO_OPTION);// 弹出提示信息确认是否取消
				if (i == 0) {// 确认取消
					Vector<Vector<Object>> itemV = itemOfDeskV
							.get(rightSelectedRow);// 获得消费项目列表
					itemV.remove(leftSelectedRow);// 取消指定消费项目
					int rowCount = itemV.size();// 获得取消后的消费项目数量
					if (rowCount == 0) {// 没有任何消费项目
						leftTableModel.setDataVector(null, leftTableColumnV);// 清空“签单列表”
						rightTableModel.removeRow(rightSelectedRow);// 取消开台
						itemOfDeskV.remove(rightSelectedRow);// 移除消费项目列表
					} else {
						if (leftSelectedRow == rowCount) {// 取消项目为最后一个
							leftSelectedRow -= 1;// 设置前一个项目被选中
						} else {// 取消项目不是最后一个
							for (int row = leftSelectedRow; row < rowCount; row++) {// 修改项目序号
								itemV.get(row).set(1, row + 1);
							}
						}
						leftTableModel.setDataVector(itemV, leftTableColumnV);// 刷新表格数据模型
						leftTable.setRowSelectionInterval(leftSelectedRow);// 设置后一个项目被选中
					}
				}
			}
		});
		orderDishesButtonPanel.add(delButton);

		final JPanel safetyButtonPanel = new JPanel();
		safetyButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(safetyButtonPanel);

		final JButton returnButton = new MButton();
		returnButton.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/main_return.png")));
		returnButton.setRolloverIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/main_return_on.png")));
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String managerName = TipWizardFrame.this.managerV.get(1)
						.toString();
				HandoverDialog handoverDialog = new HandoverDialog(
						TipWizardFrame.this);
				handoverDialog.setLocation((screenSize.width - handoverDialog
						.getWidth()) / 2, (screenSize.height - handoverDialog
						.getHeight()) / 2);
				handoverDialog.setVisible(true);
				if (!TipWizardFrame.this.managerV.get(1).toString().equals(
						managerName))
					initPopupMenu();
			}
		});
		safetyButtonPanel.add(returnButton);

		final JToggleButton lockToggleButton = new JToggleButton();// 创建按钮对象
		lockToggleButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_lock_false.png")));// 默认时显示的图片
		lockToggleButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_lock_on.png")));// 光标移入时显示的图片
		lockToggleButton.setSelectedIcon(new ImageIcon(getClass().getResource(
				"/img/main_lock_true.png")));// 选中时显示的图片
		lockToggleButton.setRolloverSelectedIcon(new ImageIcon(getClass()
				.getResource("/img/main_lock_on.png")));// 光标移入时显示的图片
		lockToggleButton.setFocusPainted(false);// 不绘制激活框
		lockToggleButton.setBorderPainted(false);// 不绘制边框
		lockToggleButton.setContentAreaFilled(false);// 不填充
		lockToggleButton.setMargin(new Insets(0, 0, 0, 0));// 设置边距
		lockToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lockToggleButton.isSelected()) {// 按钮已被选中（按下状态）
					codeTextField.setEnabled(false);// 设置“助记码”文本框为不可用
					amountTextField.setEnabled(false);// 设置“数量”文本框为不可用
					realWagesTextField.setEnabled(false);// 设置“实收金额”文本框为不可用
					checkOutButton.setEnabled(false);// 设置“结账”按钮为不可用
					addButton.setEnabled(false);// 设置“开单”按钮为不可用
					subButton.setEnabled(false);// 设置“签单”按钮为不可用
					delButton.setEnabled(false);// 设置“取消”按钮为不可用
					returnButton.setEnabled(false);// 设置“交接班”按钮为不可用
					menuLabel.setIcon(new ImageIcon(TipWizardFrame.class
							.getResource("/img/main_menu_false.png")));// 修改为不可用状态时的图片
					menuLabel.removeMouseListener(popupMenuListener);// 移除菜单的鼠标事件监听器
				} else {// 按钮未被选中（弹起状态）
					String password = JOptionPane.showInputDialog(null, "密码：",
							"解除锁定", JOptionPane.INFORMATION_MESSAGE);// 弹出输入框接受登录密码
					if (password != null
							&& TipWizardFrame.this.managerV.get(2).toString()
									.equals(password)) {
						codeTextField.setEnabled(true);// 设置“助记码”文本框为可用
						amountTextField.setEnabled(true);// 设置“数量”文本框为可用
						realWagesTextField.setEnabled(true);// 设置“实收金额”文本框为可用
						checkOutButton.setEnabled(true);// 设置“结账”按钮为可用
						addButton.setEnabled(true);// 设置“开单”按钮为可用
						subButton.setEnabled(true);// 设置“签单”按钮为可用
						delButton.setEnabled(true);// 设置“取消”按钮为可用
						returnButton.setEnabled(true);// 设置“交接班”按钮为可用
						menuLabel.setIcon(new ImageIcon(TipWizardFrame.class
								.getResource("/img/main_menu_true.png")));// 修改为可用状态时的图片
						menuLabel.addMouseListener(popupMenuListener);// 移除菜单的鼠标事件监听器
					} else {
						lockToggleButton.setSelected(true);// 设置按钮为被选中（按下状态）
					}
				}
			}
		});
		safetyButtonPanel.add(lockToggleButton);

		final JButton exitButton = new MButton();// 创建按钮对象
		exitButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_exit.png")));// 默认时显示的图片
		exitButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_exit_on.png")));// 光标移入时显示的图片
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rightTable.getRowCount() > 0) // 查看是否有正在消费的餐台
					JOptionPane.showMessageDialog(null, "还有未结账的餐台，当前不能退出系统！",
							"友情提示", JOptionPane.WARNING_MESSAGE); // 弹出提示
				else
					System.exit(0); // 退出系统
			}
		});
		safetyButtonPanel.add(exitButton);

		final JLabel bLineLabel = new JLabel();
		bLineLabel.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_vl.png")));
		functionPanel.add(bLineLabel);

		final JPanel clueOnPanel = new JPanel();
		clueOnPanel.setPreferredSize(new Dimension(170, 150));
		clueOnPanel.setLayout(new GridLayout(0, 1));
		functionPanel.add(clueOnPanel);

		final JLabel dateLabel = new JLabel();
		dateLabel.setFont(new Font("", Font.BOLD, 12));
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setText(Today.getDateOfShow());
		clueOnPanel.add(dateLabel);

		final JLabel weekLabel = new JLabel();
		weekLabel.setFont(new Font("", Font.BOLD, 12));
		weekLabel.setHorizontalAlignment(SwingConstants.CENTER);
		weekLabel.setText(Today.getDayOfWeek());
		clueOnPanel.add(weekLabel);

		timeLabel = new JLabel();// 创建用于显示时间的标签
		timeLabel.setFont(new Font("宋体", Font.BOLD, 14));// 设置标签文本为宋体、粗体、14号
		timeLabel.setForeground(new Color(255, 0, 0));// 设置标签文本为红色
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);// 设置标签文本居中显示
		clueOnPanel.add(timeLabel);
		new Time().start();// 开启线程

		operatorLabel = new JLabel();
		operatorLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(operatorLabel);
		operatorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		if (managerV == null)
			operatorLabel.setText("系统默认用户");
		else
			operatorLabel.setText("操作员：" + managerV.get(1).toString());

		initPopupMenu();// 初始化菜单
		menuLabel = new JLabel();// 创建标签
		menuLabel.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_menu_true.png")));// 显示可用状态时的图片
		popupMenuListener = new PopupMenuListener();// 创建鼠标事件监听器对象
		menuLabel.addMouseListener(popupMenuListener);// 为标签添加鼠标事件监听器
		operatePanel.add(menuLabel, BorderLayout.EAST);
		//
	}

	public void initPopupMenu() {

		popupMenu = new JPopupMenu();

		final JMenuItem passwordItem = new JMenuItem("修改密码");
		passwordItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		passwordItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePasswordDialog upDialog = new UpdatePasswordDialog();
				upDialog.setLocation(
						(screenSize.width - upDialog.getWidth()) / 2,
						(screenSize.height - upDialog.getHeight()) / 2);
				upDialog.setManagerV(managerV);
				upDialog.setVisible(true);
			}
		});
		popupMenu.add(passwordItem);

		popupMenu.addSeparator();

		final JMenu systemMenu = new JMenu("系统维护");
		systemMenu.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		popupMenu.add(systemMenu);

		final JMenuItem deskItem = new JMenuItem("台号管理");
		deskItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		deskItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeskNumDialog deskNumDialog = new DeskNumDialog(rightTable);
				deskNumDialog.setLocation((screenSize.width - deskNumDialog
						.getWidth()) / 2, (screenSize.height - deskNumDialog
						.getHeight()) / 2);
				deskNumDialog.setVisible(true);
				initNumComboBox();
			}
		});
		systemMenu.add(deskItem);

		systemMenu.addSeparator();

		final JMenuItem sortItem = new JMenuItem("菜系管理");
		sortItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		sortItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SortDialog sortDialog = new SortDialog();
				sortDialog.setLocation((screenSize.width - sortDialog
						.getWidth()) / 2, (screenSize.height - sortDialog
						.getHeight()) / 2);
				sortDialog.setVisible(true);
			}
		});
		systemMenu.add(sortItem);

		systemMenu.addSeparator();

		final JMenuItem menuItem = new JMenuItem("菜品管理");
		menuItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuDialog menuDialog = new MenuDialog();
				menuDialog.setLocation((screenSize.width - menuDialog
						.getWidth()) / 2, (screenSize.height - menuDialog
						.getHeight()) / 2);
				menuDialog.setVisible(true);
			}
		});
		systemMenu.add(menuItem);

		popupMenu.addSeparator();

		final JMenu sellMenu = new JMenu("销售统计");
		sellMenu.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		popupMenu.add(sellMenu);

		final JMenuItem dayItem = new JMenuItem("日销售统计");
		dayItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		dayItem.addActionListener(new ItemActionListener(DayDialog.class));
		sellMenu.add(dayItem);

		sellMenu.addSeparator();

		final JMenuItem monthItem = new JMenuItem("月销售统计");
		monthItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		monthItem.addActionListener(new ItemActionListener(MonthDialog.class));
		sellMenu.add(monthItem);

		sellMenu.addSeparator();

		final JMenuItem yearItem = new JMenuItem("年销售统计");
		yearItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		yearItem.addActionListener(new ItemActionListener(YearDialog.class));
		sellMenu.add(yearItem);

		popupMenu.addSeparator();

		final JMenu personnelMenu = new JMenu("人员管理");
		personnelMenu.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		popupMenu.add(personnelMenu);

		final JMenuItem recordItem = new JMenuItem("档案管理");
		recordItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		recordItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecordManageDialog recordDialog = new RecordManageDialog();
				recordDialog.setLocation((screenSize.width - recordDialog
						.getWidth()) / 2, (screenSize.height - recordDialog
						.getHeight()) / 2);
				recordDialog.setVisible(true);
			}
		});
		personnelMenu.add(recordItem);

		personnelMenu.addSeparator();

		final JMenuItem managerItem = new JMenuItem("管理员管理");
		managerItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		managerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerManageDialog managerDialog = new ManagerManageDialog();
				managerDialog.setLocation((screenSize.width - managerDialog
						.getWidth()) / 2, (screenSize.height - managerDialog
						.getHeight()) / 2);
				managerDialog.setVisible(true);
			}
		});
		personnelMenu.add(managerItem);

		popupMenu.addSeparator();

		final JMenuItem initSystemItem = new JMenuItem("初始化系统");
		initSystemItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		initSystemItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] infos = { "警告：初始化后系统数据将全部被删除！", "询问：您确认要对系统进行初始化操作？" };
				int i = JOptionPane.showConfirmDialog(TipWizardFrame.this,
						infos, "友情提示", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (i == 0) {// 初始化
					dao.initDatabase();
					JOptionPane.showMessageDialog(TipWizardFrame.this,
							"初始化完毕！", "友情提示", JOptionPane.INFORMATION_MESSAGE);
					// 重新登录
					JOptionPane.showMessageDialog(TipWizardFrame.this,
							"您需要重新登录本系统！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
					try {
						Constructor<DrinkeryManage> constructor = DrinkeryManage.class
								.getConstructor();
						constructor.newInstance();
					} catch (SecurityException ex) {
						ex.printStackTrace();
					} catch (NoSuchMethodException ex) {
						ex.printStackTrace();
					} catch (IllegalArgumentException ex) {
						ex.printStackTrace();
					} catch (InstantiationException ex) {
						ex.printStackTrace();
					} catch (IllegalAccessException ex) {
						ex.printStackTrace();
					} catch (InvocationTargetException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		popupMenu.add(initSystemItem);

		if (managerV != null) {
			if (!managerV.get(3).equals("√"))
				systemMenu.setEnabled(false);
			if (!managerV.get(4).equals("√"))
				sellMenu.setEnabled(false);
			if (!managerV.get(5).equals("√"))
				personnelMenu.setEnabled(false);
			if (!managerV.get(6).equals("√"))
				initSystemItem.setEnabled(false);
		}

	}

	private class PopupMenuListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {// 当光标进入添加该监听器的组件时将被执行
			popupMenu.show(e.getComponent(), 0, 0);// 基于触发此次事件的组件显示菜单
		}
	}

	private class ItemActionListener implements ActionListener {

		Class<?> dialogC;

		public ItemActionListener(Class dialogC) {
			this.dialogC = dialogC;
		}

		public void actionPerformed(ActionEvent e) {
			JDialog dialog = null;
			try {
				Constructor<?> constructor = dialogC.getConstructor();
				dialog = (JDialog) constructor.newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			dialog.setVisible(true);
		}

	}

	private void makeOutAnInvoice() {
		String deskNum = numComboBox.getSelectedItem().toString();// 获得台号
		String menuName = nameTextField.getText();// 获得商品名称
		// 验证
		if (deskNum.equals("请选择")) {// 验证是否已经选择台号
			JOptionPane.showMessageDialog(TipWizardFrame.this, "请选择台号！",
					"友情提示", JOptionPane.INFORMATION_MESSAGE);
			numComboBox.requestFocus();
			return;
		}
		if (menuName.length() == 0) {// 验证是否已经确定商品
			JOptionPane.showMessageDialog(TipWizardFrame.this, "请确定所点商品！",
					"友情提示", JOptionPane.INFORMATION_MESSAGE);
			codeTextField.requestFocus();
			return;
		}
		// 处理开台信息
		int rightSelectedRow = rightTable.getSelectedRow();// 获得被选中的台号
		int leftRowCount = 0;// 默认点菜数量为0
		if (rightSelectedRow == -1) {// 没有被选中的台号，即新开台
			rightSelectedRow = rightTable.getRowCount();// 被选中的台号为新开的台
			Vector deskV = new Vector();// 创建一个代表新开台的向量对象
			deskV.add(rightSelectedRow + 1);// 添加开台序号
			deskV.add(deskNum);// 添加开台号
			deskV.add(new Date().toString().substring(11, 19));// 添加开台时间
			rightTableModel.addRow(deskV);// 将开台信息添加到“开台列表”中
			rightTable.setRowSelectionInterval(rightSelectedRow);// 选中新开的台
			itemOfDeskV.add(new Vector());// 添加一个对应的签单列表
		} else { // 选中的台号已经开台，即添加菜品
			leftRowCount = leftTable.getRowCount();// 获得已点菜的数量
		}
		// 处理点菜信息
		Vector menuV = dao.sMenuByName(menuName);// 获得被点菜品
		int amount = Integer.valueOf(amountTextField.getText());// 将菜品数量转为int型
		int unitPrice = Integer.valueOf(menuV.get(5).toString()); // 将菜品单价转为int型
		int money = unitPrice * amount;// 计算菜品消费额
		Vector<Object> orderDishesV = new Vector<Object>();
		orderDishesV.add("NEW");// 添加新点菜标记
		orderDishesV.add(leftRowCount + 1);// 添加点菜序号
		orderDishesV.add(menuV.get(0));// 添加菜品编号
		orderDishesV.add(menuName);// 添加菜品名称
		orderDishesV.add(menuV.get(4));// 添加菜品单位
		orderDishesV.add(amount);// 添加菜品数量
		orderDishesV.add(unitPrice);// 添加菜品单价
		orderDishesV.add(money);// 添加菜品消费额
		leftTableModel.addRow(orderDishesV);// 将点菜信息添加到“签单列表”中
		leftTable.setRowSelectionInterval(leftRowCount);// 将新点菜设置为选中行
		itemOfDeskV.get(rightSelectedRow).add(orderDishesV);// 将新点菜信息添加到对应的签单列表
		//
		codeTextField.setText(null);
		nameTextField.setText(null);
		unitTextField.setText(null);
		priceTextField.setText(null);
		amountTextField.setText("1");
	}

	private String getNum() {
		String maxNum = dao.sOrderFormOfMaxId();
		String date = Today.getDateOfNum();
		if (maxNum == null) {
			maxNum = date + "001";
		} else {
			if (maxNum.subSequence(0, 8).equals(date)) {
				maxNum = maxNum.substring(8);
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

	private void initNumComboBox() {
		numComboBox.removeAllItems();
		numComboBox.addItem("请选择");
		Vector deskNumV = dao.sDeskNums();
		for (int i = 0; i < deskNumV.size(); i++) {
			numComboBox.addItem(deskNumV.get(i));
		}
	}

	private void a(JLabel dClueOnLabel) {
		Calendar now;
		int hour;
		int minute;
		int second;
		while (true) {
			now = Calendar.getInstance();
			hour = now.get(Calendar.HOUR_OF_DAY);
			minute = now.get(Calendar.MINUTE);
			second = now.get(Calendar.SECOND);
			dClueOnLabel.setText(hour + ":" + minute + ":" + second);
		}
	}

	class Time extends Thread {// 创建用于适时更新时间的线程类
		public void run() {// 重构父类的方法
			while (true) {
				Date date = new Date();// 创建日期对象
				timeLabel.setText(date.toString().substring(11, 19));// 获取并更新当前时间
				try {
					Thread.sleep(1000);// 令线程休眠1秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
