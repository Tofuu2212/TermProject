package Message;

import java.io.Serializable;

public class Message implements Serializable {
    //int clientID from;
    String dummydata;
    Type type;

    public Message() {
        dummydata = "empty";
        type = null;
    }

    public Message(String dummydata, Type type) {
        this.dummydata = dummydata;
        this.type = type;
    }

    public String getDummydata() {
        return dummydata;
    }
    public Type getType() { return type; }

    @Override
    public String toString() {
        return ("type: " + type + ", data: " + dummydata);
    }
}
