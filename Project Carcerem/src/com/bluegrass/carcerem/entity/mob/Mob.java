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
	
	public void move(double xDist, double yDist) {
		int xpos = (int)(x + xDist);
		int ypos = (int)(y + xDist);
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < 2; y++) {
				if(level.tiles[((xpos+(16*x)) / 16) + ((ypos+(16*y)) / 16) * level.width].isSolid()) {
					return;
				}
			}
		}
		this.x += xDist;
		this.y += yDist;
	}
	
	public void update(double deltaTime) {
	}
	
	private boolean collision() {
		return false;
	}
	
}
