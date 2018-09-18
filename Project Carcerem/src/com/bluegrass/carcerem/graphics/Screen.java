package com.bluegrass.carcerem.graphics;

import java.util.Random;

import com.bluegrass.carcerem.Game;
import com.bluegrass.carcerem.level.Level;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	
	public int[] tiles = new int[64*64];
	
	public Sprite[] tileSprites = {
			Sprite.grass,
			Sprite.wood,
			Sprite.water
	};
	
	private Random random = new Random();
	
	public Level level = new Level(64, 64);
	
	private Game game;
	
	public double xOffset, yOffset;
	public int mouseTileX, mouseTileY;
	
	public int dragX, dragY;
	public boolean isDragging = false;
	
	public Screen(int width, int height, Game game) {
		this.width = width;
		this.height = height;
		this.game = game;
		
		pixels = new int[width * height];
		
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = random.nextInt(tileSprites.length);
		}
	}
	
	public void setPixel(int x, int y, int color) {
		setPixel(x + y * width, color);
	}
	
	public void setPixel(int index, int color) {
		int a1 = ((color >> 24) & 0xFF);
		if(a1 == 0)
			return;
		if(a1 == 0xFF) {
			pixels[index] = color;
			return;
		}
		int r1 = (color & 0xFF0000) >> 16;
		int g1 = (color & 0xFF00) >> 8;
		int b1 = (color & 0xFF);
		int color2 = pixels[index];
		int a2 = ((color2 << 24) & 0xFF);
		int r2 = (color2 & 0xFF0000) >> 16;
		int g2 = (color2 & 0xFF00) >> 8;
		int b2 = (color2 & 0xFF);
		double ratio = ((float)a1 / 255.0);
		double iratio = ((float)(255-a1)/255.0);
		int a3 = (int)((a1 * ratio) + (a2 * iratio));
		int r3 = (int)((r1 * ratio) + (r2 * iratio));
		int g3 = (int)((g1 * ratio) + (g2 * iratio));
		int b3 = (int)((b1 * ratio) + (b2 * iratio));
		int result = (a3 << 24) |
					(r3 << 16) |
					(g3 << 8) |
					(b3);
		pixels[index] = result;
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			setPixel(i, 0xff000000);
		}
	}

	public void render() {
		level.render(this);
		
		switch(game.mode) {
		case SELECT:
			break;
		case SINGLE:
			renderSprite((mouseTileX*16), (mouseTileY * 16), Sprite.selectSprite, true);
			break;
		case LINE:
			if(isDragging) {
				if(Math.abs(dragY - mouseTileY) > Math.abs(dragX - mouseTileX)) {
					for(int y = 0; y <= Math.abs(dragY - mouseTileY); y++) {
						int tx = dragX, ty = dragY;
						if(dragY < mouseTileY)
							ty = dragY + y;
						else if(dragY > mouseTileY)
							ty = dragY - y;
						renderSprite((tx*16), (ty * 16), Sprite.selectSprite, true);
						
					}
				}else {
					for(int x = 0; x <= Math.abs(dragX-mouseTileX); x++) {
						int tx = dragX, ty = dragY;
						if(dragX < mouseTileX)
							tx = dragX + x;
						else if(dragX > mouseTileX)
							tx = dragX - x;
						renderSprite((tx*16), (ty * 16), Sprite.selectSprite, true);
					}
				}
			}else {
				renderSprite((mouseTileX*16), (mouseTileY * 16), Sprite.selectSprite, true);
			}
			break;
		case FILL:
			if(isDragging) {
				for(int x = 0; x <= Math.abs(dragX-mouseTileX); x++) {
					for(int y = 0; y <= Math.abs(dragY - mouseTileY); y++) {
						int tx = dragX, ty = dragY;
						if(dragX < mouseTileX)
							tx = dragX + x;
						else if(dragX > mouseTileX)
							tx = dragX - x;
						if(dragY < mouseTileY)
							ty = dragY + y;
						else if(dragY > mouseTileY)
							ty = dragY - y;
						renderSprite((tx*16), (ty * 16), Sprite.selectSprite, true);
						
					}
				}
			}else {
				renderSprite((mouseTileX*16), (mouseTileY * 16), Sprite.selectSprite, true);
			}
			break;
		}
	}
	
	public void renderSprite(int x, int y, Sprite sprite, boolean fixed) {
		if(fixed) {
			x -= (int)xOffset;
			y -= (int)yOffset;
		}
		if(x < -sprite.SIZE || x >= width || y < -sprite.SIZE || y >= height)
			return;
		
		for(int sx = 0; sx < 16; sx++) {
			for(int sy =0; sy < 16; sy++) {
				int screenX = ((x) )+sx;
				int screenY = ((y) )+sy;
				if(screenX < 0 || screenX >= width || screenY < 0 || screenY >= height)
					continue;
				int c = sprite.pixels[sx + sy * 16];
				setPixel(screenX, screenY, c);
			}
		}
	}
	
}
