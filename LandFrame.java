package com.mwq.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mwq.DrinkeryManage;
import com.mwq.dao.Dao;
import com.mwq.frame.personnel.ManagerManageDialog;
import com.mwq.frame.personnel.RecordManageDialog;
import com.mwq.mwing.MButton;

public class LandFrame extends JFrame {

	private JPasswordField passwordField;

	private JTextField usernameTextField;

	private Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			LandFrame frame = new LandFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public LandFrame() {
		super();
		setTitle("���տƼ�");
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 432, 289);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource("/img/land.png")));
		getContentPane().add(label, BorderLayout.NORTH);

		final JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridBagLayout());
		getContentPane().add(panel, BorderLayout.CENTER);

		final JLabel usernameLabel = new JLabel();
		usernameLabel.setText("�û�����");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		panel.add(usernameLabel, gridBagConstraints);

		usernameTextField = new JTextField();
		usernameTextField.setText("<�����û���>");
		usernameTextField.setColumns(24);
		usernameTextField.addFocusListener(new TextFieldFL());
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 1;
		panel.add(usernameTextField, gridBagConstraints_1);

		final JLabel passwordLabel = new JLabel();
		passwordLabel.setText("��  �룺");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_2.gridy = 1;
		gridBagConstraints_2.gridx = 0;
		panel.add(passwordLabel, gridBagConstraints_2);

		passwordField = new JPasswordField();
		passwordField.setText("      ");
		passwordField.setColumns(24);
		passwordField.addFocusListener(new PasswordFieldFL());
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 1;
		panel.add(passwordField, gridBagConstraints_3);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.anchor = GridBagConstraints.EAST;
		gridBagConstraints_4.gridwidth = 2;
		gridBagConstraints_4.gridy = 2;
		gridBagConstraints_4.gridx = 0;
		panel.add(buttonPanel, gridBagConstraints_4);

		final JButton submitButton = new MButton();
		submitButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/land_sub.png")));
		submitButton.addActionListener(new SubmitButtonAL());
		buttonPanel.add(submitButton);

		final JButton resetButton = new MButton();
		resetButton.setIcon(new ImageIcon(getClass().getResource(
				"/img/land_res.png")));
		resetButton.addActionListener(new ResetButtonAL());
		buttonPanel.add(resetButton);
		//
	}

	// ת���ַ�����Ϊ�ַ���
	private String turnCharsToString(char[] chars) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			strBuf.append(chars[i]);
		}
		return strBuf.toString().trim();
	}

	// �û�������
	class TextFieldFL implements FocusListener {
		public void focusGained(FocusEvent e) {
			usernameTextField.setText("");
		}

		public void focusLost(FocusEvent e) {
			if (usernameTextField.getText().trim().length() == 0)
				usernameTextField.setText("<�����û���>");
		}
	}

	// ���뽹��
	class PasswordFieldFL implements FocusListener {
		public void focusGained(FocusEvent e) {
			passwordField.setText("");
		}

		public void focusLost(FocusEvent e) {
			char[] passwords = passwordField.getPassword();
			String password = turnCharsToString(passwords);
			if (password.length() == 0)
				passwordField.setText("      ");
		}
	}

	// ��¼����
	class SubmitButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String username = usernameTextField.getText();
			char[] passwords = passwordField.getPassword();
			String password = turnCharsToString(passwords);
			if (username.equals("<�����û���>") || password.equals("      ")) {
				JOptionPane.showMessageDialog(LandFrame.this, "�������¼��Ϣ��",
						"������ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else {
				Vector managerV = dao.sManagerVByName(username);
				if (managerV == null) {
					if (dao.sManager().size() > 0) {
						JOptionPane.showMessageDialog(LandFrame.this,
								"��¼��Ϣ����������������룡", "������ʾ",
								JOptionPane.WARNING_MESSAGE);
					} else {
						if (username.equals("mr") && password.equals("mrsoft")) {
							land(null);
						} else {
							String[] infos = { "Ĭ���û�Ϊ��mr", "��¼����Ϊ��mrsoft",
									"�Ѿ��Զ����ã���ֱ�ӵ�������¼����ť��" };
							JOptionPane.showMessageDialog(LandFrame.this,
									infos, "������ʾ",
									JOptionPane.INFORMATION_MESSAGE);
							usernameTextField.setText("mr");
							passwordField.setText("mrsoft");
						}
					}
				} else {
					if (managerV.get(2).toString().equals(password)) {
						land(managerV);
					} else {
						JOptionPane.showMessageDialog(LandFrame.this,
								"��¼��Ϣ����������������룡", "������ʾ",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}

		private void land(Vector managerV) {// ��¼�ɹ�
			TipWizardFrame tipWizardFrame = new TipWizardFrame(managerV);// �������������
			tipWizardFrame.setVisible(true);// ����������ɼ�
			dispose();// ���ٵ�¼����
			if (managerV == null) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Dimension screenSize = toolkit.getScreenSize();
				// ��������
				JOptionPane.showMessageDialog(tipWizardFrame,
						"�����̽���һ����������Ա������", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				RecordManageDialog recordManageDialog = new RecordManageDialog();
				recordManageDialog
						.setLocation((screenSize.width - recordManageDialog
								.getWidth()) / 2,
								(screenSize.height - recordManageDialog
										.getHeight()) / 2);
				recordManageDialog.setVisible(true);
				// ����Ϊ����Ա
				JOptionPane.showMessageDialog(tipWizardFrame, "�����̽�������Ϊ��������Ա��",
						"������ʾ", JOptionPane.INFORMATION_MESSAGE);
				ManagerManageDialog managerManageDialog = new ManagerManageDialog();
				Class mmdC = ManagerManageDialog.class;
				try {
					Field[] declaredFields = mmdC.getDeclaredFields();
					for (int i = 0; i < declaredFields.length; i++) {
						Field field = declaredFields[i];
						if (field.getType().equals(JCheckBox.class)) {
							field.setAccessible(true);
							JCheckBox checkBox = (JCheckBox) field
									.get(managerManageDialog);
							checkBox.setSelected(true);
							checkBox.setEnabled(false);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				managerManageDialog
						.setLocation((screenSize.width - managerManageDialog
								.getWidth()) / 2,
								(screenSize.height - managerManageDialog
										.getHeight()) / 2);
				managerManageDialog.setVisible(true);
				// ���µ�¼
				JOptionPane.showMessageDialog(tipWizardFrame,
						"�����øոս����Ĺ���Ա��¼��ϵͳ��", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				tipWizardFrame.dispose();
				try {
					Constructor<DrinkeryManage> constructor = DrinkeryManage.class
							.getConstructor();
					constructor.newInstance();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ��ն���
	class ResetButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			usernameTextField.setText("<�����û���>");
			passwordField.setText("      ");
		}
	}

}
