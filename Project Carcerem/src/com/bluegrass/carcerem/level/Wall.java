package com.bluegrass.carcerem.level;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;
import com.bluegrass.carcerem.level.Level;
import com.bluegrass.carcerem.level.TileObject;

public class Wall extends TileObject {

	public Wall() {
		super(Sprite.walls.get("wall_"), true);
	}
	
	

	@Override
	public void render(int x, int y, Screen screen) {
		int tileX = x / 16;
		int tileY = y / 16;
		String prefix = "";
		Tile tile = screen.level.getTile(tileX, tileY-1);
		if(tile != null && tile.object != null && tile.object instanceof Wall)
			prefix += "N";
		tile = screen.level.getTile(tileX+1, tileY);
		if(tile != null && tile.object != null && tile.object instanceof Wall)
			prefix += "E";
		tile = screen.level.getTile(tileX, tileY+1);
		if(tile != null && tile.object != null && tile.object instanceof Wall)
			prefix += "S";
		tile = screen.level.getTile(tileX-1, tileY);
		if(tile != null && tile.object != null && tile.object instanceof Wall)
			prefix += "W";
		screen.renderSprite(x, y, Sprite.walls.get("wall_"+prefix), true);
	}
	
}
