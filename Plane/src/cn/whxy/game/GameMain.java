package cn.whxy.game;

import javax.swing.JFrame;

public class GameMain extends JFrame {
	//�������������
	GameBG bg=null;
	
	public GameMain() {
		//�������������
		bg=new GameBG();	
		
		this.add(bg);	
		//�����㴰����Ӽ��̼���
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
