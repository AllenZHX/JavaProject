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
		setTitle("��ϵ����");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel sortNameLabel = new JLabel();
		operatePanel.add(sortNameLabel);
		sortNameLabel.setText("��ϵ���ƣ�");

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

		final JButton addButton = new JButton();// ������Ӳ�ϵ���ư�ť����
		addButton.setText("���");
		addButton.addActionListener(new AddButtonAL());
		operatePanel.add(addButton);

		final JButton delButton = new JButton();// ����ɾ����ϵ���ư�ť����
		delButton.setText("ɾ��");
		delButton.addActionListener(new DelButtonAL());
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		columnNameV.add("��    ��");
		columnNameV.add("��ϵ����");
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
			String name = sortNameTextField.getText(); // ��ò�ϵ����
			if (name.equals("")) {// �鿴�Ƿ������˲�ϵ����
				JOptionPane.showMessageDialog(null, "�����롰��ϵ���ơ���", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				sortNameTextField.requestFocus();// Ϊ��ϵ�����ı��������ý���
				return;
			}
			int rowCount = tableModel.getRowCount();// ��õ�ǰӵ�в�ϵ�ĸ���
			Vector sortV = dao.sSortByName(name); // �����ݿ��в�ѯ�ò�ϵ
			if (sortV == null) {// ������
				dao.iSort(name); // �־û������ݿ�
				sortV = new Vector(); // ����һ������
				sortV.add(rowCount + 1);// ������
				sortV.add(name);// ��Ӳ�ϵ����
			} else { // �Ѵ���
				if (sortV.get(2).toString().equals("����")) { // ������������
					JOptionPane.showMessageDialog(null, "�ò�ϵ�Ѿ����ڣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					return;
				} else { // ������ͣ��
					dao.uSortByName(name); //�ָ�Ϊ����
					sortV.set(0, rowCount + 1);// �������
				}
			}
			tableModel.addRow(sortV);// ���²�ϵ��ӵ������
			table.setRowSelectionInterval(rowCount, rowCount);// ѡ�в�ϵ
			//
			sortNameTextField.setText(null);// ��ղ�ϵ�����ı���
		}
	}

private class DelButtonAL implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		int selectedRow = table.getSelectedRow();// ���ѡ����
		String name = table.getValueAt(selectedRow, 1).toString();// ��ò�ϵ����
		Vector<Vector> menusV = dao.sMenuVBySortName(name);// �����ݿ��ѯ�ò�ϵ�����Ĳ�Ʒ
		String[] infos = null;// ��ʾ��Ϣ
		if (menusV.size() > 0) {// ��֯��ʾ��Ϣ
			infos = new String[menusV.size() + 2];
			infos[0] = "���ɾ����ϵ��" + name + "������ͬʱɾ���������������²�Ʒ��";
			for (int i = 0; i < infos.length; i++) {
				infos[i + 1] = menusV.get(i).get(1).toString();
			}
			infos[infos.length - 1] = "ȷ��Ҫɾ����ϵ��" + name + "����";
		} else {
			infos = new String[] { "ȷ��Ҫɾ����ϵ��" + name + "����" };
		}
		int i = JOptionPane.showConfirmDialog(null, infos, "������ʾ",
				JOptionPane.YES_NO_OPTION);// ����ȷ����ʾ
		if (i == 0) {// ȷ��ɾ��
			tableModel.removeRow(selectedRow);// �ӱ����ɾ���ò�ϵ
			int rowCount = table.getRowCount();// ���ɾ����ӵ�еĲ�ϵ��
			if (rowCount > 0) {// ��ӵ�в�ϵ
				if (selectedRow < rowCount) {// ɾ���Ĳ��Ǳ�����һ�еĲ�ϵ
					for (int row = selectedRow; row < rowCount; row++) {
						table.setValueAt(row + 1 + "", row, 0);// �޸�λ��ɾ����ϵ֮������
					}
					table.setRowSelectionInterval(selectedRow);// ѡ������һ��
				} else {// ɾ�����Ǳ�����һ�еĲ�ϵ
					table.setRowSelectionInterval(rowCount - 1);// ѡ�б������һ��
				}
			}
			dao.dSortByName(name);// �����ݿ���ɾ���ò�ϵ
		}
	}
}

}
