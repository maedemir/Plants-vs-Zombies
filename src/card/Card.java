package card;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * this class is a representation of card in our game which is a JPanel
 */
public class Card extends JPanel implements Serializable {

    //shows that card can be used at the moment or need time to be charged
    private boolean CanBeUsed;
    //time needed to recharge the card
    private double rebuildTime;
    //address of images
    private String active;
    private String inactive;
    private int posX;
    private int posY;
    public Card(boolean CanBeUsed, int x, int y, String activeCard, String inactiveCard){
        active = activeCard;
        inactive = inactiveCard;
        this.CanBeUsed = CanBeUsed;
        this.posX = x;
        this.posY = y;
        setOpaque(false);
        setSize(64,90);
        setLocation(x,y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!CanBeUsed){
            g.drawImage(new ImageIcon(inactive).getImage(),0,0,null);
        } else
            g.drawImage(new ImageIcon(active).getImage(), 0, 0, null);
    }

    /**
     * set location of card
     */
    public void update(){
        setLocation(posX, posY);
    }

    public void setCanBeUsed(boolean canBeUsed) {
        CanBeUsed = canBeUsed;
    }

    public double getRebuildTime() {
        return rebuildTime;
    }

    public void setRebuildTime(double rebuildTime) {
        this.rebuildTime = rebuildTime;
    }


}
