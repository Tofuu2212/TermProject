/**
 * CLIENTHANDLER PURPOSE:
 * Server creates a client handler each time a Client tries to connect. It is the client handler's job
 * to talk to the Client and respond to their requests.
 * <p>
 * TO DO:
 * like Client, fix close() to not leave zombie processes.
 * like Client, make streams for serializable objects
 * then, make it send those objects to the game model. The server should probably create the game model.
 * So, this client handler should probably have a parameter myGame for when it's added to a game.
 * <p>
 * Nathan Zimet 2/26/25:
 * created this file following CS151 echo, Request Handler. However, this code is completely seperate from Client
 * which means there's some duplicate code BUT I hope that at some point socket can go over networks so yeah.
 */

package Server;

import java.io.*;
import java.net.*;

import Message.*;

public class ClientHandler implements Runnable {

    boolean active = true;

    protected Socket handlerSocket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    public ClientHandler(Socket socket) {
        handlerSocket = socket;
        try {
            out = new ObjectOutputStream(handlerSocket.getOutputStream());
            in = new ObjectInputStream(handlerSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error initializing streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (active) {
            try {
                serverMessageHandler.parse(receive());
                Thread.yield(); // or sleep
            } catch (Exception e) {
                break;
            }
        }

        close();
    }

    public void send(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (Exception e) { //general exception can handle multiple execptions
            System.err.println("receive failed");
        }
    }

    public Message receive() {
        Message message = null;
        try {
            message = (Message) in.readObject();
        } catch (Exception e) { //general exception can handle multiple execptions
            System.err.println("receive failed");
        }
        return message;
    }

    public void stop() {
        active = false;
    }

    private void close() {
        try {
            handlerSocket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
