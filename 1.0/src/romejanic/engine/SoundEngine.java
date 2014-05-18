package romejanic.engine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import romejanic.main.Minecraft;

public class SoundEngine implements Engine {

	public Map<String, Audio[]> sounds = new HashMap<String, Audio[]>();

	@Override
	public void init() {

		/*for(StepSound sound : Block.stepSounds.values()) {

			sound.register(this);

		}*/

	}

	@Override
	public void update() {}

	public void registerSound(String sound, InputStream[] streams) {

		if(sounds.containsKey(sound)) {

			return;

		}

		ArrayList<Audio> theSounds = new ArrayList<Audio>();

		for(InputStream stream : streams) {

			try {

				theSounds.add(AudioLoader.getAudio("OGG", stream));

			} catch(Exception e) {

				System.out.println("Cannot register part of " + sound + "!");

				continue;

			}

		}

		sounds.put(sound, ((Audio[]) theSounds.toArray(new Audio[0])));

	}

	public void playSound(String sound, float volume, float pitch) {

		try {

			if(sounds.containsKey(sound) && sounds.get(sound) != null) {

				int index = Minecraft.random.nextInt(sounds.get(sound).length);
				Audio toPlay = sounds.get(sound)[index];

				if(toPlay != null) {

					toPlay.playAsSoundEffect(volume, pitch, false);

				}

			}

		} catch(Exception e) {

			Minecraft.printStackTrace(e, "Cannot play sound!");
			
		}

	}

}
