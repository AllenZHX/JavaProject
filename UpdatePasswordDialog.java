package com.mwq.frame.personnel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.mwq.dao.Dao;

public class UpdatePasswordDialog extends JDialog {

	private JPasswordField oldPasswordField;

	private JPasswordField repeatPasswordField;

	private JPasswordField newPasswordField;

	private final Dao dao = Dao.getInstance();

	private Vector managerV;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			UpdatePasswordDialog dialog = new UpdatePasswordDialog();
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
	public UpdatePasswordDialog() {
		super();
		getContentPane().setLayout(new GridBagLayout());
		setModal(true);
		setTitle("修改密码");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 300) / 2, (screenSize.height - 200) / 2,
				300, 200);

		final JLabel oldPasswordLabel = new JLabel();
		oldPasswordLabel.setText("原 密 码：");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.gridx = 0;
		gridBagConstraints_7.gridy = 0;
		getContentPane().add(oldPasswordLabel, gridBagConstraints_7);

		oldPasswordField = new JPasswordField();
		oldPasswordField.setText("      ");
		oldPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				oldPasswordField.setText("");
			}

			public void focusLost(FocusEvent e) {
				char[] passwords = oldPasswordField.getPassword();
				String password = turnCharsToString(passwords);
				if (password.length() == 0) {
					oldPasswordField.setText("      ");
				}
			}
		});
		oldPasswordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ')
					e.consume();
			}
		});
		oldPasswordField.setColumns(25);
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.gridwidth = 3;
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 1;
		getContentPane().add(oldPasswordField, gridBagConstraints_8);

		final JLabel newPasswordLabel = new JLabel();
		newPasswordLabel.setText("新 密 码：");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		getContentPane().add(newPasswordLabel, gridBagConstraints);

		newPasswordField = new JPasswordField();
		newPasswordField.setText("      ");
		newPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				newPasswordField.setText("");
			}

			public void focusLost(FocusEvent e) {
				char[] passwords = newPasswordField.getPassword();
				String password = turnCharsToString(passwords);
				if (password.length() == 0) {
					newPasswordField.setText("      ");
				}
			}
		});
		newPasswordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char[] passwords = newPasswordField.getPassword();
				if (passwords.length < 20) {
					if (e.getKeyChar() == ' ')
						e.consume();
				} else {
					e.consume();
				}
			}
		});
		newPasswordField.setColumns(25);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_1.gridwidth = 3;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 1;
		getContentPane().add(newPasswordField, gridBagConstraints_1);

		final JLabel repeatPasswordLabel = new JLabel();
		repeatPasswordLabel.setText("重新输入：");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_2.gridy = 2;
		gridBagConstraints_2.gridx = 0;
		getContentPane().add(repeatPasswordLabel, gridBagConstraints_2);

		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setColumns(25);
		repeatPasswordField.setText("      ");
		repeatPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				repeatPasswordField.setText("");
			}

			public void focusLost(FocusEvent e) {
				char[] passwords = repeatPasswordField.getPassword();
				String password = turnCharsToString(passwords);
				if (password.length() == 0) {
					repeatPasswordField.setText("      ");
				}
			}
		});
		repeatPasswordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char[] passwords = repeatPasswordField.getPassword();
				if (passwords.length < 20) {
					if (e.getKeyChar() == ' ')
						e.consume();
				} else {
					e.consume();
				}
			}
		});
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridwidth = 3;
		gridBagConstraints_3.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_3.gridy = 2;
		gridBagConstraints_3.gridx = 1;
		getContentPane().add(repeatPasswordField, gridBagConstraints_3);

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(30, 20));
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridy = 3;
		gridBagConstraints_6.gridx = 1;
		getContentPane().add(label, gridBagConstraints_6);

		final JButton submitButton = new JButton();
		submitButton.setText("确定");
		submitButton.addActionListener(new SubmitButtonAL());
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_4.gridy = 3;
		gridBagConstraints_4.gridx = 2;
		getContentPane().add(submitButton, gridBagConstraints_4);
		//
	}

	private String turnCharsToString(char[] chars) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			strBuf.append(chars[i]);
		}
		return strBuf.toString().trim();
	}

	private class SubmitButtonAL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			char[] oldPasswords = oldPasswordField.getPassword();
			String oldPassword = turnCharsToString(oldPasswords);
			char[] newPasswords = newPasswordField.getPassword();
			String newPassword = turnCharsToString(newPasswords);
			char[] repeatPasswords = repeatPasswordField.getPassword();
			String repeatPassword = turnCharsToString(repeatPasswords);
			if (oldPassword.length() == 0 || newPassword.length() == 0
					|| repeatPassword.length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入密码！", "友情提示",
						JOptionPane.INFORMATION_MESSAGE);
				oldPasswordField.setText("      ");
				newPasswordField.setText("      ");
				repeatPasswordField.setText("      ");
				return;
			}
			if (oldPassword.equals(managerV.get(2))) {
				if (newPassword.equals(repeatPassword)) {
					String num = managerV.get(0).toString();
					managerV.set(2, newPassword);
					dao.uPasswordByRecordNum(num, newPassword);
					JOptionPane.showMessageDialog(null, "密码修改成功！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					clueOn("您两次输入的新密码不一致，请确认后重新输入！");
				}
			} else {
				clueOn("您输入的原密码错误，请确认后重新输入！");
			}
		}

		private void clueOn(String info) {
			JOptionPane.showMessageDialog(null, info, "友情提示",
					JOptionPane.INFORMATION_MESSAGE);
			oldPasswordField.setText("      ");
			newPasswordField.setText("      ");
			repeatPasswordField.setText("      ");
			return;
		}
	}

	public void setManagerV(Vector managerV) {
		this.managerV = managerV;
	}
	
}
