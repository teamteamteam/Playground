package Items;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Spiel.Enemy;
import Spiel.Player;

public class ItemMultiShot {
	private float f_posx;
	private float f_posy;
	private static final float SPEEDX = 100;
	private static BufferedImage look;
	private List<ItemMultiShot> itemMS;
	private Rectangle bounding;
	private Player player;

	static{
		try{
			look = ImageIO.read(Enemy.class.getClassLoader().getResourceAsStream("Bilder/ItemMultiShot.png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public ItemMultiShot(float x, float y, Player player, List<ItemMultiShot> itemMS){
		f_posx = x;
		f_posy = y;
		bounding = new Rectangle((int)x, (int)y, look.getWidth(), look.getHeight());
		this.player = player;
		this.itemMS = itemMS;
	}
	
	public void update(float timeSinceLastFrame){
		f_posx -= SPEEDX*timeSinceLastFrame;
		
		bounding.x = (int) f_posx;
		bounding.y = (int) f_posy;
		
		if(player.isAlive() && bounding.intersects(player.getBounding())){
			player.increaseShotAmount();
			itemMS.remove(this);
		}
		
		if(f_posx < 0-getWidth()){
			itemMS.remove(this);
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
