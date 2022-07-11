import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.Serializable;

/**
 * this class handles the sounds and musics of the game.
 * the boolean unmute shows whether the player asks to play sounds or not. if it is true the sounds are played
 * and when it is false the will be mute.
 * @author Mohadeseh Atyabi, Maedeh Mirzazadeh
 */
public class PlaySound implements Serializable {
    private Clip menuClip;
    private Clip backgroundClip;
    private Clip sunClickedClip;
    private Clip lawnmowerClip;
    private Clip gameOverClip;
    private Clip chompClip;
    private Clip zombieComingClip;
    private boolean unmute;

    public PlaySound(boolean unmute){
        this.unmute = unmute;
    }

    /**
     * this method plays the sound of the game frame. it has a loop to repeat the music while the game is run.
     * if unmute is false, this method won't play the music.
     */
    public void playBackgroundSound() {
        if (unmute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                        ".\\sounds\\background.wav").getAbsoluteFile());
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioInputStream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundClip.start();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method plays the music of the menu frame. it has a loop to repeat rhe music while the menu frame is open.
     * if unmute is false, this method won't play the music.
     */
    public void playMenuSound() {
        if (unmute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                        ".\\sounds\\menu.wav").getAbsoluteFile());
                menuClip = AudioSystem.getClip();
                menuClip.open(audioInputStream);
                menuClip.loop(Clip.LOOP_CONTINUOUSLY);
                menuClip.start();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method plays the sound of coins whenever a sun is clicked in the game panel.
     * if unmute is false, this method won't play the music.
     */
    public void playSunClicked(){
        if (unmute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                        ".\\sounds\\ting.wav").getAbsoluteFile());
                sunClickedClip = AudioSystem.getClip();
                sunClickedClip.open(audioInputStream);
                sunClickedClip.start();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method plays the sound of a lawn mower if a lawn mower starts running.
     * if unmute is false, this method won't play the music.
     */
    public void playLawnmower(){
        if (unmute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                        ".\\sounds\\lamborghini.wav").getAbsoluteFile());
                lawnmowerClip = AudioSystem.getClip();
                lawnmowerClip.open(audioInputStream);
                lawnmowerClip.start();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * thie method plays the music of the game over. it is a loop to repeat the music while the corresponding window
     * is open. if unmute is false, this method won't play the music.
     */
    public void playGameOver(){
        if (unmute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                        ".\\sounds\\game_end.wav").getAbsoluteFile());
                gameOverClip = AudioSystem.getClip();
                gameOverClip.open(audioInputStream);
                gameOverClip.loop(Clip.LOOP_CONTINUOUSLY);
                gameOverClip.start();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * when a zombie eating a plant, this method plays the sound of eating.
     * if unmute is false, this method won't play the music.
     */
    public void playChomp(){
        if (unmute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                        ".\\sounds\\chomp.wav").getAbsoluteFile());
                chompClip = AudioSystem.getClip();
                chompClip.open(audioInputStream);
                chompClip.start();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * when zombies are coming this method is called. in each game this method is called thrice. (at the beginning of
     * each wave of the game)
     * if unmute is false, this method won't play the music.
     */
    public void playZombieComing(){
        if (unmute) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(
                        ".\\sounds\\zombies_coming.wav").getAbsoluteFile());
                zombieComingClip = AudioSystem.getClip();
                zombieComingClip.open(audioInputStream);
                zombieComingClip.start();
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method stops the music of the menu frame. (if it was played before)
     */
    public void stopMenu(){
        if (menuClip != null)
            menuClip.stop();
    }

    /**
     * this method stops the music of the game frame. (if it was played before)
     */
    public void stopBackground(){
        if (backgroundClip != null)
            backgroundClip.stop();
    }

    /**
     * this method stops the music of the game over. (if it was played before)
     */
    public void stopGameOver(){
        if (gameOverClip != null)
            gameOverClip.stop();
    }

    /**
     * this method stops the sound of the chomping. (if it was played before)
     */
    public void stopChomp(){
        if (chompClip != null)
            chompClip.stop();
    }

    public boolean isUnmute() { return unmute; }

    public void setUnmute(boolean unmute) { this.unmute = unmute; }

}
