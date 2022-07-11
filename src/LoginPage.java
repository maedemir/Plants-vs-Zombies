import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class is the first window that opens when the player runs the game.
 * it receives a username and makes a client to connect to the server.
 * @author Mohadeseh Atyabi, Maedeh Mirzazadeh
 */
public class LoginPage extends JFrame {
    private JTextField userName;
    private JButton LogIn;
    private Background bg ;

    public LoginPage(){
        userName = new JTextField();
        LogIn = new JButton("Login");
        setSize(new Dimension(450,450));
        bg = new Background(".\\images\\tableBG.png",450,450);
        setContentPane(bg);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2-getSize().width/2,d.height/2-getSize().height/2);
        setVisible(true);
        setResizable(false);
        LogIn.addActionListener(new actionHandler());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setComponent();
    }

    /**
     * initializes the components on this frame and sets their locations.
     */
    public void setComponent(){
        JLabel l1 = new JLabel();
        l1.setPreferredSize(new Dimension(200,20));
        userName.setPreferredSize(new Dimension(200,50));
        LogIn.setPreferredSize(new Dimension(200,50));
        LogIn.setBackground(new Color(0x78D74D));
        GridBagLayout gridBagLayout = new GridBagLayout();
        bg.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        bg.add(userName,constraints);
        constraints.gridy=1;
        bg.add(l1,constraints);
        constraints.gridy=2;
        bg.add(LogIn,constraints);
        bg.repaint();
        bg.revalidate();
    }

    /**
     * this class is for handling the action events on the login page.
     */
    private class actionHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(LogIn)){
                if(userName.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Please enter username first!",
                            "",JOptionPane.WARNING_MESSAGE);
                }else {
                    Client client = new Client();
                    client.signIn(userName.getText());
                    MenuFrame mf = new MenuFrame(true, client);
                    dispose();
                }
            }
        }
    }
}
