package Java_2019_2_task;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class R21030 extends JFrame{
	R21030(){
		setTitle("떨어지는 사각형 움직이기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MyPanel my = new MyPanel();
		setContentPane(my);
		
		setSize(500,500);
		setVisible(true);
		getContentPane().requestFocus();
	}
	class MyPanel extends JPanel{
		Point p = new Point(120, 10); 
		MyPanel(){
			this.setBackground(Color.BLACK);
			MyThread my2 = new MyThread();
			
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar() == 'a') {
						p.x = p.x - 50;
						repaint();
					} else if(e.getKeyChar() == 'd') {
						p.x = p.x + 50;
						repaint();
					} 
				}
			});
			my2.start();
		}	
		class MyThread extends Thread{   
			public void run() {
				for(int i=0; i<10; i++) {	
					p.y = p.y+50;
					try { Thread.sleep(1000); } 
					catch(Exception e) {return;}
					repaint();
				}
			}
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.YELLOW);
			g.fillRoundRect(p.x, p.y, 50, 50, 10, 10);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new R21030();
	}

}
