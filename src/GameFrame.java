import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Mohadeseh Atyabi / Maede mirzazadeh
 * this class is the main frame of the game which game panel will be added to this and this class handls pause menu
 */
public class GameFrame extends JFrame{
    private GamePanel gamePanel;
    private JMenuItem item1, item2, item3;
    private JMenuBar menuBar;
    private JMenu menu;
    private FileUtil fileUtil;
    private Client client;


    public GameFrame(FileUtil fileUtil, int NormalOrHard, PlaySound playSound, Client client){
        this.client = client;
        setSize(1012,785);
        menuArranger();
        gamePanel = new GamePanel(NormalOrHard,playSound.isUnmute(),client);
        gamePanel.setLocation(0,20);
        getLayeredPane().add(gamePanel,Integer.valueOf(0));
        this.fileUtil = fileUtil;
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2-getSize().width/2,d.height/2-getSize().height/2);
        setVisible(true);
        addWindowListener(new windowHandler());
    }


    /**
     * this constructor is for loading a game!
     * @param client client to be added
     * @param gamePanel game panel to be loaded
     */
    public GameFrame(Client client, GamePanel gamePanel){
        this.client=client;
        setSize(1012,785);
        menuArranger();
        this.gamePanel = gamePanel;
        gamePanel.initTimers();
        getLayeredPane().add(gamePanel,Integer.valueOf(0));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width/2-getSize().width/2,d.height/2-getSize().height/2);
        setVisible(true);
    }

    /**
     * this class handles action related to closing the window!
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
     * initializes the menubar (pause menu)
     */
    private void menuArranger(){
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menu.addMouseListener(new mouseHandler());

        item1 = new JMenuItem("Resume Game");
        KeyStroke ctrlH = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        item1.setAccelerator(ctrlH);
        item1.addActionListener(new actionHandler());

        item2 = new JMenuItem("Save Game");
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        item2.setAccelerator(ctrlS);
        item2.addActionListener(new actionHandler());

        item3 = new JMenuItem("Exit to Main Menu");
        KeyStroke ctrlE = KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        item3.setAccelerator(ctrlE);
        item3.addActionListener(new actionHandler());
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        repaint();
        revalidate();
    }


    /**
     * this class is for handling actions related to pause menu bar
     */
    private class actionHandler implements ActionListener {
         @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(item1)){
                System.out.println("Resume game");
                gamePanel.resume();
            }
            else if (e.getSource().equals(item2)){
                System.out.println("Save game");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date now = Calendar.getInstance().getTime();
                String savedDate = dateFormat.format(now);
                gamePanel.setDateOfGame(savedDate);
                client.saveGame(gamePanel);
                JOptionPane.showMessageDialog(null,"Game saved successfully!","",
                        JOptionPane.INFORMATION_MESSAGE);
                gamePanel.getPlaySound().stopBackground();
                dispose();
                MenuFrame menuFrame = new MenuFrame(gamePanel.getPlaySound().isUnmute(),client);

            }
            else if (e.getSource().equals(item3)){
                System.out.println("Exit to main menu");
                gamePanel.getPlaySound().stopBackground();
                dispose();
                MenuFrame menuFrame = new MenuFrame(gamePanel.getPlaySound().isUnmute(),client);
            }
        }
    }

    /**
     * this class is for handling clicking on menu
     */
    private class mouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource().equals(menu)){
                System.out.println("Menu clicked");
                gamePanel.waite();
            }
        }
    }


}
