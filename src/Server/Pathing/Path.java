package Server.Pathing;

import Server.Entity.Entity;
import Server.Game;


//player does NOT ignore terrain
//TODO: point A MUST not be in a wall, add if statement
// if point A is in a wall, just return A -> B
//TODO: point B MUST not be in a wall, add if statement
// if point B is in a wall, find nearest point and call
// makePath on those two points
//Actually, it's sort of fine most of the time
//Not sure it's worth fixing this stuff if the whole thing
// is so doo doo.

//Path usage: create a line from A to B, then call getPath on that line
//you'd think i'd create the line here, but no I was too stupid

import java.util.ArrayList;

public class Path {
    ArrayList<Point> path = new ArrayList<>();
    Entity entity;
    Point A;
    Point B;
    Line l;
    boolean hasLine = false;

    public Path(Point B, Entity entity) {
        this.A = new Point(entity.x, entity.y);
        this.B = B;
        this.entity = entity;
        makePath();
    }

    //Projectiles use simple path
    public void makeSimplePath() {
        path.add(A);
        path.add(B);
    }

    //Units use this path maker
    public void makePath() {
        path.add(A);
        makePath(A, B, 0, 0);
    }

    //direction:
    // 1 = left, 0 = none, -1 = right
    public void makePath(Point P, Point Q, int direction, int attempts) {

        if (attempts == 10) return;

        Line PQ = new Line(P, Q);
        Point T = PQ.generateNextPoint();

        while (!T.inWall()) {
            T = PQ.generateNextPoint();
            if (PQ.drawn) {
                if (Math.abs(Q.x - path.get(path.size() - 1).x) < 2
                        || Math.abs(Q.y - path.get(path.size() - 1).y) < 2)
                    path.set(path.size() - 1, Q);
                else path.add(Q);
                return;
            }
        }

        Point p = T;
        while (T.inWall()) T = PQ.generateNextPoint(); //B must not be in wall
        Point q = T;

        Line pqBisectorLeft = Line.makeLeftBisector(p, q);
        Point t = pqBisectorLeft.generateNextPoint();

        //initially, no direction
        if (direction == 0) {
            direction = 1;
            Point tOpposite;
            while (t.inWall()) {
                tOpposite = pqBisectorLeft.getOppositePoint();
                if (!(tOpposite.inWall())) {
                    t = tOpposite;
                    direction = -1;
                    break;
                }
                t = pqBisectorLeft.generateNextPoint();
            }

            //going left
        } else if (direction == 1) {
            while (t.inWall()) {
                t = pqBisectorLeft.generateNextPoint();
            }

            //going right
        } else { //direction == -1
            Line pqBisectorRight = Line.makeRightBisector(p, q);
            t = pqBisectorRight.generateNextPoint();
            while (t.inWall()) {
                t = pqBisectorRight.generateNextPoint();
            }
        }

        //what does this do i forgot
        t.x = Math.floor(t.x);
        t.y = Math.floor(t.y);

        makePath(P, t, direction, attempts + 1);
        makePath(t, Q, direction, attempts + 1);

    }

    public Point generateNextPoint() {
        if (path.size() == 1) {
            return B;
        }
        if (!hasLine) {
            l = new Line(path.get(0), path.get(1));
            l.tstep *= entity.speed;
            hasLine = true;
        }

        if (l.t < 1) {
            return l.generateNextPoint();
        } else {
            path.remove(0);
            l.reset();
            hasLine = false;
        }

        return this.generateNextPoint();
    }
}
