/**
 * CLIENTHANDLER PURPOSE:
 * Server creates a client handler each time a Client tries to connect. It is the client handler's job
 * to talk to the Client and respond to their requests.
 *
 * TO DO:
 * like Client, fix close() to not leave zombie processes.
 * like Client, make streams for serializable objects
 * then, make it send those objects to the game model. The server should probably create the game model.
 * So, this client handler should probably have a parameter myGame for when it's added to a game.
 *
 * Nathan Zimet 2/26/25:
 * created this file following CS151 echo, Request Handler. However, this code is completely seperate from Client
 * which means there's some duplicate code BUT I hope that at some point socket can go over networks so yeah.
 */

package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    boolean active = true;

    protected Socket handlerSocket;
    PrintWriter out;
    BufferedReader in;
    BufferedReader stdIn;

    public ClientHandler(Socket socket) throws IOException {
        handlerSocket = socket;
        initStreams();
    }

    //This is a mirror of Client  communication streams
    public void initStreams() {
        try {
            out = new PrintWriter(handlerSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(handlerSocket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {

        while(active) {
            try {
                // receive request
                String inputLine, outputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Client: " + inputLine);
                    outputLine = "Server: " + inputLine;
                    out.println(outputLine);
                    if (outputLine.equals("bye"))   //apparently, this is always false 0.o well it looks shitty anyway
                        break;
                }

                // sleep
                Thread.yield(); // or sleep

            } catch(Exception e) {
                //send(e.getMessage() + "... ending session");
                break;
            }
        }

        // close
        //close();

    }
}
