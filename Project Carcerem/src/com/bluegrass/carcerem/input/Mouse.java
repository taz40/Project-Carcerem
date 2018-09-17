package com.bluegrass.carcerem.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseMotionListener, MouseListener{
	
	public enum MouseMode{
		SELECT,
		SINGLE,
		LINE,
		FILL
	}

	
	private boolean buttons[] = new boolean[5];
	private int x, y;

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
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
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
}
