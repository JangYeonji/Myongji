package Java_2019_1_project;

public abstract class GameObject { // 추상 클래스
	protected int distance; // 한 번 이동 거리, protected라서 같은 패키지의 서브 클래스 가능
	protected int x, y; // 현재 위치(화면 맵 상의 위치), protected
	
	//Bear와 Fish의 super을 받아들임
	public GameObject(int startX, int startY, int distance) { // 초기 위치와 이동 거리 설정
		this.x = startX; this.y = startY;
		this.distance = distance;
		//Game클래스에서 Bear의 x=0, y=0, distance=1
		//Game클래스에서 Fish의 x=5, y=5, distance=2
	}
	//Game클래스의 update와 clear 메소드에 GameObject의 x,y 리턴
	public int getX() { return x; } 
	public int getY() { return y; }
	
	//매개변수가 객체다. 그래야 fish랑 bear가 둘 다 들어갈 수 있다.
	public boolean collide(GameObject p) { // fish의 객체가 객체 p와 충돌했으면 true 리턴
		//GameObjcet가 들어가면 p의 x,y랑 나의 x,y랑 비교한다.
		if(this.x == p.getX() && this.y == p.getY())
			return true;
		else 
			return false;
	}
	//추상메소드로 만들어준 다음 오버라이딩 하게 함
	public abstract void move(); // 이동한 후의 새로운 위치로 x, y 변경, Bear와 Fish에서 사용
	public abstract char getShape(); // 객체의 모양을 나타내는 문자 리턴, Bear와 Fish에서 사용
}
