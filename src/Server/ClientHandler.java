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
import java.util.LinkedList;
import java.util.Queue;

import Message.*;

public class ClientHandler implements Runnable {

    boolean debug = true;

    boolean active = true;

    protected Socket handlerSocket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    Queue<Message> messageQueue;

    Game game;

    public ClientHandler(Socket socket) {
        handlerSocket = socket;
        messageQueue = new LinkedList<Message>();
        try {
            out = new ObjectOutputStream(handlerSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(handlerSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("error setting up client handler");
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (active) {
            try {
                receive();
                //Thread.yield(); // or sleep
            } catch (Exception e) {
                break;
            }
        }
        close();
    }

    public void send(Message message) {
        try {
            if (debug) System.out.println("In send, msg id: " + message.id);
            out.writeObject(message);
            out.flush();
            if (debug) System.out.println("send msg id " + message.id);
        } catch (Exception e) {
            System.err.println("client handler send failed");
        }
    }

    public Message receive() {
        Message message = null;
        try {
            if (debug) System.out.println("In receive");
            message = (Message) in.readObject();
            addMsgToQueue(message);
            if (debug) System.out.println("read msg id " + message.id);
        } catch (Exception e) {
            System.err.println("client handler receive failed");
            active = false;
        }
        return message;
    }

    public synchronized void addMsgToQueue(Message message) {
        messageQueue.add(message);
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
