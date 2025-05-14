package Client;

import java.awt.*;
import java.io.*;
import java.net.Socket;

import Client.DrawObject.DrawObjChampion;
import Client.DrawObject.DrawObjMinion;
import Client.DrawObject.DrawObject;
import Message.*;

public class ClientMessageHandler implements Runnable {

    protected Socket myClientSocket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    boolean active = true;
    Client myClient;

    boolean debug = true;

    public ClientMessageHandler(String hostName, int portNumber, Client myClient) {
        this.myClient = myClient;
        try {
            myClientSocket = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(myClientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myClientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("error creating client message handler");
        }

    }

    @Override
    public void run() {
        if (debug) System.out.println("clientMessageHanlder run called");
        while (active) {
            try {
                Message message = receive();
                if (debug) System.out.println("received message: " + message.id);

                boolean has = false;
                for (DrawObject d : myClient.gameView.drawObjects) {
                    if (d.getID() == message.id) {
                        has = true;
                        if (message.kill) {
                            myClient.gameView.drawObjects.remove(d);
                        }
                        else {
                            if (debug) System.out.println("updating existing object: " + message.id);
                            d.x = message.x;
                            d.y = message.y;
                            if (message.mainType == Type.CHAMPION) {
                                if (debug) System.out.println("updating existing champion: " + message.id);
                                myClient.gameView.playerX = message.x;
                                myClient.gameView.playerY = message.y;

                            }
                        }
                    }
                }
                if (!has) {
                    if (debug) System.out.println("id didn't exist yet...");
                    if (message.mainType == Type.CHAMPION) {
                        if (debug) System.out.println("Drawing new champion: " + message.id);
                        myClient.gameView.drawObjects.add(new DrawObjChampion(message.x, message.y));
                        myClient.gameView.playerX = message.x;
                        myClient.gameView.playerY = message.y;

                    }
                    else if (message.mainType == Type.MINION) {
                        if (debug) System.out.println("Drawing new minion");
                        myClient.gameView.drawObjects.add(new DrawObjMinion(message.x, message.y));
                    }
                }

                //Thread.yield(); // or sleep
            } catch (Exception e) {
                break;
            }
        }

        close();
    }

    public Message receive() {
        Message message = null;
        try {
            message = (Message) in.readObject();
            if (debug) System.out.println("client message handler recieved message id: " + message.id);
        } catch (Exception e) {
            System.err.println("receive failed");
        }
        return message;
    }

    public void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.err.println("send failed");
        }
    }

    public void stop() {
        active = false;
    }

    private void close() {
        try {
            myClientSocket.close();
            out.close();
            in.close();
        } catch (IOException ignored) {}
    }

}
