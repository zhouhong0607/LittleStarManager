package com;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Login extends JFrame
{
	private JLabel userLabel;
	private JLabel passLabel;
	private JButton exit;
	private JButton login;

	private static TbUserlist user;

	public static void main(String[] args)
	{
	
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Thread.sleep(2000);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				new Login();

			}
		});
	}

	public Login()
	{
		setTitle("登录小星星幼儿园信息管理系统");
		final JPanel panel = new LoginPanel();
		panel.setLayout(null);
		getContentPane().add(panel);
		setBounds(300, 200, panel.getWidth(), panel.getHeight());
		userLabel = new JLabel();
		userLabel.setText("用户名：");
		userLabel.setBounds(100, 135, 200, 18);
		panel.add(userLabel);
		final JTextField userName = new JTextField();
		userName.setBounds(150, 135, 200, 18);
		panel.add(userName);
		passLabel = new JLabel();
		passLabel.setText("密  码：");
		passLabel.setBounds(100, 165, 200, 18);
		panel.add(passLabel);
		final JPasswordField userPassword = new JPasswordField();
		userPassword.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(final KeyEvent e)
			{
				if (e.getKeyCode() == 10)
					login.doClick();
			}
		});
		userPassword.setBounds(150, 165, 200, 18);
		panel.add(userPassword);
		login = new JButton();
		login.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				if (Dao.conn == null)
				{
					JOptionPane.showMessageDialog(null, "数据库连接异常", "警告提示窗",
							JOptionPane.WARNING_MESSAGE);
				} else
				{
					user = Dao.getUser(userName.getText(),
							userPassword.getText());
					if (user.getUsername() == null || user.getName() == null)
					{
						userName.setText(null);
						userPassword.setText(null);
						JOptionPane.showMessageDialog(null, "登录信息有误", "警告提示窗",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					setVisible(false);
					new ManagerFrame().setVisible(true);
				}
			}
		});
		login.setText("登录");
		login.setBounds(180, 195, 60, 18);
		panel.add(login);
		exit = new JButton();
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				System.exit(0);
			}
		});
		exit.setText("退出");
		exit.setBounds(260, 195, 60, 18);
		panel.add(exit);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	public static TbUserlist getUser()
	{
		return user;
	}

	public static void setUser(TbUserlist user)
	{
		Login.user = user;
	}

}