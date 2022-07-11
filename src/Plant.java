import java.io.Serializable;

/**
 * this class is representation of plants in game and is an abstract class
 */
public abstract class Plant implements Serializable {
    //Life of the plant
    private int life;
    //Needed sun to be produced
    private int neededSun;
    //Address of the living plant
    private String livingImage;
    //Address of the dying plant
    private String dyingImage;
    //x position
    private int x;
    //y position
    private int y;

    public Plant(int life, int sun, int startX, int startY){

        this.life = life;
        this.neededSun = sun;
        this.x= startX;
        this.y=startY;
    }

    /**
     * Reduces the life of the plant according to the type of the zombie
     * which eats it.
     * @param toSubtract Number to subtract from life of the plant
     */
    public boolean subtractLife(int toSubtract){
        if(toSubtract>=life){
            return false;
        }
        this.life -= toSubtract;
        return true;
    }

    public void setLivingImage(String livingImage) { this.livingImage = livingImage; }

    public void setDyingImage(String dyingImage) { this.dyingImage = dyingImage; }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public int getNeededSun() { return neededSun; }

}
