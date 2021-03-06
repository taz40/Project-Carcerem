package com.bluegrass.carcerem.graphics;

import java.awt.image.BufferedImage;
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
	
	public static double zoom = 1.5;
	
	private Random random = new Random();
	
	public Level level = new Level(64, 64);
	
	private Game game;
	
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
	}
	
	public void renderSprite(int x, int y, Sprite sprite, boolean fixed) {
		if(fixed) {
			x -= (int)level.xOffset;
			y -= (int)level.yOffset;
		}
		if(x < -sprite.SIZE*zoom || x >= width/zoom || y < -sprite.SIZE*zoom || y >= height/zoom)
			return;
				
		for(int sx = 0; sx < 16; sx++) {
			for(int sy =0; sy < 16; sy++) {
				int screenX = ((x) )+sx;
				int screenY = ((y) )+sy;
				//if(screenX < 3 || screenX >= width || screenY < 3 || screenY >= height)
					//continue;
				int c = sprite.pixels[sx + sy * 16];
				for(int xp = 0; xp < zoom; xp++) {
					for(int yp = 0; yp < zoom; yp++) {
						if(screenX*zoom+xp < 0 || screenX*zoom+xp >= width || screenY*zoom+yp < 0 || screenY*zoom+yp >= height)
							continue;
						setPixel((int)Math.floor(screenX*zoom+xp), (int) Math.floor(screenY*zoom+yp), c);
					}
				}
			}
		}
	}
	
}
