package Java_2019_2_tetris;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyTetris1_2  extends JFrame{
	MyTetris1_2(){
		setTitle("테트리스1");
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
		Point p = new Point(0,0);
		Point block = new Point(p.x+4, p.y+2);   //현재 위치
		
		//5.1 제네릭을 이용한 남은 블록 관리
		java.util.List<Point> rBlocks = new ArrayList<>();
		
		MyPanel(){
			
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
				if(e.getKeyCode() == KeyEvent.VK_LEFT) 
					block.x -= 1;
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
					block.x += 1;
				if(e.getKeyCode() == KeyEvent.VK_DOWN) 
					block.y = 14;
				repaint();  // 3.2 스레드 stop
			}
		}
		
		// 3.1 쓰레드 클래스
		class MyThread extends Thread{   
			public void run() {      //3.2 run() 구현
				while(true) {	
					if( block.y != 14 )   //3.3 !fallen(떨어지지 않은)
						block.y += 1;
					else {    //5.2 떨어진 블록은 arrayList에
						//System.out.println("block: "+block.x+", "+block.y);  //테스트를 위한 출력
						rBlocks.add(new Point(block.x, block.y));
						block.x=4; block.y=2;
					}
					try { Thread.sleep(1000); } catch(Exception ex) {return;}
					repaint();  //2. repaint() 호출
				}
			}
		}
		//2.1 그래픽
		@Override
		protected void paintComponent(Graphics g) {  // 1.1 그래픽
			super.paintComponent(g); //이전 잔상 지우기
			g.setColor(Color.gray);
			g.fillRoundRect(block.x*40-40, block.y*40-40, 40, 40, 10, 10);
			g.setColor(Color.LIGHT_GRAY);
			g.setColor(Color.lightGray);
			for(Point rBlock:rBlocks)
				g.fillRoundRect(rBlock.x*40-40, rBlock.y*40-40, 40, 40, 10, 10);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyTetris1_2();
	}

}
