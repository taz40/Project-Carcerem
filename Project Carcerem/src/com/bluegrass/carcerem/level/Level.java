package com.bluegrass.carcerem.level;

import java.util.ArrayList;
import java.util.Random;

import com.bluegrass.carcerem.entity.Entity;
import com.bluegrass.carcerem.entity.mob.ConstructionWorker;
import com.bluegrass.carcerem.graphics.Screen;

public class Level {
	
	public int width;
	public int height;
	public Tile[] tiles;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public final Tile[] tileMap = {
			Tile.grass,
			Tile.wood,
			Tile.water
	};
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width*height];
		Random rand = new Random();
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = tileMap[rand.nextInt(tileMap.length)];
		}
		entities.add(new ConstructionWorker(this, 0, 4 * 16));
	}
	
	public void render(Screen screen) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int tileIndex = x + y * width;
				int screenX =  ((x * 16) - (int)screen.xOffset);
				int screenY =  ((y * 16) - (int)screen.yOffset);
				if(screenX < -16 || screenX >= screen.width || screenY < -16 || screenY >= screen.height)
					continue;
				tiles[tileIndex].render(screenX, screenY, screen);
			}
		}
		
		for(Entity e : entities) {
			e.render(screen);
		}
	}
	
	public void update(double deltaTime) {
		for(Entity e : entities) {
			e.update(deltaTime);
		}
		
		
	}
	
	public void setTile(int x, int y, Tile tile) {
		if(x < 0 || x >= width || y < 0 || y >= height)
			return;
		tiles[x + y * width] = tile;
	}

}
