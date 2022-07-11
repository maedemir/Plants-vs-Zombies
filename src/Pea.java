
import java.io.Serializable;
/**
 * this class represents a pea which is produced by pea shooter each second.
 * when a pea hits a zombie , it reduces the life of the zombie.
 */
public class Pea implements Serializable {
    private int damage;
    private int startX;
    private int startY;
    private int endX;
    private String image;

    public Pea(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
        endX = 1000;
        damage = 30;
        image = ".\\images\\pea.png";
    }

    /**
     * this method updates the location of the pea.
     * each time this method is called it increases the x position of the pea 10 pixels
     * till it reaches the end of the yard.
     */
    public void update(){
        if (startX < endX){
            startX += 10;
        }
    }

    public String getImage() { return image; }

    public int getDamage(){ return damage; }
    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
}
