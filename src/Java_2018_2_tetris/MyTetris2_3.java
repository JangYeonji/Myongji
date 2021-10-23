package Java_2018_2_tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyTetris2_3 extends JFrame{
	public MyTetris2_3() {
		setTitle("나의 테트리스");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//1.1 패널 추가
		MyPanel mypanel = new MyPanel();
		setContentPane(mypanel);
		
		setSize(320,600);
		setVisible(true);
		
		getContentPane().requestFocus(); //4.2 추가
	} //MyTetris2 생성자 끝
	//1. 패널 클래스
	class MyPanel extends JPanel{
		//필드 배열
		Point[]SHAPE_COORD = new Point[] {new Point(0,0),new Point(-1,0), new Point(1,0), new Point(0,1)} ;
		Point[]shapeCoord = new Point[4]; //현재 배치(회전)
		Point[]shape = new Point[4]; //현재 위치
		
		//5. ArrayList 떨어진 블럭들 저장
		List<Point> rBlock= new ArrayList<>(); //java.util은 배열, java.awt은 그래픽
		
		//생성자
		public MyPanel() {
			this.setBackground(Color.cyan);
			for(int i=0;i<SHAPE_COORD.length;i++) { 
				shapeCoord[i] = new Point(SHAPE_COORD[i].x,SHAPE_COORD[i].y); //@중요@배열 객체 요소 복사(주소복사, SHAPE_COORD[i].x)
				shape[i] = new Point(shapeCoord[i].x+4,shapeCoord[i].y+2); //@중요@배열 객체 요소 복사
			} //for 끝
			
			//3.1 스레드 객체 만들고 start
			MyThread thread = new MyThread();
			thread.start();
			
			//4.1 이벤트 리스너 객체 생성
			this.addKeyListener(new MyListener());
			this.requestFocus();
			
		} //MyPanel 생성자 끝
		
		//3. 스레드 클래스
		class MyThread extends Thread{
			@Override
			public void run() {
				boolean fallen = false; //바닥에 떨어졌는지 아닌지. 처음엔 false
				while(true) {
					fallen = isFallen(); //메소드 호출
					
					if(fallen != true) { //떨어지지 않았다면 //깃발 체크
						for(int i=0;i<shape.length;i++) { 
							shape[i].y++; //현재 위치가 바뀜
						}
					}else { //떨어졌다면 다시 새로 생기게
						for(int i=0;i<shape.length;i++)  //rBlock 중간에 공백이 생겨서 for문 2개로 돌림
							//rBlock.add(shape[i]); 안됨
							rBlock.add(new Point(shape[i].x, shape[i].y)); //ArrayList에 새롭게 추가
						//라인이 다 찼을때
						for(int i=0;i<shape.length;i++) { 
							if(isLineFull(shape[i].y) == true){ //shape[i].y 정수  //해당 블록이 최종 놓인 라인
								reMoveLine(shape[i].y); 
							}
							
							shapeCoord[i].x = SHAPE_COORD[i].x; //새로운 좌표(회전되지 않은)값 복사
							shapeCoord[i].y = SHAPE_COORD[i].y;
						
							shape[i].x = shapeCoord[i].x+4; //새로운 shape 생성 및 위치 지정
							shape[i].y = shapeCoord[i].y+2;
						}
						fallen = false; //다시 떨어지게
					}
					try { Thread.sleep(1000); } catch (Exception e) { }
					repaint();
				} //while 끝
			} //run 끝
		} //MyThread 끝
		
		//4. 리스너 클래스
		class MyListener extends KeyAdapter{
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_UP) { //회전
					int tmp;
					for(int i=0;i<shapeCoord.length;i++) {
						tmp = shapeCoord[i].x;
						shapeCoord[i].x = -shapeCoord[i].y;  //마이너스 주의
						shapeCoord[i].y = tmp;
					} 
					for(int i=0;i<shape.length;i++) {
						shape[i].x = shapeCoord[i].x + shape[0].x; //0,0
						shape[i].y = shapeCoord[i].y + shape[0].y; //0에 주의
					}
				} //if 끝
				if(e.getKeyCode()==KeyEvent.VK_LEFT) { //왼쪽
					for(int i=0;i<shape.length;i++) {
						shape[i].x--;
					}
				} //if 끝
				if(e.getKeyCode()==KeyEvent.VK_RIGHT) { //오른쪽
					for(int i=0;i<shape.length;i++) {
						shape[i].x++;
					}
				} //if 끝
				repaint(); //3.2 스레드 stop
			} //keyPressed 끝
		} //MyListener 끝
		
		//2. 그래픽 paintComponent
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); //@중요@ 이전 잔상 지우기
			g.setColor(Color.RED);
			for(int i=0;i<shape.length;i++) { //떨어지는 블럭들(shape:배열)
				g.fillRoundRect(shape[i].x*40-40, shape[i].y*40-40, 40, 40, 10, 10); //shape[i] = 4
				//40을 곱해줬기 때문에 위에서 블록 단위로 사용 가능
			}
			g.setColor(Color.PINK);
			for(int i=0;i<rBlock.size();i++) { //length 아님 //떨어진 블럭들(rBlock:ArrayList)
				g.fillRoundRect(rBlock.get(i).x*40-40, rBlock.get(i).y*40-40, 40, 40, 10, 10); //shape[i] = 4
			}
		} //paintComponent 끝
		
		//6. 메소드
		public boolean isFallen() {
			boolean result = false;
			for(int i=0;i<shape.length;i++) {
				if(shape[i].y ==14 || isBlockNext(shape[i])) //다음에 블록이 있어?
					result = true;
			}
			return result;
		} //isFallen끝
		public boolean isBlockNext(Point p){ //Point 타입이라
			boolean result = false;
			for(Point r:rBlock) { //for(int i=0;i<rBlock.size();i++) {
				//p.x와 값이 같고, p.y보다 1큰 블럭이 rBlock에 있어?
				if(p.x ==r.x && p.y ==r.y){ //if(p.x==rBlock.get(i).x&&p.y+1==rBlock.get(i).y) { 
					result = true;
					break;
				}
			}
			return result;
		}
		public boolean isLineFull(int y) {
			boolean result = false;
			int cnt = 0;
			for(Point p:rBlock) {
				if(y==p.y) {
					cnt++;
					if(cnt==8)
						break;
				}
			}
			if( cnt==8)
				return true;
			else
				return false;
		}
		public void reMoveLine(int y) {
			int cnt=0, index=0;
			while(cnt<8) { //for문 안쓰는 이유?
				if(y==rBlock.get(index).y) {
					rBlock.remove(index);
					cnt++;
				}else {
					index++;
				}
			}
			for(Point r:rBlock) { // 남은 블록을 아래로 한 줄씩 이동
				if(r.y < y) //남은 블럭 중 y값이 지워진 라인의 y값보다 작을 때만 y++
					r.y++;
			}
		}
	} //MyPanel 클래스 끝
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyTetris2_3();
	}

}
