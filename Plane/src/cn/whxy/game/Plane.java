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
	//����booleanֵ���жϷɻ��Ƿ񿪻�
	boolean isFire1=false;
	boolean isFire2=false;
	boolean isFire3=false;
	boolean isLife=true;
	//�����ڵ�����
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
		
		//������ͼƬ
		g.drawImage(planeImg, x, y, 100,100, null);
		if(isFire1) {
			fire1();//���ÿ��ڷ���
		}else if(isFire2) {
			fire2();
		}else if(isFire3) {
			fire3();
		}
	}	
	//�Զ��忪�𷽷�
	public void fire1() {
		//�����ڵ�����
		shell=new Shell(this);
		shell.U=true;		
		//���ڵ��������һ������
		shellList.add(shell);
		//�ڵ��ķ���Ϊ����
		shell=new Shell(this);
		shell.LU=true;
		shell.x-=20;
		shellList.add(shell);
		//�ڵ��ķ���Ϊ����
		shell=new Shell(this);
		shell.RU=true;
		shell.x+=20;
		shellList.add(shell);
	}
	public void fire2() {
		shell=new Shell(this);
		shell.U=true;		
		//���ڵ��������һ������
		shellList.add(shell);
	}
	public void fire3() {
		shell=new Shell(this);
		shell.U=true;		
		//���ڵ��������һ������
		shellList.add(shell);
		//�ڵ��ķ���Ϊ����
		shell=new Shell(this);
		shell.LUU=true;
		shell.x-=20;
		shellList.add(shell);
		//�ڵ��ķ���Ϊ����
		shell=new Shell(this);
		shell.RUU=true;
		shell.x+=20;
		shellList.add(shell);
	}
	//�Զ���ɻ��ƶ�����
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
  //��ȡ��ҷɻ��ľ�������������ײ���
  //ʹ��Rectangle��
    public Rectangle getRect() {
    	return new Rectangle(x,y,planeImg.getWidth(),planeImg.getHeight());
    }
    
}