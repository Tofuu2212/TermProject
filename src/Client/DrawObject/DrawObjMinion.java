package Client.DrawObject;

import java.awt.*;

public class DrawObjMinion extends DrawObject {

    private static final int DIAMETER = 25;

    public DrawObjMinion(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y, DIAMETER, DIAMETER);
}
