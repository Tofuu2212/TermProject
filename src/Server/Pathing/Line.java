package Server.Pathing;

import Server.Game;

import java.util.ArrayList;

/*
If this is re-used in Views, tstep needs a multiplier based on FPS
for the animations
 */

public class Line {
    Point p1;
    Point p2;
    double t = 0;
    double tstep;
    boolean drawn = false;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        tstep = (1.0 / (dist(p1, p2) * Game.TERRAIN_MAP_SIZE / Game.MAP_SIZE));
    }

    //dist for doubles. there is intDist in Game class
    public static double dist(Point a, Point b) {
        double temp = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
        return Math.sqrt(temp);
    }

    public Point generateNextPoint() {
        if (t > 1)
            drawn = true;
        Point p = new Point((p1.x + ((p2.x - p1.x) * t)), (p1.y + ((p2.y - p1.y) * t)));
        t += tstep;
        return p;
    }

    public Point getOppositePoint() {
        double tPrime = t * -1;
        return new Point((p1.x + ((p2.x - p1.x) * tPrime)), (p1.y + ((p2.y - p1.y) * tPrime)));
    }

    public void reset() {
        t = 0;
        drawn = false;
    }

    public static Line makeLeftBisector(Point A, Point B) {
        double Px = (B.x + A.x) / 2.0;
        double Py = (B.y + A.y) / 2.0;
        Point P = new Point(Px, Py);
        double Qx = P.x - P.y + A.y;
        double Qy = P.x - A.x + P.y;
        Point Q = new Point(Qx, Qy);
        //System.out.println(P);
        //System.out.println(Q);
        return new Line(P, Q);
    }

    public static Line makeRightBisector(Point A, Point B) {
        double Px = (B.x + A.x) / 2.0;
        double Py = (B.y + A.y) / 2.0;
        Point P = new Point(Px, Py);
        double Qx = P.y - A.y + P.x;
        double Qy = P.y - P.x + A.y;
        Point Q = new Point(Qx, Qy);
        //System.out.println(P);
        //System.out.println(Q);
        return new Line(P, Q);
    }

//    public ArrayList<Point> getPath() {
//        Path path = new Path(p1, p2);
//        return path.path;
//    }

}
