package com.bluegrass.carcerem.entity;

import java.awt.event.KeyEvent;

import com.bluegrass.carcerem.Game;
import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;
import com.bluegrass.carcerem.input.Keyboard;
import com.bluegrass.carcerem.input.Mouse;
import com.bluegrass.carcerem.level.Level;
import com.bluegrass.carcerem.level.Tile;
import com.bluegrass.carcerem.level.Wall;

public class UserInterface extends Entity {
	
	
	public enum MouseMode{
		SELECT,
		SINGLE,
		LINE,
		FILL
	}
	
	private int mouseTileX, mouseTileY;
	
	private boolean isDragging;
	private int dragX, dragY;
	
	//The current mouse mode
	public MouseMode mode = MouseMode.SELECT;

	public UserInterface(Level level) {
		super(level);
	}
	
	public void render(Screen screen) {
		switch(mode) {
		case SELECT:
			break;
		case SINGLE:
			screen.renderSprite((mouseTileX*16), (mouseTileY * 16), Sprite.selectSprite, true);
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
						screen.renderSprite((tx*16), (ty * 16), Sprite.selectSprite, true);
						
					}
				}else {
					for(int x = 0; x <= Math.abs(dragX-mouseTileX); x++) {
						int tx = dragX, ty = dragY;
						if(dragX < mouseTileX)
							tx = dragX + x;
						else if(dragX > mouseTileX)
							tx = dragX - x;
						screen.renderSprite((tx*16), (ty * 16), Sprite.selectSprite, true);
					}
				}
			}else {
				screen.renderSprite((mouseTileX*16), (mouseTileY * 16), Sprite.selectSprite, true);
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
						screen.renderSprite((tx*16), (ty * 16), Sprite.selectSprite, true);
						
					}
				}
			}else {
				screen.renderSprite((mouseTileX*16), (mouseTileY * 16), Sprite.selectSprite, true);
			}
			break;
		}
	}
	
	public void update(double deltaTime) {
		//find the tile the mouse is hovering over
		mouseTileX = (int) Math.floor(((Game.mouse.getX() / Game.scale/Screen.zoom) + level.xOffset) / 16);
		mouseTileY = (int) Math.floor(((Game.mouse.getY() / Game.scale/Screen.zoom) + level.yOffset) / 16);
		
		//switch between modes if the ESC key is pressed
		if(Keyboard.getKey(KeyEvent.VK_ESCAPE)) {
			switch(mode) {
			case SELECT:
				mode = MouseMode.SINGLE;
				break;
			case SINGLE:
				mode = MouseMode.LINE;
				break;
			case LINE:
				mode = MouseMode.FILL;
				break;
			default:
				mode = MouseMode.SELECT;
				break;
			}
		}
		
		//change tiles based on mode
		switch(mode) {
		case SELECT:
			if(Game.mouse.getX() >= 0 && Game.mouse.getY() >= 0) {
				level.xTarget = (int) ((Game.mouse.getX()/Game.scale/Screen.zoom) + level.xOffset);
				level.yTarget = (int) ((Game.mouse.getY()/Game.scale/Screen.zoom) + level.yOffset);
			}
			break;
		case SINGLE:
			if(Game.mouse.getButtonDown(1)) {
				//level.setTile(mouseTileX, mouseTileY, Tile.wood);
				if(level.getTile(mouseTileX, mouseTileY) != null)
					level.getTile(mouseTileX, mouseTileY).object = new Wall();
			}
			break;
		case LINE:
			if(Game.mouse.getButtonDown(1)) {
				if(!isDragging) {
					isDragging = true;
					dragX = (int) Math.floor(((Game.mouse.getX() / Game.scale/Screen.zoom) + level.xOffset) / 16);
					dragY = (int) Math.floor(((Game.mouse.getY() / Game.scale/Screen.zoom) + level.yOffset) / 16);
				}
			}else {
				if(isDragging) {
					isDragging = false;
					if(Math.abs(dragY - mouseTileY) > Math.abs(dragX - mouseTileX)) {
						for(int y = 0; y <= Math.abs(dragY - mouseTileY); y++) {
							int tx = dragX, ty = dragY;
							if(dragY < mouseTileY)
								ty = dragY + y;
							else if(dragY > mouseTileY)
								ty = dragY - y;
							//level.setTile(tx, ty, Tile.wood);
							if(level.getTile(tx, ty) != null)
								level.getTile(tx, ty).object = new Wall();
							
						}
					}else {
						for(int x = 0; x <= Math.abs(dragX-mouseTileX); x++) {
							int tx = dragX, ty = dragY;
							if(dragX < mouseTileX)
								tx = dragX + x;
							else if(dragX > mouseTileX)
								tx = dragX - x;
							//level.setTile(tx, ty, Tile.wood);
							if(level.getTile(tx, ty) != null)
								level.getTile(tx, ty).object = new Wall();
						}
					}
				}
			}
			break;
		case FILL:
			if(Game.mouse.getButtonDown(1)) {
				if(!isDragging) {
					isDragging = true;
					dragX = (int) Math.floor(((Game.mouse.getX() / Game.scale/Screen.zoom) + level.xOffset) / 16);
					dragY = (int) Math.floor(((Game.mouse.getY() / Game.scale/Screen.zoom) + level.yOffset) / 16);
				}
			}else {
				if(isDragging) {
					isDragging = false;
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
							//level.setTile(tx, ty, Tile.wood);
							if(level.getTile(tx, ty) != null)
								level.getTile(tx, ty).object = new Wall();
							
						}
					}
				}
			}
			break;
		}
	}

}
