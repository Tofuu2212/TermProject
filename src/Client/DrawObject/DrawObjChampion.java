package Client.DrawObject;

import Message.Type;

import java.awt.*;

public class DrawObjChampion extends DrawObject {

    private static final int radius = 20;

    public DrawObjChampion(int x, int y) {
        super(x, y);
        type = Type.CHAMPION;
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