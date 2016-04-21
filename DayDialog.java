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
import javax.swing.SwingUtilities;

import com.mwq.dao.Dao;
import com.mwq.mwing.FixedColumnTablePanel;
import com.mwq.tool.Today;

public class DayDialog extends JDialog {

	private Vector<String> tableColumnV;

	private Vector<Vector<Object>> tableDataV;

	private JComboBox dayComboBox;

	private JComboBox monthComboBox;

	private JComboBox yearComboBox;

	private int daysOfMonth[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };

	private Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DayDialog dialog = new DayDialog();
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
	public DayDialog() {
		super();
		setModal(true);
		setTitle("日销售统计");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		int year = Today.getYEAR();
		int month = Today.getMONTH();
		int day = Today.getDAY();

		yearComboBox = new JComboBox();
		yearComboBox.setMaximumRowCount(10);
		String minDatetime = dao.sOrderFormOfMinDatetime();
		if (minDatetime == null) {
			yearComboBox.addItem(year);
		} else {
			int minYear = Integer.valueOf(minDatetime.substring(0, 4));
			for (int y = minYear; y <= year; y++) {
				yearComboBox.addItem(y);
			}
		}
		yearComboBox.setSelectedItem(year);
		judgeLeapYear(year);
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();// 获得选中的年度
				judgeLeapYear(year);// 判断是否为闰年，以确定2月份的天数
				int month = (Integer) monthComboBox.getSelectedItem();// 获得选中的月份
				if (month == 2) {// 如果选中的为2月
					int itemCount = dayComboBox.getItemCount();// 获得日下拉菜单当前的天数
					if (itemCount != daysOfMonth[2]) {// 如果日下拉菜单当前的天数不等于2月份的天数
						if (itemCount == 28)// 如果日下拉菜单当前的天数为28天
							dayComboBox.addItem(29);// 则添加为29天
						else
							// 否则日下拉菜单当前的天数则为29天
							dayComboBox.removeItem(29);// 则减少为28天
					}
				}
			}
		});
		panel.add(yearComboBox);

		final JLabel yearLabel = new JLabel();
		yearLabel.setText("年");
		panel.add(yearLabel);

		monthComboBox = new JComboBox();
		monthComboBox.setMaximumRowCount(12);
		for (int m = 1; m < 13; m++) {
			monthComboBox.addItem(m);
		}
		monthComboBox.setSelectedItem(month);
		monthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int month = (Integer) monthComboBox.getSelectedItem();// 获得选中的月份
				int itemCount = dayComboBox.getItemCount();// 获得日下拉菜单当前的天数
				while (itemCount != daysOfMonth[month]) {// 如果日下拉菜单当前的天数不等于选中月份的天数
					if (itemCount > daysOfMonth[month]) {// 如果大于选中月份的天数
						dayComboBox.removeItem(itemCount);// 则移除最后一个选择项
						itemCount--;// 并将日下拉菜单当前的天数减1
					} else {// 否则小于选中月份的天数
						itemCount++;// 将日下拉菜单当前的天数加1
						dayComboBox.addItem(itemCount);// 并添加为选择项
					}
				}
			}
		});
		panel.add(monthComboBox);

		final JLabel monthLabel = new JLabel();
		monthLabel.setText("月");
		panel.add(monthLabel);

		dayComboBox = new JComboBox();
		dayComboBox.setMaximumRowCount(10);
		int days = daysOfMonth[month];
		for (int d = 1; d <= days; d++) {
			dayComboBox.addItem(d);
		}
		dayComboBox.setSelectedItem(day);
		panel.add(dayComboBox);

		final JLabel dayLabel = new JLabel();
		dayLabel.setText("日    ");
		panel.add(dayLabel);

		final JButton submitButton = new JButton();
		submitButton.setText("确定");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();
				int month = (Integer) monthComboBox.getSelectedItem();
				int day = (Integer) dayComboBox.getSelectedItem();
				int columnCount = tableColumnV.size();
				Vector orderFormsV = dao.sOrderFormOfDay(year + "-" + month
						+ "-" + day);// 查询指定日期的所有消费单
				tableDataV.removeAllElements();// 清空表格数据
				for (int row = 0; row < orderFormsV.size(); row++) {// 添加消费单信息

					Vector<Object> rowV = new Vector<Object>();// 表格行对象

					Vector orderFormV = (Vector) orderFormsV.get(row);// 消费单对象
					String orderFormNum = orderFormV.get(1).toString();// 获得消费单编号
					rowV.add(orderFormNum);// 编号
					rowV.add(orderFormV.get(2));// 台号
					rowV.add(orderFormV.get(3).toString().substring(11, 19));// 开台时间
					rowV.add(orderFormV.get(4));// 消费金额
					for (int column = 4; column < columnCount; column++) {
						rowV.add("——");// 商品消费数量，默认未消费
					}
					Vector orderItemsV = dao
							.sOrderItemAndMenuByOrderFormNum(orderFormNum);// 查询指定消费单的所有消费项目
					for (int i = 0; i < orderItemsV.size(); i++) {// 遍历消费项目
						Vector orderItemV = (Vector) orderItemsV.get(i);// 消费项目对象
						String menuName = orderItemV.get(3).toString();// 获得消费项目名称
						for (int column = 4; column < columnCount; column++) {// 遍历表格列
							if (tableColumnV.get(column).equals(menuName)) {// 查看表格列名和消费项目名是否相同
								int amount = (Integer) orderItemV.get(4);// 获得该项目的消费数量
								Object cellValue = rowV.get(column);// 获得表格单元格的值
								if (cellValue.equals("——"))// 没有消费过
									rowV.set(column, amount);
								else
									// 已经消费过
									rowV.set(column, (Integer) cellValue
											+ amount);
								break;
							}
						}
					}
					tableDataV.add(rowV);// 添加到表格数据中
				}
				Vector<Object> totalRowV = new Vector<Object>();// 统计行对象
				totalRowV.add("总计");// 编号列
				totalRowV.add("——");// 台号列
				totalRowV.add("——");// 开台时间列
				int rowCount = tableDataV.size();// 消费单数量
				for (int column = 3; column < columnCount; column++) {// 遍历列
					int total = 0;// 默认消费数量为0
					for (int row = 0; row < rowCount; row++) {// 遍历行
						Object cellValue = tableDataV.get(row).get(column);// 获得表格单元格的值
						if (!cellValue.equals("——"))// 查看是否消费过
							total += (Integer) cellValue;// 累加消费数量
					}
					totalRowV.add(total);// 添加到表格行
				}
				tableDataV.add(totalRowV);// 添加到表格数据中
				Container contentPane = getContentPane();// 获得容器
				contentPane.remove(1);// 移除原表格
				contentPane.add(new FixedColumnTablePanel(tableColumnV,
						tableDataV, 4), BorderLayout.CENTER);// 添加新表格
				SwingUtilities.updateComponentTreeUI(contentPane);// 刷新窗体
			}
		});
		panel.add(submitButton);

		tableColumnV = new Vector<String>();
		tableColumnV.add("编号");
		tableColumnV.add("台号");
		tableColumnV.add("开台时间");
		tableColumnV.add("消费金额");
		Vector<Vector<Object>> menuV = dao.sMenu();
		for (int i = 0; i < menuV.size(); i++) {
			tableColumnV.add(menuV.get(i).get(3).toString());
		}

		tableDataV = new Vector<Vector<Object>>();

		getContentPane().add(
				new FixedColumnTablePanel(tableColumnV, tableDataV, 4),
				BorderLayout.CENTER);
		//
	}

	private void judgeLeapYear(int year) {
		if (year % 100 == 0) {
			if (year % 400 == 0)
				daysOfMonth[2] = 29;
			else
				daysOfMonth[2] = 28;
		} else {
			if (year % 4 == 0)
				daysOfMonth[2] = 29;
			else
				daysOfMonth[2] = 28;
		}
	}

}
