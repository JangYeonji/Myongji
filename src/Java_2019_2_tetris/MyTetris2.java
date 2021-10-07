package Java_2019_2_tetris;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyTetris2 extends JFrame{
	MyTetris2(){
		setTitle("테트리스2");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MyPanel my = new MyPanel();
		setContentPane(my);
		
		setSize(320,600);
		setVisible(true);
		getContentPane().requestFocus();
	}
	//1. panel 클래스
	class MyPanel extends JPanel{
		//1.1 배열
		Point[] SHAPE_COORD = new Point[] {new Point(0,0),new Point(-1,0), new Point(1,0), new Point(0,1)};
		Point[] shapeCoord = new Point[4];   //현재 배치
		Point[] shape = new Point[4];  //현재 위치
		
		MyPanel(){
			System.arraycopy(SHAPE_COORD, 0, shapeCoord, 0, SHAPE_COORD.length);  //1.2 1차원 배열복사
			
			for(int i=0; i<shape.length; i++ )     //1.3 1차원 배열복사2
				shape[i] = new Point(shapeCoord[i].x + (320/40)/2,  shapeCoord[i].y + 2);
			
			this.setBackground(Color.white);
			//3.2 스레드 객체 만들기
			MyThread my = new MyThread();  
			my.start(); //3.3 스레드 시작
			
			requestFocus();  //4.3
			//4.2 키 리스너 객체 만들기
			KeyListener keyListener = new KeyListener();
			this.addKeyListener(keyListener);
			
		}
		
		//4.1 키리스너 클래스 만들기
		class KeyListener extends KeyAdapter { 
			@Override
			public void keyPressed(KeyEvent e) {  
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					int tmp;
					for(int i=0; i<shapeCoord.length; i++) {
						tmp=shapeCoord[i].y;
						shapeCoord[i].y = shapeCoord[i].x;
						shapeCoord[i].x = -tmp;
					}	
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT) 
					for(int i=0; i<shapeCoord.length; i++) 
						shape[i].x -= 1;
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
					for(int i=0; i<shapeCoord.length; i++) 
						shape[i].x += 1;
				
				for(int i=0; i<shapeCoord.length; i++) {
					shape[i] . x = shapeCoord[i].x + shape[0] . x;
					shape[i] . y = shapeCoord[i].y + shape[0] . y;
				}
				repaint();  // 3.2 스레드 stop
			}
		}
		
		// 3.1 쓰레드 클래스
		class MyThread extends Thread{   
			public void run() {      //3.2 run() 구현
				boolean fallen=false;
				while(true) {	
					/*for(int i=0; i<shape.length;i++)
						if( shape[i].y == 14 ) {
							fallen=true;
							break;
						}*/
					fallen=isFallen();
					if(fallen != true)
						for(int i=0; i<shape.length; i++) 
							shape[i].y += 1;
					try { Thread.sleep(1000); } catch(Exception ex) {return;}
					repaint();  //2. repaint() 호출
				}
			}
			// 5. 함수 만들기
			 public boolean isFallen() {  
			        boolean temp=false;  
			          
			        for(Point p:shape) {  
			            if(p.y == 14) {  
			                temp=true;  
			                break;  
			            }  
			        }  
			        return temp;  
			    }  
		}
		
		//2.1 그래픽
		@Override
		protected void paintComponent(Graphics g) {  // 1.1 그래픽
			super.paintComponent(g); //이전 잔상 지우기
			
			for(int i=0; i<shape.length; i++) {
				g.setColor(Color.gray);
				g.fillRoundRect(shape[i].x*40-40, shape[i].y*40-40, 40, 40, 10, 10);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRoundRect(shape[i].x*40-40+2, shape[i].y*40-40+2, 40-4, 40-4, 10, 10);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyTetris2();
	}

}
