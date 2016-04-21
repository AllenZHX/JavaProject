package com.mwq;

import java.awt.Dimension;
import java.awt.SplashScreen;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.mwq.dao.Dao;
import com.mwq.frame.LandFrame;

public class DrinkeryManage {

	public DrinkeryManage() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Dimension screenSize = toolkit.getScreenSize();
				LandFrame landFrame = new LandFrame();
				Dimension frameSize = landFrame.getSize();
				if (frameSize.width > screenSize.width) {
					frameSize.width = screenSize.width;
				}
				if (frameSize.height > screenSize.height) {
					frameSize.height = screenSize.height;
				}
				landFrame.setLocation((screenSize.width - frameSize.width) / 2,
						(screenSize.height - frameSize.height) / 2);
				landFrame.setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		SplashScreen splashScreen = SplashScreen.getSplashScreen();
		if (splashScreen != null) {
			Dao.getInstance();
			new DrinkeryManage();
		} else {
			JOptionPane.showMessageDialog(null,
					"程序缺少必要的资源文件，导致闪屏界面失败，程序将终止执行。", "资源不全",
					JOptionPane.WARNING_MESSAGE);
		}
	}

}
