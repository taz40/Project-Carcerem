package com.bluegrass.carcerem.entity.mob;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;
import com.bluegrass.carcerem.level.Level;

public class ConstructionWorker extends Mob {

	public ConstructionWorker(Level level) {
		this(level, 0, 0);
	}
	
	public ConstructionWorker(Level level, int x, int y) {
		super(level, Sprite.constructionWorker);
		this.x = x;
		this.y = y;
	}
	
	public void update(double deltaTime) {
		move(32.0 * deltaTime, 0);
	}
	
	public void render(Screen screen) {
		int screenX = (int)(x);
		int screenY = (int)(y);
		screen.renderSprite(screenX, screenY, sprite, true);
	}

}
