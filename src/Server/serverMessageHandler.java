package Server;

/*This is currently a dummy parser class that only prints
the dummy data from the Message object
In the future it should actually parse key presses etc. and
send them to the Game object to update the Game's state


the keyword FINAL makes this class a Utility class.
also i renamed this idk if it's in GIT as the correct name??
 */

import Message.*;

public final class serverMessageHandler {

    //probably has to be static
    public static void parse(Message message) {
        System.out.println(message.getDummydata());
    }

}
