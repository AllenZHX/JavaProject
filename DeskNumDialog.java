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
		setTitle("̨�Ź���");
		setResizable(false);
		setBounds(100, 100, 500, 375);
		getContentPane().setLayout(new BorderLayout());

		this.openingDeskTable = rightTable;

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		operatePanel.add(numLabel);
		numLabel.setText("̨  �ţ�");

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
		seatingLabel.setText("  ��λ����");

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

		final JButton addButton = new JButton();// �������̨�Ű�ť����
		addButton.setText("���");
		addButton.addActionListener(new AddButtonAL());
		operatePanel.add(addButton);

		final JButton delButton = new JButton();// ����ɾ��̨�Ű�ť����
		delButton.setText("ɾ��");
		delButton.addActionListener(new DelButtonAL());
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "��  ��", "̨  ��", "��λ��" };
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
			String num = numTextField.getText();// ��ȡ̨��
			if (num.equals("")) {// �鿴�Ƿ�������̨��
				JOptionPane.showMessageDialog(null, "�����롰̨�š���", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				numTextField.requestFocus();// Ϊ̨���ı��������ý���
				return;
			}
			String seating = seatingTextField.getText();// ��ȡ��λ��
			if (seating.equals("")) {// �鿴�Ƿ���������λ��
				JOptionPane.showMessageDialog(null, "�����롰��λ������", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				seatingTextField.requestFocus();// Ϊ��λ���ı��������ý���
				return;
			}
			int rowCount = table.getRowCount();// ��õ�ǰӵ��̨�ŵĸ���
			Vector deskV = dao.sDeskByNum(num);// �����ݿ��в�ѯ��̨��
			if (deskV == null) {// ������
				dao.iDesk(num, seating);// �־û������ݿ�
				deskV = new Vector();// ����һ������
				deskV.add(rowCount + 1);// ������
				deskV.add(num);// ���̨��
				deskV.add(seating);// �����λ��
			} else {// �Ѵ���
				if (deskV.get(2).toString().equals("����")) {// ���ҿ���
					JOptionPane.showMessageDialog(null, "��̨���Ѿ����ڣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					return;
				} else {// �����ѳ���
					dao.uDeskByNum(num, seating);// �ָ�Ϊ����
					deskV.insertElementAt(rowCount + 1, 0);// ������
				}
			}
			tableModel.addRow(deskV);// ��̨����ӵ������
			table.setRowSelectionInterval(rowCount, rowCount);// ѡ��̨��
			//
			numTextField.setText(null);// ��̨���ı�������Ϊ��
			seatingTextField.setText(null);// ����λ���ı�������Ϊ��
			numTextField.requestFocus();// Ϊ̨���ı��������ý���
		}
	}

private class DelButtonAL implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		int selectedRow = table.getSelectedRow();// ���ѡ����
		if (selectedRow < 0) {// δѡ���κ���
			JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���Ĳ�̨��", "������ʾ",
					JOptionPane.INFORMATION_MESSAGE);// ������ʾ
			return;
		}
		String deskNum = table.getValueAt(selectedRow, 1).toString();// ���̨��
		for (int row = 0; row < openingDeskTable.getRowCount(); row++) {// �������ڱ�ʹ�õĲ�̨
			if (deskNum.equals(openingDeskTable.getValueAt(row, 1))) {// �鿴�ò�̨�Ƿ����ڱ�ʹ��
				JOptionPane.showMessageDialog(null, "�ò�̨����ʹ�ã�����ɾ����",
						"������ʾ", JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				return;
			}
		}
		String infos[] = new String[] {// ��֯ȷ����Ϣ
		"ȷ��Ҫɾ����̨��", "    ̨  �ţ�" + deskNum,
				"    ��λ����" + table.getValueAt(selectedRow, 2) };
		int i = JOptionPane.showConfirmDialog(null, infos, "������ʾ",
				JOptionPane.YES_NO_OPTION);// ����ȷ����ʾ
		if (i == 0) {// ȷ��ɾ��
			dao.dDeskByNum(deskNum);// �����ݿ���ɾ��
			tableModel.setDataVector(dao.sDesk(), columnNameV);// ˢ�±��
			int rowCount = table.getRowCount();// ���ɾ����ӵ�еĲ�̨��
			if (rowCount > 0) {// ��ӵ�в�̨
				if (selectedRow == rowCount)// ɾ����Ϊ���һ����̨
					selectedRow -= 1;// ��ѡ�еĲ�̨ǰ��һ��
				table.setRowSelectionInterval(selectedRow, selectedRow);// ���õ�ǰѡ�еĲ�̨
			}
		}
	}
}

}
