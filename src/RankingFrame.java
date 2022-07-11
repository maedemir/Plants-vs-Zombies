import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * this class shows ranking and score board as a JTable inside a JFrame
 * @author Mohadeseh Atyabi, Maedeh Mirzazadeh
 */
public class RankingFrame extends JFrame {
    public RankingFrame(HashMap<String,Integer>users){
        super("Leader board");
        setSize(450,450);
        makeTable(users);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(d.width/2-this.getSize().width/2, d.height/2-this.getSize().height/2);
        setVisible(true);
        setResizable(false);
    }

    /**
     * this method make a ranking table
     * @param users a hashmap of usernames and their scores
     */
    public void makeTable(HashMap<String,Integer> users){
        int numberOfRows = users.keySet().size();
        String[] columnNames = {"Name","Score"};
        String[][] dataValues = new String[numberOfRows][2];
        int i = 0;
        for (String str : users.keySet()) {
            dataValues[i][0]=str;
            dataValues[i][1]= String.valueOf(users.get(str));
            i++;
        }
        JTable RankingTable = new JTable(dataValues,columnNames) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        RankingTable.setSelectionBackground(new Color(0x76EE61));
        RankingTable.setBounds(30,40,200,300);
        JScrollPane scrollPane =new JScrollPane(RankingTable);
        RankingTable.setCellSelectionEnabled(true);
        add(scrollPane);
        this.repaint();
        revalidate();

    }
}
