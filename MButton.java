package com.mwq.mwing;

import java.awt.Insets;

import javax.swing.JButton;

public class MButton extends JButton {
	public MButton() {
		setFocusPainted(false);// �����Ƽ����
		setBorderPainted(false);// �����Ʊ߿�
		setContentAreaFilled(false);// �����
		setMargin(new Insets(0, 0, 0, 0));// ���ñ߾�
	}
}
