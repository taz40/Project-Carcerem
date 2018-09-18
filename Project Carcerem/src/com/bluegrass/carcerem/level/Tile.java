package com.bluegrass.carcerem.level;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;

public class Tile {
	
	public static final Tile grass = new Tile(Sprite.grass);
	public static final Tile dirt = new Tile(Sprite.dirt);
	public static final Tile wood = new Tile(Sprite.wood, true);
	public static final Tile water = new Tile(Sprite.water);
	
	public boolean isSolid = false;
	
	public Sprite sprite;
	
	public Tile(Sprite sprite) {
		this(sprite, false);
	}
	
	public Tile(Sprite sprite, boolean isSolid) {
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
