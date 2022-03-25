package main.java.de.tum.in.ase.eist.Controller;

import java.net.URL;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {

    private static final String BACKGROUND_MUSIC_FILE = "backround1.wav";
    private static final String COLLISION_SOUND_FILE = "collision.wav";

    private static final double COLLISION_SOUND_VOLUME = 0.5;

    private MediaPlayer musicPlayer;
    private AudioClip collisionPlayer;
    private String fileURL;

    public AudioPlayer() {
    }

    public void playBackgroundMusic() {
        setMusicPlayer();
        if (isPlayingBackgroundMusic()) {
            return;
        }
        // Loop for the main music sound:
        this.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        this.musicPlayer.play();
    }

    public void playCollisionSound() {
        setCollisionPlayer();
        collisionPlayer.play(COLLISION_SOUND_VOLUME);
    }

    public void stopBackgroundMusic() {
        if (isPlayingBackgroundMusic()) {
            this.musicPlayer.stop();
        }
    }

    public boolean isPlayingBackgroundMusic() {
        return MediaPlayer.Status.PLAYING.equals(this.musicPlayer.getStatus());
    }

    private Media loadAudioFile() {
        return new Media(convertNameToUrl(AudioPlayer.BACKGROUND_MUSIC_FILE));
    }

    public String convertNameToUrl(String fileName) {
        URL musicSourceUrl = getClass().getClassLoader().getResource(fileName);
        if (musicSourceUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        fileURL = musicSourceUrl.toExternalForm();
        return fileURL;
    }

    public void setMusicPlayer() {
        this.musicPlayer = new MediaPlayer(loadAudioFile());
    }

    public void setCollisionPlayer() {
        this.collisionPlayer = new AudioClip(convertNameToUrl(COLLISION_SOUND_FILE));
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getFileURL() {
        return this.fileURL;
    }

    public static String getBackgroundMusicFile() {
        return BACKGROUND_MUSIC_FILE;
    }

    public static String getCollsionSoundFile() {
        return COLLISION_SOUND_FILE;
    }
}
