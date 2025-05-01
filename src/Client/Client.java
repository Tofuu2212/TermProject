/**
 * CLIENT PURPOSE:
 * the user runs Client.java if they want to participate in a game. Client.java creates a socket
 * connection with Server.java

 * RUNNING CLIENT:
 * must have a server running with matching port and name.
 * go into IntelliJ Edit Run Configuratinon > Modify Options > allow multiple instances
 * this way, multiple clients can run with the same server. This is why ClientHandler is multithreaded

 * CHANGES TO BE MADE:
 * properly closing all sockets.
 * change stream to serialized object stream.
 * the speak() loop is a double loop when it can probably be a single loop
 * exception handling could use a pass over

 * Nathan Zimet 2/26/25:
 * Created this class from ChatGPT origins of the simplest possible Client-Server setup in Java and modifying it
 * to be more like CS151 echo.
 */

package Client;

import javax.swing.*;
import java.io.*;
import java.net.*;
import Message.*;

public class Client {

    protected Socket myClientSocket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    clientMessageHandler clientListener;

    public Client(String host, int port) throws IOException {
        myClientSocket = new Socket(host, port);
        try {
            out = new ObjectOutputStream(myClientSocket.getOutputStream());
            in = new ObjectInputStream(myClientSocket.getInputStream());
            clientListener = new clientMessageHandler(in, this);
        } catch (IOException e) {
            System.err.println("Error initializing streams: " + e.getMessage());
        }

    }

    public void start() {
        createAndShowGUI();

        Thread thread = new Thread(clientListener);
        thread.start();
    }

    private void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Client Window");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setVisible(true);
    }

    private void close() {
        try {
            myClientSocket.close();
            out.close();
            in.close();
        } catch (IOException ignored) {}
    }

    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 12345;

        Client client = new Client(hostName, portNumber);
        client.start();

    }
}
