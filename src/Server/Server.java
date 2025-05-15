/**
 * Accept clients and create a client handler for them.
 * */


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
                Game game = new Game(handler); //game.addPlayer(this)
                handler.setGame(game);
                Thread clientThread = new Thread(handler);
                Thread gameThread = new Thread(game);
                clientThread.start();
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