package com.mwq.mwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MDateField extends JPanel {

	private int year;// 年

	private int month;// 月

	private int day;// 日

	private int[] daysOfMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };// 每月的天数

	private JTextField textField;// 日期文本框

	private final boolean fillWithToday;// 是否以当天日期填充

	public MDateField(boolean fillWithToday) {
		super();
		setLayout(new BorderLayout());

		this.fillWithToday = fillWithToday;

		getDateOfToday();// 获得当天的日期
		judgeLeapYear();// 判断是否为闰年

		textField = new JTextField();
		textField.setColumns(12);
		textField.setFocusable(false);// 文本框不能获得焦点
		textField.setToolTipText("单击选择日期");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		initFillTextField();// 初始填充日期文本框
		textField.addMouseListener(new MouseAdapter() {// 添加鼠标事件监听器
					@Override
					public void mouseClicked(MouseEvent e) {
						showChooseDateDialog();// 显示选择日期对话框
					}
				});
		add(textField, BorderLayout.CENTER);

		final JButton button = new JButton();
		button.setText("...");
		button.setToolTipText("单击选择日期");
		button.setMargin(new Insets(0, 4, 0, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showChooseDateDialog();// 显示选择日期对话框
			}
		});
		add(button, BorderLayout.EAST);
		//
	}

	private void getDateOfToday() {
		Calendar today = Calendar.getInstance();// 获得当天的日期
		year = today.get(Calendar.YEAR);// 获得年
		month = today.get(Calendar.MONTH) + 1;// 获得月
		day = today.get(Calendar.DAY_OF_MONTH);// 获得日
	}

	private void judgeLeapYear() {// 判断是否为闰年，以确定2月份的天数
		if (year % 4 == 0) {
			if (year % 100 != 0 || year % 400 == 0)
				daysOfMonth[2] = 29;
			else
				daysOfMonth[2] = 28;
		} else {
			daysOfMonth[2] = 28;
		}
	}

	private String getSelectedDate() {
		return year + "-" + month + "-" + day;
	}

	private void initFillTextField() {
		if (fillWithToday)
			textField.setText(getSelectedDate());// 以当前日期填充日期文本框
		else
			textField.setText("<单击选择>");// 以提示文本填充日期文本框
	}

	public String getDateAndInit(boolean isInit) {
		if (isInit) {
			getDateOfToday();// 获得当天的日期
			judgeLeapYear();// 判断是否为闰年
			initFillTextField();// 初始填充日期文本框
		}
		return getSelectedDate();
	}

	public void showChooseDateDialog() {
		Dimension preferredSize = textField.getPreferredSize();// 获得日期文本框的首选大小
		Point locationOnScreen = textField.getLocationOnScreen();// 获得日期文本框在屏幕中的坐标
		int x = (int) locationOnScreen.getX();// 日期选择框的横轴起始坐标
		int y = (int) (locationOnScreen.getY() + preferredSize.getHeight());// 日期选择框的纵轴起始坐标
		int width = 310;// 日期选择框的宽度
		int height = 182;// 日期选择框的高度
		JRootPane rootPane = textField.getRootPane();// 获得日期文本框所属的超级容器
		Point rootPaneLocationOnScreen = rootPane.getLocationOnScreen();// 获得超级容器在屏幕中的坐标
		/* 判断日期文本框的下方是否有足够的空间显示日期选择框，如果没有则修改日期选择框的纵轴起始坐标以显示到其上方 */
		if (y + height > rootPaneLocationOnScreen.getY() + rootPane.getHeight()) {
			y = (int) (locationOnScreen.getY() - height);
		}
		ChooseDateDialog dialog = new ChooseDateDialog();// 创建日期选择框对象
		dialog.setBounds(x, y, width, height);// 设置日期选择框的显示位置
		dialog.setVisible(true);// 设置日期选择框可见
	}

	class ChooseDateDialog extends JDialog {

		private JTextField yearTextField;// 年文本框

		private JTextField monthTextField;// 月文本框

		private DefaultTableModel tableModel;// 表格模型

		public ChooseDateDialog() {
			super();
			setModal(true);
			setUndecorated(true);
			setBounds(100, 100, 310, 153);

			final JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBorder(new LineBorder(Color.black, 1, false));
			getContentPane().add(panel, BorderLayout.CENTER);

			final JPanel buttonPanel = new JPanel();
			panel.add(buttonPanel, BorderLayout.NORTH);

			final JButton pyButton = new JButton();
			pyButton.setText("<<");
			pyButton.setToolTipText("上一年");
			pyButton.setMargin(new Insets(0, 10, 0, 10));
			pyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					yearTextField.setText(--year + "");// 将年减一
					judgeLeapYear();// 判断是否为闰年
					refreshTableModel();// 刷新表格模型
				}
			});
			buttonPanel.add(pyButton);

			final JButton pmButton = new JButton();
			pmButton.setText("<");
			pmButton.setToolTipText("上一月");
			pmButton.setMargin(new Insets(0, 12, 0, 12));
			pmButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (month == 1) {// 判断当前是否为1月，如果是则上一月为上一年的12月
						month = 12;
						yearTextField.setText(--year + "");
					} else {// 否则将月份减1
						month--;
					}
					monthTextField.setText(month + "");
					refreshTableModel();// 刷新表格模型
				}
			});
			buttonPanel.add(pmButton);

			yearTextField = new JTextField();
			yearTextField.setColumns(6);
			yearTextField.setText(year + "");
			yearTextField.setEditable(false);
			yearTextField.setHorizontalAlignment(JTextField.CENTER);
			buttonPanel.add(yearTextField);

			final JLabel yLabel = new JLabel();
			yLabel.setText("年");
			buttonPanel.add(yLabel);

			monthTextField = new JTextField();
			monthTextField.setColumns(3);
			monthTextField.setText(month + "");
			monthTextField.setEditable(false);
			monthTextField.setHorizontalAlignment(JTextField.CENTER);
			buttonPanel.add(monthTextField);

			final JLabel mLabel = new JLabel();
			mLabel.setText("月");
			buttonPanel.add(mLabel);

			final JButton nmButton = new JButton();
			nmButton.setText(">");
			nmButton.setToolTipText("下一月");
			nmButton.setMargin(new Insets(0, 12, 0, 12));
			nmButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (month == 12) {// 判断当前是否为12月，如果是则下一月为下一年的1月
						month = 1;
						yearTextField.setText(++year + "");
					} else {// 否则将月份加1
						month++;
					}
					monthTextField.setText(month + "");
					refreshTableModel();// 刷新表格模型
				}
			});
			buttonPanel.add(nmButton);

			final JButton nyButton = new JButton();
			nyButton.setText(">>");
			nyButton.setToolTipText("下一年");
			nyButton.setMargin(new Insets(0, 10, 0, 10));
			nyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					yearTextField.setText(++year + "");// 将年加一
					judgeLeapYear();// 判断是否为闰年
					refreshTableModel();// 刷新表格模型
				}
			});
			buttonPanel.add(nyButton);

			final JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);

			tableModel = new DefaultTableModel(6, 7);// 创建一个6行7列的表格模型
			String[] columnNames = { "日", "一", "二", "三", "四", "五", "六" };
			tableModel.setColumnIdentifiers(columnNames);// 设置表格列名
			refreshTableModel();// 刷新表格模型

			final JTable table = new MTable(tableModel);
			table.setRowSelectionAllowed(false);
			table.setToolTipText("单击选择");
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String valueOfCell = table.getValueAt(
							table.getSelectedRow(), table.getSelectedColumn())
							.toString();
					if (!valueOfCell.startsWith("[")) {// 判断用户选择的是否为当前月的日期
						day = Integer.valueOf(valueOfCell);// 获得用户选择的日期
						textField.setText(getSelectedDate());// 设置用户选择的日期
						dispose();// 销毁日期选择框
					}
				}
			});
			scrollPane.setViewportView(table);

			final JLabel lLabel = new JLabel();
			lLabel.setText(" ");
			panel.add(lLabel, BorderLayout.WEST);

			final JLabel rLabel = new JLabel();
			rLabel.setText(" ");
			panel.add(rLabel, BorderLayout.EAST);

			final JPanel todayPanel = new JPanel();
			panel.add(todayPanel, BorderLayout.SOUTH);

			final JLabel todayLabel = new JLabel();
			todayLabel.setText("今天：" + getSelectedDate() + "  ");
			todayPanel.add(todayLabel);

			final JButton todayButton = new JButton();
			todayButton.setText("...");
			todayButton.setToolTipText("显示当前日期");
			todayButton.setMargin(new Insets(0, 4, 0, 1));
			todayButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getDateOfToday();// 获得当前日期
					judgeLeapYear();// 判断是否为闰年
					yearTextField.setText(year + "");
					monthTextField.setText(month + "");
					refreshTableModel();// 刷新表格模型
				}
			});
			todayPanel.add(todayButton);

		}

		private void refreshTableModel() {
			DateFormat dateFormat = DateFormat.getDateInstance();
			try {
				dateFormat.parse(year + "-" + month + "-1");// 解析指定日期
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar theDay = dateFormat.getCalendar();// 获得指定日期对象
			int num = 0;
			int previousMonthDays = theDay.get(Calendar.DAY_OF_WEEK) - 1;// 显示上月的天数
			if (previousMonthDays > 0) {// 判断是否需要显示上月的日期
				int days = (month - 1 <= 1 ? 31 : daysOfMonth[month - 1]);// 计算上月的天数
				for (int day = days - (previousMonthDays - 1); day <= days; day++) {// 设置上月的日期
					tableModel.setValueAt("[ " + day + " ]", num / 7, num % 7);
					num++;
				}
			}
			for (int day = 1; day <= daysOfMonth[month]; day++) {// 设置本月的日期
				tableModel.setValueAt(day, num / 7, num % 7);
				num++;
			}
			int nextMonthDays = 42 - num;// 显示下月的天数
			for (int day = 1; day <= nextMonthDays; day++) {// 设置下月的日期
				tableModel.setValueAt("[ " + day + " ]", num / 7, num % 7);
				num++;
			}
		}

	}

	class MTable extends JTable {

		public MTable() {
			super();
		}

		public MTable(DefaultTableModel tableModel) {
			super(tableModel);
		}

		// 表格列不可重排，并且列名居中显示
		@Override
		public JTableHeader getTableHeader() {
			tableHeader.setReorderingAllowed(false);// 设置表格列不可重排
			// 获得表格头的单元格对象
			DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader
					.getDefaultRenderer();
			// 设置单元格内容（即列名）居中显示
			headerRenderer
					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			return tableHeader;
		}

		// 列值居中显示
		@Override
		public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
			// 获得除表格头部分的单元格对象
			DefaultTableCellRenderer tableRenderer = (DefaultTableCellRenderer) super
					.getDefaultRenderer(columnClass);
			// 设置单元格内容居中显示
			tableRenderer
					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			return tableRenderer;
		}

		// 表格不可编辑
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	}

}
