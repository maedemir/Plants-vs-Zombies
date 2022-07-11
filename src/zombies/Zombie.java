package zombies;
import java.io.Serializable;

/**
 * @author  Maedeh Mirzazadeh
 * this class is a representation of Zombie in the game
 */
public class Zombie  implements Serializable {
    private int life;
    private double speed;
    private int destroyPower;
    private  int posX;
    private int posY;
    private boolean isAlive;
    //isRunning shows if zombie is running or eating a plant!
    private boolean isRunning;
    //this counter shows the time a zombie is eating a plant
    private int eatingCounter=0;

    public Zombie(int posX, int posY){
        this.posX=posX;
        this.posY=posY;
        isAlive=true;
        isRunning=true;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPosY() {
        return posY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDestroyPower() {
        return destroyPower;
    }

    public void setDestroyPower(int destroyPower) {
        this.destroyPower = destroyPower;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    /**
     * this method is used when a shoot collides the zombie and decrease it's live
     * @param damage how many of zombie's life will be decreased
     * @return true is zombie is still alive , false if not(means zombie dies)
     */
    public boolean decreaseLife(int damage){
        if(life > damage){
            life-=damage;
            return true;
        }
        else {
            life = 0 ;
            return false;
        }
    }

    /**
     * this method increase the counter each time it is called
     */
    public void addEatingCounter() {
        this.eatingCounter += 1;
    }

    /**
     * reset the counter for new eating :))
     */
    public void resetCounter(){
        this.eatingCounter=0;
    }
}

