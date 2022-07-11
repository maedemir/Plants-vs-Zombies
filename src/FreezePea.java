
import java.io.Serializable;

/**
 * this class represents a freeze pea which is produced by snow pea shooter each second.
 * when a freeze pea hits a zombie , it reduces the life of the zombie.
 */
public class FreezePea implements Serializable {
    private int damage;
    private boolean firstHit;
    private int startX;
    private int startY;
    private int endX;
    private String image;

    public FreezePea(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
        endX = 1000;
        damage = 35;
        image = ".\\images\\freezepea.png";
        firstHit = false;
    }

    /**
     * this method updates the location of the freeze pea.
     * each time this method is called it increases the x position of the freeze pea 10 pixels
     * till it reaches the end of the yard.
     */
    public void update(){
        if (startX < endX){
            startX += 10;
        }
    }

    public int getDamage(){ return damage; }

    public int getStartX() {
        return startX;
    }

    public String getImage() {
        return image;
    }

    public int getStartY() {
        return startY;
    }
}
