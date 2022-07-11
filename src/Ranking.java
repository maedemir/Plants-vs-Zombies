import java.io.Serializable;
import java.util.*;

/**
 * this class handles the rankings of the game.
 * it has a hashmap that maps the usernames of the players to their scores.
 */
public class Ranking implements Serializable {
    private HashMap<String, Integer> users;

    public Ranking() {
        users = new HashMap<>();
    }

    /**
     * to sortUsers rankings
     * @return sorted users
     */
    private HashMap<String,Integer> sortUsers() {
        users = sortByValue(users);
        return users;
    }

    /**
     * this method sorts the given hashmap by it's values (the values are the scores of the players)
     * @param hm the hashmap which should be sorted by values
     * @return the sorted hashmap
     */
    public  HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user.getScore());
    }

    public HashMap<String, Integer> getUsers() {
        return sortUsers();
    }

}
