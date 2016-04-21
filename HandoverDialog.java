package com.mwq.frame.personnel;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.mwq.dao.Dao;
import com.mwq.frame.TipWizardFrame;

public class HandoverDialog extends JDialog {

	private JPasswordField carryOnPasswordField;

	private JTextField carryOnUserNameTextField;

	private JPasswordField onGuardPasswordField;

	private JTextField onGuardUserNameTextField;

	private Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			HandoverDialog dialog = new HandoverDialog(null);
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
	public HandoverDialog(final TipWizardFrame tipWizardFrame) {
		super();
		setModal(true);
		setResizable(false);
		getContentPane().setLayout(new GridBagLayout());
		setTitle("岗位交接");
		setBounds(100, 100, 220, 240);

		final JPanel onGuardPanel = new JPanel();
		onGuardPanel.setLayout(new GridBagLayout());
		onGuardPanel.setBorder(new TitledBorder(null, "在岗信息",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		getContentPane().add(onGuardPanel, gridBagConstraints);

		final JLabel onGuardUsernameLabel = new JLabel();
		onGuardUsernameLabel.setText("用户：");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.gridx = 0;
		gridBagConstraints_1.gridy = 0;
		onGuardPanel.add(onGuardUsernameLabel, gridBagConstraints_1);

		onGuardUserNameTextField = new JTextField();
		onGuardUserNameTextField.setColumns(20);
		onGuardUserNameTextField.setFocusable(false);
		onGuardUserNameTextField.setText(tipWizardFrame.managerV.get(1)
				.toString());
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridx = 1;
		gridBagConstraints_2.gridy = 0;
		onGuardPanel.add(onGuardUserNameTextField, gridBagConstraints_2);

		final JLabel onGuardPasswordLabel = new JLabel();
		onGuardPasswordLabel.setText("密码：");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 0;
		onGuardPanel.add(onGuardPasswordLabel, gridBagConstraints_3);

		onGuardPasswordField = new JPasswordField();
		onGuardPasswordField.setColumns(20);
		onGuardPasswordField.setText("      ");
		onGuardPasswordField.addFocusListener(new PasswordFieldFocusListener(
				onGuardPasswordField));
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_4.gridy = 1;
		gridBagConstraints_4.gridx = 1;
		onGuardPanel.add(onGuardPasswordField, gridBagConstraints_4);

		final JPanel carryOnPanel = new JPanel();
		carryOnPanel.setLayout(new GridBagLayout());
		carryOnPanel.setBorder(new TitledBorder(null, "接岗信息",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.gridy = 1;
		gridBagConstraints_5.gridx = 0;
		getContentPane().add(carryOnPanel, gridBagConstraints_5);

		final JLabel carryOnUsernameLabel = new JLabel();
		carryOnUsernameLabel.setText("用户：");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 0;
		carryOnPanel.add(carryOnUsernameLabel, gridBagConstraints_6);

		carryOnUserNameTextField = new JTextField();
		carryOnUserNameTextField.setColumns(20);
		carryOnUserNameTextField.setText("<输入用户名>");
		carryOnUserNameTextField.addFocusListener(new TextFieldFL());
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.anchor = GridBagConstraints.WEST;
		gridBagConstraints_7.gridx = 1;
		gridBagConstraints_7.gridy = 0;
		carryOnPanel.add(carryOnUserNameTextField, gridBagConstraints_7);

		final JLabel carryOnPasswordLabel = new JLabel();
		carryOnPasswordLabel.setText("密码：");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 0;
		carryOnPanel.add(carryOnPasswordLabel, gridBagConstraints_8);

		carryOnPasswordField = new JPasswordField();
		carryOnPasswordField.setColumns(20);
		carryOnPasswordField.setText("      ");
		carryOnPasswordField.addFocusListener(new PasswordFieldFocusListener(
				carryOnPasswordField));
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 1;
		carryOnPanel.add(carryOnPasswordField, gridBagConstraints_9);

		final JPanel buttonPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		buttonPanel.setLayout(flowLayout);
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.ipadx = 50;
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 0;
		getContentPane().add(buttonPanel, gridBagConstraints_10);

		final JButton subButton = new JButton();
		subButton.setText("确定");
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				char[] ogPasswords = onGuardPasswordField.getPassword();
				String ogPassword = turnCharsToString(ogPasswords);

				String coUsername = carryOnUserNameTextField.getText().trim();

				char[] coPasswords = carryOnPasswordField.getPassword();
				String coPassword = turnCharsToString(coPasswords);

				if (ogPassword.equals("") || coUsername.equals("")
						|| coPassword.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入岗位信息！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (ogPassword.equals(tipWizardFrame.managerV.get(2))) {
						Vector coUserV = dao.sManagerVByName(coUsername);
						if (coUserV == null) {
							clueOn();
						} else {
							if (coUserV.get(2).equals(coPassword)) {
								tipWizardFrame.managerV = coUserV;
								tipWizardFrame.operatorLabel.setText("操作员："
										+ coUserV.get(1).toString());
								tipWizardFrame.initPopupMenu();
								dispose();
							} else {
								clueOn();
							}
						}
					} else {
						clueOn();
					}
				}
			}

			private void clueOn() {
				JOptionPane.showMessageDialog(null, "岗位信息输入错误！", "友情提示",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		buttonPanel.add(subButton);

		final JButton resButton = new JButton();
		resButton.setText("清空");
		resButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onGuardPasswordField.setText("      ");
				carryOnUserNameTextField.setText("<输入用户名>");
				carryOnPasswordField.setText("      ");
			}
		});
		buttonPanel.add(resButton);
		//
	}

	private String turnCharsToString(char[] chars) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			strBuf.append(chars[i]);
		}
		return strBuf.toString().trim();
	}

	// 用户名焦点
	class TextFieldFL implements FocusListener {
		public void focusGained(FocusEvent e) {
			carryOnUserNameTextField.setText("");
		}

		public void focusLost(FocusEvent e) {
			if (carryOnUserNameTextField.getText().trim().length() == 0)
				carryOnUserNameTextField.setText("<输入用户名>");
		}
	}

	// 密码焦点
	class PasswordFieldFocusListener implements FocusListener {

		private JPasswordField passwordField;

		public PasswordFieldFocusListener(JPasswordField passwordField) {
			this.passwordField = passwordField;
		}

		public void focusGained(FocusEvent e) {
			passwordField.setText("");
		}

		public void focusLost(FocusEvent e) {
			char[] passwords = passwordField.getPassword();
			String password = turnCharsToString(passwords);
			if (password.length() == 0) {
				passwordField.setText("      ");
			}
		}

	}

}
