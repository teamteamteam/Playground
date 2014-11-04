import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Main {

	public static void playSimpleSound() {
		String filename = "delmar.wav";
		try {
			InputStream in = new FileInputStream(new File(filename));
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Mixer.Info[] devices = SoundSystem.getAvailableDevices();
		Line.Info[] ports = SoundSystem.initializeMixer(devices[7]);
		SoundSystem.setUpLine(ports[0]);
		BufferedInputStream audioFileStream = null;
		try {
			audioFileStream = new BufferedInputStream(new FileInputStream(new File("delmar.wav")));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Thread t = new Thread(new Runnable() {
			public void run() {
				double x = 0.0;
				while(true) {
					float volume = (float) (0.5 + Math.abs(Math.sin(x)) / 2);
					x += 0.04;
					SoundSystem.setVolume(volume);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		SoundSystem.playFromInputStream(audioFileStream);
		

	}
	
	
}
