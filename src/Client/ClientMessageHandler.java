package Client;

import java.io.*;
import java.net.Socket;

import Message.*;
import Server.ServerMessageParser;

public class ClientMessageHandler implements Runnable {

    protected Socket myClientSocket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    boolean active = true;
    Client myClient;

    public ClientMessageHandler(String hostName, int portNumber, Client myClient) {
        this.myClient = myClient;
        try {
            myClientSocket = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(myClientSocket.getOutputStream());
            in = new ObjectInputStream(myClientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("error creating client message handler");
        }

    }

    @Override
    public void run() {
        System.out.println("clientMessageHanlder run called");
        while (active) {
            try {
                Message message = receive();
                System.out.println("Client received " + message);
                Thread.yield(); // or sleep
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
