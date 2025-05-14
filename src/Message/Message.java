package Message;

//TODO: fill out constructor types and fix toString for easy debugging
// update ClientMessageHandler to parse or add a new parser class like Server has

import java.io.Serializable;

public class Message implements Serializable {
    int id;
    boolean kill;
    int x;
    int y;

    Type mainType;
    Type subType;

    public Message() {

    }

    public Type getType() { return null; }

    @Override
    public String toString() {
        return ("ok");
    }
}
