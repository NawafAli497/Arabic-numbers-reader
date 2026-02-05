import javax.sound.sampled.*;
import java.io.InputStream;

public class SoundPlayer {

	public static void play(String wavName) {
	    try {
	        InputStream is = SoundPlayer.class.getResourceAsStream("/" + wavName);
	        if (is == null) throw new RuntimeException("WAV not found: " + wavName);

	        // IMPORTANT: wrap so AudioSystem can read properly
	        AudioInputStream audio = AudioSystem.getAudioInputStream(new java.io.BufferedInputStream(is));

	        Clip clip = AudioSystem.getClip();
	        clip.open(audio);

	        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
	            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	            gain.setValue(0.0f); // or 6.0f
	        }

	        clip.start();

	        // Keep JVM alive until playback ends
	        Thread.sleep(Math.max(1, clip.getMicrosecondLength() / 920));

	        clip.close();
	        audio.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
