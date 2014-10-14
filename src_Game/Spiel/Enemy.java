package Spiel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import Items.ItemFastShot;
import Items.ItemMultiShot;

public class Enemy {
	private static BufferedImage[] look = new BufferedImage[3];
	private static BufferedImage look_dead;
	private final static float NEEDEDTIME = 0.2f;
	private final static int PUNKTE = 1;
	private final static Random rand = new Random();
	private float aniTime = 0;
	private float deadTime = 0;
	private float f_posx;
	private float f_posy;
	private Rectangle bounding;
	private List<Enemy> enemys;
	private List<ItemFastShot> itemFS;
	private List<ItemMultiShot> itemMS;
	private final int FASTSHOTSPAWNRATE = 10;
	private final int MULTISHOTSPAWNRATE = 25;
	private boolean alive = true;
	private Player player;
	
	static{
		try{
			look[0] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("Bilder/Enemy1.png"));
			look[1] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("Bilder/Enemy2.png"));
			look[2] = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("Bilder/Enemy3.png"));
			look_dead = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("Bilder/EnemyTot.png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public Enemy(float x, float y, List<Bullet> bullets, Player player, List<Enemy> enemys, List<ItemFastShot> itemFS, List<ItemMultiShot> itemMS){
		f_posx = x;
		f_posy = y;
		bounding = new Rectangle((int)x, (int)y, look[0].getWidth(), look[0].getHeight());
		this.player = player;
		this.enemys = enemys;
		this.itemFS = itemFS;
		this.itemMS = itemMS;
	}
	
	public void update(float timeSinceLastFrame){
		aniTime += timeSinceLastFrame;
		if(aniTime > NEEDEDTIME){
			aniTime = 0;
		}
		
/*		if(player.getY() - f_posy < 2){
			f_posy -= 100*timeSinceLastFrame;
		}else if(player.getY() - f_posy > -2){
			f_posy += 100*timeSinceLastFrame;
		}
*/	
		f_posx -= 100*timeSinceLastFrame;
		if(f_posx <= -getLook().getWidth()){
			f_posx = 800;
			f_posy = rand.nextInt(600-getLook().getHeight());
			alive = true;
		}
		
		if(!alive){
			deadTime += timeSinceLastFrame;
			if(deadTime > 2){
				enemys.remove(this);
			}
		}
		
		bounding.x = (int) f_posx;
		bounding.y = (int) f_posy;
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	                       
	public BufferedImage getLook(){
		if(!alive){
			return look_dead;
		}else{
			if(look.length == 0){
				return null;
			}
			for(int i = 0; i<look.length; i++){
				if(aniTime < (float) (NEEDEDTIME/look.length*(i+1))){
					return look[i];
				}
			}
			return look[look.length-1];			
		}
	}

	public static int getWidth(){
		return look[0].getWidth();
	}

	public static int getHeight(){
		return look[0].getHeight();
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void EnemyDies(){
		player.addPunkte(PUNKTE);
		alive = false;
		if(rand.nextInt(FASTSHOTSPAWNRATE) == 0){
			itemFS.add(new ItemFastShot(f_posx, f_posy, player, itemFS));
		}
		if(rand.nextInt(MULTISHOTSPAWNRATE) == 0){
			itemMS.add(new ItemMultiShot(f_posx, f_posy, player, itemMS));
		}
	}
}
