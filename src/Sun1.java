
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * @author Mohadeseh Atyabi, Maedeh Miezazadeh
 * this class represents a sun in the game which is produced eather by sky or by planted sunflowers.
 * when a sun is clicked by the player, 25 scores will be added to the total score of the player.
 */
public class Sun1 extends JPanel implements Serializable {
    private int startX;
    private int startY;
    private int endY;
    private String image;
    private int NormalOrHard;
    private int skyProduceRate;

    public Sun1(int startX, int startY, int endY, int NormalOrHard){
        this.startX = startX;
        this.startY = startY;
        this.endY = endY;
        image = ".\\images\\Gifs\\sun.gif";

        setOpaque(false);
        setSize(80, 80);
        setLocation(startX, startY);

        this.NormalOrHard = NormalOrHard;
        if (NormalOrHard==0){
            skyProduceRate = 25;
        }else if (NormalOrHard==1){
            skyProduceRate = 30;
        }

    }

    /**
     * Updates the location of the sun and increases it 4 pixels.
     * the start and end point of the sun which is produced by sky is determined randomly, but a sun produced by a sunflower
     * will be at the block of that sunflower.
     */
    public void update(){
        if (startY < endY){
            startY += 4;
        }
        setLocation(startX, startY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(image).getImage(),0,0,null);
    }

    public int getSkyProduceRate(){ return skyProduceRate; }

}
