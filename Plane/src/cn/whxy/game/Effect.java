package cn.whxy.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Effect implements Runnable{
	BufferedImage[] effPics=null;
	int x,y;
	int index=0;
	boolean isLife=true;
	public Effect() {
		//创建数组
		effPics=new BufferedImage[7];
		for(int i=0;i<effPics.length;i++) {
		try {
			effPics[i]=ImageIO.read(new FileInputStream(".\\pic\\bz\\0"+(i+1)+".png"));
		} 
		 catch (IOException e) {
			
			e.printStackTrace();
		}		
		}
		
	}
	public void draw(Graphics g) {
		g.drawImage(effPics[index], x, y, null);
	}
	//重写线程体：改变下标值
	public void run() {
		while(isLife) {
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			index++;
			if(index==effPics.length-1) {
				break;
			}
		}
		isLife=false;
	}
}
