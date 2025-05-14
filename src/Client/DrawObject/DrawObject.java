package Client.DrawObject;

import java.awt.*;

abstract class DrawObject {
    int x;
    int y;
    int id;

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

    public abstract void draw(Graphics2D g2d);
}
