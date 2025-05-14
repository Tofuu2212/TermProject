package Server.Entity;

//A minion is a unit with an automatic update method
//and only one spell, auto attack

import Message.Type;
import Server.Game;
import Server.Pathing.*;

import java.util.ArrayList;

import static Server.Pathing.Line.dist;

public class Minion extends Unit {

    public Minion(Game map, int x, int y, int id) {
        super(map, x, y, 10, 1, id);
        health = 100;
        attackRange = 50;
        type = Type.MINION;
    }

    public void logic() {
        this.setPath(map.player.x, map.player.y);
        Point n = path.generateNextPoint();

        Point playerLoc = new Point(map.player.x, map.player.y);
        if (dist(n, playerLoc) < this.size + this.attackRange) {
            System.out.println("evil minion: I'm attacking you");
        } else {
            x = (int) n.x;
            y = (int) n.y;
        }
    }
}
