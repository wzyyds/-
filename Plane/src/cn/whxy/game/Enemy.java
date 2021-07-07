package cn.whxy.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy implements Runnable{
	
	int x=new Random().nextInt(1000);
	int y=new Random().nextInt(200)-200;
	EnemyShell Eshell=null;
	ArrayList<EnemyShell> EshellList=null;
	BufferedImage enemyPic=null;
	boolean isFire=true; 
	boolean isLife=true;
	int i=0;
	FireSpeed fs=null;
	public Enemy() {
		try {
			
			enemyPic=ImageIO.read(new FileInputStream(".\\pic\\enemyPlane.png"));
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		EshellList=new ArrayList<EnemyShell>();
		//启动线程
		new Thread(this).start();
		fs=new FireSpeed();
		//启动用于控制开火频率的线程
		new Thread(fs).start();
	}
	public void draw(Graphics g) {
		g.drawImage(enemyPic, x, y,100,100, null);
		
	}
	public void move() { 
			
		y+=1;		
		if(y>1000) {
			isLife=false;
		}
	}
	public void fire() {
		//创建炮弹对象
		Eshell=new EnemyShell(this);	
		Eshell.D=true;
		//将炮弹对象存入一个集合
		EshellList.add(Eshell);
		
		Eshell=new EnemyShell(this);	
		Eshell.LD=true;
		//将炮弹对象存入一个集合
		EshellList.add(Eshell);
		Eshell.x-=20;
		Eshell=new EnemyShell(this);	
		Eshell.RD=true;
		//将炮弹对象存入一个集合
		EshellList.add(Eshell);
		Eshell.x+=20;
	}
	//创建内部类：实现线程，用于控制敌机的开火频率
	class FireSpeed implements Runnable {		
		public void run() {
			//控制开火频率
			while(isLife) {
				fire();
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
		}
	}
	public void run() {
		while(isLife) {			
			this.move();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	 //获取敌机飞机的矩形区域，用于碰撞检测
	  //使用Rectangle类
	    public Rectangle getRect() {
	    	return new Rectangle(x,y,enemyPic.getWidth(),enemyPic.getHeight());
	    }
	    //碰撞检测方法：检测敌机与友机的碰撞
	    public boolean isHitPlane(Plane plane) {
	    	//判断友机与敌机是否碰撞
	    	if(this.isLife && plane.isLife &&this.getRect().intersects(plane.getRect())) {
	    		this.isLife=false;
	    		plane.isLife=false;
	    		return true;
	    	}
	    	return false;
	    }
}
