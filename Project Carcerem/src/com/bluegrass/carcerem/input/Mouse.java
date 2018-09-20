package com.bluegrass.carcerem.input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseMotionListener, MouseListener, MouseWheelListener{

	
	private boolean buttons[] = new boolean[5];
	private int x, y;
	private int wheelRotation;
	private Rectangle bounds;
	
	public Mouse(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!(e.getX() > bounds.x && e.getX() <= bounds.width+bounds.x && e.getY() > bounds.y && e.getY() <= bounds.height+bounds.y))
			return;
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(!(e.getX() > bounds.x && e.getX() <= bounds.width && e.getY() > bounds.y && e.getY() <= bounds.height)) {
			return;
		}
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!(e.getX() > bounds.x && e.getX() <= bounds.width && e.getY() > bounds.y && e.getY() <= bounds.height)) {
			return;
		}
		x = e.getX();
		y = e.getY();
	}
	
	public boolean getButton(int button) {
		boolean temp = buttons[button];
		buttons[button] = false;
		return temp;
	}
	
	public boolean getButtonDown(int button) {
		return buttons[button];
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getLastWheelRotation() {
		int temp = wheelRotation;
		wheelRotation = 0;
		return temp;
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(!(e.getX() > bounds.x && e.getX() <= bounds.width && e.getY() > bounds.y && e.getY() <= bounds.height)) {
			return;
		}
		wheelRotation = e.getWheelRotation();
	}
}
