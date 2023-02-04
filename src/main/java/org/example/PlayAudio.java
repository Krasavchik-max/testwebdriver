package org.example;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlayAudio {
    public static void main() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        // specify the audio file to play
        String audioFile = "Ring05.wav";

        // create an AudioInputStream object from the audio file
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFile));

        // create a Clip object from the AudioInputStream
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        // play the audio
        clip.start();

        // wait for the audio to finish playing
        while (!clip.isRunning())
            Thread.sleep(10);
        while (clip.isRunning())
            Thread.sleep(10);

        // close the clip
        clip.close();

    }

}
