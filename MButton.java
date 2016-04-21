package com.mwq.mwing;

import java.awt.Insets;

import javax.swing.JButton;

public class MButton extends JButton {
	public MButton() {
		setFocusPainted(false);// 不绘制激活框
		setBorderPainted(false);// 不绘制边框
		setContentAreaFilled(false);// 不填充
		setMargin(new Insets(0, 0, 0, 0));// 设置边距
	}
}
