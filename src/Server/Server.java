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
                // accept a connection
                Socket acceptedSocket = myServerSocket.accept();

                // make handler
                ClientHandler handler = new ClientHandler(acceptedSocket);

                // start handler in its own thread
                Thread thread = new Thread(handler);
                thread.start();

            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(2);
            } // catch
        } // while

    }

    public static void main(String[] args) throws IOException {
        int portNumber = 12345;
        Server server = new Server(portNumber);
        server.listen();
    }
}

