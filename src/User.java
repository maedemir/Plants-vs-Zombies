import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class is a representation of users in the game!
 * it has a username, a score and a list of the unfinished games.
 * @author Maeded Mirzazzadeh
 */
public class User implements Serializable {
    private String username;
    private int score;
    private ArrayList<GamePanel> unfinishedGames;

    public User(){
        score = 0;
        unfinishedGames = new ArrayList<>();
    }

    /**
     * this method is for changing user's total score after winning or loosing a game!
     * @param score score he/she got (can be positive or negative)
     */
    public void changeScore(int score){
        this.score+=score;
    }

    /**
     * adds an unfinished game to the list of the unfinished games of the user.
     * @param game unfinished game to be added to the list
     */
    public void addUnfinishedGame(GamePanel game){
        //we should check if there is such game or not!
        unfinishedGames.add(game);
    }

    public ArrayList<GamePanel> getUnfinishedGames() {
        return unfinishedGames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getScore() {
        return score;
    }

}
