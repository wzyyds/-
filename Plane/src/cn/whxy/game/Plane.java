package cn.whxy.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Plane {
	int x=600;
	int y=800;
	BufferedImage planeImg=null;
	//声明boolean值，判断飞机是否开火
	boolean isFire1=false;
	boolean isFire2=false;
	boolean isFire3=false;
	boolean isLife=true;
	//声明炮弹对象
	Shell shell=null;
	ArrayList<Shell> shellList=null;
	public Plane() {
		try {
		planeImg=ImageIO.read(new FileInputStream(".\\pic\\plane.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		shellList=new ArrayList<Shell>();
}
	public void draw(Graphics g) {
		
		//画背景图片
		g.drawImage(planeImg, x, y, 100,100, null);
		if(isFire1) {
			fire1();//调用开炮方法
		}else if(isFire2) {
			fire2();
		}else if(isFire3) {
			fire3();
		}
	}	
	//自定义开火方法
	public void fire1() {
		//创建炮弹对象
		shell=new Shell(this);
		shell.U=true;		
		//将炮弹对象存入一个集合
		shellList.add(shell);
		//炮弹的方向为左上
		shell=new Shell(this);
		shell.LU=true;
		shell.x-=20;
		shellList.add(shell);
		//炮弹的方向为右上
		shell=new Shell(this);
		shell.RU=true;
		shell.x+=20;
		shellList.add(shell);
	}
	public void fire2() {
		shell=new Shell(this);
		shell.U=true;		
		//将炮弹对象存入一个集合
		shellList.add(shell);
	}
	public void fire3() {
		shell=new Shell(this);
		shell.U=true;		
		//将炮弹对象存入一个集合
		shellList.add(shell);
		//炮弹的方向为左上
		shell=new Shell(this);
		shell.LUU=true;
		shell.x-=20;
		shellList.add(shell);
		//炮弹的方向为右上
		shell=new Shell(this);
		shell.RUU=true;
		shell.x+=20;
		shellList.add(shell);
	}
	//自定义飞机移动方法
    public void moveUp() {
    	if(y>0) {
		y-=5;
	}else {
		y=0;
	}
    }
    public void moveDown() {
    	if(y<=800) {
    		y+=5;
    	}else {
    		y=800;
    	}
    }
    public void moveLeft() {
    	if(x>0) {
		x-=10;
	}else {
		x=0;
		}
    }
    public void moveRight() {
		if(x<1150) {
    	x+=10;
	}else {
		x=1150;
	}
}
  //获取玩家飞机的矩形区域，用于碰撞检测
  //使用Rectangle类
    public Rectangle getRect() {
    	return new Rectangle(x,y,planeImg.getWidth(),planeImg.getHeight());
    }
    
}