import java.io.*;
import java.net.Socket;

/**
 * this class is used on server side
 * when ever a client sends a request server will respond to it using this class!
 * note that each client should have a separate client handler !
 * this class also uses file util class to do stuff related to users' files
 */
class ClientHandler implements Runnable {

    private Socket connectionSocket;
    private int clientNum;
    private InputStream in;
    private OutputStream out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean toContinue = true;
    public ClientHandler(Socket connectionSocket, int clientNum) {
        this.connectionSocket = connectionSocket;
        this.clientNum=clientNum;
        try {
            out = connectionSocket.getOutputStream();
            in = connectionSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("cant make stream");
            e.printStackTrace();
        }

    }

    /**
     * this method is for logging in a client
     */
    private void LogIn(){
        byte[] buffer = new byte[2048];
        try {
            int read = in.read(buffer);
            String username = new String(buffer, 0, read);
            System.out.println(username);
            FileUtil fileUtil = new FileUtil();
            User user = fileUtil.openUserFile(username);
            oos = new ObjectOutputStream(out);
            oos.writeObject(user);
            oos.flush();
            fileUtil.saveRankings(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
        run();
    }

    /**
     * this method will save a game to client's related file
     */
    private void saveGame(){
        try {
            ois = new ObjectInputStream(in);
            User user = (User)ois.readObject();
            System.out.println(user.getScore());
            FileUtil fileUtil = new FileUtil();
            fileUtil.saveGame(user);
            fileUtil.saveRankings(user);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        run();
    }

    /**
     * this method is for ending connection
     * it will rewrite user on it's file!
     */
    private void endConnection(){
        try {
            ois = new ObjectInputStream(in);
            User user = (User)ois.readObject();
            System.out.println(user.getScore());
            FileUtil fileUtil = new FileUtil();
            fileUtil.saveGame(user);
            fileUtil.saveRankings(user);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method is for changing client's total score
     */
    private void changeScore(){
        try {
            ois = new ObjectInputStream(in);
            User user = (User)ois.readObject();
            System.out.println(user.getScore());
            FileUtil fileUtil = new FileUtil();
            fileUtil.saveGame(user);
            fileUtil.saveRankings(user);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        run();

    }


    /**
     * this method is used to show ranking to client when ever client asks for it
     */
    private void Ranking(){
        try {
          oos = new ObjectOutputStream(out);
          oos.writeObject(FileUtil.returnRanking());
          oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        run();
    }

    /**
     * this method is for server responses to changing client's username
     */
    private void ChangeUsername(){
        try {
            ois = new ObjectInputStream(in);
            User user = (User)ois.readObject();
            out.write("ok".getBytes());
            out.flush();
            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
            String newUsername = new String(buffer, 0, read);
            System.out.println("user name that client sent :"+newUsername);
            boolean answer = FileUtil.ChangeUsername(user,newUsername);
            out.write(answer?"1".getBytes():"0".getBytes());
            out.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        try {
            System.out.println("to continue is : "+toContinue);
            while (toContinue) {
                byte[] buffer = new byte[2048];
                int read ;
                if ((read = in.read(buffer)) != -1) {
                    String command = new String(buffer, 0, read);
                    switch (command) {
                        case "0":
                            out.write("13".getBytes());
                            System.out.println("exit !");
                            out.flush();
                            endConnection();
                            toContinue=false;
                            return;

                        case "1":
                            out.write("13".getBytes());
                            System.out.println("sign in !");
                            out.flush();
                            LogIn();
                            break;
                        case "2":
                            out.write("13".getBytes());
                            System.out.println("save state !");
                            out.flush();
                            saveGame();
                            break;
                        case "3":
                            System.out.println("Rankings !");
                            Ranking();
                            break;
                        case "4":
                            System.out.println("save scores !");
                            out.write("13".getBytes());
                            out.flush();
                            changeScore();
                            break;
                        case "5":
                            System.out.println("change username !");
                            out.write("13".getBytes());
                            out.flush();
                            ChangeUsername();
                            break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}