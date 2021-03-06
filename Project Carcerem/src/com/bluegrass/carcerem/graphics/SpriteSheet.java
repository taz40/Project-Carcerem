package com.bluegrass.carcerem.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public static SpriteSheet sheet = new SpriteSheet("/textures/spritesheet.png", 256);
	public static SpriteSheet wallsSheet = new SpriteSheet("/textures/walls.png", 96);
	public static SpriteSheet wallsSheetBrick = new SpriteSheet("/textures/wallsBrick.png", 96);
	
	private String path;
	public final int SIZE;
	public int[] pixels;
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = Math.min(image.getWidth(), SIZE);
			int h = Math.min(image.getHeight(), SIZE);
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
