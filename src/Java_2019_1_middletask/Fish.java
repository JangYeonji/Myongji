package Java_2019_1_middletask;

public class Fish extends GameObject { //GameObject를 상속받는 Fish 선언
	 public Fish(int x, int y, int distance) {
		super(x, y, distance); 
		//부모부분 만들 때 super부터 하자. 이게 없다면 부모의 기본생성자를 부름
		//-->기본이 없어서 오류가 남 자동으로 안만들어짐
		//GameObject의 생성자 GameObject(x,y,distance) 호출
		//Game클래스에서 Fish의 x=5, y=5, distance=2
	 }
	 
	 //move를 오버라이드 함
	 public void move() { //Fish의 랜덤 좌표 출력(GameObject클래스에서 추상클래스 사용), 타입은 void
	 // 한 번 움직이는 과정 전개
		//2,4가 나오면 안움직이고 0,3이나 1일때 움직이니까 3/5확률로 게임을 어렵게 한다.
		 int n = (int)(Math.random()*5); // 0,1,2,3,4 중에서 0인 경우 + 방향, 1인 경우 - 방향, 나머지 정지
		 if(n==0 || n==3) x += distance; //만약 랜덤값이 0이면 x좌표는 오른쪽으로 2 움직인다.
		 else if(n==1) x -= distance; //만약 랜덤값이 1이면 x좌표는 왼쪽으로 2 움직인다.

		 if(x < 0) x=0; //만약 x좌표가 0보다 작으면 x는 그대로 0이다.
		 if(x >= Game.MAX_X) x = Game.MAX_X - 1; //만약 x좌표가 Game클래스의 MAX_X보다 크면 그 값보다 -1해라
		 
		 //x랑 같음
		 n = (int)(Math.random()*5);
		 if(n==0) y += distance;
		 else if(n==1) y -= distance;

		 if(y < 0) y=0;
		 if(y >= Game.MAX_Y) y= Game.MAX_Y - 1;
	 }

	 public char getShape() { // Fish의 모양 리턴(GameObject클래스에서 추상클래스 사용), 타입은 char
		 return '*';
	 }
}

