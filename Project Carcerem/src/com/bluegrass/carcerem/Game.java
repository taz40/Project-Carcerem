package com.bluegrass.carcerem;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.graphics.Sprite;
import com.bluegrass.carcerem.input.Keyboard;
import com.bluegrass.carcerem.input.Mouse;
import com.bluegrass.carcerem.input.Mouse.MouseMode;
import com.bluegrass.carcerem.level.Tile;

//The Main class
public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	//Width and Height of the render area
	public static int width = 300;
	public static int height = width / 16 * 9;
	//Scale of the window, based on the render area
	public static int scale = 3;
	
	//The game loop thread
	private Thread thread;
	private boolean running = false;
	
	//The Window
	private JFrame frame;
	//The Screen(renderer)
	private Screen screen;
	
	//The Image and pixels to be rendered to the screen
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	//input listeners
	public Keyboard keyboard = new Keyboard();
	public Mouse mouse = new Mouse();
	
	//The title of the window
	public static final String TITLE = "Project Carcerem";
	
	//The current mouse mode
	public MouseMode mode = MouseMode.SELECT;
	
	//main method
	public static void main(String[] args) {
		//create new game, and apply some settings
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		//add the input listeners
		game.addKeyListener(game.keyboard);
		game.addMouseListener(game.mouse);
		game.addMouseMotionListener(game.mouse);
		//start the game
		game.start();
	}
	
	public Game() {
		//Set the size of the window
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);
		
		//initialize the screen and the window
		screen = new Screen(width, height, this);
		frame = new JFrame();
	}
	
	//start the game
	public synchronized void start() {
		//start the game thread
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	//stop the game
	public synchronized void stop() {
		//stop the game thread
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//game loop
	@Override
	public void run() {
		//last time in nanoseconds
		long lastTime = System.nanoTime();
		//last time in miliseconds
		long timer = System.currentTimeMillis();
		//change in time sence last frame
		double deltaTime = 0;
		//frames this second so far
		int frames = 0;
		while(running) {
			//get time, and calculate the change
			long currentTime = System.nanoTime();
			deltaTime += currentTime - lastTime;
			lastTime = currentTime;
			//update the game, based on the time elapsed
			update(deltaTime / 1000000000.0);
			//render the game
			render();
			//another frame this second
			frames++;
			//check if a second has passed
			currentTime = System.currentTimeMillis();
			if(currentTime - timer > 1000) {
				//set title with the fps value
				frame.setTitle(TITLE + " | " + frames + " fps");
				frames = 0;
				timer = currentTime;
			}
			deltaTime = 0;
		}
	}

	//render the game
	private void render() {
		//make sure we are triple buffering
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		//clear and render the screen
		screen.clear();
		screen.render();
		
		//copy screen into the image
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		//draw the image
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, width, height, null);
		g.dispose();
		bs.show();
	}

	//update the game
	private void update(double deltaTime) {
		//check for movement
		if(keyboard.getKeyDown(KeyEvent.VK_W) || keyboard.getKeyDown(KeyEvent.VK_UP))
			screen.yOffset -= 64.0 * deltaTime;
		if(keyboard.getKeyDown(KeyEvent.VK_S) || keyboard.getKeyDown(KeyEvent.VK_DOWN))
			screen.yOffset += 64.0 * deltaTime;
		if(keyboard.getKeyDown(KeyEvent.VK_D) || keyboard.getKeyDown(KeyEvent.VK_RIGHT))
			screen.xOffset += 64.0 * deltaTime;
		if(keyboard.getKeyDown(KeyEvent.VK_A) || keyboard.getKeyDown(KeyEvent.VK_LEFT))
			screen.xOffset -= 64.0 * deltaTime;
		
		//find the tile the mouse is hovering over
		screen.mouseTileX = (int) Math.floor(((mouse.getX() / scale) + screen.xOffset) / 16);
		screen.mouseTileY = (int) Math.floor(((mouse.getY() / scale) + screen.yOffset) / 16);
		
		//switch between modes if the ESC key is pressed
		if(keyboard.getKey(KeyEvent.VK_ESCAPE)) {
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
			break;
		case SINGLE:
			if(mouse.getButtonDown(1)) {
				screen.level.setTile(screen.mouseTileX, screen.mouseTileY, Tile.wood);
			}
			break;
		case LINE:
			if(mouse.getButtonDown(1)) {
				if(!screen.isDragging) {
					screen.isDragging = true;
					screen.dragX = (int) Math.floor(((mouse.getX() / scale) + screen.xOffset) / 16);
					screen.dragY = (int) Math.floor(((mouse.getY() / scale) + screen.yOffset) / 16);
				}
			}else {
				if(screen.isDragging) {
					screen.isDragging = false;
					if(Math.abs(screen.dragY - screen.mouseTileY) > Math.abs(screen.dragX - screen.mouseTileX)) {
						for(int y = 0; y <= Math.abs(screen.dragY - screen.mouseTileY); y++) {
							int tx = screen.dragX, ty = screen.dragY;
							if(screen.dragY < screen.mouseTileY)
								ty = screen.dragY + y;
							else if(screen.dragY > screen.mouseTileY)
								ty = screen.dragY - y;
							screen.level.setTile(tx, ty, Tile.wood);
							
						}
					}else {
						for(int x = 0; x <= Math.abs(screen.dragX-screen.mouseTileX); x++) {
							int tx = screen.dragX, ty = screen.dragY;
							if(screen.dragX < screen.mouseTileX)
								tx = screen.dragX + x;
							else if(screen.dragX > screen.mouseTileX)
								tx = screen.dragX - x;
							screen.level.setTile(tx, ty, Tile.wood);
						}
					}
				}
			}
			break;
		case FILL:
			if(mouse.getButtonDown(1)) {
				if(!screen.isDragging) {
					screen.isDragging = true;
					screen.dragX = (int) Math.floor(((mouse.getX() / scale) + screen.xOffset) / 16);
					screen.dragY = (int) Math.floor(((mouse.getY() / scale) + screen.yOffset) / 16);
				}
			}else {
				if(screen.isDragging) {
					screen.isDragging = false;
					for(int x = 0; x <= Math.abs(screen.dragX-screen.mouseTileX); x++) {
						for(int y = 0; y <= Math.abs(screen.dragY - screen.mouseTileY); y++) {
							int tx = screen.dragX, ty = screen.dragY;
							if(screen.dragX < screen.mouseTileX)
								tx = screen.dragX + x;
							else if(screen.dragX > screen.mouseTileX)
								tx = screen.dragX - x;
							if(screen.dragY < screen.mouseTileY)
								ty = screen.dragY + y;
							else if(screen.dragY > screen.mouseTileY)
								ty = screen.dragY - y;
							screen.level.setTile(tx, ty, Tile.wood);
							
						}
					}
				}
			}
			break;
		}
		
		
	}

}
