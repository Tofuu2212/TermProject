package Server.Entity;

//A champion is a unit with custom controls and many spells
//In contrast, a minion has an automatic update cycle and only an auto attack

import Message.Type;
import Server.Game;
import Server.Pathing.Point;

public class Champion extends Unit {

    public Champion(Game map, int x, int y) {
        super(map, x, y, 20,1, 10);
        health = 200;
        type = Type.CHAMPION;
    }

    public void logic() {

        Point n = path.generateNextPoint();
        x = (int) n.x;
        y = (int) n.y;

    }
}
