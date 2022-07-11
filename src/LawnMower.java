
import zombies.BucketHeadZombie;
import zombies.ConeHeadZombie;
import zombies.NormalZombie;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Iterator;

/**
 * this class represents a lawn mower in the game.
 * in each game we have five lawn movers on the left side of the yard. if a zombies hits a lawn mover.
 * it will be activated and runs toward right side of the yard and if hits zombie in it's path, kills that zombie.
 * if a lawn mower is activated, it won't be replaced by another one.
 */
public class LawnMower extends JPanel implements Serializable {
    private String waitingImage;
    private String runningImage;
    private boolean isRunning;
    private GameState gameState;
    private int xPos;
    private int yPos;
    private int counter;

    public LawnMower(int y, GameState gameState){
        this.gameState = gameState;
        waitingImage = ".\\images\\Gifs\\lawn_mower.gif";
        runningImage = ".\\images\\Gifs\\lawnmowerActivated.gif";
        this.xPos=0;
        this.yPos=y;
        counter = 0;
        isRunning=false;
        setOpaque(false);
        setSize(82,70);
        setLocation(xPos,yPos);
    }

    /**
     * this method updates the x position of the activated lawn mover. each time this method is called, it increases
     * the x position of the lawn mower 10 pixels.
     * after moving the lawn mover, this method checks if a zombie is hit by the lawn mower, kills it and removes
     * that zombie from the list of the zombies in the game.
     * if the lawn mower reaches the right side of the yard, it will be removed from the yard.(it's isRunning field will be false)
     */
    public void update(){
        if(isRunning){
            if (xPos < 1000){
                xPos+=10;
            }
            Iterator<NormalZombie> iterator1 = gameState.getNormalZombies().get((yPos-130)/123).iterator();
            while (iterator1.hasNext()){
                NormalZombie normalZombie = iterator1.next();
                if (Math.abs(normalZombie.getPosX()-xPos)<16){
                    System.out.println("Normal removed");
                    iterator1.remove();
                }
            }
            Iterator<ConeHeadZombie> iterator2 = gameState.getConeHeadZombies().get((yPos-130)/123).iterator();
            while (iterator2.hasNext()){
                ConeHeadZombie coneHeadZombie = iterator2.next();
                if (Math.abs(coneHeadZombie.getPosX()-xPos)<16){
                    System.out.println("Cone removed");
                    iterator2.remove();
                }
            }
            Iterator<BucketHeadZombie> iterator3 = gameState.getBucketHeadZombies().get((yPos-130)/123).iterator();
            while (iterator3.hasNext()){
                BucketHeadZombie bucketHeadZombie = iterator3.next();
                if (Math.abs(bucketHeadZombie.getPosX()-xPos)<16){
                    System.out.println("Bucket removed");
                    iterator3.remove();
                }
            }
            if(xPos>=1000){
                isRunning=false;
            }
            setLocation(xPos,yPos);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isRunning){
            setSize(100,100);
            g.drawImage(new ImageIcon(runningImage).getImage(),0,0,null);
        }else {
            g.drawImage(new ImageIcon(waitingImage).getImage(),0,0,null);
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getXPos() {
        return xPos;
    }
}
