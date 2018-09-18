package com.bluegrass.carcerem.entity.mob;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;
import com.bluegrass.carcerem.level.Level;

public class ConstructionWorker extends Mob {
	
	double speed = 32.0;

	public ConstructionWorker(Level level) {
		this(level, 0, 0);
	}
	
	public ConstructionWorker(Level level, int x, int y) {
		super(level, Sprite.constructionWorker);
		this.x = x;
		this.y = y;
	}
	
	public void update(double deltaTime) {
		double deltaX = 0;
		double deltaY = 0;
		if(this.x < level.xTarget) {
			deltaX = Math.min(speed * deltaTime, level.xTarget - x);
		}else if(this.x > level.xTarget) {
			deltaX = -Math.min(speed * deltaTime, x - level.xTarget);
		}
		
		if(this.y < level.yTarget) {
			deltaY = Math.min(speed * deltaTime, level.yTarget - y);
		}else if(this.y > level.yTarget) {
			deltaY = -Math.min(speed * deltaTime, y - level.yTarget);
		}
		
		move(deltaX, deltaY);
	}
	
	public void render(Screen screen) {
		int screenX = (int)(x);
		int screenY = (int)(y);
		screen.renderSprite(screenX-8, screenY-8, sprite, true);
	}

}
