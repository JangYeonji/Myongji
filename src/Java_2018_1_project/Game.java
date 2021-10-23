package Java_2018_1_project;

public class Game {
	public static final int MAX_X = 20; //모눈종이->옆으로 20칸
	public static final int MAX_Y = 10; //밑으로 10줄
	private char map [][] = new char [MAX_Y][MAX_X]; //2차원 배열 생성, Game 클래스에서만 사용가능
	private GameObject [] m = new GameObject[3]; //곰1개, 물고기2개인 배열 생성
	int state; // 0: 게임 중, 1: Monster가 winner, 2:Human이 winner
	
	public Game() { //기본생성자 실행
		for(int i=0; i<MAX_Y; i++) //이중for문 메모리에 '-'를 채운다.
			for(int j=0; j<MAX_X; j++)
				map[i][j] = '-'; 
		
		//기본생성자를 사용하지 않을 것이다!
		m[0] = new Bear(0, 0, 1); //첫번째 배열 Bear의 x,y,distance 선언
		m[1] = new Fish(5, 5, 2); //두번째 배열 Fish의 x,y,distance 선언
		m[2] = new Fish(15, 7, 2); //세번째 배열 Fish의 x,y,distance 선언
		state = 0; // 게임 중
	}
	
	void run() { //게임 실행하는 메소드
		System.out.println("** Bear의 Fish 먹기 게임을 시작합니다.**");;
		System.out.println("** Fish 하나만 먹어도 승리합니다.**");;

		update(); // 초기 좌표에 따른 맵 설정
		draw(); // 초기 게임 맵을 보여줌

		while(!doesEnd()) { //doesEnd가 true가 나올때까지 계속 반복해라-->충돌하면 끝내라
			clear(); // 현재의 맵 지움, move 다음에 넣어도 상관없다.
			for(int i=0; i<m.length; i++) 
				m[i].move(); //GameObject 클래스의 move를 해라
			update(); // 움직인 후 좌표 변경에 따른 맵 갱신
			draw(); // 맵 그리기
		}
		System.out.println("Bear Wins!!"); //true가 나오면 출력
	}
	//맵 내용 바꾸기
	void update() { // 두번째 Fish부터 먼저 그려서 Fish를 먹는 경우 Fish가 보이지 않기
		for(int i=m.length-1; i>=0; i--) 
			map[m[i].getY()][m[i].getX()] = m[i].getShape();
	}
	void clear() { //Bear과 Fish의 좌표를 '-'로 지워라
		for(int i=0; i<m.length; i++) 
			map[m[i].getY()][m[i].getX()] = '-';
	}
	void draw() { //맵을 사용자에게 보여주기
		System.out.println();
		for(int i=0; i<MAX_Y; i++) {
			for(int j=0; j<MAX_X; j++)
				System.out.print(map[i][j]); //옆으로 10줄
			System.out.println(); //다음줄로 변경
		}
	}
	boolean doesEnd() { //반환값 boolean타입
		// Bear ate Fish
		if(m[0].collide(m[1])) { //GameObject에서 true가 리턴되면 여기서도 true를 리턴해라.
			return true;
		}
		else if(m[0].collide(m[2])){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static void main(String[] args) { //main 메소드
		Game game = new Game(); //Game 객체 생성--> 판 만들어주고, Bear과 Fish의 좌표 선언
		game.run(); //game의 run 메소드 실행
	}

}

