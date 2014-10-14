package Spiel;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import Items.ItemFastShot;
import Items.ItemMultiShot;

public class Main {
	
	static List<Bullet> bullets = new LinkedList<Bullet>();
	static List<Enemy> enemys = new LinkedList<Enemy>();
	static List<ItemFastShot> itemFS = new LinkedList<ItemFastShot>();
	static List<ItemMultiShot> itemMS = new LinkedList<ItemMultiShot>();
	static List<Explosion> explosionen = new LinkedList<Explosion>();
	static Player player = new Player(150, 300, 800, 600, bullets, enemys, explosionen);
	static Background back = new Background(50);
	static Random rand = new Random();

	public static void main(String[] args) {
		final float ENEMYSPAWNTIME = 0.5f;
		float timeSinceLastEnemySpawn = 0;
		
		long lastFrame = System.currentTimeMillis();
		spawnEnemy();
		
		Fenster f = new Fenster(player, back, bullets, enemys, itemFS, itemMS, explosionen);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setUndecorated(true);		//keine titlebar, vor setVisible!!
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.makeBufferStr();
		
// 			Für vollbild~~~
/*	 	DisplayMode displayMode = new DisplayMode(800, 600, 16, 75);
		GraphicsEnvironment enviroment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = enviroment.getDefaultScreenDevice();
			
		device.setFullScreenWindow(f);
		device.setDisplayMode(displayMode);
*/		
		while(true){
			if(Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)){
				System.exit(0);
			}
			
			long thisFrame = System.currentTimeMillis();
			float timeSinceLastFrame = ((float) (thisFrame - lastFrame))/1000f;
			lastFrame = thisFrame;
			player.update(timeSinceLastFrame);
			back.update(timeSinceLastFrame);
			
			timeSinceLastEnemySpawn += timeSinceLastFrame;
			if(timeSinceLastEnemySpawn > ENEMYSPAWNTIME){
				timeSinceLastEnemySpawn -= ENEMYSPAWNTIME;
				spawnEnemy();
			}
			
			for(int i = 0; i<bullets.size(); i++){
				bullets.get(i).update(timeSinceLastFrame);
			}
			
			for(int i = 0; i<enemys.size(); i++){
				enemys.get(i).update(timeSinceLastFrame);
			}
			
			for(int i = 0; i<itemFS.size(); i++){
				itemFS.get(i).update(timeSinceLastFrame);
			}
			
			for(int i = 0; i<itemMS.size(); i++){
				itemMS.get(i).update(timeSinceLastFrame);
			}
			
  			for(int i = 0; i<explosionen.size(); i++){
				explosionen.get(i).update(timeSinceLastFrame);
			}
			
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			f.repaintFenster();
		}
	}
	
	public static void spawnEnemy(){
		enemys.add(new Enemy(800, rand.nextInt(600-Enemy.getHeight()), bullets, player, enemys, itemFS, itemMS));
	}
}