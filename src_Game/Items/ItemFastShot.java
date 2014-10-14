package Items;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Spiel.Enemy;
import Spiel.Player;


public class ItemFastShot {

	private float f_posx;
	private float f_posy;
	private static final float SPEEDX = 100;
	private static BufferedImage look;
	private List<ItemFastShot> itemFS;
	private Rectangle bounding;
	private Player player;

	static{
		try{
			look = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("Bilder/ItemFastShot.png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public ItemFastShot(float x, float y, Player player, List<ItemFastShot> itemFS){
		f_posx = x;
		f_posy = y;
		bounding = new Rectangle((int)x, (int)y, look.getWidth(), look.getHeight());
		this.player = player;
		this.itemFS = itemFS;
	}
	
	public void update(float timeSinceLastFrame){
		f_posx -= SPEEDX*timeSinceLastFrame;
		
		bounding.x = (int) f_posx;
		bounding.y = (int) f_posy;
		
		if(player.isAlive() && bounding.intersects(player.getBounding())){
			player.addShotfrequenzy(-0.02f);
			itemFS.remove(this);
		}
		
		if(f_posx < 0-getWidth()){
			itemFS.remove(this);
		}
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public static BufferedImage getLook() {
		return look;
	}
	
	public static int getWidth(){
		return look.getWidth();
	}

	public static int getHeight(){
		return look.getHeight();
	}
}