package etudiant;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class TableauBlancStudent extends JFrame implements MouseMotionListener ,MouseListener{
	 private int xmousePressed =0, ymousePressed;
	   private int xmouseReleased,ymouseReleased;
//	   private Client c;
	   public TableauBlancStudent(String username)
	   {
		   super();
		   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   this.setTitle("Tbleau Blanc ( Student : "+username+")");
	    	this.setSize(500, 500);
	    	this.setLayout(null);
	    	this.setVisible(true);
	    	this.addMouseListener(this);
	    	this.addMouseMotionListener(this);
	   }
	   public void setArg(int xmousePressed,int ymousePressed,int xmouseReleased,int ymouseReleased) {
			  this.xmousePressed=xmousePressed;
			   this.xmouseReleased=xmouseReleased;
			   this.ymousePressed=ymousePressed;
			   this.ymouseReleased=ymouseReleased;
			   repaint ();
		  }
	   public void paint(Graphics g) {
			   if(xmousePressed != 0)
		      g.drawLine(xmousePressed, ymousePressed, xmouseReleased, ymouseReleased);
		   }
	   
	   
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
