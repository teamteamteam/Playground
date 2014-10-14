package Spiel;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Player {
	private Rectangle bounding;
	private float f_posx;
	private float f_posy;
	private int worldsize_x;
	private int worldsize_y;
	private int punkte = 0;
	private int playerMoveSpeed = 150;
	private int shotAmount = 1;
	private int shotSpeed = 400;
	private float Shotfrequenzy = 1f;
	private BufferedImage look;
	private BufferedImage look_dead;
	private List<Bullet> bullets;
	private List<Enemy> enemys;
	private List<Explosion> explosionen;
	private float timeSinceLastShot;
	private boolean alive = true;
	
	public Player(int x, int y, int worldsize_x, int worldsize_y, List<Bullet> bullets, List<Enemy> enemys, List<Explosion> explosionen){
		try {
			System.out.println(getClass().getClassLoader().getResourceAsStream("Bilder/Player.png"));
			look = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/Player.png"));
			look_dead = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Bilder/PlayerTot.png"));

		} catch (IOException e) {e.printStackTrace();}
		bounding = new Rectangle(x, y, look.getWidth(), look.getHeight());
		f_posx = x;
		f_posy = y;
		this.worldsize_x = worldsize_x;
		this.worldsize_y = worldsize_y;
		this.bullets = bullets;
		this.enemys = enemys;
		this.explosionen = explosionen;
	}
	
	public void update(float timeSinceLastFrame){
		if(!alive){ 
			return;
		}
		timeSinceLastShot += timeSinceLastFrame;
		if(Keyboard.isKeyDown(KeyEvent.VK_W))	f_posy -= playerMoveSpeed * timeSinceLastFrame;
		if(Keyboard.isKeyDown(KeyEvent.VK_S))  	f_posy += playerMoveSpeed * timeSinceLastFrame;
		if(Keyboard.isKeyDown(KeyEvent.VK_A))  	f_posx -= playerMoveSpeed * timeSinceLastFrame;
		if(Keyboard.isKeyDown(KeyEvent.VK_D)) 	f_posx += playerMoveSpeed * timeSinceLastFrame;
		if(timeSinceLastShot > Shotfrequenzy && Keyboard.isKeyDown(KeyEvent.VK_SPACE)){
			timeSinceLastShot = 0;
				//Schuss pos, von oben nach unten.
			if(shotAmount == 4 || shotAmount == 5 || shotAmount == 6){
				bullets.add(new Bullet(f_posx+4, f_posy+5-Bullet.getLook().getHeight()/2, shotSpeed, -25, bullets, enemys, explosionen));
			}
			if(shotAmount == 2 || shotAmount == 3 || shotAmount == 5 || shotAmount == 6){
				bullets.add(new Bullet(f_posx+20, f_posy+10-Bullet.getLook().getHeight()/2, shotSpeed, 0, bullets, enemys, explosionen));
			}
			if(shotAmount == 1 || shotAmount == 3 || shotAmount == 4 || shotAmount ==6){
				bullets.add(new Bullet(f_posx+look.getWidth()-15, f_posy+look.getHeight()/2-Bullet.getLook().getHeight()/2, shotSpeed, 0, bullets, enemys, explosionen));
			}
			if(shotAmount == 2 || shotAmount == 3 || shotAmount == 5 || shotAmount == 6){
				bullets.add(new Bullet(f_posx+20, f_posy+getLook().getHeight()-10-Bullet.getLook().getHeight()/2, shotSpeed, 0, bullets, enemys, explosionen));
			}
			if(shotAmount == 4 || shotAmount == 5 || shotAmount == 6){
				bullets.add(new Bullet(f_posx+4, f_posy+getLook().getHeight()-5-Bullet.getLook().getHeight()/2, shotSpeed, 25, bullets, enemys, explosionen));
			}
		}
		
		if(f_posx < 0){
			f_posx = 0;
		}
		if(f_posx >= worldsize_x - bounding.width){
			f_posx = worldsize_x - bounding.width;
		}
		if(f_posy < 0){
			f_posy = 0;
		}
		if(f_posy >= worldsize_y - bounding.height){
			f_posy = worldsize_y - bounding.height;
		}
		
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;
	
		for(int i = 0; i < enemys.size(); i++){
			Enemy e = enemys.get(i);
			if(e.isAlive() && bounding.intersects(e.getBounding())){
				alive = false;
			}
		}
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public BufferedImage getLook(){
		if(alive){
			return look;
		} else {
			return look_dead; 
		}
	}
	
	public float getX(){
		return f_posx;
	}
	
	public float getY(){
		return f_posy;
	}
	
	public void addPunkte(int punkte){
		this.punkte += punkte;
	}
	
	public String getPunkte(){
		return String.valueOf(punkte);
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void addShotfrequenzy(float f){
		Shotfrequenzy += f;
	}
	
	public void increaseShotAmount(){
		if(shotAmount <6){
			shotAmount++;
		}
	}
}
