package Message;

//TODO: fill out constructor types and fix toString for easy debugging
// update ClientMessageHandler to parse or add a new parser class like Server has

import java.io.Serializable;

public class Message implements Serializable {
    public int id;
    public boolean kill;
    public int x;
    public int y;

    public Type mainType;
    public Type subType;

    public Message(int id, boolean kill, int x, int y, Type mainType, Type subType) {
        this.id = id;
        this.kill = kill;
        this.x = x;
        this.y = y;
        this.mainType = mainType;
        this.subType = subType;
    }

    @Override
    public String toString() {
        return ("ok");
    }
}
