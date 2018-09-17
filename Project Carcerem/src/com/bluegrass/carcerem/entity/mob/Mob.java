package com.bluegrass.carcerem.entity.mob;

import com.bluegrass.carcerem.entity.Entity;
import com.bluegrass.carcerem.graphics.Sprite;
import com.bluegrass.carcerem.level.Level;

public abstract class Mob extends Entity {

	public Mob(Level level, Sprite sprite) {
		super(level);
		this.sprite = sprite;
	}

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	
	public void move() {
	}
	
	public void update(double deltaTime) {
	}
	
	private boolean collision() {
		return false;
	}
	
}
