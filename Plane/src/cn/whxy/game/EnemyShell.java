package cn.whxy.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class EnemyShell implements Runnable{
	int x,y;
	BufferedImage EsPic=null;
	boolean D=false,LD=false,RD=false;
	boolean isLife=true;
	public EnemyShell(Enemy enemy) {
		try {
			EsPic=ImageIO.read(new FileInputStream(".\\pic\\16X16ZD2.png"));		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	
		x=enemy.x+enemy.enemyPic.getWidth()/2-EsPic.getWidth()+22;
		y=enemy.y-EsPic.getHeight()+80;
		new Thread(this).start();
}
	public void draw(Graphics g) {
		g.drawImage(EsPic,x,y,null);
	}
	public void run() {
		while(isLife) {
		this.move();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
		
		
	}
	public void move() {
		if(D) {
		y+=5;
		}else if(LD) {
			x-=4;
			y+=3;
		}else if(RD) {
			x+=4;
			y+=3;
		}
		if(x<-15 || x>1280 || y>960) {
			isLife=false;
			
		}
	}
	 //获取敌机炮弹的矩形区域，用于碰撞检测
	  //使用Rectangle类
	    public Rectangle getRect() {
	    	return new Rectangle(x,y,EsPic.getWidth(),EsPic.getHeight());
	    }
	  //碰撞检测方法：检测敌机炮弹与友机的碰撞
	    public boolean isHitPlane(Plane plane) {
	    	//判断友机与敌机是否碰撞
	    	if(this.isLife && plane.isLife && this.getRect().intersects(plane.getRect())) {
	    		this.isLife=false;
	    		plane.isLife=false;
	    		return true;
	    	}
	    	return false;
	    }
}