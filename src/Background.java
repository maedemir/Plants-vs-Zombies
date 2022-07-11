
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * @author Mohadeseh Atyabi
 * this class sets an image to background of a JPanel
 */
public class Background extends JPanel implements Serializable {
    private String pic;
    int width,height;
    public Background(String address,int width, int height){
        pic = address;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(new ImageIcon(pic).getImage(),0,0,width,height,null);
    }

}
