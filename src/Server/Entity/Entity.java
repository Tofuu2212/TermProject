package Server.Entity;

import Server.Game;
import Server.Pathing.*;
import Message.*;

public class Entity {

    Game map;
    public int x;
    public int y;
    public int size;
    public double speed; //a multiplier of path tstep
    public int id;
    public Path path;
    boolean targetable;
    public Type type;

    public Entity(Game map, int x, int y, int size, double speed, int id) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.id = id;
        setPath(256, 256);
    }

    public void setPath(int newX, int newY) {
        Point there = new Point(newX, newY);
        this.path = new Path(there, this);
    }

    public void update() {
        int[] previousPartition = {y * map.MAP_PARTITIONS / map.MAP_SIZE, x * map.MAP_PARTITIONS / map.MAP_SIZE};

        logic();

        int[] newPartition = {y * map.MAP_PARTITIONS / map.MAP_SIZE, x * map.MAP_PARTITIONS / map.MAP_SIZE};
        if (previousPartition[0] != newPartition[0] || previousPartition[1] != newPartition[1]) {
            map.entities[previousPartition[0]][previousPartition[1]].remove(this);
            map.entities[newPartition[0]][newPartition[1]].add(this);
        }
    }

    private void logic() {
//        Point n = path.generateNextPoint();
//        x = (int) n.x;
//        y = (int) n.y;
        x++;
        y++;
    }

    public String toString() {
        return ("[" + x + "," + y + "]");

    }
}
