package uet.oop.bomberman.sound;


import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    public static synchronized void playSound(final String name) {
        try {
            URL path = Sound.class.getResource(name);
            File file = new File(path.getPath());
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static synchronized void loopSound(final String name) {
        try {
            URL path = Sound.class.getResource(name);
            File file = new File(path.getPath());
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void explosion() {
        playSound("Explosion.wav");
    }
    public static void mob_death(){
        playSound("Mob_death.wav");
    }
    public static void bomb_set(){
        playSound("BOM_SET.wav");
    }
    public static void get_item(){
        playSound("ITEM_GET.wav");
    }
    public static void theme_music(){
        loopSound("THEME.wav");
    }
    public static void stage_interval(){
        loopSound("PLAYER_OUT.wav");
    }
    public static void win(){
        playSound("WIN.wav");
    }
}
