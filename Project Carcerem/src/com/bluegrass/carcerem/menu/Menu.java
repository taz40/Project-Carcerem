package com.bluegrass.carcerem.menu;

import java.awt.Graphics;

import com.bluegrass.carcerem.Game;

public class Menu {
	
	public void update(double deltaTime) {
		if(Game.uiMouse.getButton(1)) {
			System.out.println("ui click");
		}
	}
	
	public void render(Graphics g) {
		g.fillRect(Game.width-Game.uiWidth, 0, Game.uiWidth, Game.height);
	}

}
