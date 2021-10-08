package Java_2019_2_tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyTetris1 extends JFrame{
	MyTetris1(){
		setTitle("테트리스1");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MyPanel my = new MyPanel(); //1.1 패널 객체 생성
		setContentPane(my); //1.2 패널 컨텐트패인 부착
		
		setSize(320,600); 
		setVisible(true);
		my.requestFocus();
	}
	//1. panel 클래스
	class MyPanel extends JPanel{
		//1.1 배열
		Point p = new Point(0,0); //블록 초기 위치 (단위 블록 크기)
		Point block = new Point(p.x+4, p.y+2);   //현재 위치

		
		public MyPanel(){
			setBackground(Color.yellow);
			//3.1 스레드 객체 만들고 출발
			MyThread my = new MyThread();  
			my.start(); //3.2 스레드 시작
			
			this.requestFocus();  //4.3 키리스너 동작
			//4.2 이벤트 키리스너 객체 만들고 등록
			KeyListener keyListener = new KeyListener();
			this.addKeyListener(keyListener);
		}

		//4. 키리스너 클래스 만들기 ->떨어지는 동안에 움직일 수 있게
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
		
		// 3. 쓰레드 클래스 or runable인터페이스
		class MyThread extends Thread{   
			public void run() {      //3.1 run() 구현
				while(true) {	
					if( block.y != 14 )   //3.2 fallen
						block.y ++;
					try { Thread.sleep(1000); } catch(Exception ex) {return;}
					repaint();  //2. repaint() 호출
				}
			}
		}
		
		//2. 그래픽
		@Override
		protected void paintComponent(Graphics g) { 
			super.paintComponent(g); //이전 잔상 지우기
			g.setColor(Color.red);
			//g.fillRoundRect(140, 80, 40, 40, 10, 10);
			
			//320px인데 블록의 크기는 40이라 8개 만들 수 있음
			//block.x는 4x3칸에 들어간다.
			//오른쪽 기준점에서 왼쪽기준점으로 하기 위해 -40을 해준다.
			//블록 단위로 1씩 빼준다.
			g.fillRoundRect(block.x*40-40, block.y*40-40, 40, 40, 10, 10);
			
			//g.fillRoundRect(block.x*40-40+2, block.y*40-40+2, 40-4, 40-4, 10, 10);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyTetris1();
	}

}
