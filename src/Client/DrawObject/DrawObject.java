package Client.DrawObject;

import java.awt.*;
import Message.*;

public abstract class DrawObject {
    public int x;
    public int y;
    public int id;
    public Type type;

    public DrawObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public abstract void draw(Graphics g, int drawOffsetX, int drawOffsetY, int scale);
}
