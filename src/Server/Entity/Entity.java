package Server.Entity;

import Server.Game;
import Server.Pathing.*;

public class Entity {

    Game map;
    public int x;
    public int y;
    public int size;
    public double speed; //a multiplier of path tstep
    public int id;
    public Path path;

    public Entity(Game map, int x, int y, int speed, int id) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.id = id;
    }

    public void update() {

    }

    public String toString() {
        return ("[" + x + "," + y + "]");

    }
}
