import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * this class is a representation of menu frame in the class
 * @author Mohadeseh Atyabi, Maedeh Mirzazadeh
 */
public class MenuFrame extends JFrame{

    private JLabel newGame;
    private JLabel loadGame;
    private JLabel option;
    private JLabel ranking;
    private JLabel exitGame;
    private JLabel mute_unmute;
    private JLabel changeUsername;
    private Background background;
    private String[] level;
    private JList list;
    private FileUtil fileUtil;
    private PlaySound playSound;
    private boolean unmute;
    private Client client;

    public MenuFrame(boolean unmute, Client client){
        this.client=client;
        setSize(1000, 563);
        background = new Background(".\\images\\menu.png" , 1000 , 563);
        setContentPane(background);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2-getSize().width/2,d.height/2-getSize().height/2);
        setVisible(true);

        level = new String[]{"Normal", "Hard"};
        list = new JList(level);
        playSound = new PlaySound(unmute);
        playSound.playMenuSound();
        addWindowListener(new windowHandler());
        menuInit();
    }

    /**
     * controls closing of menu frame
     */
    public class windowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit?", "Exit warning",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                client.sendUserToServer();
                dispose();
            }
        }
    }

    /**
     * initializes the menu
     */
    private void menuInit(){

        //New game
        newGame = new JLabel();
        newGame.setBounds(42,328,140,84);
        newGame.addMouseListener(new mouseHandler());
        newGame.setOpaque(false);
        getLayeredPane().add(newGame,Integer.valueOf(1));

        //Load game
        loadGame = new JLabel();
        loadGame.setBounds(50,10,290,30);
        loadGame.setOpaque(false);
        loadGame.addMouseListener(new mouseHandler());
        getLayeredPane().add(loadGame,Integer.valueOf(1));

        //Ranking
        ranking = new JLabel();
        ranking.setBounds(60,144, 280,40);
        ranking.addMouseListener(new mouseHandler());
        ranking.setOpaque(false);
        getLayeredPane().add(ranking,Integer.valueOf(1));

        //Option
        option = new JLabel();
        option.setBounds(867,430,90,60);
        option.setOpaque(false);
        option.addMouseListener(new mouseHandler());
        getLayeredPane().add(option,Integer.valueOf(1));

        //Exit game
        exitGame = new JLabel();
        exitGame.setBounds(775,430,80,60);
        exitGame.setOpaque(false);
        exitGame.addMouseListener(new mouseHandler());
        getLayeredPane().add(exitGame,Integer.valueOf(1));

        //Mute_Unmute
        mute_unmute = new JLabel("Mute");
        setMuteIcon();
        mute_unmute.setBounds(561,360,47,58);
        mute_unmute.setOpaque(false);
        mute_unmute.addMouseListener(new mouseHandler());
        getLayeredPane().add(mute_unmute,Integer.valueOf(1));

        //change username
        changeUsername = new JLabel();
        changeUsername.setBounds(21,205,214,48);
        changeUsername.setOpaque(false);
        changeUsername.addMouseListener(new mouseHandler());
        getLayeredPane().add(changeUsername,Integer.valueOf(1));
    }


    /**
     * this method is for setting mute unmute icons
     */
    public void setMuteIcon(){
        if (playSound.isUnmute()){
            mute_unmute.setIcon(new ImageIcon(".\\images\\unmute.png"));
        } else {
            mute_unmute.setIcon(new ImageIcon(".\\images\\mute.png"));
        }
        repaint();
    }

    /**
     * this class is for controlling mouse events related to menu frame
     */
    private class mouseHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if(e.getSource().equals(newGame)){
                System.out.println("New game");
                dispose();
                GameFrame gameFrame = new GameFrame(fileUtil,
                        list.getSelectedIndex()==-1 ? 0 : list.getSelectedIndex(),playSound,client);
                playSound.stopMenu();
            }
            else if (e.getSource().equals(ranking)){
                System.out.println("Ranking");
                RankingFrame RF = new RankingFrame(client.showRankings());
                System.out.println("1)"+client.getUser().getUsername());
            }
            else if (e.getSource().equals(loadGame)){
                if(client.getUser()!=null){
                    LoadGameFrame loadGameFrame = new LoadGameFrame(client,client.getUser().getUsername(),playSound);

                }
                else {
                    System.out.println("cant load frame cause user is null");
                }
            }
            else if (e.getSource().equals(option)){
                System.out.println("Option");
                JOptionPane.showMessageDialog(null,list,"Select level",JOptionPane.PLAIN_MESSAGE);
            }
            else if (e.getSource().equals(exitGame)){
                System.out.println("Exit game");
                client.sendUserToServer();
                dispose();
            }
            else if (e.getSource().equals(mute_unmute)){
                playSound.setUnmute(!playSound.isUnmute());
                if (!playSound.isUnmute()){
                    playSound.stopMenu();
                } else {
                    playSound.playMenuSound();
                }
                setMuteIcon();
                System.out.println("Unmute is:" + playSound.isUnmute());
            }
            else if(e.getSource().equals(changeUsername)){
                System.out.println("change username");
                String answer = JOptionPane.showInputDialog(null,
                        "please enter new username:","Change username",JOptionPane.QUESTION_MESSAGE);
                System.out.println(answer);
                if(answer!=null) {
                    String successful = client.changeUsername(answer);

                    if (successful.equals("0")) {
                        JOptionPane.showMessageDialog(null, "Operation failed!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Done!", "",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        playSound.stopMenu();
                        LoginPage loginPage = new LoginPage();
                    }
                }

            }
        }
    }

}