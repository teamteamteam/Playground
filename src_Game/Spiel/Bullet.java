package Spiel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Bullet {
	private static BufferedImage look;
	
	static{
		try {
			look = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("Bilder/Schuss.png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private float f_posx;
	private float f_posy;
	private float f_speedx;
	private float f_speedy;
	private Rectangle bounding;
	private List<Bullet> bullets;
	private List<Enemy> enemys;
	private List<Explosion> explosionen;
	private float timeAlive;
	private final float TIMETOLIVE = 3;
	
	public Bullet(float x, float y, float speedx, float speedy, List<Bullet> bullets, List<Enemy> enemys, List<Explosion> explosionen){
		f_posx = x;
		f_posy = y;
		f_speedx = speedx;
		f_speedy = speedy;
		bounding = new Rectangle((int) f_posx, (int) f_posy, look.getWidth(), look.getHeight()); 
		this.bullets = bullets;
		this.enemys = enemys;
		this.explosionen = explosionen;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive += timeSinceLastFrame;
		if(timeAlive > TIMETOLIVE){
			bullets.remove(this);
		}
		f_posx += f_speedx*timeSinceLastFrame;
		f_posy += f_speedy*timeSinceLastFrame;
		bounding.x = (int) f_posx;
		bounding.y = (int) f_posy;
	
	for(int i = 0; i < enemys.size(); i++){
			Enemy e = enemys.get(i);
			if(e.isAlive() && bounding.intersects(e.getBounding())){
				explosionen.add(new Explosion(f_posx, f_posy, explosionen));
				bullets.remove(this);
				e.EnemyDies();
			}
		}
	}
	
	public static BufferedImage getLook() {
		return look;
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
}