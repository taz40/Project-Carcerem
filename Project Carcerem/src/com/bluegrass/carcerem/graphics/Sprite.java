package com.bluegrass.carcerem.graphics;

public class Sprite {
	
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.sheet);
	public static Sprite wood = new Sprite(16, 1, 0, SpriteSheet.sheet);
	public static Sprite water = new Sprite(16, 2, 0, SpriteSheet.sheet);
	public static Sprite selectSprite = new Sprite(16, 3, 0, SpriteSheet.sheet);
	
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
