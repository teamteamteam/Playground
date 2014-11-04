import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.Port.Info;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.spi.MixerProvider;

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
		Mixer mixer = null;
		Line port = null;
		CompoundControl volCtrl = null;
		SourceDataLine clip = null;
		try {
			javax.sound.sampled.Mixer.Info[] foo = AudioSystem.getMixerInfo();
			System.out.println("[MixerInfos]");
			for(int i=0; i < foo.length; i++) {
				System.out.println("["+i+"] " + foo[i]);
			}
			System.out.println();
		
			javax.sound.sampled.Mixer.Info mixerInfo = foo[7];
			
			mixer = AudioSystem.getMixer(mixerInfo);
			mixer.open();

			Line.Info[] targetLineInfo = mixer.getTargetLineInfo();
			
			System.out.println("[TargetLineInfo]");
			for(int j=0; j<targetLineInfo.length; j++) {
				System.out.println("["+j+"] " + targetLineInfo[j]);
			}
			System.out.println();
			
			port = mixer.getLine(targetLineInfo[0]);
			port.open();
			
			System.out.println("[Port]");
			System.out.println(port);
			System.out.println();
			
			//Get controls
			Control[] c = port.getControls();
			System.out.println("[Controls]");
			for(int k=0; k<c.length; k++) {
				System.out.println(c[k]);
			}
			System.out.println();
			
			volCtrl = (CompoundControl) port.getControl(c[0].getType());
			System.out.println(volCtrl.getClass());
			
			Control[] subC = volCtrl.getMemberControls();
			System.out.println("[CompoundControl]");
			for(int g=0; g<subC.length; g++) {
				System.out.println("["+g+"] " + subC[g]);
			}
			System.out.println();
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("delmar.wav"));
			DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioInputStream .getFormat());
			System.out.println(dataLineInfo);
			clip = AudioSystem.getSourceDataLine(audioInputStream.getFormat());
			clip.open(audioInputStream.getFormat());

			clip.start();
			
			System.out.println("[Clip]");
			System.out.println(clip);
			
			InputStream fifi = new FileInputStream(new File("delmar.wav"));
			byte[] b = new byte[512];
			while(fifi.available() > 0) {
				fifi.read(b, 0, 512);
				clip.write(b, 0, 512);
			}

			clip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
