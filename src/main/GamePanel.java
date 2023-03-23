package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
			final int originalTileSize = 16;
	        final int scale = 3;
	 public final int tileSize = originalTileSize * scale;//48-48
	 public final int maxScreenCol = 16;
	 public final int maxScreenRow = 12;
	 public final int screenWidth = tileSize * maxScreenCol;
	 public final int screenHeight = tileSize *  maxScreenRow;
	
	 		Sound music = new Sound();
	 		Sound se = new Sound();
			TileManager  tileM = new TileManager(this);
			KeyHandler keyH = new KeyHandler();
	 public CollisionCheker cCheker = new CollisionCheker(this);
	 public Player player = new Player(this, keyH);
	 public AssetSetter aSetter = new AssetSetter(this);
	 public UI ui = new UI(this);
	 public SuperObject obj[] = new SuperObject[10];
	 		Thread gameThread;
	 
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	public final int worlWidth = tileSize * maxWorldCol;
	public final int worlHeight = tileSize * maxWorldRow;
	
	int FPS = 60;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.YELLOW);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		aSetter.setObject();
		playMusic(0);
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterwal = 1_000_000_000/FPS; //0.016...sec
		double nextDrawTime = System.nanoTime() + drawInterwal;
		double delta = 0;
		long lastTime = System.nanoTime();
		
		long currentTime;
		long time;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
		
			currentTime  = System.nanoTime();
			
			delta += (currentTime - lastTime)/drawInterwal;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >=1) {
			update();
			repaint();
			delta--;   					
			drawCount++;				
}
		if(timer >= 1_000_000_000) {
		System.out.println("FPS:" + drawCount);
		drawCount = 0;
		timer = 0;
		}
	}
}
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
				player.draw(g2);
				ui.draw(g2);
				g2.dispose();
	}
	public void playMusic(int i) {
		music.setFile(i);music.play();music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
