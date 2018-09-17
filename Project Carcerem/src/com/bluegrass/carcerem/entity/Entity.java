package com.bluegrass.carcerem.entity;

import java.util.Random;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.level.Level;

public abstract class Entity {
	
	public double x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public Entity(Level level) {
		this.level = level;
	}
	
	public void update(double deltaTime) {
	}
	
	public void render(Screen screen) {
	}
	
	public void remove() {
		//TODO: remove from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}

}
