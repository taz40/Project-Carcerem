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
		if(!collision(xDist, 0)) {
			this.x += xDist;
		}
		if(!collision(0, yDist)) {
			this.y += yDist;
		}
	}
	
	public void update(double deltaTime) {
	}
	
	private boolean collision(double Xa, double Ya) {
		int xpos = (int)(x + Xa);
		int ypos = (int)(y + Ya);
		for(int x = -1; x < 2; x += 2) {
			for(int y = -1; y < 2; y += 2) {
				int cornerTileX = (xpos+(8*x)) / 16;
				int cornerTileY = (ypos+(8*y)) / 16;
				if(level.getTile(cornerTileX, cornerTileY) != null && level.getTile(cornerTileX, cornerTileY).isSolid()) {
					return true;
				}
			}
		}
		return false;
	}
	
}
