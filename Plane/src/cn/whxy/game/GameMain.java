package cn.whxy.game;

import javax.swing.JFrame;

public class GameMain extends JFrame {
	//声明画布类对象
	GameBG bg=null;
	
	public GameMain() {
		//创建画布类对象
		bg=new GameBG();	
		
		this.add(bg);	
		//给顶层窗口添加键盘监听
		this.addKeyListener(bg.mkl);
		this.setTitle("PlaneGame");
		this.setSize(1280,960);
		this.setLocation(400,100);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new GameMain();
	}
}
