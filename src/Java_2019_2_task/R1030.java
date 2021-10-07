package Java_2019_2_task;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class R1030 extends JFrame{
	R1030(){
		setTitle("드래그하여 타원 그리기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MyPanel my = new MyPanel();
		setContentPane(my);
		
		setSize(500,500);
		setVisible(true);
		getContentPane().requestFocus();
			}
	class MyPanel extends JPanel{
		Point p1 = new Point(); 
		Point p2 = new Point();
		MyPanel(){
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
			
					p1.x = e.getX();
					p1.y = e.getY();
				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
			
					p2.x = e.getX();
					p2.y = e.getY();
					repaint();
				}
			});
			
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); 
			g.setColor(Color.BLACK);
			Point pp = new Point(p1.x<p2.x?p1.x:p2.x, p1.y<p2.y?p1.y:p2.y);
			
			int width= Math.abs(p1.x - p2.x);	
			int height= Math.abs(p1.y - p2.y);
			g.fillOval(pp.x,pp.y, width, height);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new R1030();
	}

}
