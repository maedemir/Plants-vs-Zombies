

import javax.swing.*;
import java.io.Serializable;

/**
 * defender is a JPanel that represents each cell in the yard!
 * each collider can have an assigned plant inside it
 */
public class Defender extends JPanel implements Serializable {
    //The planet in the defender
    private Plant assignedDefender;
    public Defender(){
        assignedDefender = null;
        setOpaque(false);
        setSize(100,120);
    }

    /**
     * this method checks if a zombie is inside a defender or not!
     * @param x x of zombie
     * @return true if zombie is inside defender , false if not!
     */
    public boolean isInsideDefender(int x) {
        return (x > getLocation().x) && (x < getLocation().x + 75);
    }

    public void setAssignedDefender(Plant assignedDefender) { this.assignedDefender = assignedDefender; }

    public Plant getAssignedDefender() { return assignedDefender; }

}
