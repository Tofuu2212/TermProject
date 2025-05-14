/*

 */

package Client;

import javax.swing.*;
import java.io.*;
import java.net.*;
import Message.*;

public class Client {

    ClientMessageHandler clientListener;
    GameView gameView;
    //ClientView clientView;

    public Client(String hostName, int portNumber) {
        clientListener = new ClientMessageHandler(hostName, portNumber, this);

    }

    public void start() {
        Thread thread = new Thread(clientListener);
        thread.start();
        //createAndShowGUI();
        gameView = new GameView(clientListener, this);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Client Window");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //TODO: https://docs.oracle.com/javase/7/docs/api/javax/swing/JFrame.html#setDefaultCloseOperation(int);

        //clientView = new ClientView(clientListener, this);
        gameView = new GameView(clientListener, this);
        frame.add(gameView);
        Thread gameThread = new Thread(gameView);
        gameThread.start();
        frame.setVisible(true);
    }

    public void listen() {
        while (true) {
            clientListener.receive();
        }
    }

    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 12345;

        Client client = new Client(hostName, portNumber);
        client.start();

    }
}
