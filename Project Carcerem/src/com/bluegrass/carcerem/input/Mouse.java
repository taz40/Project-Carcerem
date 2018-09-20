package com.bluegrass.carcerem.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseMotionListener, MouseListener, MouseWheelListener{

	
	private static boolean buttons[] = new boolean[5];
	private static int x, y;
	private static int wheelRotation;

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
	
	public static boolean getButton(int button) {
		boolean temp = buttons[button];
		buttons[button] = false;
		return temp;
	}
	
	public static boolean getButtonDown(int button) {
		return buttons[button];
	}
	
	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}

	public static int getLastWheelRotation() {
		int temp = wheelRotation;
		wheelRotation = 0;
		return temp;
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheelRotation = e.getWheelRotation();
	}
}
