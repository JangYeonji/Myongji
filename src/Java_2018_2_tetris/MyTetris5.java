package Java_2018_2_tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class MyTetris5 extends JFrame implements Runnable, KeyListener {

	private final int width; // 가로
	private final int height; // 세로
	private final int xCnt; // 가로배열크기
	private final int yCnt; // 세로배열크기
	private final int area; // 가로세로길이
	private final int time; // 빠르기
	private final boolean[][] grid; //
	private final JPanel[][] background; // 배경판넬
	private final Container fc; // 컨테이너
	private Item nextItem; // 다음나올것
	private Item currentItem; // 현재의 아이템
	private final ArrayList<Item> itemList; // 아이템리스트
	private final ArrayList<Color> colorList; // 컬러리스트
	private final Random rnd;
	private final JPanel top, next, center; // 상단 가리는부분
	private boolean isKey = true; // 키보드활성화여부
	private final Color bgColor = new Color(250, 250, 220); // 배경컬러

	// public static boolean isRight = false; //오른쪽여부
	Thread t;

	public MyTetris5(final String str) {

		this.setTitle(str);
		this.xCnt = 14;
		this.yCnt = 25;
		this.time = 300;
		this.area = 20;
		this.width = this.xCnt * this.area;
		this.height = this.yCnt * this.area;
		this.itemList = new ArrayList<Item>();
		this.background = new JPanel[this.xCnt][this.yCnt];
		this.grid = new boolean[this.xCnt][this.yCnt];
		this.rnd = new Random(System.currentTimeMillis());
		this.fc = this.getContentPane();
		this.center = new JPanel();
		this.center.setSize(this.width, this.height);
		this.center.setLayout(null);
		this.center.setBackground(new Color(224, 255, 216));
		this.fc.add(this.center, "Center");
		this.addKeyListener(this);
		this.setBounds(200, 200, this.width + 8, this.height + 13);

		// 아이템 추가하기
		itemList.add(new Rect(this.area, this.center, this.xCnt));
		itemList.add(new OneThree(this.area, this.center, this.xCnt));
		itemList.add(new ThreeOne(this.area, this.center, this.xCnt));
		itemList.add(new LineBlock(this.area, this.center, this.xCnt));
		itemList.add(new Triangle(this.area, this.center, this.xCnt));
		itemList.add(new RightBlock(this.area, this.center, this.xCnt));
		itemList.add(new LeftBlock(this.area, this.center, this.xCnt));
		
		// 색 추가
		this.colorList = new ArrayList<Color>();
		this.colorList.add(Color.red);
		this.colorList.add(Color.blue);
		this.colorList.add(Color.green);
		this.colorList.add(Color.orange);
		this.colorList.add(Color.pink);
		this.colorList.add(new Color(170, 40, 150)); // 보라

		// 상단 셋팅======
		this.top = new JPanel();
		this.next = new JPanel();
		this.top.setBounds(0, 0, this.xCnt * this.area, this.area * 4);
		this.top.setBackground(new Color(244, 211, 99));
		this.next.setBounds((this.xCnt - 4) * this.area, 0, this.area * 4, this.area * 4);
		this.next.setBackground(new Color(245, 180, 250));
		this.center.add(this.top);
		this.top.setLayout(null);
		this.top.add(this.next);

		// 백그라운드 패널 셋팅 ==========
		for (int i = 0; i < background.length; i++) 
			for (int p = 0; p < background[i].length; p++) {
				this.background[i][p] = new JPanel();
				this.background[i][p].setBounds(i * this.area, p * this.area, this.area, this.area);
				this.background[i][p].setBackground(this.bgColor);
				this.center.add(background[i][p]);
			}

		// 아이템 셋팅
		this.currentItem = itemList.get(rnd.nextInt(itemList.size()));
		this.currentItem.setColor(this.colorList.get(this.rnd.nextInt(this.colorList.size())));
		this.currentItem.setDefaultLocation();
		setNextItem();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		t = new Thread(this);
		t.start();
	}

	// 넥스트 아이템 셋팅
	public void setNextItem() {

		Item temp;
		do {
			temp = itemList.get(rnd.nextInt(itemList.size()));
		} while (temp == this.currentItem); // 현재아이템과 중복X

		this.nextItem = temp;
		this.nextItem.setColor(this.colorList.get(this.rnd.nextInt(this.colorList.size())));
		this.nextItem.setNextLocation(); // 위치셋팅
	}

	// 아이템 새로 나오기 셋팅
	public void setNewItem() {
		this.currentItem = this.nextItem;
		this.currentItem.setDefaultLocation();
		setNextItem();
	}

	// 백그라운드 블럭 채우기
	public void setBack(final int x, final int y, final Color c)	{
		this.background[x][y].setBackground(c);
		this.background[x][y].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		this.grid[x][y] = true;
	}

	// 백그라운드 블럭 비우기
	public void setEmptyBack(final int x, final int y){
		this.background[x][y].setBorder(null);
		this.background[x][y].setBackground(this.bgColor);
		this.grid[x][y] = false;
	}

	// 현재의 블록 백그라운드로 복사
	public void setCopyBlock(){
		final Block[] tempBlock = this.currentItem.getBlock();
		for (int i = 0; i < tempBlock.length; i++)
			setBack(tempBlock[i].getX(), tempBlock[i].getY(), this.currentItem.getColor());
		this.currentItem.setReadyLocation(); // 대기위치로 돌아가기
	}

	// 줄없애기 체크
	public void checkLine(){

		for (int i = 0; i < grid[0].length; i++){ // i = Y값 = ROW
			boolean isLine = true;
			for (int p = 0; p < grid.length; p++){ // p = X값 = Column
				// System.out.print(p+","+i+" : " + grid[p][i]);
				if (!grid[p][i]){ // 하나라도 공백이 있으면 break;
					isLine = false;
					break;
				}
			}
			if (isLine){ // 줄없앰
				deleteLine(i);
				System.out.println(i + "줄 없앰");
			}
		}
	}

	// 줄없애고 위에거 한칸씩 내리기
	public void deleteLine(final int line) {
		final boolean temp[] = new boolean[xCnt];
		final JPanel tempPanel[] = new JPanel[xCnt];

		for (int i = line; i > 0; i--) { // i = 줄 = Y
			for (int p = 0; p < grid.length; p++) { // p = 열 = X
				if (i == line) { // 현재줄 템프변수에 저장
					tempPanel[p] = background[p][i];
					tempPanel[p].setLocation(p * this.area, 0);
				}
				// 모든줄 한칸씩 내리기
				grid[p][i] = grid[p][i - 1];
				background[p][i] = background[p][i - 1];
				background[p][i].setLocation(p * this.area, i * this.area);
			}
		}
		// 없앤줄 맨위로 올리기
		for (int i = 0; i < grid.length; i++) {
			background[i][0] = tempPanel[i];
			setEmptyBack(i, 0);
		}
	}

	// 프린트정보출력 임시
	public void printInfo() {
		final Block temp = this.currentItem.getCurrentXY();
		System.out.println("x : " + temp.getX() + ", y : " + temp.getY());
	}

	// 아이템 회전체크 -> 회전
	public void goRotate() {
		final Block[] tempBlock = this.currentItem.getNextBlock();
		for (int i = 0; i < tempBlock.length; i++) {
			final int x = tempBlock[i].getX();
			final int y = tempBlock[i].getY();
			if (x < 0 || x >= this.xCnt || y + 1 >= this.yCnt || this.grid[x][y])
				return;
		}
		this.currentItem.moveRotate();
	}

	// 아이템다운체크 -> 이동
	public boolean goDown() {
		final Block[] tempBlock = this.currentItem.getBlock();
		for (int i = 0; i < tempBlock.length; i++) {
			final int x = tempBlock[i].getX();
			final int y = tempBlock[i].getY() + 1;
			if (y + 1 >= this.yCnt || this.grid[x][y]) {
				if (!this.isKey)
					gameEnd(); // 게임끝
				setCopyBlock(); // 백그라운드블럭 셋팅
				checkLine(); // 줄없애기 체크
				setNewItem(); // 다음아이템 셋팅
				return false;
			}
		}
		this.currentItem.moveDown();
		return true;
	}

	// 아이템오른쪽이동체크 -> 이동
	public void goRight() {
		final Block[] tempBlock = this.currentItem.getBlock();
		for (int i = 0; i < tempBlock.length; i++) {
			final int x = tempBlock[i].getX() + 1;
			final int y = tempBlock[i].getY();
			if (x >= this.xCnt || this.grid[x][y])
				return;
		}
		this.currentItem.moveRight();
	}

	// 아이템왼쪽이동체크 -> 이동
	public void goLeft() {
		final Block[] tempBlock = this.currentItem.getBlock();
		for (int i = 0; i < tempBlock.length; i++) {
			final int x = tempBlock[i].getX() - 1;
			final int y = tempBlock[i].getY();
			if (x < 0 || this.grid[x][y])
				return;
		}
		this.currentItem.moveLeft();
	}

	// 벽돌없애기 체크 -> 없애기
	public void keyPressed(final KeyEvent e) {
		if (!this.isKey)
			return;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			goDown();
			break;
		case KeyEvent.VK_LEFT:
			goLeft();
			break;
		case KeyEvent.VK_RIGHT:
			goRight();
			break;
		case KeyEvent.VK_UP:
			goRotate();
			break;
		case KeyEvent.VK_SPACE:
			while (goDown()) {
			}
			break;
		}
	}

	public void keyReleased(final KeyEvent e) {}
	public void keyTyped(final KeyEvent e) {}

	// 게임종료체크
	public void gameEnd() {
		JOptionPane.showMessageDialog(null, "게임이 종료되었습니다.", "게임종료", JOptionPane.ERROR_MESSAGE);
		t.stop();
	}

	// 쓰레드메인
	public void run() {
		try {
			while (true) {
				Thread.sleep(this.time);
				// 판넬위쪽이면 키리스너 동작X
				if (this.currentItem.getCurrentXY().getY() < 3)
					this.isKey = false;
				else
					this.isKey = true;
				goDown(); // 아이템밑으로이동

			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

//////////////////
	public static void main(final String[] args) {
		new MyTetris5("테트리스 Beta Ver 0.5");
	}
}

/* Class  
 * - Block : 블록의 X좌표 Y좌표의 정보를 가지고 있는 블록 클래스. 
 * - Item : 블록을 가지고 테트리스  아이템(모양) 만든다(부모클래스) 
 * - Rect, OneThree, ThreeOne ... : Item 클래스를 상속  각블록 위치정보 셋팅
 */

//블록 클래스
class Block {
	private int x;
	private int y;

	public Block() {}

	public Block(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	// 해당 포인트만큼 감산
	public void move(final int xPlus, final int yPlus) {
		this.x += xPlus;
		this.y += yPlus;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Block getBlock() {
		return this;
	}

	public void setXY(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
}

//아이템 클래스
class Item {
	JPanel[] panel; // 판넬
	Block[] block; // 현재포인트
	Block[][] block_info; // 각 각도별 포인트정보

	// 앞의배열 0~3 각도 0-0도 1-90도 2-180도 3-270도
	// 뒤의 배열은 판넬 갯수

	Block currentXY;
	int cnt; // 총판넬개수
	int angle; // 총각도개수
	int current_angle; // 현재각도값
	int xCnt; // 가로값
	Color color; // 색
	int area; // 넓이

	public Item(final int area, final int angle, final int cnt, final int xCnt) {
		this.angle = angle;
		this.cnt = cnt;
		this.panel = new JPanel[cnt]; // 판넬개수 셋팅
		this.block = new Block[cnt]; // 포인트 셋팅
		this.block_info = new Block[angle][cnt]; // 포인트 각도, 개수셋팅
		this.area = area;
		this.currentXY = new Block(0, 0); // 현재값
		this.xCnt = xCnt;
		for (int i = 0; i < cnt; i++) // 패널생성
			this.panel[i] = new JPanel();
	}

	public void setDefaultRandom() {
		this.current_angle = (int) (Math.random() * this.angle);
		this.block = this.block_info[this.current_angle];
	}

	// 컨테이너에 등록
	public void setItem(final Container c) {
		for (int i = 0; i < panel.length; i++) {
			panel[i].setBackground(this.color); // 배경색
			panel[i].setSize(area, area); // 넓이
			panel[i].setLocation(((block[i].getX()) * area) - 100, ((block[i].getY()) * area) - 100); // 기본위치 안보이는곳에 생성
			panel[i].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
			c.add(panel[i]); // 컨테이너에 등록
		}
	}

	// 다음위치조정
	public void setNextLocation() {
		for (int i = 0; i < panel.length; i++) {
			final int x = block[i].getX() + (xCnt - 3);
			final int y = block[i].getY() + 1;
			panel[i].setLocation(x * area, y * area);
		}
		this.currentXY.setXY((xCnt - 3), 1);
	}

	// 시작위치조정
	public void setDefaultLocation() {
		for (int i = 0; i < panel.length; i++) {
			final int x = block[i].getX() + (int) (xCnt / 2 - 2);
			final int y = block[i].getY() + 2;
			panel[i].setLocation(x * area, y * area);
		}
		this.currentXY.setXY((int) (xCnt / 2 - 2), 2);
	}

	// 대기상태 위치조정
	public void setReadyLocation() {
		for (int i = 0; i < panel.length; i++) {
			panel[i].setLocation(((block[i].getX()) * area) - 100, ((block[i].getY()) * area) - 100);
		}
	}

	// 현재위치조정
	public void setCurrentXY(final int x, final int y) {
		this.currentXY.move(x, y);
	}

	// 현재위치반환
	public Block getCurrentXY() {
		return this.currentXY;
	}

	// 현재 포인트 리턴
	public Block[] getBlock() {
		final Block[] temp = new Block[cnt];
		for (int i = 0; i < block.length; i++) {
			final int x = block[i].getX() + this.currentXY.getX();
			final int y = block[i].getY() + this.currentXY.getY();
			temp[i] = new Block(x, y);
		}
		return temp;
	}

	// 다음 움직일 각도의 포인트정보 반환
	public Block[] getNextBlock() {
		int nextAngle;
		if (this.angle == 1)
			return getBlock(); // 각도가1개뿐이면 리턴
		else if (this.angle - 1 == this.current_angle)
			nextAngle = 0; // 마지막앵글이면 1번앵글로
		else
			nextAngle = this.current_angle + 1; // 다음각도 셋팅

		final Block[] temp = new Block[cnt];
		for (int i = 0; i < block.length; i++) {
			final int x = block_info[nextAngle][i].getX() + this.currentXY.getX();
			final int y = block_info[nextAngle][i].getY() + this.currentXY.getY();
			temp[i] = new Block(x, y);
		}
		return temp;
	}

	// 현재앵글리턴
	public int getCurrentAngle() {
		return this.current_angle;
	}

	// Rotate
	public void moveRotate() {
		if (this.angle == 1)
			return; // 각도가1개뿐이면 리턴
		if (this.current_angle + 1 == this.angle){ // 최고각도면 처음각도로
			this.block = this.block_info[0];
			this.current_angle = 0;
		} else {
			this.current_angle++;
			this.block = this.block_info[this.current_angle];
		}
		this.setMove();
	}

	// 현재의 포인트 정보를 판넬에 적용하여 움직여라
	public void setMove() {
		for (int i = 0; i < panel.length; i++) {
			// 현재블록의 x,y값에 현재x,y포인트값을 더한값을 각area값과 곱한다.
			final int x = this.block[i].getX() + this.currentXY.getX();
			final int y = this.block[i].getY() + this.currentXY.getY();
			panel[i].setLocation(x * area, y * area);
		}
	}

	// 아래로 한칸 움직임
	public void moveDown() {
		this.currentXY.move(0, 1);
		this.setMove();
	}

	// 오른쪽으로 한칸 움직임
	public void moveRight() {
		this.currentXY.move(1, 0);
		this.setMove();
	}

	// 왼쪽으로 한칸 움직임
	public void moveLeft() {
		this.currentXY.move(-1, 0);
		this.setMove();
	}

	// 현재 색 리턴
	public Color getColor() {
		return this.color;
	}

	// 현재 색 셋팅
	public void setColor(final Color c) {
		this.color = c;
		for (int i = 0; i < panel.length; i++)
			panel[i].setBackground(this.color);

	}
}

//ㅁ
class Rect extends Item {
	
	public Rect(final int area, final Container con, final int xCnt) {
		super(area, 1, 4, xCnt); // 영역길이, 각도갯수, 판넬개수
		block_info[0][0] = new Block(0, 0);
		block_info[0][1] = new Block(0, 1);
		block_info[0][2] = new Block(1, 0);
		block_info[0][3] = new Block(1, 1);
		this.setDefaultRandom(); // 랜덤셋팅
		this.setItem(con); // 컨테이너에 등록
	}
}

//ㄱ
class OneThree extends Item {
	
	public OneThree(final int area, final Container con, final int xCnt) {
		super(area, 4, 4, xCnt); // 영역길이, 각도갯수, 판넬개수
		block_info[0][0] = new Block(0, 0);
		block_info[0][1] = new Block(0, 1);
		block_info[0][2] = new Block(1, 1);
		block_info[0][3] = new Block(2, 1);
		block_info[1][0] = new Block(0, 2);
		block_info[1][1] = new Block(1, 2);
		block_info[1][2] = new Block(1, 1);
		block_info[1][3] = new Block(1, 0);
		block_info[2][0] = new Block(2, 1);
		block_info[2][1] = new Block(2, 0);
		block_info[2][2] = new Block(1, 0);
		block_info[2][3] = new Block(0, 0);
		block_info[3][0] = new Block(1, 0);
		block_info[3][1] = new Block(0, 0);
		block_info[3][2] = new Block(0, 1);
		block_info[3][3] = new Block(0, 2);
		this.setDefaultRandom(); // 랜덤셋팅
		this.setItem(con); // 컨테이너에 등록
	}
}

//ㄱ반대
class ThreeOne extends Item {

	public ThreeOne(final int area, final Container con, final int xCnt) {
		super(area, 4, 4, xCnt); // 영역길이, 각도갯수, 판넬개수
		block_info[0][0] = new Block(0, 1);
		block_info[0][1] = new Block(0, 0);
		block_info[0][2] = new Block(1, 0);
		block_info[0][3] = new Block(2, 0);
		block_info[1][0] = new Block(1, 2);
		block_info[1][1] = new Block(0, 2);
		block_info[1][2] = new Block(0, 1);
		block_info[1][3] = new Block(0, 0);
		block_info[2][0] = new Block(2, 0);
		block_info[2][1] = new Block(2, 1);
		block_info[2][2] = new Block(1, 1);
		block_info[2][3] = new Block(0, 1);
		block_info[3][0] = new Block(0, 0);
		block_info[3][1] = new Block(1, 0);
		block_info[3][2] = new Block(1, 1);
		block_info[3][3] = new Block(1, 2);
		this.setDefaultRandom(); // 랜덤셋팅
		this.setItem(con); // 컨테이너에 등록
	}
}

//ㅣ
class LineBlock extends Item{
	
	public LineBlock(final int area, final Container con, final int xCnt)	{
		super(area, 2, 4, xCnt); // 영역길이, 각도갯수, 판넬개수
		block_info[0][0] = new Block(0, -1);
		block_info[0][1] = new Block(0, 0);
		block_info[0][2] = new Block(0, 1);
		block_info[0][3] = new Block(0, 2);
		block_info[1][0] = new Block(-1, 0);
		block_info[1][1] = new Block(0, 0);
		block_info[1][2] = new Block(1, 0);
		block_info[1][3] = new Block(2, 0);
		this.setDefaultRandom(); // 랜덤셋팅
		this.setItem(con); // 컨테이너에 등록
	}
}

//┷ 
class Triangle extends Item{

	public Triangle(final int area, final Container con, final int xCnt){
		super(area, 4, 4, xCnt); // 영역길이, 각도갯수, 판넬개수
		block_info[0][0] = new Block(1, 0);
		block_info[0][1] = new Block(0, 1);
		block_info[0][2] = new Block(1, 1);
		block_info[0][3] = new Block(2, 1);
		block_info[1][0] = new Block(0, 0);
		block_info[1][1] = new Block(0, 1);
		block_info[1][2] = new Block(0, 2);
		block_info[1][3] = new Block(1, 1);
		block_info[2][0] = new Block(0, 0);
		block_info[2][1] = new Block(1, 0);
		block_info[2][2] = new Block(2, 0);
		block_info[2][3] = new Block(1, 1);
		block_info[3][0] = new Block(0, 1);
		block_info[3][1] = new Block(1, 0);
		block_info[3][2] = new Block(1, 1);
		block_info[3][3] = new Block(1, 2);
		this.setDefaultRandom(); // 랜덤셋팅
		this.setItem(con); // 컨테이너에 등록
	}
}

//_|- 
class RightBlock extends Item{

	public RightBlock(final int area, final Container con, final int xCnt)	{
		super(area, 2, 4, xCnt); // 영역길이, 각도갯수, 판넬개수
		block_info[0][0] = new Block(0, 0);
		block_info[0][1] = new Block(0, 1);
		block_info[0][2] = new Block(1, 1);
		block_info[0][3] = new Block(1, 2);
		block_info[1][0] = new Block(1, 0);
		block_info[1][1] = new Block(0, 0);
		block_info[1][2] = new Block(0, 1);
		block_info[1][3] = new Block(-1, 1);
		this.setDefaultRandom(); // 랜덤셋팅
		this.setItem(con); // 컨테이너에 등록
	}
}

//-|_ 
class LeftBlock extends Item{

	public LeftBlock(final int area, final Container con, final int xCnt)	{
		super(area, 2, 4, xCnt); // 영역길이, 각도갯수, 판넬개수
		block_info[0][0] = new Block(0, 0);
		block_info[0][1] = new Block(1, 0);
		block_info[0][2] = new Block(1, 1);
		block_info[0][3] = new Block(2, 1);
		block_info[1][0] = new Block(0, 1);
		block_info[1][1] = new Block(0, 0);
		block_info[1][2] = new Block(1, 0);
		block_info[1][3] = new Block(1, -1);
		this.setDefaultRandom(); // 랜덤셋팅
		this.setItem(con); // 컨테이너에 등록
	}
}
