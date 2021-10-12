package Java_2019_2_project;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Game extends JFrame{
	Statement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	//상단 이미지 라벨 생성
	JLabel gamestart = new JLabel(new ImageIcon("image/GameStart.png"));
	JLabel gameover = new JLabel(new ImageIcon("image/GameOver.png"));
	JLabel winner = new JLabel(new ImageIcon("image/Winner.png"));
	int num = 0; //DB 단어 번호
	String wd = null; //DB 랜덤 단어 저장
	char[] ch; //단어 스펠링씩
	JLabel wlabel; //랜덤 단어 출력
	char[] wrong = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; //키보드 비교
	JLabel cha; //떨어지는 이미지
	int cnt=0; //틀린 횟수
	JButton a;
	
	Container c = getContentPane(); 
	
	Game() { 
		super("game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.requestFocus();
		this.requestFocus();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			System.out.println("드라이버 적재 성공");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/data2?serverTimezone=UTC&useSSL=false", "root", "root"); 
			System.out.println("DB연결 성공");
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}
		
		GameStart gs = new GameStart();
		setContentPane(gs);
		gs.setLayout(null);
		gs.setBackground(Color.white);
		gs.requestFocus();
		
		try {
			rs = stmt.executeQuery("SELECT count(*) FROM word");
			while(rs.next()) {
				num = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}
		int random = (int)(Math.random()*num);
		
		try {
			rs2 = stmt.executeQuery("SELECT word FROM word WHERE num ='" + random + "'");
			while(rs2.next()) {
				wd = rs2.getString("word"); //랜덤으로 선택한 단어
			}
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}
		wlabel = new JLabel(wd);	
		wlabel.setBounds(350,300,100,50);
		//wlabel.setVisible(false); 원래는 안보이게
		wlabel.setFont(new Font("gothic", NORMAL, 30));
		gs.add(wlabel);
		
		ch = new char[wd.length()];
		for(int i=0;i<ch.length;i++) {
			ch[i] = wd.charAt(i);
		}
		
		if(cnt > 6) { //키 입력이 6번이 넘어갔을 때
			gameover.setBounds(10,10,640,100);
			this.add(gameover);
			wlabel.setVisible(false);
		}
		setSize(670,650);
		setVisible(true);
	}
	class GameStart extends JPanel{ //게임 화면 구성
		Point timer = new Point(0,120);

		GameStart(){
			setLayout(null);

			MyThread myThread = new MyThread();
			myThread.start();
			
			this.addKeyListener(new MyListener());
			this.requestFocus();
			
			//캐릭터 이미지 라벨 생성
			ImageIcon CH = new ImageIcon("image/char.png");
			cha = new JLabel(CH);
			
			//불 이미지 라벨 생성
			ImageIcon fi = new ImageIcon("image/fire.png");
			JLabel fire = new JLabel(fi);
			
			//알파벳 이미지 버튼 생성
			ImageIcon AA = new ImageIcon("image/a.jpg");
			JButton a = new JButton(AA);
			ImageIcon BB = new ImageIcon("image/B.jpg");
			JButton b = new JButton(BB);
			ImageIcon CC = new ImageIcon("image/C.jpg");
			JButton c1 = new JButton(CC);
			ImageIcon DD = new ImageIcon("image/D.jpg");
			JButton d = new JButton(DD);
			ImageIcon EE = new ImageIcon("image/E.jpg");
			JButton e = new JButton(EE);
			ImageIcon FF = new ImageIcon("image/F.jpg");
			JButton f = new JButton(FF);
			ImageIcon GG = new ImageIcon("image/G.jpg");
			JButton g = new JButton(GG);
			ImageIcon HH = new ImageIcon("image/H.jpg");
			JButton h = new JButton(HH);
			ImageIcon II = new ImageIcon("image/I.jpg");
			JButton i = new JButton(II);
			ImageIcon JJ = new ImageIcon("image/J.jpg");
			JButton j = new JButton(JJ);
			ImageIcon KK = new ImageIcon("image/K.jpg");
			JButton k = new JButton(KK);
			ImageIcon LL = new ImageIcon("image/L.jpg");
			JButton l = new JButton(LL);
			ImageIcon MM = new ImageIcon("image/M.jpg");
			JButton m = new JButton(MM);
			ImageIcon NN = new ImageIcon("image/N.jpg");
			JButton n = new JButton(NN);
			ImageIcon OO = new ImageIcon("image/O.jpg");
			JButton o = new JButton(OO);
			ImageIcon PP = new ImageIcon("image/P.jpg");
			JButton p = new JButton(PP);
			ImageIcon QQ = new ImageIcon("image/Q.jpg");
			JButton q = new JButton(QQ);
			ImageIcon RR = new ImageIcon("image/R.jpg");
			JButton r = new JButton(RR);
			ImageIcon SS = new ImageIcon("image/S.jpg");
			JButton s = new JButton(SS);
			ImageIcon TT = new ImageIcon("image/T.jpg");
			JButton t = new JButton(TT);
			ImageIcon UU = new ImageIcon("image/U.jpg");
			JButton u = new JButton(UU);
			ImageIcon VV = new ImageIcon("image/V.jpg");
			JButton v = new JButton(VV);
			ImageIcon WW = new ImageIcon("image/W.jpg");
			JButton w = new JButton(WW);
			ImageIcon XX = new ImageIcon("image/X.jpg");
			JButton x = new JButton(XX);
			ImageIcon YY = new ImageIcon("image/Y.jpg");
			JButton y = new JButton(YY);
			ImageIcon ZZ = new ImageIcon("image/Z.jpg");
			JButton z = new JButton(ZZ);
			
			
			//상단 이미지 라벨 부착
			gamestart.setBounds(10,10,640,100);
			this.add(gamestart);
			
			//캐릭터 이미지 라벨 부착
			cha.setBounds(80,145,210,210);
			this.add(cha);
			
			//불 이미지 라벨 부착
			fire.setBounds(0,265,350,300);
			this.add(fire);
			
			//알파벳 이미지 버튼 부착
			a.setBounds(10,500,40,40);
			this.add(a);

			b.setBounds(60,500,40,40);
			this.add(b);

			c1.setBounds(110,500,40,40);
			this.add(c1);

			d.setBounds(160,500,40,40);
			this.add(d);

			e.setBounds(210,500,40,40);
			this.add(e);

			f.setBounds(260,500,40,40);
			this.add(f);

			g.setBounds(310,500,40,40);
			this.add(g);

			h.setBounds(360,500,40,40);
			this.add(h);

			i.setBounds(410,500,40,40);
			this.add(i);

			j.setBounds(460,500,40,40);
			this.add(j);

			k.setBounds(510,500,40,40);
			this.add(k);

			l.setBounds(560,500,40,40);
			this.add(l);

			m.setBounds(610,500,40,40);
			this.add(m);

			n.setBounds(10,550,40,40);
			this.add(n);

			o.setBounds(60,550,40,40);
			this.add(o);

			p.setBounds(110,550,40,40);
			this.add(p);

			q.setBounds(160,550,40,40);
			this.add(q);

			r.setBounds(210,550,40,40);
			this.add(r);

			s.setBounds(260,550,40,40);
			this.add(s);

			t.setBounds(310,550,40,40);
			this.add(t);

			u.setBounds(360,550,40,40);
			this.add(u);

			v.setBounds(410,550,40,40);
			this.add(v);

			w.setBounds(460,550,40,40);
			this.add(w);

			x.setBounds(510,550,40,40);
			this.add(x);

			y.setBounds(560,550,40,40);
			this.add(y);

			z.setBounds(610,550,40,40);
			this.add(z);
		}
		class MyThread extends Thread{
			@Override
			public void run() {
				boolean fallen =false;
				while(true) {
				fallen = false;  
					if( fallen != true) {
							timer.x+=10;
					}else {  
						if(timer.x == 650) { //타이머가 650에 닿으면 게임오버로 변경
							gamestart.setIcon(new ImageIcon("image/GameOver.png"));
							return; 
						}
						fallen =false;
					}
					try { Thread.sleep(500); } catch(Exception e) {return;}
					repaint();
				}
			} 
		}
		//방법1. 버튼 클릭
		/*class MyActionListener implements ActionListener { //버튼 눌리는 경우
			public void actionPerformed(ActionEvent e) {
				JButton a = (JButton)e.getSource();
				a.setVisible(false); //버튼 숨기기
				if (맞으면){
					//_에 스펠링 추가
				}
				else {
					int x = cha.getX();
					int y = cha.getY();
					cha.setLocation(x,y+30);//이미지 아래로 이동
					cnt++; //틀린 횟수 증가
				}
			}
		}//class MyActionListener*/	
	
		//방법2. 키 입력
		class MyListener extends KeyAdapter{
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				for(int i=0;i<wrong.length;i++) {
					if(e.getKeyChar()==wrong[i]) {  //wrong[i] 중에 키를 입력하면
						for(int j=0; j<ch.length; i++) { 
							if(ch[j]==wrong[i]) { //입력한 wrong[i]와 단어 중 알파벳하나(ch[j])가 같으면
								//핑크박스 안 해당 위치에 알파벳 이미지 옮기기
								int x = cha.getX();
								int y = cha.getY();
								cha.setLocation(x,y+30);//이미지 아래로 이동
								cnt++;
							}
						}
					}
				}
			}
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); 
			g.setColor(Color.pink);
			g.fillRoundRect(timer.x, timer.y, 40, 20, 10, 5);
			for(int i=0; i<ch.length; i++) {
				g.setColor(Color.pink);
				g.fillRect(350+(i*60), 395, 50, 10);
			}
			for(int i=0; i<ch.length; i++) {
				g.setColor(Color.pink);
				g.drawRect(350+(i*60), 350, 50, 50);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Game();
	}

}
