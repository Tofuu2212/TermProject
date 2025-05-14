package Client.DrawObject;

import Message.Type;

import java.awt.*;

public class DrawObjMinion extends DrawObject {

    private static final int radius = 10;

    public DrawObjMinion(int x, int y) {
        super(x, y);
        type = Type.MINION;
    }

    @Override
    public void draw(Graphics g, int drawOffsetX, int drawOffsetY, int scale) {

        System.out.println("drawing " + type + "x: " + drawOffsetX + " y: " + drawOffsetY);

        x *= scale;
        y *= scale;
        x += drawOffsetX;
        y += drawOffsetY;
        int diameter = 2 * radius;
        int topLeftX = x - radius;
        int topLeftY = y - radius;
        g.drawOval(topLeftX, topLeftY, diameter, diameter);
    }
}
