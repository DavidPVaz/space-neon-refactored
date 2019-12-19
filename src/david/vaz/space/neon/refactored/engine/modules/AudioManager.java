package david.vaz.space.neon.refactored.engine.modules;

import david.vaz.space.neon.refactored.resources.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class AudioManager {

    private static AudioManager audioManager = null;
    private final Map<Sound, Music> sounds = new HashMap<>();
    private Sound inLoopSound;

    private AudioManager() {

    }

    public static AudioManager getInstance() {
        return audioManager == null ? audioManager = new AudioManager() : audioManager;
    }

    public void setup() {
        Arrays.stream(Sound.values()).forEach(sound -> {
            Music music = new Music();
            music.setup(sound.path());
            sounds.put(sound, music);
        });
    }

    public void play(Sound sound, boolean loop) {

        Music music = sounds.get(sound);
        music.play();

        if (loop) {
            inLoopSound = sound;
            music.loop();
        }

        if (!loop) {
            music.setup(sound.path());
        }

    }

    public void stopCurrentInLoopSound() {
        sounds.get(inLoopSound).stop();
    }

    public void close() {
        sounds.values().forEach(Music::close);
    }

    private class Music {

        private Clip clip;

        private void setup(String path) {

            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                //auto close for sound effects
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        Line clip = event.getLine();
                        clip.close();
                    }
                });

            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                System.err.println(e.getMessage());
            }
        }

        private void play() {
            clip.start();
        }

        private void loop() {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        private void stop() {
            clip.stop();
        }

        private void close() {
            clip.close();
        }
    }
}
