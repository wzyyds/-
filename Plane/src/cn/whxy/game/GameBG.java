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
 * ������
 */
import javax.swing.JPanel;


public class GameBG extends JPanel implements Runnable{
	Plane plane=null;
	BufferedImage bgImg=null;
	//�������̼��������
	MyKeyListener mkl=null;
	//Shell shell=null;
	Enemy ep=null;
	//�����жϷɻ��ƶ������booleanֵ
	boolean U=false,D=false,L=false,R=false;
	//�����л����϶���
	ArrayList<Enemy> ePlaneList=null;	
	ArrayList<Effect> effList=null;
	public GameBG() {
		
	    plane=new Plane();
		try {
		bgImg=ImageIO.read(new FileInputStream(".\\pic\\bg1.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		//�������̼��������
		mkl=new MyKeyListener();
		//����ǰ�Ļ�������Ӽ��̼���
		this.addKeyListener(mkl);
		//�����л����϶���
		ePlaneList=new ArrayList<Enemy>();
		effList=new ArrayList<Effect>();
		
		//���������л��߳�
		new Thread(new CreateEPlane()).start();
		//shell=new Shell();
		new Thread(this).start();
}
	//��дpaint()����
	public void paint(Graphics g) {
		//������ͼƬ
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
			//����ڵ�������
			if(shell.isLife) {
				shell.draw(g);
			}else {
				//�ڵ����˴Ӽ����������һ���ڵ�����
				plane.shellList.remove(shell);
			}
			
			
			//����Ƿ������ĳһ�ܵл�(�����л�����)
		for(int x=0;x<ePlaneList.size();x++) {
			Enemy ep=ePlaneList.get(x);
			
			if(shell.isHitPlane(ep)) {				
				//�����ˣ�������ըЧ��
				Effect ef=new Effect();
				  ef.x = ep.x + ep.enemyPic.getWidth() / 2 - ef.effPics[3].getWidth() / 2;
                  ef.y =ep.y + ep.enemyPic.getHeight() / 2 - ef.effPics[3].getHeight() / 2;
                  effList.add(ef);
                  new Thread(ef).start();
                  
			 } else {
                 //�ڵ����� ���
                 ep.EshellList.remove(ep);
             }
				
			}
		}
		
		
			//enemy.draw(g);
		//�����л����ϣ����л�			
		
		//shell.draw(g);
		//�жϷɻ����ƶ�����
		
		/*if(plane.shell !=null) {
			plane.shell.draw(g);
		}*/
	    //����plane������ڵ����ϣ����ڵ�
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
			//���ڵ�
			Eshell.draw(g);
			if(Eshell.isHitPlane(plane)) {
				//�����˲�����ըЧ��
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
		//��������
		g.dispose();
	}
   //�Զ����ڲ��ࣺʵ�ּ��̼���
   class MyKeyListener extends KeyAdapter{

	   //��д�������·���
	   public void keyPressed(KeyEvent e) {		  
		   if(e.getKeyCode()==KeyEvent.VK_W) {//w����ASCII��
			   U=true;
			 
		   }
		   if(e.getKeyCode()==KeyEvent.VK_A) {//A����ASCII��
			   L=true;
			  
		   }
           if(e.getKeyCode()==KeyEvent.VK_S) {//S����ASCII��
        	   D=true;
			   
		   }
           if(e.getKeyCode()==KeyEvent.VK_D) {//D����ASCII��
        	   R=true;
		   }
           if(e.getKeyCode()==KeyEvent.VK_J) {//J����ASCII��
        	  plane.isFire1=true;
		   }
           if(e.getKeyCode()==KeyEvent.VK_K) {//J����ASCII��
         	  plane.isFire2=true;
 		   }
           if(e.getKeyCode()==KeyEvent.VK_L) {//J����ASCII��
          	  plane.isFire3=true;
  		   }
           if(e.getKeyCode()==KeyEvent.VK_F1) {//J����ASCII��
           	  plane.isLife=true;
   		   }
           
	   }
	   //��д�����ͷŷ���
	   public void keyReleased(KeyEvent e) {
		   if(e.getKeyCode()==KeyEvent.VK_W) {//w����ASCII��
			   U=false;
		   }
		   if(e.getKeyCode()==KeyEvent.VK_A) {//A����ASCII��
			   L=false;
		   }
           if(e.getKeyCode()==KeyEvent.VK_S) {//S����ASCII��
			   D=false;
		   }
           if(e.getKeyCode()==KeyEvent.VK_D) {//D����ASCII��
			   R=false;
		   } if(e.getKeyCode()==KeyEvent.VK_J) {//J����ASCII��
        	  plane.isFire1=false;
		   }
		   if(e.getKeyCode()==KeyEvent.VK_K) {//J����ASCII��
	        	  plane.isFire2=false;
		   }
		   if(e.getKeyCode()==KeyEvent.VK_L) {//J����ASCII��
	         	  plane.isFire3=false;
	 		   }
           
	   }	  	   	   
   }
   //�Զ����ڲ��࣬ʵ���̣߳�ʵ�ִ�����ܵл�
   class CreateEPlane implements Runnable{
	//��дrun����
	public void run() {
		while(true) {
			ep=new Enemy();
			//���л�������뼯����
			ePlaneList.add(ep);
			try {
				Thread.sleep(5000);//1�봴��һ�ܵл�
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
		}
		
	}
   }   
   
   //��д�߳��壬ʵ�ֻ����ػ�
   public void run() {
	   while(true) {
		   this.repaint();//�Զ�����paint�������ػ���������
		   try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	   }
   }
}
