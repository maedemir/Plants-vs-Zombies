import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * this class manages that client wants to send to server
 */
public class Client {
    private Socket socket;
    private User user;// user that use this socket
    private InputStream in;
    private OutputStream out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String username;
    public Client() {
        user = new User();
        try {
            //local host
            socket = new Socket("127.0.0.1",1234);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *this method send 1 to server to say that client wants to sign in
     * @param username username of client to sign in with
     */
    public synchronized void signIn(String username){
        this.username=username;
        try {
            byte[] buffer = new byte[2048];
           // the sign in code (for Server) = "1"
            out.write("1".getBytes());// send message to sign in
            out.flush();

            int read = in.read(buffer);
            String answer = new String(buffer, 0, read);
            System.out.println(answer);
            if (answer.equals("13")) {// send username
                out.write(username.getBytes());
                out.flush();
            }
            ois = new ObjectInputStream(in);
            if(user!=null){
                user = (User)ois.readObject();

            }else {
                System.out.println("user was null");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method will send "2" to server to say client wants to save an unfinished game!
     * @param gamePanel game panel that user wants to save
     */
    public synchronized void saveGame(GamePanel gamePanel){
        user.addUnfinishedGame(gamePanel);
        for (GamePanel g : user.getUnfinishedGames()) {
            System.out.println(g.getDateOfGame());
        }
        String answer;
        try {
            byte[] buffer = new byte[2048];
            out.write("2".getBytes());// send message to save
            out.flush();
            int read = in.read(buffer);
            answer = new String(buffer, 0, read);
            System.out.println(answer);
            if(answer.equals("13")){
                oos = new ObjectOutputStream(out);
               oos.writeObject(user);
               oos.flush();
               System.out.println("user sent2```");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this method sends 0 to server so that server understands client wants to be saved and end its connection
     */
    public void sendUserToServer(){
        try {
            byte[] buffer = new byte[2048];
            out.write("0".getBytes());// send message to save user and end connection
            out.flush();
            int read = in.read(buffer);
            String answer = new String(buffer, 0, read);
            System.out.println("answer to exit :"+answer);
            if(answer.equals("13")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(user);
                oos.flush();
                this.socket.close();
                System.out.println("socket closed in client");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is called when a client loose or win a game , and send "4" to server so that server will update its
     * file
     * @param score
     */
    public void saveScore(int score ){
        user.changeScore(score);
        try {
            byte[] buffer = new byte[2048];
            // the sign in code (for Server) = "1"
            out.write("4".getBytes());// send message to save
            out.flush();
            int read = in.read(buffer);
            String answer = new String(buffer, 0, read);
            System.out.println("answer to exit :"+answer);
            if(answer.equals("13")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(user);
                oos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * when a client want to see rankings this method will be called and it sends "3" to server so that server returns
     * the sorted rankings;
     * @return a hashmap of usernames and scores in ranking
     */

    public HashMap<String,Integer> showRankings(){
        HashMap<String,Integer> users = new HashMap<>();
        try {
            // the Ranking code (for Server) = "3"
            out.write("3".getBytes());// send message to save
            out.flush();
            ois = new ObjectInputStream(in);
            Ranking ranking =(Ranking)ois.readObject();
            users = ranking.getUsers();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * this method calls server when a client wants to change its username
     * @param newUsername new username
     * @return "0" if operation fails , "1" if changing username is done successfully
     */

    public String changeUsername(String newUsername){
        try {
            out.write("5".getBytes());
            out.flush();
            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
            String answer = new String(buffer, 0, read);
            if(answer.equals("13")){
                oos = new ObjectOutputStream(out);
                oos.writeObject(this.user);
                oos.flush();
            }
            read = in.read(buffer);
            String answer2 = new String(buffer,0,read);
            if(answer2.equals("ok")){
                out.write(newUsername.getBytes());
                out.flush();
            }
            read = in.read(buffer);
            String answer3 = new String(buffer,0,read);
            return answer3;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";

    }

    public User getUser() {
        return user;
    }

}
