package com.bluegrass.carcerem.level;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;

public class TileObject {
	
	public boolean isSolid = false;
	public Sprite sprite;
	
	public TileObject(Sprite sprite) {
		this(sprite, false);
	}
	
	public TileObject(Sprite sprite, boolean isSolid) {
		this.sprite = sprite;
		this.isSolid = isSolid;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderSprite(x, y, sprite, true);
	}
	
	
	public boolean isSolid() {
		return isSolid;
	}

}
