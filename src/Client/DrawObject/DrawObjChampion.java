package Client.DrawObject;

import java.awt.*;

public class DrawObjChampion extends DrawObject {
    private static final int DIAMETER = 50;

    public DrawObjChampion(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x, y, DIAMETER, DIAMETER);
    }
}