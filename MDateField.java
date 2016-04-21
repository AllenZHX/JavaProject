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

	private int year;// ��

	private int month;// ��

	private int day;// ��

	private int[] daysOfMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };// ÿ�µ�����

	private JTextField textField;// �����ı���

	private final boolean fillWithToday;// �Ƿ��Ե����������

	public MDateField(boolean fillWithToday) {
		super();
		setLayout(new BorderLayout());

		this.fillWithToday = fillWithToday;

		getDateOfToday();// ��õ��������
		judgeLeapYear();// �ж��Ƿ�Ϊ����

		textField = new JTextField();
		textField.setColumns(12);
		textField.setFocusable(false);// �ı����ܻ�ý���
		textField.setToolTipText("����ѡ������");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		initFillTextField();// ��ʼ��������ı���
		textField.addMouseListener(new MouseAdapter() {// �������¼�������
					@Override
					public void mouseClicked(MouseEvent e) {
						showChooseDateDialog();// ��ʾѡ�����ڶԻ���
					}
				});
		add(textField, BorderLayout.CENTER);

		final JButton button = new JButton();
		button.setText("...");
		button.setToolTipText("����ѡ������");
		button.setMargin(new Insets(0, 4, 0, 1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showChooseDateDialog();// ��ʾѡ�����ڶԻ���
			}
		});
		add(button, BorderLayout.EAST);
		//
	}

	private void getDateOfToday() {
		Calendar today = Calendar.getInstance();// ��õ��������
		year = today.get(Calendar.YEAR);// �����
		month = today.get(Calendar.MONTH) + 1;// �����
		day = today.get(Calendar.DAY_OF_MONTH);// �����
	}

	private void judgeLeapYear() {// �ж��Ƿ�Ϊ���꣬��ȷ��2�·ݵ�����
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
			textField.setText(getSelectedDate());// �Ե�ǰ������������ı���
		else
			textField.setText("<����ѡ��>");// ����ʾ�ı���������ı���
	}

	public String getDateAndInit(boolean isInit) {
		if (isInit) {
			getDateOfToday();// ��õ��������
			judgeLeapYear();// �ж��Ƿ�Ϊ����
			initFillTextField();// ��ʼ��������ı���
		}
		return getSelectedDate();
	}

	public void showChooseDateDialog() {
		Dimension preferredSize = textField.getPreferredSize();// ��������ı������ѡ��С
		Point locationOnScreen = textField.getLocationOnScreen();// ��������ı�������Ļ�е�����
		int x = (int) locationOnScreen.getX();// ����ѡ���ĺ�����ʼ����
		int y = (int) (locationOnScreen.getY() + preferredSize.getHeight());// ����ѡ����������ʼ����
		int width = 310;// ����ѡ���Ŀ��
		int height = 182;// ����ѡ���ĸ߶�
		JRootPane rootPane = textField.getRootPane();// ��������ı��������ĳ�������
		Point rootPaneLocationOnScreen = rootPane.getLocationOnScreen();// ��ó�����������Ļ�е�����
		/* �ж������ı�����·��Ƿ����㹻�Ŀռ���ʾ����ѡ������û�����޸�����ѡ����������ʼ��������ʾ�����Ϸ� */
		if (y + height > rootPaneLocationOnScreen.getY() + rootPane.getHeight()) {
			y = (int) (locationOnScreen.getY() - height);
		}
		ChooseDateDialog dialog = new ChooseDateDialog();// ��������ѡ������
		dialog.setBounds(x, y, width, height);// ��������ѡ������ʾλ��
		dialog.setVisible(true);// ��������ѡ���ɼ�
	}

	class ChooseDateDialog extends JDialog {

		private JTextField yearTextField;// ���ı���

		private JTextField monthTextField;// ���ı���

		private DefaultTableModel tableModel;// ���ģ��

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
			pyButton.setToolTipText("��һ��");
			pyButton.setMargin(new Insets(0, 10, 0, 10));
			pyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					yearTextField.setText(--year + "");// �����һ
					judgeLeapYear();// �ж��Ƿ�Ϊ����
					refreshTableModel();// ˢ�±��ģ��
				}
			});
			buttonPanel.add(pyButton);

			final JButton pmButton = new JButton();
			pmButton.setText("<");
			pmButton.setToolTipText("��һ��");
			pmButton.setMargin(new Insets(0, 12, 0, 12));
			pmButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (month == 1) {// �жϵ�ǰ�Ƿ�Ϊ1�£����������һ��Ϊ��һ���12��
						month = 12;
						yearTextField.setText(--year + "");
					} else {// �����·ݼ�1
						month--;
					}
					monthTextField.setText(month + "");
					refreshTableModel();// ˢ�±��ģ��
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
			yLabel.setText("��");
			buttonPanel.add(yLabel);

			monthTextField = new JTextField();
			monthTextField.setColumns(3);
			monthTextField.setText(month + "");
			monthTextField.setEditable(false);
			monthTextField.setHorizontalAlignment(JTextField.CENTER);
			buttonPanel.add(monthTextField);

			final JLabel mLabel = new JLabel();
			mLabel.setText("��");
			buttonPanel.add(mLabel);

			final JButton nmButton = new JButton();
			nmButton.setText(">");
			nmButton.setToolTipText("��һ��");
			nmButton.setMargin(new Insets(0, 12, 0, 12));
			nmButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (month == 12) {// �жϵ�ǰ�Ƿ�Ϊ12�£����������һ��Ϊ��һ���1��
						month = 1;
						yearTextField.setText(++year + "");
					} else {// �����·ݼ�1
						month++;
					}
					monthTextField.setText(month + "");
					refreshTableModel();// ˢ�±��ģ��
				}
			});
			buttonPanel.add(nmButton);

			final JButton nyButton = new JButton();
			nyButton.setText(">>");
			nyButton.setToolTipText("��һ��");
			nyButton.setMargin(new Insets(0, 10, 0, 10));
			nyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					yearTextField.setText(++year + "");// �����һ
					judgeLeapYear();// �ж��Ƿ�Ϊ����
					refreshTableModel();// ˢ�±��ģ��
				}
			});
			buttonPanel.add(nyButton);

			final JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);

			tableModel = new DefaultTableModel(6, 7);// ����һ��6��7�еı��ģ��
			String[] columnNames = { "��", "һ", "��", "��", "��", "��", "��" };
			tableModel.setColumnIdentifiers(columnNames);// ���ñ������
			refreshTableModel();// ˢ�±��ģ��

			final JTable table = new MTable(tableModel);
			table.setRowSelectionAllowed(false);
			table.setToolTipText("����ѡ��");
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String valueOfCell = table.getValueAt(
							table.getSelectedRow(), table.getSelectedColumn())
							.toString();
					if (!valueOfCell.startsWith("[")) {// �ж��û�ѡ����Ƿ�Ϊ��ǰ�µ�����
						day = Integer.valueOf(valueOfCell);// ����û�ѡ�������
						textField.setText(getSelectedDate());// �����û�ѡ�������
						dispose();// ��������ѡ���
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
			todayLabel.setText("���죺" + getSelectedDate() + "  ");
			todayPanel.add(todayLabel);

			final JButton todayButton = new JButton();
			todayButton.setText("...");
			todayButton.setToolTipText("��ʾ��ǰ����");
			todayButton.setMargin(new Insets(0, 4, 0, 1));
			todayButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getDateOfToday();// ��õ�ǰ����
					judgeLeapYear();// �ж��Ƿ�Ϊ����
					yearTextField.setText(year + "");
					monthTextField.setText(month + "");
					refreshTableModel();// ˢ�±��ģ��
				}
			});
			todayPanel.add(todayButton);

		}

		private void refreshTableModel() {
			DateFormat dateFormat = DateFormat.getDateInstance();
			try {
				dateFormat.parse(year + "-" + month + "-1");// ����ָ������
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar theDay = dateFormat.getCalendar();// ���ָ�����ڶ���
			int num = 0;
			int previousMonthDays = theDay.get(Calendar.DAY_OF_WEEK) - 1;// ��ʾ���µ�����
			if (previousMonthDays > 0) {// �ж��Ƿ���Ҫ��ʾ���µ�����
				int days = (month - 1 <= 1 ? 31 : daysOfMonth[month - 1]);// �������µ�����
				for (int day = days - (previousMonthDays - 1); day <= days; day++) {// �������µ�����
					tableModel.setValueAt("[ " + day + " ]", num / 7, num % 7);
					num++;
				}
			}
			for (int day = 1; day <= daysOfMonth[month]; day++) {// ���ñ��µ�����
				tableModel.setValueAt(day, num / 7, num % 7);
				num++;
			}
			int nextMonthDays = 42 - num;// ��ʾ���µ�����
			for (int day = 1; day <= nextMonthDays; day++) {// �������µ�����
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

		// ����в������ţ���������������ʾ
		@Override
		public JTableHeader getTableHeader() {
			tableHeader.setReorderingAllowed(false);// ���ñ���в�������
			// ��ñ��ͷ�ĵ�Ԫ�����
			DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader
					.getDefaultRenderer();
			// ���õ�Ԫ�����ݣ���������������ʾ
			headerRenderer
					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			return tableHeader;
		}

		// ��ֵ������ʾ
		@Override
		public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
			// ��ó����ͷ���ֵĵ�Ԫ�����
			DefaultTableCellRenderer tableRenderer = (DefaultTableCellRenderer) super
					.getDefaultRenderer(columnClass);
			// ���õ�Ԫ�����ݾ�����ʾ
			tableRenderer
					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			return tableRenderer;
		}

		// ��񲻿ɱ༭
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	}

}
