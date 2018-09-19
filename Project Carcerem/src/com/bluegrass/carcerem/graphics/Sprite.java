package com.bluegrass.carcerem.graphics;

import java.util.HashMap;
import java.util.Map;

public class Sprite {
	
	public static Sprite grass = new Sprite(16, 5, 0, SpriteSheet.sheet);
	public static Sprite dirt = new Sprite(16, 6, 0, SpriteSheet.sheet);
	public static Sprite wood = new Sprite(16, 1, 0, SpriteSheet.sheet);
	public static Sprite water = new Sprite(16, 2, 0, SpriteSheet.sheet);
	public static Sprite selectSprite = new Sprite(16, 3, 0, SpriteSheet.sheet);
	public static Sprite constructionWorker = new Sprite(16, 4, 0, SpriteSheet.sheet);
	
	public static Map<String, Sprite> walls = new HashMap<String, Sprite>();
	
	static {
		walls.put("wall_", new Sprite(16, 1, 1, SpriteSheet.wallsSheet));
		walls.put("wall_S", new Sprite(16, 0, 5, SpriteSheet.wallsSheet));
		walls.put("wall_N", new Sprite(16, 1, 5, SpriteSheet.wallsSheet));
		walls.put("wall_E", new Sprite(16, 2, 5, SpriteSheet.wallsSheet));
		walls.put("wall_W", new Sprite(16, 3, 5, SpriteSheet.wallsSheet));
		walls.put("wall_ES", new Sprite(16, 0, 0, SpriteSheet.wallsSheet));
		walls.put("wall_EW", new Sprite(16, 1, 0, SpriteSheet.wallsSheet));
		walls.put("wall_SW", new Sprite(16, 2, 0, SpriteSheet.wallsSheet));
		walls.put("wall_NS", new Sprite(16, 0, 1, SpriteSheet.wallsSheet));
		walls.put("wall_NE", new Sprite(16, 0, 2, SpriteSheet.wallsSheet));
		walls.put("wall_NW", new Sprite(16, 2, 2, SpriteSheet.wallsSheet));
		walls.put("wall_ESW", new Sprite(16, 4, 0, SpriteSheet.wallsSheet));
		walls.put("wall_NSW", new Sprite(16, 5, 1, SpriteSheet.wallsSheet));
		walls.put("wall_NEW", new Sprite(16, 4, 2, SpriteSheet.wallsSheet));
		walls.put("wall_NES", new Sprite(16, 3, 1, SpriteSheet.wallsSheet));
		walls.put("wall_NESW", new Sprite(16, 4, 1, SpriteSheet.wallsSheet));
		
		
		walls.put("brickwall_", new Sprite(16, 1, 1, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_S", new Sprite(16, 0, 5, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_N", new Sprite(16, 1, 5, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_E", new Sprite(16, 2, 5, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_W", new Sprite(16, 3, 5, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_ES", new Sprite(16, 0, 0, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_EW", new Sprite(16, 1, 0, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_SW", new Sprite(16, 2, 0, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_NS", new Sprite(16, 0, 1, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_NE", new Sprite(16, 0, 2, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_NW", new Sprite(16, 2, 2, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_ESW", new Sprite(16, 4, 0, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_NSW", new Sprite(16, 5, 1, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_NEW", new Sprite(16, 4, 2, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_NES", new Sprite(16, 3, 1, SpriteSheet.wallsSheetBrick));
		walls.put("brickwall_NESW", new Sprite(16, 4, 1, SpriteSheet.wallsSheetBrick));
	}
	
	public final int SIZE;
	private int x, y;
	public int [] pixels;
	private SpriteSheet sheet;
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	public void load() {
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				pixels[x + y * SIZE] = sheet.pixels[(x+this.x) + (y+this.y) * sheet.SIZE];
			}
		}
	}
	
}
