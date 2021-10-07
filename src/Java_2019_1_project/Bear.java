package Java_2019_1_project;

import java.util.Scanner;

public class Bear extends GameObject { //GameObject를 상속받는 Bear 선언
	private int distance; // 한 번 이동 거리, Bear 클래스 내에서만 가능
	public Bear(int x, int y, int distance) { 
		super(x, y, distance); 
		//GameObject의 생성자 GameObject(x,y,distance) 호출
		//Game클래스에서 Bear의 x=0, y=0, distance=1
	}
	//move를 오버라이드 함
	public void move() { //Bear의 좌표 출력(GameObject클래스에서 추상클래스 사용), 타입은 void
		System.out.print("왼쪽(a), 아래(s), 위(w), 오른쪽(d) >> ");
		char c;
		Scanner scanner = new Scanner(System.in);
		c = scanner.next().charAt(0); //charAt(x)문자열에서 x번째 문자
		switch(c) {
			case 'a' : // left
				x--; //x좌표 -1
				if(x < 0) x = 0; //만약 x좌표가 0보다 작으면 x는 그대로 0이다.
				break;
			case 'd' : // right
				x++; //x좌표 +1
				if(x >= Game.MAX_X) x = Game.MAX_X - 1; 
				//만약 x좌표가 Game클래스의 MAX_X보다 크면 그 값보다 -1해라. 왜냐하면 배열로 0~19까진데 MAX_X값은 20이니까...
				break;
			case 'w' : // up
				y--; //y좌표 -1
				if(y < 0) y = 0; //만약 y좌표가 0보다 작으면 y는 그대로 0이다.
				break;
			case 's' : // down
				y++; 
				if(y >= Game.MAX_Y) y = Game.MAX_Y - 1;
				//만약 y좌표가 Game클래스의 MAX_Y보다 크면 그 값보다 -1해라. 왜냐하면 배열로 0~9까진데 MAX_Y값은 10이니까...
				break;
		}		
	}
	public char getShape() { // Bear의 모양 리턴(GameObject클래스에서 추상클래스 사용), 타입은 char
		return '<';
	}
}
