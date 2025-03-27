/**
 * CLIENT PURPOSE:
 * the user runs Client.java if they want to participate in a game. Client.java creates a socket
 * connection with Server.java
 *
 * RUNNING CLIENT:
 * must have a server running with matching port and name.
 * go into IntelliJ Edit Run Configuratinon > Modify Options > allow multiple instances
 * this way, multiple clients can run with the same server. This is why ClientHandler is multithreaded
 *
 * CHANGES TO BE MADE:
 * properly closing all sockets.
 * change stream to serialized object stream.
 * the speak() loop is a double loop when it can probably be a single loop
 * exception handling could use a pass over
 *
 *
 * Nathan Zimet 2/26/25:
 * Created this class from ChatGPT origins of the simplest possible Client-Server setup in Java and modifying it
 * to be more like CS151 echo.
 */

package Client;

import java.io.*;
import java.net.*;

public class Client {

    protected Socket myClientSocket;
    PrintWriter out;
    BufferedReader in;
    BufferedReader stdIn;

    public Client(String host, int port) throws IOException {
        myClientSocket = new Socket(host, port);
        initStreams();
    }

    //This is a mirror of Client Handler communication streams
    public void initStreams() {
        try {
            out = new PrintWriter(myClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //infinite loop while Client is running to talk to Server
    public void speak() {
        while (true) {
            try {
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                    System.out.println("Server: " + in.readLine());
                    if (userInput.equals("bye"))
                        break;
                }

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 12345;

        Client client = new Client(hostName, portNumber);
        client.speak();

    }
}
