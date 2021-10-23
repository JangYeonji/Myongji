package Java_2018_2_tetris;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class MyTetris2_6 extends JFrame{
	public MyTetris2_6() {
		setTitle("테트리스2_4");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//1.2 패널 객체 생성 추가
		MyPanel myPanel = new MyPanel();
		setContentPane(myPanel);
		
		setSize(320,600);
		setVisible(true);
		myPanel.requestFocus();  ////////////
}// MyTetris2_2 생성자

//1. 패널 클래스
class MyPanel extends JPanel{
	//Point[] SHAPE_COORD = new Point[] {new Point(0,0), new Point(-1,0), new Point(1,0), new Point(0,1)};
	
	Point[][] SHAPE_COORD = new Point[][] { {new Point(0,0), new Point(-1,0), new Point(1,0), new Point(0,1)},
		{new Point(0,0), new Point(0,-1), new Point(-1,0), new Point(-1,1)},
		{new Point(0,0), new Point(0,-1), new Point(1,0), new Point(1,1)},
		{new Point(0,0), new Point(0,-1), new Point(0,2), new Point(0,1)},
		{new Point(0,0), new Point(0,-1), new Point(-1,-1), new Point(0,1)},
		{new Point(0,0), new Point(0,-1), new Point(1,-1), new Point(0,1)},
		{new Point(0,0), new Point(1,0), new Point(0,-1), new Point(1,-1)}
	};
	Point[] shapeCoord = new Point[4];
	Point[] shape = new Point[4];
	//5 ArrayList:떨어진 블럭들을 저장
	List<Point> rBlock = new ArrayList<>();  //java.util
	MyPanel(){   //생성자
		this.setBackground(Color.cyan);
		
		int type = (int)(Math.random()*7);
		
		for(int i=0; i<shapeCoord.length; i++) {
			shapeCoord[i] = new Point(SHAPE_COORD[type][i].x, SHAPE_COORD[type][i].y);
			shape[i] = new Point(SHAPE_COORD[type][i].x+4, SHAPE_COORD[type][i].y+2);
		}// for
		
		//3.2 스레드 객체 만들기
		MyThread myThread = new MyThread();
		myThread.start();
		
		//4.2 이벤트 리스너 등록
		MyListener myListener = new MyListener();
		this.addKeyListener(myListener);
	   	
	}// MyPanel()
	
	//4. 이벤트 클래스 만들기
	class MyListener extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {  //회전
				int tmp;
				for(int i=0; i<shapeCoord.length; i++) {
					tmp = shapeCoord[i].x;
					shapeCoord[i].x = - shapeCoord[i].y;
					shapeCoord[i].y = tmp;
				}// for
				
				for(int i=0; i<shape.length; i++) { 
					shape[i].x = shapeCoord[i].x + shape[0].x;
					shape[i].y = shapeCoord[i].y + shape[0].y;
				}//for
				
			}// if
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {  //왼쪽
				boolean stop = false;
				for(int i=0; i<shape.length; i++) { //하나의 shape[i]라도 체크
					if(shape[i].x == 1) { //왼쪽 끝에 있음
						stop = true; 
						break; //첫 발견시 벗어나기
					}
				}
				if(stop == false) {
					for(int i=0; i<shape.length; i++)
						shape[i].x--; //x의 값 감소
				}
			} //if 왼쪽이동

			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {  //오른쪽
				boolean stop = false;
				for(int i=0; i<shape.length; i++) { //하나의 shape[i]라도 체크
					if(shape[i].x == 8) { //오른쪽 끝에 있음
						stop = true; 
						break; //첫 발견시 벗어나기
					}
				}
				if(stop == false) {
					for(int i=0; i<shape.length; i++)
						shape[i].x++;
				}
				
			} //if 오른쪽이동
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {  //아래로
				boolean down = false;
				
				while(down != true) {
					down = isFallen();
				
						if( down == false ) {
							for(int i=0; i<shape.length; i++) 
								shape[i].y += 2;
							down = true; break; 
						}//if
			
				}//while		
			} //if 아래로 이동
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {  //새로
				
				;
			} //if 새로
			
			///////////////////////////////////////////왼쪽, 오른쪽 if--- 집에서
			
			repaint();
			
		} //keyPressed
		
	}//MyListener
	
	//3. 스레드 클래스
	class MyThread extends Thread{
		@Override
		public void run() {
			int type = (int)(Math.random()*7);
			boolean fallen =false;
			while(true) {
/*					for(int i=0; i<shape.length; i++) {
					if(shape[i].y == 14 || 내 아래에 블럭이 있다) {
						fallen = true;
						break;  // 가장 가까운 루프를 벗어남
					}//if
				}//for
*/					fallen = isFallen();   // 메소드로 처리
				if( fallen != true) {
					for(int i=0; i<shape.length; i++) {
						shape[i].y++;
					}//for
				}else {   //떨어진 후 *******************************************
					for(int i=0; i<shape.length; i++)   // rBlock 추가  ArrayList=> size(), add(객체), remove(), get(i)
						rBlock.add(new Point(shape[i].x, shape[i].y) );   //오류  shape[i]
					
					for(int i=0; i<shape.length; i++) {  	
						if( isLineFull(shape[i].y) == true ) // 해당 블록이 최종 놓인 라인이 가득차면
							removeLine(shape[i].y);  // 라인 지우기
					}//for 중요
					
					for(int i=0; i<shapeCoord.length; i++) {  // 회전 안한 오리지날 모양
						shapeCoord[i].x = SHAPE_COORD[type][i].x;
						shapeCoord[i].y = SHAPE_COORD[type][i].y;
					}
					
					for(int i=0; i<shape.length; i++) {  // 새 shape
						shape[i].x = shapeCoord[i].x + 4;
						shape[i].y= shapeCoord[i].y + 2;
					}//for
					fallen =false;
				}//else
				
				
				try { Thread.sleep(500); } catch(Exception e) { }
				repaint();
			}//while
		} //run
		
	}//MyThread
	
	//6. 메소드 들
	public boolean isFallen(){
		boolean result = false;
		for(int i=0; i<shape.length; i++) {
			if(shape[i].y == 14 || isBlockNext(shape[i]) ) {
				result = true;
				break;  // 가장 가까운 루프를 벗어남
			}//if
		}//for
		return result;
	}
	//6.2 
	public boolean isBlockNext(Point p){
		boolean result = false;
		/*for(int i=0; i<rBlock.size(); i++) 
			if( rBlock.get(i).x == p.x    &&   rBlock.get(i).y  == p.y+1     )*/
		for(Point r:rBlock)
			if(r.x == p.x && r.y == p.y+1) {
				result = true;
				break;
			}
		return result;
	}
	//6.3 isLineFull()
	public boolean isLineFull(int y) {
		boolean result=false;
		int cnt=0;
		for(Point r : rBlock) {
			if(r.y == y) {
				cnt++;  // 8이면 break하자
			}
		}//for
		if( cnt==8) 
			result=true;
		return result;
	}
	//6.3
	public void removeLine(int y){
		int cnt=0, index=0;
		while(cnt<8) {
			if(y == rBlock.get(index).y) {
				rBlock.remove(index);
				cnt++;
			}else
				index++;
		}
/*			for(Point r : rBlock) {
			if(y == r.y) 
				rBlock.remove(r); //
		}*/
		for(Point r : rBlock) {
			if(r.y < y)
				r.y++;
		}
		for(Point b : shape) {
			if(b.y < y)
				b.y++;
		}
	}
	
	//2. paintComponent
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //중요
		g.setColor(Color.RED);
		for(int i=0; i<shape.length; i++)  //떨어지는 블럭들(shape:배열)
			g.fillRoundRect(shape[i].x*40-40, shape[i].y*40-40, 40, 40, 10, 10);
		
		g.setColor(Color.PINK);  
		for(int i=0; i<rBlock.size(); i++)   //떨어진 블럭들(rBlock:ArrayList)
			g.fillRoundRect(rBlock.get(i).x*40-40, rBlock.get(i).y*40-40, 40, 40, 10, 10);
	}
	
} //MyPanel class
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyTetris2_6();
	}

}
