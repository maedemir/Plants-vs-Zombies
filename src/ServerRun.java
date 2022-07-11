
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRun {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket server = new ServerSocket(1234)) {
            System.out.print("Server started.\nWaiting for a client ... \n");
            for (int i = 0; i > -1; i++) {
                Socket connectionSocket = server.accept();
                ClientHandler temp = new ClientHandler(connectionSocket, i);
                pool.execute(temp);
            }
            pool.shutdown();
            System.out.print("Closing server ... ");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("done.\n");
    }
}
