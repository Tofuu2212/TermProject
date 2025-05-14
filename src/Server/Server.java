/**
 * SERVER PURPOSE:
 * Accept clients and create a client handler for them.
 *
 * RUNNING SERVER:
 * just press run before running any clients
 *
 * TO DO:
 * add a game object field and tell client handlers when they're in a game
 * should it be multiple games can be running at the same time or just one?
 *
 */

package Server;

import java.io.*;
import java.net.*;

public class Server {

    protected ServerSocket myServerSocket;
    protected int myPort;

    public Server(int port) throws IOException {
        myPort = port;
        myServerSocket = new ServerSocket(port);
    }

    //infinite loop to listen for client requests
    public void listen() {

        System.out.println("Server is running...");

        while (true) {
            try {
                Socket acceptedSocket = myServerSocket.accept();
                ClientHandler handler = new ClientHandler(acceptedSocket);
                Game game = new Game();
                handler.setGame(game);
                Thread clientThread = new Thread(handler);
                clientThread.start();
                Thread gameThread = new Thread(game);
                gameThread.start();

            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(2);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int portNumber = 12345;
        Server server = new Server(portNumber);
        server.listen();
    }
}