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
		x += 32.0 * deltaTime;
	}
	
	public void render(Screen screen) {
		int screenX = (int)(x - screen.xOffset);
		int screenY = (int)(y - screen.yOffset);
		if(screenX < -16 || screenX >= screen.width || screenY < -16 || screenY >= screen.height)
			return;
		screen.renderSprite(screenX, screenY, sprite);
	}

}
