package cn.whxy.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
/*
 * 玩家炮弹类
 */

public class Shell implements Runnable{
	BufferedImage shellPic=null;
	int x,y;	
	//bool型isLife,判断炮弹是否还在屏幕内
	boolean isLife=true;
	boolean U=false,LU=false,RU=false,LUU=false,RUU=false;
	boolean isFire1=false,isFire2=false,isFire3=false;
	public Shell(Plane plane) {
		try {
			shellPic=ImageIO.read(new FileInputStream(".\\pic\\16X16ZD4.png"));
		}catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	x=plane.x+plane.planeImg.getWidth()/2-shellPic.getWidth()+10;
	y=plane.y-shellPic.getWidth()-15;
	if(LU) {
		x-=30;
	}
	if(RU) {
		x+=30;
	}
	new Thread(this).start();
	}
	//自定义画炮弹方法
	public void draw(Graphics g) {
		g.drawImage(shellPic, x, y,null);
	}
	public void move() {
		if(U) {
		y-=8;
		}else if(LU) {
			x-=4;
			y-=5;
		}else if(RU) {
			x+=4;
			y-=5;
		}else if(LUU) {
			y-=8;
		}else if(RUU) {
			y-=8;
		}
		if(x<-15 || x>1280 || y<-40) {
			isLife=false;
			
		}
	}
	public void run() {
		while(isLife) {
		this.move();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
	}
	 //获取敌机炮弹的矩形区域，用于碰撞检测
	  //使用Rectangle类
	    public Rectangle getRect() {
	    	return new Rectangle(x,y,shellPic.getWidth(),shellPic.getHeight());
	    }
	  //碰撞检测方法：检测友机炮弹与敌机的碰撞
	    public boolean isHitPlane(Enemy ep) {
	    	
	    	if(this.isLife &&ep.isLife &&this.getRect().intersects(ep.getRect())) {
	    		this.isLife=false;
	    		
	    		ep.isLife=false;
	    		return true;
	    	}
	    	return false;
	    }
}

