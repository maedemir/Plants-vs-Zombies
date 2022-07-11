import java.io.Serializable;

/**
 * this class represents a chomper in the game
 *when a zombie reaches chomper , it will swallow zombie if it is not digesting another zombie
 */
public class Chomper extends Plant implements Serializable {
    private boolean isEating;
    private int damage;
    //for counting eating time!
    private int counter;
    private int digestTime;

    public Chomper( int startX, int startY, int NormalOrHard){
        super(70,150,startX,startY);
        isEating=false;
        damage = NormalOrHard==0?35:25;
    }

    /**
     * update timer that holds digesting time of a zombie
     * if it reaches to maximum , is eating of chomper will be false
     */
    public void updateCounter(){
        if(counter<digestTime*1000){
            counter+=50;
        }else {
            isEating=false;
        }
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setDigestTime(int digestTime) {
        this.digestTime = digestTime;
    }

    public boolean isEating() {
        return isEating;
    }

    public void setEating(boolean eating) {
        isEating = eating;
    }

    public int getDamage() {
        return damage;
    }

}
