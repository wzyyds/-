package cn.whxy.game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/*
 * 画布类
 */
import javax.swing.JPanel;


public class GameBG extends JPanel implements Runnable{
	Plane plane=null;
	BufferedImage bgImg=null;
	//声明键盘监听类对象
	MyKeyListener mkl=null;
	//Shell shell=null;
	Enemy ep=null;
	//用于判断飞机移动方向的boolean值
	boolean U=false,D=false,L=false,R=false;
	//声明敌机集合对象
	ArrayList<Enemy> ePlaneList=null;	
	ArrayList<Effect> effList=null;
	public GameBG() {
		
	    plane=new Plane();
		try {
		bgImg=ImageIO.read(new FileInputStream(".\\pic\\bg1.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		//创建键盘监听类对象
		mkl=new MyKeyListener();
		//给当前的画布类添加键盘监听
		this.addKeyListener(mkl);
		//创建敌机集合对象
		ePlaneList=new ArrayList<Enemy>();
		effList=new ArrayList<Effect>();
		
		//启动创建敌机线程
		new Thread(new CreateEPlane()).start();
		//shell=new Shell();
		new Thread(this).start();
}
	//重写paint()方法
	public void paint(Graphics g) {
		//画背景图片
		g.drawImage(bgImg, 0, 0, 1280,960, null);
		if(plane.isLife) {
			plane.draw(g);
			
			if(U && !D && !L && !R) {
				plane.moveUp();
			}else if(!U && D && !L && !R) {
				plane.moveDown();
			}else if(!U && !D && L && !R) {
				plane.moveLeft();
			}else if(!U && !D && !L && R) {
				plane.moveRight();
			}if(U && !D && !L && !R) {
				plane.moveUp();
			}else if(!U && D && !L && !R) {
				plane.moveDown();
			}else if(!U && !D && L && !R) {
				plane.moveLeft();
			}else if(U && !D && L && !R) {
				plane.moveUp();
				plane.moveLeft();
			}else if(U && !D && !L && R) {
				plane.moveUp();
				plane.moveRight();
			}else if(!U && D && L && !R) {
				plane.moveDown();
				plane.moveLeft();
			}else if(!U && D && !L && R) {
				plane.moveDown();
				plane.moveRight();
			}
			
			}
		
		for(int i=0;i<plane.shellList.size();i++) {
			Shell shell=plane.shellList.get(i);
			//如果炮弹还活着
			if(shell.isLife) {
				shell.draw(g);
			}else {
				//炮弹死了从集合中清除这一个炮弹对象
				plane.shellList.remove(shell);
			}
			
			
			//检测是否打中了某一架敌机(遍历敌机集合)
		for(int x=0;x<ePlaneList.size();x++) {
			Enemy ep=ePlaneList.get(x);
			
			if(shell.isHitPlane(ep)) {				
				//打中了，产生爆炸效果
				Effect ef=new Effect();
				  ef.x = ep.x + ep.enemyPic.getWidth() / 2 - ef.effPics[3].getWidth() / 2;
                  ef.y =ep.y + ep.enemyPic.getHeight() / 2 - ef.effPics[3].getHeight() / 2;
                  effList.add(ef);
                  new Thread(ef).start();
                  
			 } else {
                 //炮弹死亡 清楚
                 ep.EshellList.remove(ep);
             }
				
			}
		}
		
		
			//enemy.draw(g);
		//遍历敌机集合，画敌机			
		
		//shell.draw(g);
		//判断飞机的移动方向
		
		/*if(plane.shell !=null) {
			plane.shell.draw(g);
		}*/
	    //遍历plane对象的炮弹集合，画炮弹
			for(int x=0;x<ePlaneList.size();x++) {
				Enemy ep=ePlaneList.get(x);
				if(ep.isLife) {
				ep.draw(g);
				if(ep.isHitPlane(plane)) {
					Effect ef=new Effect();
					ef.x=plane.x+plane.planeImg.getWidth()/2-ef.effPics[3].getWidth()/2;
					ef.y=plane.y+plane.planeImg.getHeight()/2-ef.effPics[3].getHeight()/2;
					new Thread(ef).start();
					effList.add(ef);
				}
				}
				//else {
					//ePlaneList.remove(ep);
			//}
		for(int index=0;index<ep.EshellList.size();index++) {
			EnemyShell Eshell=ep.EshellList.get(index);
			if(Eshell.isLife) {
			//画炮弹
			Eshell.draw(g);
			if(Eshell.isHitPlane(plane)) {
				//打中了产生爆炸效果
				Effect ef=new Effect();
				ef.x=plane.x+plane.planeImg.getWidth()/2-ef.effPics[3].getWidth()/2;
				ef.y=plane.y+plane.planeImg.getHeight()/2-ef.effPics[3].getHeight()/2;
				effList.add(ef);
				new Thread(ef).start();
			}			   
			}else {
				ep.EshellList.remove(Eshell);
			}
			}			
					}
		
		for(int x=0;x<effList.size();x++) {
			Effect ef=effList.get(x);
			if(ef.isLife){
			ef.draw(g);
		}else {
			effList.remove(ef);
		}
		}
		//画笔销毁
		g.dispose();
	}
   //自定义内部类：实现键盘监听
   class MyKeyListener extends KeyAdapter{

	   //重写按键按下方法
	   public void keyPressed(KeyEvent e) {		  
		   if(e.getKeyCode()==KeyEvent.VK_W) {//w键的ASCII码
			   U=true;
			 
		   }
		   if(e.getKeyCode()==KeyEvent.VK_A) {//A键的ASCII码
			   L=true;
			  
		   }
           if(e.getKeyCode()==KeyEvent.VK_S) {//S键的ASCII码
        	   D=true;
			   
		   }
           if(e.getKeyCode()==KeyEvent.VK_D) {//D键的ASCII码
        	   R=true;
		   }
           if(e.getKeyCode()==KeyEvent.VK_J) {//J键的ASCII码
        	  plane.isFire1=true;
		   }
           if(e.getKeyCode()==KeyEvent.VK_K) {//J键的ASCII码
         	  plane.isFire2=true;
 		   }
           if(e.getKeyCode()==KeyEvent.VK_L) {//J键的ASCII码
          	  plane.isFire3=true;
  		   }
           if(e.getKeyCode()==KeyEvent.VK_F1) {//J键的ASCII码
           	  plane.isLife=true;
   		   }
           
	   }
	   //重写按键释放方法
	   public void keyReleased(KeyEvent e) {
		   if(e.getKeyCode()==KeyEvent.VK_W) {//w键的ASCII码
			   U=false;
		   }
		   if(e.getKeyCode()==KeyEvent.VK_A) {//A键的ASCII码
			   L=false;
		   }
           if(e.getKeyCode()==KeyEvent.VK_S) {//S键的ASCII码
			   D=false;
		   }
           if(e.getKeyCode()==KeyEvent.VK_D) {//D键的ASCII码
			   R=false;
		   } if(e.getKeyCode()==KeyEvent.VK_J) {//J键的ASCII码
        	  plane.isFire1=false;
		   }
		   if(e.getKeyCode()==KeyEvent.VK_K) {//J键的ASCII码
	        	  plane.isFire2=false;
		   }
		   if(e.getKeyCode()==KeyEvent.VK_L) {//J键的ASCII码
	         	  plane.isFire3=false;
	 		   }
           
	   }	  	   	   
   }
   //自定义内部类，实现线程，实现创建多架敌机
   class CreateEPlane implements Runnable{
	//重写run方法
	public void run() {
		while(true) {
			ep=new Enemy();
			//将敌机对象存入集合中
			ePlaneList.add(ep);
			try {
				Thread.sleep(5000);//1秒创建一架敌机
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
	}
   }   
   
   //重写线程体，实现画面重绘
   public void run() {
	   while(true) {
		   this.repaint();//自动调用paint方法，重绘整个画面
		   try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	   }
   }
}
