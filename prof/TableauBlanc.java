package prof;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;



public class TableauBlanc extends JFrame implements MouseMotionListener ,MouseListener{
	   private int xmousePressed, ymousePressed;
	   private int xmouseReleased,ymouseReleased;
	   private Prof prof;
	   private ArrayList<String> listStudents;
//	   private MeetStudentView meet;
	   
	   public static void main(String[] args) {
//		   MeetView c= new MeetView();
		  
	   }
	   public TableauBlanc (Prof prof,ArrayList<String> listStudents){
		   super();
		   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   this.setTitle("Tbleau Blanc ( Professor : "+prof.getUsername()+")");
	    	this.setSize(500, 500);
	    	this.setLayout(null);
	    	this.setVisible(true);
	    	this.addMouseListener(this);
	    	this.addMouseMotionListener(this);
	    	this.prof=prof;
	    	this.listStudents=listStudents;
	    	try {
				prof.openTableaublanc(listStudents);
			} catch (Exception e) {
				e.printStackTrace();
			}
//	    	 meet = new MeetStudentView();
	   }
	   
	   public void paint(Graphics g) {
//		      super.paint(g);
//			   g.fillOval((int)x, (int)y, 10, 10);
//		      g.drawString("x = "+x+" ; y = "+y,20,20);
		      g.drawLine(xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);
		      try {
				prof.tableaublanc(listStudents,xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);
//				meet.setArg(xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);
			} catch (Exception e) {
				e.printStackTrace();
			}
//		      m.setArg(xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);
		      xmousePressed=xmouseReleased;
		      ymousePressed=ymouseReleased;
	   }
	   
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		xmousePressed = e.getX();
	      ymousePressed = e.getY();
//	      repaint();
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		xmouseReleased = e.getX();
	      ymouseReleased = e.getY();
	      repaint();
	      //test
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
