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
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);// ��ִ���κβ���
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {// ���ڹرմ���֮ǰ��ִ��
				if (rightTable.getRowCount() > 0)// �鿴�Ƿ����������ѵĲ�̨
					JOptionPane.showMessageDialog(null, "����δ���˵Ĳ�̨����ǰ�����˳�ϵͳ��",
							"������ʾ", JOptionPane.WARNING_MESSAGE);// ������ʾ
				else
					System.exit(0);// �˳�ϵͳ
			}
		});
		setTitle("���տƼ�");
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
		leftTabbedPane.addTab("ǩ���б�", null, leftScrollPane, null);

		itemOfDeskV = new Vector<Vector<Vector<Object>>>();

		leftTableColumnV = new Vector<String>();
		String leftTableColumns[] = { "  ", "��    ��", "��Ʒ���", "��Ʒ����", "��    λ",
				"��    ��", "��    ��", "��    ��" };
		for (int i = 0; i < leftTableColumns.length; i++) {
			leftTableColumnV.add(leftTableColumns[i]);
		}

		leftTableDataV = new Vector<Vector<Object>>();

		leftTableModel = new DefaultTableModel(leftTableDataV, leftTableColumnV);
		leftTableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) { // ����ӻ��Ƴ������ʱ��ִ��
				int rowCount = leftTable.getRowCount();// ��á�ǩ���б��е�����
				int expenditure = 0;// Ĭ������ 0 Ԫ
				for (int row = 0; row < rowCount; row++) {// ͨ��ѭ��ͳ�����ѽ��
					expenditure += Integer.valueOf(leftTable.getValueAt(row, 7)
							.toString());// �ۼ����ѽ��
				}
				expenditureTextField.setText(expenditure + "");// ���¡����ѽ��ı���
			}
		});

		leftTable = new MTable(leftTableModel);
		leftScrollPane.setViewportView(leftTable);

		final JTabbedPane rightTabbedPane = new JTabbedPane();
		splitPane.setRightComponent(rightTabbedPane);

		final JScrollPane rightScrollPane = new JScrollPane();
		rightTabbedPane.addTab("��̨�б�", null, rightScrollPane, null);

		rightTableColumnV = new Vector<String>();
		rightTableColumnV.add("��    ��");
		rightTableColumnV.add("̨    ��");
		rightTableColumnV.add("��̨ʱ��");

		rightTableDataV = new Vector<Vector<Object>>();

		rightTableModel = new DefaultTableModel(rightTableDataV,
				rightTableColumnV);

		rightTable = new MTable(rightTableModel);
		rightTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRow = rightTable.getSelectedRow();// ��á���̨�б��е�ѡ����
				leftTableDataV.removeAllElements(); // ��ա�ǩ���б��е�������Ŀ
				leftTableDataV.addAll(itemOfDeskV.get(selectedRow));// ��ǩ���б������ѡ�в�̨��������Ŀ
				leftTableModel.setDataVector(leftTableDataV, leftTableColumnV);// ˢ�¡�ǩ���б����ģ�͵�����
				leftTable.setRowSelectionInterval(0);// ѡ�С�ǩ���б��еĵ�һ��
				numComboBox.setSelectedItem(rightTable.getValueAt(selectedRow,
						1));// ͬ��ѡ�С�̨�š�ѡ����еĸ�̨��
			}
		});
		rightScrollPane.setViewportView(rightTable);

		final JPanel operatePanel = new JPanel();
		operatePanel.setPreferredSize(new Dimension(0, 200));
		operatePanel.setLayout(new BorderLayout());
		getContentPane().add(operatePanel, BorderLayout.SOUTH);

		final JPanel orderDishesPanel = new JPanel();
		operatePanel.add(orderDishesPanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel(); // ������̨�š���ǩ
		numLabel.setText("̨�ţ�"); // ���ñ�ǩ�ı�
		orderDishesPanel.add(numLabel);

		numComboBox = new JComboBox(); // ����̨��ѡ���
		numComboBox.setNextFocusableComponent(codeTextField); // ������һ����Ҫ��ý�������
		initNumComboBox(); // ��ʼ��̨��ѡ���
		numComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = rightTable.getRowCount(); // ����ѿ�̨��
				if (rowCount == 0) // û�п�̨
					return; // ��ִ���κβ���
				Object selectedDeskNum = numComboBox.getSelectedItem(); // ��á�̨�š�ѡ����е�ѡ����
				int needSelectedRow = -1; // Ĭ��ѡ�е�̨��δ��̨
				for (int row = 0; row < rowCount; row++) { // �鿴ѡ�е�̨���Ƿ��Ѿ���̨
					if (selectedDeskNum.equals(rightTable.getValueAt(row, 1))) { // �Ѿ���̨
						needSelectedRow = row; // �������ڡ���̨�б� �е�������
						break; // ����ѭ��
					}
				}
				if (needSelectedRow == -1) { // ѡ�е�̨����δ��̨������Ҫ��̨
					rightTable.clearSelection(); // ȡ��ѡ�񡰿�̨�б��е�ѡ����
					leftTableDataV.removeAllElements(); // ��ա�ǩ���б��е�������
					leftTableModel.setDataVector(leftTableDataV,
							leftTableColumnV); // ˢ�¡�ǩ���б����ģ�͵�����
				} else { // ѡ�е�̨���Ѿ���̨������Ҫ���������Ŀ
					rightTable.setRowSelectionInterval(needSelectedRow);// �ڡ���̨�б���ѡ�и�̨��
					leftTableDataV.removeAllElements(); // ��ա�ǩ���б��е�������Ŀ
					leftTableDataV.addAll(itemOfDeskV.get(needSelectedRow)); // ��ǩ���б������ѡ�в�̨��������Ŀ
					leftTableModel.setDataVector(leftTableDataV,
							leftTableColumnV); // ˢ�¡�ǩ���б����ģ�͵�����
					leftTable.setRowSelectionInterval(0); // ѡ�С�ǩ���б��еĵ�һ��
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
		codeALabel.setText("    ��Ʒ��");
		codePanel.add(codeALabel);

		final JRadioButton numRadioButton = new JRadioButton();
		numRadioButton.setFocusable(false);
		buttonGroup.add(numRadioButton);
		numRadioButton.setText("���");
		codePanel.add(numRadioButton);

		final JLabel codeBLabel = new JLabel();
		codeBLabel.setText("/");
		codePanel.add(codeBLabel);

		final JRadioButton codeRadioButton = new JRadioButton();
		codeRadioButton.setFocusable(false);
		buttonGroup.add(codeRadioButton);
		codeRadioButton.setSelected(true);
		codeRadioButton.setText("������");
		codePanel.add(codeRadioButton);

		final JLabel codeCLabel = new JLabel();
		codeCLabel.setText("����");
		codePanel.add(codeCLabel);

		codeTextField = new JTextField();
		codeTextField.setColumns(16);
		codeTextField.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {// ͨ�����̼�����ʵ�ֿ����ı������������
				if ("abcdefghijklmnopqrstuvwxyz0123456789".indexOf(e
						.getKeyChar()) < 0)// ֻ����������ĸ������
					e.consume();// ���ٴ˴μ����¼�
			}

			public void keyReleased(KeyEvent e) {// ͨ�����̼�����ʵ�����ܻ�ȡ��Ʒ
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {// ���»س���
					makeOutAnInvoice();// ����
				} else {
					String input = codeTextField.getText();// �����������
					Vector menuV = null;// ���������Ĳ�Ʒ
					if (input.length() > 0) {// ȷ������������
						if (codeRadioButton.isSelected()) {// ���������ѯ
							Vector menusV = dao.sMenuByCode(input);// ��ѯ���������Ĳ�Ʒ
							if (menusV.size() > 0) {// ���ڷ��������Ĳ�Ʒ
								menuV = (Vector) menusV.get(0);// ��õ�һ�����������Ĳ�Ʒ
								menuV.remove(0);// �Ƴ���Ʒ�����
							}
						} else {// ����Ų�ѯ
							if (input.length() == 9)// ȷ�ϱ���Ƿ�Ϊ9λ
								menuV = dao.sMenuByNum(input);// ��ѯ���������Ĳ�Ʒ
						}
					}
					if (menuV == null) {// �����ڷ��������Ĳ�Ʒ
						nameTextField.setText(null);// ���á���Ʒ���ơ��ı���Ϊ��
						unitTextField.setText(null);// ���á���λ���ı���Ϊ��
						priceTextField.setText(null);// ���á����ۡ��ı���Ϊ��
					} else {// ���ڷ��������Ĳ�Ʒ
						nameTextField.setText(menuV.get(2).toString());// ���á���Ʒ���ơ��ı���Ϊ���������Ĳ�Ʒ����
						unitTextField.setText(menuV.get(4).toString());// ���á���λ���ı���Ϊ���������Ĳ�Ʒ��λ
						priceTextField.setText(menuV.get(5).toString());// ���á����ۡ��ı���Ϊ���������Ĳ�Ʒ����
					}
				}
			}
		});
		orderDishesPanel.add(codeTextField);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("    ��Ʒ���ƣ�");
		orderDishesPanel.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setColumns(24);
		nameTextField.setEditable(false);
		nameTextField.setFocusable(false);// ���á���Ʒ���ơ��ı��򲻿��Ի�ý���
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		orderDishesPanel.add(nameTextField);

		final JLabel unitLabel = new JLabel();
		unitLabel.setText("    ��λ��");
		orderDishesPanel.add(unitLabel);

		unitTextField = new JTextField();
		unitTextField.setColumns(6);
		unitTextField.setEditable(false);
		unitTextField.setFocusable(false); // ���á���λ���ı��򲻿��Ի�ý���
		unitTextField.setHorizontalAlignment(SwingConstants.CENTER);
		orderDishesPanel.add(unitTextField);

		final JLabel priceLabel = new JLabel();
		priceLabel.setText("    ���ۣ�");
		orderDishesPanel.add(priceLabel);

		priceTextField = new JTextField();
		priceTextField.setColumns(6);
		priceTextField.setEditable(false);
		priceTextField.setFocusable(false); // ���á����ۡ��ı��򲻿��Ի�ý���
		priceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		orderDishesPanel.add(priceTextField);

		final JLabel amountLabel = new JLabel();
		amountLabel.setText("Ԫ    ������");
		orderDishesPanel.add(amountLabel);

		amountTextField = new JTextField();// �������������ı���
		amountTextField.setColumns(6);
		amountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		amountTextField.setNextFocusableComponent(codeTextField);
		amountTextField.setText("1");// Ĭ������Ϊ1
		amountTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {// ���ı����ý���ʱִ��
				amountTextField.setText(null);// ���á��������ı���Ϊ��
			}

			public void focusLost(FocusEvent e) {// ���ı���ʧȥ����ʱִ��
				String amount = amountTextField.getText();// ������������
				if (amount.length() == 0)// δ��������
					amountTextField.setText("1"); // �ָ�ΪĬ������1
			}
		});
		amountTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int length = amountTextField.getText().length();// ��ȡ��ǰ������λ��
				if (length < 2) {// λ��С����λ
					String num = (length == 0 ? "123456789" : "0123456789"); // ������������ַ�������ַ���
					if (num.indexOf(e.getKeyChar()) < 0)// �鿴�����ַ��Ƿ����������������ַ���
						e.consume(); // ���������������������ַ��������ٴ˴ΰ����¼�
				} else {
					e.consume(); // �����С��������������λ�������ٴ˴ΰ����¼�
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					makeOutAnInvoice();// ����
					codeTextField.requestFocus();// Ϊ����������������ı������󽹵�
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
		expenditureLabel.setText("���ѽ�");
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
		expenditureUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 2;
		checkOutPanel.add(expenditureUnitLabel, gridBagConstraints_14);

		final JLabel realWagesLabel = new JLabel();
		realWagesLabel.setFont(new Font("", Font.BOLD, 16));
		realWagesLabel.setText("ʵ�ս�");
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
				int length = realWagesTextField.getText().length();// ��ȡ��ǰ������λ��
				if (length < 8) {// λ��С����λ
					String num = (length == 4 ? "123456789" : "0123456789"); // ������������ַ�������ַ���
					if (num.indexOf(e.getKeyChar()) < 0)// �鿴�����ַ��Ƿ����������������ַ���
						e.consume(); // ���������������������ַ��������ٴ˴ΰ����¼�
				} else {
					e.consume(); // �����С��������������λ�������ٴ˴ΰ����¼�
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
		realWagesUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_15.gridy = 1;
		gridBagConstraints_15.gridx = 2;
		checkOutPanel.add(realWagesUnitLabel, gridBagConstraints_15);

		final JButton checkOutButton = new JButton();
		checkOutButton.setText("�� ��");
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();
				if (selectedRow < 0) {// δѡ���κβ�̨
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ���˵Ĳ�̨��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					return;
				}
				int rowCount = leftTable.getRowCount();// ��ý��˲�̨�ĵ������
				if (leftTable.getValueAt(rowCount - 1, 0).equals("NEW")) {// ����δǩ����Ʒ
					JOptionPane.showMessageDialog(null, "����ȷ��δǩ����Ʒ�Ĵ���ʽ��",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					return;
				}
				int expenditure = Integer.valueOf(expenditureTextField
						.getText());// ������ѽ��
				int realWages = Integer.valueOf(realWagesTextField.getText());// ���ʵ�ս��
				if (realWages < expenditure) {// �鿴ʵ�ս���Ƿ�С�����ѽ��
					if (realWages == 0)// δ����ʵ�ս��
						JOptionPane.showMessageDialog(null, "������ʵ�ս�", "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					else
						// ʵ�ս��С�����ѽ��
						JOptionPane.showMessageDialog(null, "ʵ�ս���С�����ѽ�",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					realWagesTextField.requestFocus();// Ϊ��ʵ�ս��ı��������ý���
					return;
				}
				changeTextField.setText((realWages - expenditure) + "");// ���㲢���á������
				String[] orderFormData = {
						getNum(),
						rightTable.getValueAt(selectedRow, 1).toString(),
						Today.getDate() + " "
								+ rightTable.getValueAt(selectedRow, 2),
						expenditureTextField.getText(),
						TipWizardFrame.this.managerV.get(0).toString() };// ��֯���ѵ���Ϣ
				dao.iOrderForm(orderFormData);// �־û������ݿ�
				String[] orderItemData = new String[4];// �����洢������Ŀ��Ϣ
				orderItemData[0] = dao.sOrderFormOfMaxId();// ������ѵ����
				for (int row = 0; row < rowCount; row++) {// ͨ��ѭ���־û�������Ŀ��Ϣ
					orderItemData[1] = leftTable.getValueAt(row, 2).toString();// ��ò�Ʒ���
					orderItemData[2] = leftTable.getValueAt(row, 5).toString();// ��ò�Ʒ����
					orderItemData[3] = leftTable.getValueAt(row, 7).toString();// ��ò�Ʒ���ѽ��
					dao.iOrderItem(orderItemData);// �־û������ݿ�
				}
				JOptionPane.showMessageDialog(null, rightTable.getValueAt(
						selectedRow, 1)
						+ " ������ɣ�", "������ʾ", JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				//
				rightTableModel.removeRow(selectedRow);// �ӡ���̨�б����Ƴ����˲�̨
				leftTableDataV.removeAllElements();// ��ա�ǩ���б�
				leftTableModel.setDataVector(leftTableDataV, leftTableColumnV);// ˢ�¡�ǩ���б�
				realWagesTextField.setText("0");// ��ա�ʵ�ս��ı���
				changeTextField.setText("0");// ��ա�������ı���
				itemOfDeskV.remove(selectedRow);// �����ݼ����Ƴ����˲�̨
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
		changeLabel.setText("�����");
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
		changeUnitLabel.setText(" Ԫ");
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

		final JButton subButton = new MButton();// ������ť
		subButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_sub.png")));// ��ťĬ��ʱ��ʾ��ͼƬ
		subButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_sub_on.png")));// �������ʱ��ʾ��ͼƬ
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();// ��á���̨�б��е�ѡ����
				if (selectedRow < 0) {// δѡ���κ���
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫǩ����̨�ţ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					int lastRowIndex = leftTable.getRowCount() - 1;// ������һ��������Ŀ������
					Object lastRowSign = leftTable.getValueAt(lastRowIndex, 0);// ������һ��������Ŀ�ı��
					if (lastRowSign.equals("NEW")) {// Ϊδǩ����Ŀ
						Object firstRowSign = leftTable.getValueAt(0, 0);// ��õ�һ��������Ŀ�ı��
						if (firstRowSign.equals("NEW")) {// Ϊδǩ����Ŀ����Ϊ�¿�̨ǩ��
							for (int row = lastRowIndex; row >= 0; row--) {
								leftTable.setValueAt("", row, 0);// ǩ��
							}
						} else {// Ϊ��ǩ����Ŀ����Ϊ����Ӳ�Ʒǩ��
							for (int row = lastRowIndex; row >= 0; row--) {
								if (leftTable.getValueAt(row, 0).equals("NEW"))// Ϊδǩ����Ŀ
									leftTable.setValueAt("", row, 0);// ǩ��
								else
									// Ϊ��ǩ����Ŀ����Ϊ����Ӳ�Ʒǩ�����
									break;
							}
						}
					}
				}
			}
		});
		orderDishesButtonPanel.add(subButton);

		final JButton delButton = new MButton();// ������ť
		delButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_del.png")));// ��ťĬ��ʱ��ʾ��ͼƬ
		delButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_del_on.png")));// �������ʱ��ʾ��ͼƬ
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int leftSelectedRow = leftTable.getSelectedRow();// ��á�ǩ���б��е�ѡ���У���Ҫȡ����������Ŀ
				if (leftSelectedRow < 0) {// δѡ���κ���
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫȡ������Ʒ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					return;
				}
				if (leftTable.getValueAt(leftSelectedRow, 0).equals("")) {// ��ǩ����Ʒ������ȡ��
					JOptionPane.showMessageDialog(null, "�ܱ�Ǹ������Ʒ�Ѿ�����ȡ����",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					return;
				}
				int rightSelectedRow = rightTable.getSelectedRow();// ��á���̨�б��е�ѡ���У���Ҫȡ����Ʒ�Ĳ�̨
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫȡ����"
						+ rightTable.getValueAt(rightSelectedRow, 1) + "���е���Ʒ��"
						+ leftTable.getValueAt(leftSelectedRow, 3) + "����",
						"������ʾ", JOptionPane.YES_NO_OPTION);// ������ʾ��Ϣȷ���Ƿ�ȡ��
				if (i == 0) {// ȷ��ȡ��
					Vector<Vector<Object>> itemV = itemOfDeskV
							.get(rightSelectedRow);// ���������Ŀ�б�
					itemV.remove(leftSelectedRow);// ȡ��ָ��������Ŀ
					int rowCount = itemV.size();// ���ȡ�����������Ŀ����
					if (rowCount == 0) {// û���κ�������Ŀ
						leftTableModel.setDataVector(null, leftTableColumnV);// ��ա�ǩ���б�
						rightTableModel.removeRow(rightSelectedRow);// ȡ����̨
						itemOfDeskV.remove(rightSelectedRow);// �Ƴ�������Ŀ�б�
					} else {
						if (leftSelectedRow == rowCount) {// ȡ����ĿΪ���һ��
							leftSelectedRow -= 1;// ����ǰһ����Ŀ��ѡ��
						} else {// ȡ����Ŀ�������һ��
							for (int row = leftSelectedRow; row < rowCount; row++) {// �޸���Ŀ���
								itemV.get(row).set(1, row + 1);
							}
						}
						leftTableModel.setDataVector(itemV, leftTableColumnV);// ˢ�±������ģ��
						leftTable.setRowSelectionInterval(leftSelectedRow);// ���ú�һ����Ŀ��ѡ��
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

		final JToggleButton lockToggleButton = new JToggleButton();// ������ť����
		lockToggleButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_lock_false.png")));// Ĭ��ʱ��ʾ��ͼƬ
		lockToggleButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_lock_on.png")));// �������ʱ��ʾ��ͼƬ
		lockToggleButton.setSelectedIcon(new ImageIcon(getClass().getResource(
				"/img/main_lock_true.png")));// ѡ��ʱ��ʾ��ͼƬ
		lockToggleButton.setRolloverSelectedIcon(new ImageIcon(getClass()
				.getResource("/img/main_lock_on.png")));// �������ʱ��ʾ��ͼƬ
		lockToggleButton.setFocusPainted(false);// �����Ƽ����
		lockToggleButton.setBorderPainted(false);// �����Ʊ߿�
		lockToggleButton.setContentAreaFilled(false);// �����
		lockToggleButton.setMargin(new Insets(0, 0, 0, 0));// ���ñ߾�
		lockToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lockToggleButton.isSelected()) {// ��ť�ѱ�ѡ�У�����״̬��
					codeTextField.setEnabled(false);// ���á������롱�ı���Ϊ������
					amountTextField.setEnabled(false);// ���á��������ı���Ϊ������
					realWagesTextField.setEnabled(false);// ���á�ʵ�ս��ı���Ϊ������
					checkOutButton.setEnabled(false);// ���á����ˡ���ťΪ������
					addButton.setEnabled(false);// ���á���������ťΪ������
					subButton.setEnabled(false);// ���á�ǩ������ťΪ������
					delButton.setEnabled(false);// ���á�ȡ������ťΪ������
					returnButton.setEnabled(false);// ���á����Ӱࡱ��ťΪ������
					menuLabel.setIcon(new ImageIcon(TipWizardFrame.class
							.getResource("/img/main_menu_false.png")));// �޸�Ϊ������״̬ʱ��ͼƬ
					menuLabel.removeMouseListener(popupMenuListener);// �Ƴ��˵�������¼�������
				} else {// ��ťδ��ѡ�У�����״̬��
					String password = JOptionPane.showInputDialog(null, "���룺",
							"�������", JOptionPane.INFORMATION_MESSAGE);// �����������ܵ�¼����
					if (password != null
							&& TipWizardFrame.this.managerV.get(2).toString()
									.equals(password)) {
						codeTextField.setEnabled(true);// ���á������롱�ı���Ϊ����
						amountTextField.setEnabled(true);// ���á��������ı���Ϊ����
						realWagesTextField.setEnabled(true);// ���á�ʵ�ս��ı���Ϊ����
						checkOutButton.setEnabled(true);// ���á����ˡ���ťΪ����
						addButton.setEnabled(true);// ���á���������ťΪ����
						subButton.setEnabled(true);// ���á�ǩ������ťΪ����
						delButton.setEnabled(true);// ���á�ȡ������ťΪ����
						returnButton.setEnabled(true);// ���á����Ӱࡱ��ťΪ����
						menuLabel.setIcon(new ImageIcon(TipWizardFrame.class
								.getResource("/img/main_menu_true.png")));// �޸�Ϊ����״̬ʱ��ͼƬ
						menuLabel.addMouseListener(popupMenuListener);// �Ƴ��˵�������¼�������
					} else {
						lockToggleButton.setSelected(true);// ���ð�ťΪ��ѡ�У�����״̬��
					}
				}
			}
		});
		safetyButtonPanel.add(lockToggleButton);

		final JButton exitButton = new MButton();// ������ť����
		exitButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_exit.png")));// Ĭ��ʱ��ʾ��ͼƬ
		exitButton.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/img/main_exit_on.png")));// �������ʱ��ʾ��ͼƬ
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rightTable.getRowCount() > 0) // �鿴�Ƿ����������ѵĲ�̨
					JOptionPane.showMessageDialog(null, "����δ���˵Ĳ�̨����ǰ�����˳�ϵͳ��",
							"������ʾ", JOptionPane.WARNING_MESSAGE); // ������ʾ
				else
					System.exit(0); // �˳�ϵͳ
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

		timeLabel = new JLabel();// ����������ʾʱ��ı�ǩ
		timeLabel.setFont(new Font("����", Font.BOLD, 14));// ���ñ�ǩ�ı�Ϊ���塢���塢14��
		timeLabel.setForeground(new Color(255, 0, 0));// ���ñ�ǩ�ı�Ϊ��ɫ
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);// ���ñ�ǩ�ı�������ʾ
		clueOnPanel.add(timeLabel);
		new Time().start();// �����߳�

		operatorLabel = new JLabel();
		operatorLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(operatorLabel);
		operatorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		if (managerV == null)
			operatorLabel.setText("ϵͳĬ���û�");
		else
			operatorLabel.setText("����Ա��" + managerV.get(1).toString());

		initPopupMenu();// ��ʼ���˵�
		menuLabel = new JLabel();// ������ǩ
		menuLabel.setIcon(new ImageIcon(getClass().getResource(
				"/img/main_menu_true.png")));// ��ʾ����״̬ʱ��ͼƬ
		popupMenuListener = new PopupMenuListener();// ��������¼�����������
		menuLabel.addMouseListener(popupMenuListener);// Ϊ��ǩ�������¼�������
		operatePanel.add(menuLabel, BorderLayout.EAST);
		//
	}

	public void initPopupMenu() {

		popupMenu = new JPopupMenu();

		final JMenuItem passwordItem = new JMenuItem("�޸�����");
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

		final JMenu systemMenu = new JMenu("ϵͳά��");
		systemMenu.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		popupMenu.add(systemMenu);

		final JMenuItem deskItem = new JMenuItem("̨�Ź���");
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

		final JMenuItem sortItem = new JMenuItem("��ϵ����");
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

		final JMenuItem menuItem = new JMenuItem("��Ʒ����");
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

		final JMenu sellMenu = new JMenu("����ͳ��");
		sellMenu.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		popupMenu.add(sellMenu);

		final JMenuItem dayItem = new JMenuItem("������ͳ��");
		dayItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		dayItem.addActionListener(new ItemActionListener(DayDialog.class));
		sellMenu.add(dayItem);

		sellMenu.addSeparator();

		final JMenuItem monthItem = new JMenuItem("������ͳ��");
		monthItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		monthItem.addActionListener(new ItemActionListener(MonthDialog.class));
		sellMenu.add(monthItem);

		sellMenu.addSeparator();

		final JMenuItem yearItem = new JMenuItem("������ͳ��");
		yearItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		yearItem.addActionListener(new ItemActionListener(YearDialog.class));
		sellMenu.add(yearItem);

		popupMenu.addSeparator();

		final JMenu personnelMenu = new JMenu("��Ա����");
		personnelMenu.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		popupMenu.add(personnelMenu);

		final JMenuItem recordItem = new JMenuItem("��������");
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

		final JMenuItem managerItem = new JMenuItem("����Ա����");
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

		final JMenuItem initSystemItem = new JMenuItem("��ʼ��ϵͳ");
		initSystemItem.setIcon(new ImageIcon(TipWizardFrame.class
				.getResource("/img/tab.png")));
		initSystemItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] infos = { "���棺��ʼ����ϵͳ���ݽ�ȫ����ɾ����", "ѯ�ʣ���ȷ��Ҫ��ϵͳ���г�ʼ��������" };
				int i = JOptionPane.showConfirmDialog(TipWizardFrame.this,
						infos, "������ʾ", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (i == 0) {// ��ʼ��
					dao.initDatabase();
					JOptionPane.showMessageDialog(TipWizardFrame.this,
							"��ʼ����ϣ�", "������ʾ", JOptionPane.INFORMATION_MESSAGE);
					// ���µ�¼
					JOptionPane.showMessageDialog(TipWizardFrame.this,
							"����Ҫ���µ�¼��ϵͳ��", "������ʾ",
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
			if (!managerV.get(3).equals("��"))
				systemMenu.setEnabled(false);
			if (!managerV.get(4).equals("��"))
				sellMenu.setEnabled(false);
			if (!managerV.get(5).equals("��"))
				personnelMenu.setEnabled(false);
			if (!managerV.get(6).equals("��"))
				initSystemItem.setEnabled(false);
		}

	}

	private class PopupMenuListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {// ����������Ӹü����������ʱ����ִ��
			popupMenu.show(e.getComponent(), 0, 0);// ���ڴ����˴��¼��������ʾ�˵�
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
		String deskNum = numComboBox.getSelectedItem().toString();// ���̨��
		String menuName = nameTextField.getText();// �����Ʒ����
		// ��֤
		if (deskNum.equals("��ѡ��")) {// ��֤�Ƿ��Ѿ�ѡ��̨��
			JOptionPane.showMessageDialog(TipWizardFrame.this, "��ѡ��̨�ţ�",
					"������ʾ", JOptionPane.INFORMATION_MESSAGE);
			numComboBox.requestFocus();
			return;
		}
		if (menuName.length() == 0) {// ��֤�Ƿ��Ѿ�ȷ����Ʒ
			JOptionPane.showMessageDialog(TipWizardFrame.this, "��ȷ��������Ʒ��",
					"������ʾ", JOptionPane.INFORMATION_MESSAGE);
			codeTextField.requestFocus();
			return;
		}
		// ����̨��Ϣ
		int rightSelectedRow = rightTable.getSelectedRow();// ��ñ�ѡ�е�̨��
		int leftRowCount = 0;// Ĭ�ϵ������Ϊ0
		if (rightSelectedRow == -1) {// û�б�ѡ�е�̨�ţ����¿�̨
			rightSelectedRow = rightTable.getRowCount();// ��ѡ�е�̨��Ϊ�¿���̨
			Vector deskV = new Vector();// ����һ�������¿�̨����������
			deskV.add(rightSelectedRow + 1);// ��ӿ�̨���
			deskV.add(deskNum);// ��ӿ�̨��
			deskV.add(new Date().toString().substring(11, 19));// ��ӿ�̨ʱ��
			rightTableModel.addRow(deskV);// ����̨��Ϣ��ӵ�����̨�б���
			rightTable.setRowSelectionInterval(rightSelectedRow);// ѡ���¿���̨
			itemOfDeskV.add(new Vector());// ���һ����Ӧ��ǩ���б�
		} else { // ѡ�е�̨���Ѿ���̨������Ӳ�Ʒ
			leftRowCount = leftTable.getRowCount();// ����ѵ�˵�����
		}
		// ��������Ϣ
		Vector menuV = dao.sMenuByName(menuName);// ��ñ����Ʒ
		int amount = Integer.valueOf(amountTextField.getText());// ����Ʒ����תΪint��
		int unitPrice = Integer.valueOf(menuV.get(5).toString()); // ����Ʒ����תΪint��
		int money = unitPrice * amount;// �����Ʒ���Ѷ�
		Vector<Object> orderDishesV = new Vector<Object>();
		orderDishesV.add("NEW");// ����µ�˱��
		orderDishesV.add(leftRowCount + 1);// ��ӵ�����
		orderDishesV.add(menuV.get(0));// ��Ӳ�Ʒ���
		orderDishesV.add(menuName);// ��Ӳ�Ʒ����
		orderDishesV.add(menuV.get(4));// ��Ӳ�Ʒ��λ
		orderDishesV.add(amount);// ��Ӳ�Ʒ����
		orderDishesV.add(unitPrice);// ��Ӳ�Ʒ����
		orderDishesV.add(money);// ��Ӳ�Ʒ���Ѷ�
		leftTableModel.addRow(orderDishesV);// �������Ϣ��ӵ���ǩ���б���
		leftTable.setRowSelectionInterval(leftRowCount);// ���µ������Ϊѡ����
		itemOfDeskV.get(rightSelectedRow).add(orderDishesV);// ���µ����Ϣ��ӵ���Ӧ��ǩ���б�
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
		numComboBox.addItem("��ѡ��");
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

	class Time extends Thread {// ����������ʱ����ʱ����߳���
		public void run() {// �ع�����ķ���
			while (true) {
				Date date = new Date();// �������ڶ���
				timeLabel.setText(date.toString().substring(11, 19));// ��ȡ�����µ�ǰʱ��
				try {
					Thread.sleep(1000);// ���߳�����1��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
