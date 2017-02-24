package com.echo.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import com.echo.spider.EchoSpider;
import com.echo.util.StringUtil;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import org.apache.log4j.Logger;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.awt.Toolkit;

public class DownEchoFrm extends JFrame
{
	public static Logger loger=Logger.getLogger(DownEchoFrm.class);
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField musicId_Txt;
	private JButton down_Btn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{   
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF(); //加载BeautyEye 
			        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated; ////设置本属性将改变窗口边框样式定义
 					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF(); //加载BeautyEye 
 				    UIManager.put("RootPane.setupButtonVisible", false);//隐藏设置按钮
					DownEchoFrm frame = new DownEchoFrm();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DownEchoFrm()
	{
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(DownEchoFrm.class.getResource("/images/music2.png")));
		setResizable(false);
		setTitle("echo\u4E0B\u8F7D");
		setForeground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 125);
		contentPane = new JPanel();
		contentPane.setForeground(UIManager.getColor("TextPane.inactiveBackground"));
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("\u6B4C\u66F2id");
		lblNewLabel.setIcon(new ImageIcon(DownEchoFrm.class.getResource("/images/music2.png")));
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		
		musicId_Txt = new JTextField();
		musicId_Txt.setColumns(10);
		
		down_Btn = new JButton("\u4E0B\u8F7D");
		down_Btn.setHorizontalAlignment(SwingConstants.LEFT);
		down_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //下载
				downEchoActionPerformed(e); 
			}

		});
		down_Btn.setIcon(new ImageIcon(DownEchoFrm.class.getResource("/images/download_red.png")));
		down_Btn.setFont(new Font("微软雅黑", Font.PLAIN, 15));
//		down_Btn.setBorder(BorderFactory.createRaisedBevelBorder());
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(30)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(musicId_Txt, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addGap(63)
					.addComponent(down_Btn)
					.addContainerGap(51, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(down_Btn, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(musicId_Txt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		this.setLocationRelativeTo(null);
//		this.setUndecorated(true);
		
	}
	
	/**
	 * 下载
	 * @param e
	 */
	public void downEchoActionPerformed(ActionEvent e){
		
		this.down_Btn.setEnabled(false);
		
		new Thread(new Runnable() { 

			@Override
			public void run() {
				
				String musicId=musicId_Txt.getText().trim();
				boolean flag=true;
				if(StringUtil.isNotEmpty(musicId)){
					flag=EchoSpider.getPageByUrl(musicId);
				}else{
					JOptionPane.showMessageDialog(null, "歌曲Id不能为空！");
					down_Btn.setEnabled(true);
					return;
				}
				down_Btn.setEnabled(true);
				if(flag==true){
					loger.debug("musicId="+musicId+"的歌曲下载成功！");
					JOptionPane.showMessageDialog(null, "下载成功！");
					return;
				}else{
					loger.debug("musicId="+musicId+"的歌曲下载异常！");
					JOptionPane.showMessageDialog(null, "下载异常！");
					return;
				}
				
			}
		}).start();
		
	}
}