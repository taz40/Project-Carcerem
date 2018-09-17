package com.bluegrass.carcerem.level;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;

public class Tile {
	
	public static final Tile grass = new Tile(Sprite.grass);
	public static final Tile wood = new Tile(Sprite.wood);
	public static final Tile water = new Tile(Sprite.water);
	
	
	public Sprite sprite;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderSprite(x, y, sprite);
	}

}
