package Spiel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;

import Items.ItemFastShot;
import Items.ItemMultiShot;

public class Fenster extends JFrame {
	private static final long serialVersionUID = 1L;
	final Background back;
	final Player player;
	
	private BufferStrategy bufferStr;
	private List<Bullet> bullets;
	private List<Enemy> enemys;
	private List<ItemFastShot> itemFS;
	private List<ItemMultiShot> itemMS;
	private List<Explosion> explosionen;
	
	public Fenster(Player player, Background back, List<Bullet> bullets, List<Enemy> enemys, List<ItemFastShot> itemFS, List<ItemMultiShot> itemMS, List<Explosion> explosionen){
		super("Fenster");
		addKeyListener(new Keyboard());
		this.back = back;
		this.player = player;
		this.bullets = bullets;
		this.enemys = enemys;
		this.itemFS = itemFS;
		this.itemMS = itemMS;		
		this.explosionen = explosionen;
	}
	
	public void makeBufferStr(){
		createBufferStrategy(2);
		bufferStr = getBufferStrategy();
	}	
	
	public void repaintFenster(){
		Graphics g = bufferStr.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.setFont(new Font("", Font.PLAIN, 25));
		draw(g);
		g.dispose();
		bufferStr.show();
	}
	
	private void draw(Graphics g){
		g.drawImage(back.getLook(), back.getx(), 0, null);
		g.drawImage(back.getLook(), back.getx() + back.getLook().getWidth(), 0, null);
				
		for(int i = 0; i<bullets.size(); i++){
			Bullet b = bullets.get(i);
			g.drawImage(Bullet.getLook(), b.getBounding().x, b.getBounding().y, null);
		}

		g.drawImage(player.getLook(), player.getBounding().x, player.getBounding().y, null);
		
		for(int i = 0; i<itemFS.size(); i++){
			ItemFastShot ifs = itemFS.get(i);
			g.drawImage(ItemFastShot.getLook(), ifs.getBounding().x, ifs.getBounding().y, null);
		}
		for(int i = 0; i<itemMS.size(); i++){
			ItemMultiShot ims = itemMS.get(i);
			g.drawImage(ItemMultiShot.getLook(), ims.getBounding().x, ims.getBounding().y, null);
		}
		
		for(int i = 0; i<enemys.size(); i++){
			Enemy e = enemys.get(i);
			g.drawImage(e.getLook(), e.getBounding().x, e.getBounding().y, null);
		}
		
		for(int i = 0; i<explosionen.size(); i++){
			Explosion exp = explosionen.get(i);
			g.drawImage(exp.getLook(), exp.getBounding().x, exp.getBounding().y, null);
		}
		
		g.drawString(player.getPunkte(), 700, 30);
	}
}
