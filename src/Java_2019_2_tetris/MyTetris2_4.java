package Java_2019_2_tetris;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class MyTetris2_4 extends JFrame{
	public MyTetris2_4() {
		setTitle("테트리스2_2");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//1.2 패널 객체 생성 추가
		setContentPane(new MyPanel());
		
		setSize(335,600);
		setVisible(true);
		getContentPane().requestFocus();
}// MyTetris2 생성자

//1. 패널 클래스
class MyPanel extends JPanel{
	Point[] SHAPE_COORD = new Point[] {new Point(0,0), new Point(-1,0), new Point(1,0), new Point(0,1)};
	Point[] shapeCoord = new Point[4];
	Point[] shape = new Point[4];
	//5. ArrayList : 떨어진 블럭들을 저장
	List<Point> rBlock = new ArrayList<>(); //////
	MyPanel(){
		this.setBackground(Color.cyan);
		for(int i=0; i<SHAPE_COORD.length; i++) {
			
			shapeCoord[i] = new Point(SHAPE_COORD[i].x, SHAPE_COORD[i].y);
			shape[i] = new Point(SHAPE_COORD[i].x+4, SHAPE_COORD[i].y+2);
		}// for
		
		//3.2 스레드 객체 만들기
		MyThread myThread = new MyThread();
		myThread.start();
		
		//4.2 이벤트 리스너 등록
		MyListener myListener = new MyListener();
		this.addKeyListener(myListener);
		this.requestFocus();  //
		
		//5.2 
		rBlock = new ArrayList<>();
	}
	//4. 이벤트 클래스 만들기
	class MyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {  // ^회전
				int tmp;
				for(int i=0; i<shapeCoord.length; i++) {
					tmp = shapeCoord[i].x;
					shapeCoord[i].x = -shapeCoord[i].y;
					shapeCoord[i].y = tmp;
				}
				for(int i=0; i<shape.length; i++) {
					shape[i].x = shapeCoord[i].x + shape[0].x;
					shape[i].y = shapeCoord[i].y + shape[0].y;
				}
			}//if
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				for(int i=0; i<shape.length; i++) 
					shape[i].x --;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				for(int i=0; i<shape.length; i++) 
					shape[i].x ++;
			}	
			repaint();
		}// keyPressed
	}//MyListener
	
	//3. 스레드 클래스
	class MyThread extends Thread{
		@Override
		public void run() {
			boolean fallen = false;
			while(true) {
				/*for(int i=0; i<shape.length; i++)
					if( shape[i].y == 14)
						fallen = true;*/
				fallen = isFallen();
				if( fallen != true ) { // 1씩 떨어지도록 y좌표값 증가
					 for(int i=0; i<shape.length; i++)
						if(!isBlockAt(shape[i]) ) 
							shape[i].y++;
				}else {   // 떨어진 블럭의 처리
					List<Integer> tmp = new ArrayList<>();
					for(int i=0; i<shape.length; i++) {
						rBlock.add(new Point(shape[i].x, shape[i].y )); // ArrayList에 추가
						
						//line이 full인지check
						if(isLineFull(shape[i].y)) {
							removeLine(shape[i].y);
							System.out.println("y: " + shape[i].y);
						}
						shapeCoord[i].x = SHAPE_COORD[i].x; // 새로운 좌표(회전되지 않은)값 복사
						shapeCoord[i].y = SHAPE_COORD[i].y;
						
						shape[i].x =shapeCoord[i].x +4; //새로운 shape 생성 및 위치 지정
						shape[i].y = shapeCoord[i].y +2; 
					}//for
					fallen=false;
				}//else
				try { Thread.sleep(300);}catch(Exception e) {};
				repaint();
			}//while
		}//run
		
	}
	
	//2. paintComponent
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //중요
		g.setColor(Color.RED);
		for(int i=0; i<shape.length; i++)
			g.fillRoundRect(shape[i].x*40-40, shape[i].y*40-40, 40, 40, 10, 10);
		g.setColor(Color.pink);
		for(int i=0; i<rBlock.size(); i++)
			g.fillRoundRect(rBlock.get(i) .x*40-40, rBlock.get(i).y*40-40, 40, 40, 10, 10);
	}
	
	//6. 메소드  ///////////////////////
	public void removeLine(int y) {
		int cnt=0, index=0;
		while(cnt < 8) {
			if(y ==rBlock.get(index).y) {
				rBlock.remove(index);
				cnt++;
			}
			else
				index++;
				//System.out.println("r.x: " + r.getX() + "r.y: " + r.getY());
		}
		for(Point r:rBlock) {  // 남은 블록을 아래로 한 줄씩 이동
			if(r.y < y)
				r.y++;
		}
	}
	
	public  boolean isLineFull(int y) {
		boolean result=false;
		int cnt=0;
		for(Point r:rBlock) {
			if(y == r.y) {
				cnt++;
				if(cnt == 8) 
					break;
			}
		}
		if(cnt==8)
			return true;
		else
			return false;
	}
	
	public boolean isFallen(){
		boolean result = false;
		for(int i=0; i<shape.length; i++)
			if( shape[i].y == 14 || isBlockAt(shape[i])) //false대신 isBlockAt
				result = true; //break 추가 가능
		return result;
	}
	
	public boolean isBlockAt(Point p){ //p.x, p.y가 들어옴
		boolean result = false;
		for(Point r:rBlock) //rBlock에 있는걸 Point r에 가져온만큼??
			if(p.x == r.x  && p.y+1== r.y)
				result = true;
		return result;
	}
} //MyPanel class
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyTetris2_4();
	}

}
