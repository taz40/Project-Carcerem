package com.bluegrass.carcerem.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private static boolean[] keys = new boolean[256];

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static boolean getKey(int keyCode) {
		boolean temp = keys[keyCode];
		keys[keyCode] = false;
		return temp;
	}
	
	public static boolean getKeyDown(int keyCode) {
		return keys[keyCode];
	}

}
