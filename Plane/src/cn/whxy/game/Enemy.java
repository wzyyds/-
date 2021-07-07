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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		EshellList=new ArrayList<EnemyShell>();
		//�����߳�
		new Thread(this).start();
		fs=new FireSpeed();
		//�������ڿ��ƿ���Ƶ�ʵ��߳�
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
		//�����ڵ�����
		Eshell=new EnemyShell(this);	
		Eshell.D=true;
		//���ڵ��������һ������
		EshellList.add(Eshell);
		
		Eshell=new EnemyShell(this);	
		Eshell.LD=true;
		//���ڵ��������һ������
		EshellList.add(Eshell);
		Eshell.x-=20;
		Eshell=new EnemyShell(this);	
		Eshell.RD=true;
		//���ڵ��������һ������
		EshellList.add(Eshell);
		Eshell.x+=20;
	}
	//�����ڲ��ࣺʵ���̣߳����ڿ��Ƶл��Ŀ���Ƶ��
	class FireSpeed implements Runnable {		
		public void run() {
			//���ƿ���Ƶ��
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	 //��ȡ�л��ɻ��ľ�������������ײ���
	  //ʹ��Rectangle��
	    public Rectangle getRect() {
	    	return new Rectangle(x,y,enemyPic.getWidth(),enemyPic.getHeight());
	    }
	    //��ײ��ⷽ�������л����ѻ�����ײ
	    public boolean isHitPlane(Plane plane) {
	    	//�ж��ѻ���л��Ƿ���ײ
	    	if(this.isLife && plane.isLife &&this.getRect().intersects(plane.getRect())) {
	    		this.isLife=false;
	    		plane.isLife=false;
	    		return true;
	    	}
	    	return false;
	    }
}
