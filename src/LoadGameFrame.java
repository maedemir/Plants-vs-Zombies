import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * this class is a representation of load game frame , which shows info of all the game's that user has saved already!
 * @author  Maede Mirzazadeh, Mohadeseh Atyabi
 */
public class LoadGameFrame extends JFrame {
    private String userName;
    private JTable gameTable;
    private PlaySound playSound;
    private Client client;

    public LoadGameFrame (Client client, String userName, PlaySound playSound){
        super("Load game");
        this.client=client;
        this.playSound = playSound;
        setSize(450,450);
        this.userName=userName;
        makeTable();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);
        setVisible(true);
        setResizable(false);
    }

    /**
     * this method shows the saved games of the user which are received by the client from the server.
     * the cells of this table is not editable but the can be selected to load the corresponding game.
     */
    public void makeTable() {
        //first we should  find out how many rows we need by reading player's file
        int numberOfRows = client.getUser().getUnfinishedGames().size();
        String[] columnNames = {"Date","medium/hard"};
        String[][] dataValues = new String[numberOfRows][2];
        for (int i = 0; i < numberOfRows; i++) {
            dataValues[i][0] = client.getUser().getUnfinishedGames().get(i).getDateOfGame();
            dataValues[i][1] = client.getUser().getUnfinishedGames().get(i).getNormalOrHard();
        }
        gameTable = new JTable(dataValues,columnNames) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        gameTable.setSelectionBackground(new Color(0x76EE61));
        gameTable.setBounds(30,40,200,300);
        JScrollPane scrollPane =new JScrollPane(gameTable);
        gameTable.setCellSelectionEnabled(true);
       ListSelectionModel select= gameTable.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        select.addListSelectionListener(new listListener());
        add(scrollPane);
        this.repaint();
        revalidate();
    }

    /**
     * this class handles the cells of the table when they are selected.
     * when the player selects a cell , he/she will be asked whether to load the game or not. if the player
     * chooses "no" the game won't be loaded but if chooses "yes this method closes all the open frames and opens the
     * game frame to resume the selected game.
     */
    private class listListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int column = 0;
            int row = gameTable.getSelectedRow();
            String value = gameTable.getModel().getValueAt(row, column).toString();
            System.out.println(value+" "+row);
            int option = JOptionPane.showConfirmDialog(null,"you want to load game"+value+"?"
            ,userName+"!",JOptionPane.YES_NO_OPTION);
            if(option==0){
                playSound.stopMenu();
                System.out.println("you pressed yes");
                //closing all previous frames
                for (Frame f : Frame.getFrames()) {
                    f.dispose();
                }
                //load game frame
                //do stuff related to loading game
                GameFrame gameFrame = new GameFrame(client,client.getUser().getUnfinishedGames().get(row));
                client.getUser().getUnfinishedGames().get(row).startSound(playSound.isUnmute());
                client.getUser().getUnfinishedGames().remove(row);
            }

        }
    }
}