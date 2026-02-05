import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

	// The "Cache" - stores the sound data in memory
	private static Map<String, Clip> soundCache = new HashMap<>();

	// 1. Call this ONCE when your program starts (e.g. in main)
	public static void loadAllSounds(String[] files) {


		for (String file : files) {
			loadSound(file);
		}
		System.out.println("All sounds loaded!");
	}

	public static void loadSound(String filename) {
		try {
			InputStream is = SoundPlayer.class.getResourceAsStream("/" + filename);
			if (is == null) return;

			AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);

			// Save it to the map
			soundCache.put(filename, clip);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2. This method is now instant because it reads from memory
	public static void play(String filename) {
		if (!soundCache.containsKey(filename)) {
			System.out.println("Sound not found: " + filename);
			return;
		}

		Clip clip = soundCache.get(filename);

		// Rewind to the start
		clip.setFramePosition(0);
		clip.start();

		// We still need to sleep to prevent them playing on top of each other
		try {
			long duration = clip.getMicrosecondLength() / 1000;
			Thread.sleep(duration - 50); // Small overlap for natural flow
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}