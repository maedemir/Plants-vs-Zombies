import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * this class handles reading and writing on files.
 * just server can use this class to handle the files.
 */
public class FileUtil implements Serializable{

    /**
     * this method gets the username of the user who wants to log in.
     * if the user logged in before , this method reads it from the file and returns it (user) ,
     * if it is the first time the user logs in , this method creates new user with this username and adds it to the files
     * and returns this user.
     * @param userName username of the user who wants to log in
     * @return the user
     */
    public User openUserFile(String userName){
        User user = null;
        Path p = Paths.get(".\\users");
        try ( DirectoryStream<Path> users = Files.newDirectoryStream(p)){
            for (Path p1 : users) {
                File temp =  p1.toFile();
                if(temp.getName().equals(userName)){
                    ObjectInputStream o1 = new ObjectInputStream(new FileInputStream(p1.toString()));
                    user = (User) o1.readObject();
                    o1.close();
                    return user;
                }
            }
        } catch (IOException |ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (user==null){
            user = new User();
            user.setUsername(userName);
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".\\users\\"+userName))) {
                oos.writeObject(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * this method gets a user and writes it on it's file.
     * in other words, it updates the given user and saves it.
     * @param user current user whose information should be updated
     */
    public void saveGame(User user){
        Path p = Paths.get(".\\users");
        try ( DirectoryStream<Path> users = Files.newDirectoryStream(p)){
            for (Path p1 : users) {
                File temp =  p1.toFile();
                if(temp.getName().equals(user.getUsername())){
                    ObjectOutputStream o1 = new ObjectOutputStream(new FileOutputStream(p1.toString()));
                    o1.writeObject(user);
                    o1.close();
                }
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }

    /**
     * this method receives a user and adds it to the rankings of the game.
     * if ranking file exists , this method updates it's data but if it doesn't exist ,
     * this method creates it and adds this user to it.
     * @param user user should be added to the rankings
     */
    public synchronized void saveRankings(User user){
        Path p = Paths.get(".\\Rankings");
        try (DirectoryStream<Path> dir  = Files.newDirectoryStream(p)){

            for (Path temp : dir) {
                File file =  temp.toFile();
                if(file.getName().equals("ranking")){
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(".\\Rankings\\ranking"));
                    Ranking ranking = (Ranking) ois.readObject();
                    ranking.addUser(user);
                    ois.close();
                    ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(".\\Rankings\\ranking"));
                    oos2.writeObject(ranking);
                    oos2.close();
                    for (String str : ranking.getUsers().keySet()) {
                        System.out.println(str+"      :"+ranking.getUsers().get(str));
                    }
                    return;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".\\Rankings\\ranking"))) {
            Ranking ranking = new Ranking();
            ranking.addUser(user);
            oos.writeObject(ranking);
            for (String str : ranking.getUsers().keySet()) {
                System.out.println("in second try : "+str+"      :"+ranking.getUsers().get(str));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * this methods reads the rankings file and returns it.
     * @return rankings which is read from file.
     */
    public static Ranking returnRanking(){
        Ranking ranking = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(".\\Rankings\\ranking"))) {
            ranking = (Ranking)ois.readObject();
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return ranking;
    }

    /**
     * this method is used to change the username of the given user to the new username
     * which is received in the constructor.
     * this method checks if there exists a user in the file whose username equals the new username , it doesn't change it
     * and returns false, but if it is new to the file, it changes the username and replace old file with new one and
     * saves it and returns true.
     * @param user user whose username is asked to be changed
     * @param newUsername new username of the user
     * @return if the username is changed, it returns true else returns false
     */
    public static boolean ChangeUsername(User user , String newUsername){
        Path p = Paths.get(".\\users");
        try ( DirectoryStream<Path> users = Files.newDirectoryStream(p)){
            for (Path p1 : users) {
                File temp =  p1.toFile();
                if(temp.getName().equals(newUsername)){
                   return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path origin = Paths.get(".\\users\\"+user.getUsername());
        try{
            // rename a file in the same directory
            Files.move(origin, origin.resolveSibling(newUsername));
            replaceInRanking(user,newUsername);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("can not rename the file!");
            return false;
        }
        try (ObjectOutputStream o2 = new ObjectOutputStream(new FileOutputStream(".\\users\\"+newUsername))){
            user.setUsername(newUsername);
            o2.writeObject(user);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    /**
     * when the username of a user is changed , this method is called to replace the old username with the new one
     * in the rankings file.
     * @param user the user whose username is changed
     * @param newUsername new username of the user
     */
    public static void replaceInRanking(User user, String newUsername){

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(".\\Rankings\\ranking"))){
            Ranking ranking = (Ranking)ois.readObject();
            ranking.getUsers().remove(user.getUsername());
            user.setUsername(newUsername);
            ranking.addUser(user);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".\\Rankings\\ranking"));
            oos.writeObject(ranking);
            oos.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}
