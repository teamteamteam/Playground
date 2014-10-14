package Spiel;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Explosion {
	private static BufferedImage[] look = new BufferedImage[5];
	private float f_posx;
	private float f_posy;
	private Rectangle bounding;
	private float aniTime = 0;
	private final static float NEEDEDTIME = 0.3f;
	private List<Explosion> explosionen;
	
	
	static{
		try {
			look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("Bilder/Explosion1.png"));
			look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("Bilder/Explosion2.png"));
			look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("Bilder/Explosion3.png"));
			look[3] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("Bilder/Explosion4.png"));
			look[4] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("Bilder/Explosion5.png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public Explosion(float x, float y, List<Explosion> explosionen){
		f_posx = x;
		f_posy = y;
		bounding = new Rectangle((int)x, (int)y, look[0].getWidth(), look[0].getHeight());
		this.explosionen = explosionen;
	}
	
	public void update(float timeSinceLastFrame){
		aniTime += timeSinceLastFrame;
		if(aniTime > NEEDEDTIME){
			explosionen.remove(this);
		}
	}
	
	public BufferedImage getLook(){
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
	
	public Rectangle getBounding(){
		return bounding;
		
	}
}
