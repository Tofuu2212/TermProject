package Client;

import java.io.*;

import Message.*;

public class clientMessageHandler implements Runnable {

    boolean active = true;
    protected ObjectInputStream in;
    Client myClient;

    public clientMessageHandler(ObjectInputStream in, Client myClient) {
        this.in = in;
        this.myClient = myClient;
    }

    @Override
    public void run() {
        System.out.println("clientMessageHanlder run called");
        while (active) {
            try {
                Message message;
                while ((message = (Message) in.readObject()) != null) {
                    System.out.println("Server replied: " + message);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Server closed connection.");
            }
        }
    }

    public void stop() {
        active = false;
    }

}
