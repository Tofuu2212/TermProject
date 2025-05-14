package Server;

/*This is currently a dummy parser class that only prints
the dummy data from the Message object
In the future it should actually parse key presses etc. and
send them to the Game object to update the Game's state


the keyword FINAL makes this class a Utility class.
also i renamed this idk if it's in GIT as the correct name??
 */

import Message.*;

public final class ServerMessageParser {

    static Type currentType;

    //probably has to be static and maybe also syncrhonized unless it's only called from one place like the main thread?
    public static void parse(Message message) {

        currentType = message.getType();

        switch (currentType) {
            case DUMMY_ONE:
                System.out.println("Type 1: " + message);
                break;
            case DUMMY_TWO:
                System.out.println("Type 2: " + message);
                break;
            default:
                System.out.println("ok... whatever ");
        }

    }

}
