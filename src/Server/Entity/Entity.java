package Server.Entity;

import Server.Game;

public class Entity {

    Game map;
    int x;
    int y;
    int id;

    public Entity(Game map, int x, int y, int id) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public String toString() {
        return ("[" + x + "," + y + "]");

    }
}
