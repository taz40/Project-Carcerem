package com.bluegrass.carcerem;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.bluegrass.carcerem.graphics.Screen;
import com.bluegrass.carcerem.input.Keyboard;
import com.bluegrass.carcerem.input.Mouse;

//The Main class
public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	//Width and Height of the render area
	public static int width = 900;
	public static int height = width / 16 * 9;
	
	public static int uiWidth = 200;
	
	//Scale of the window, based on the render area
	public static int scale = 1;
	
	//The game loop thread
	private Thread thread;
	private boolean running = false;
	
	//The Window
	private JFrame frame;
	//The Screen(renderer)
	public Screen screen;
	
	//The Image and pixels to be rendered to the screen
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	//input listeners
	public Keyboard keyboard = new Keyboard();
	public Mouse mouse = new Mouse();
	
	private int dragXLast;
	private int dragYLast;
	private boolean dragging = false;
	
	//The title of the window
	public static final String TITLE = "Project Carcerem";
	
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
		game.addMouseWheelListener(game.mouse);
		
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
		g.setColor(new Color(255,255,255,128));
		g.fillRect(width-uiWidth, 0, uiWidth, height);
		g.dispose();
		bs.show();
	}

	//update the game
	private void update(double deltaTime) {
		screen.level.update(deltaTime);
		
		if(dragging) {
			screen.level.xOffset -= (Mouse.getX() - dragXLast) / Screen.zoom;
			screen.level.yOffset -= (Mouse.getY() - dragYLast) / Screen.zoom;
			dragXLast = Mouse.getX();
			dragYLast = Mouse.getY();
			if(!Mouse.getButtonDown(2)) {
				dragging = false;
			}
		}else {
			if(Mouse.getButtonDown(2)) {
				dragging = true;
				dragXLast = Mouse.getX();
				dragYLast = Mouse.getY();
			}
		}
		
		//check for movement
		if(Keyboard.getKeyDown(KeyEvent.VK_W) || Keyboard.getKeyDown(KeyEvent.VK_UP))
			screen.level.yOffset -= 64.0 * deltaTime;
		if(Keyboard.getKeyDown(KeyEvent.VK_S) || Keyboard.getKeyDown(KeyEvent.VK_DOWN))
			screen.level.yOffset += 64.0 * deltaTime;
		if(Keyboard.getKeyDown(KeyEvent.VK_D) || Keyboard.getKeyDown(KeyEvent.VK_RIGHT))
			screen.level.xOffset += 64.0 * deltaTime;
		if(Keyboard.getKeyDown(KeyEvent.VK_A) || Keyboard.getKeyDown(KeyEvent.VK_LEFT))
			screen.level.xOffset -= 64.0 * deltaTime;
		
		double zoomChange = Mouse.getLastWheelRotation() * .1 * Screen.zoom;
		//double zoomChange = Mouse.getLastWheelRotation() >= 1 ? 1 : 0;
		double oldZoom = Screen.zoom;
		Screen.zoom -= zoomChange;
		if(screen.zoom < 1) {
			screen.zoom = 1;
			zoomChange = 0;
		} 
		if(screen.zoom > 4) {
			screen.zoom = 4;
			zoomChange = 0;
		} 
		if(zoomChange != 0) {
			int centerX = (int) (screen.level.xOffset + width / oldZoom / 2);
			int centerY = (int) (screen.level.yOffset + height / oldZoom / 2);
			screen.level.xOffset = -(width /Screen.zoom/2) + centerX;
			screen.level.yOffset = -(height /Screen.zoom/2) + centerY;
		}

		
	}

}
