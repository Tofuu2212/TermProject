package Server.Entity;

//Everything with an HP bar abilities is a Unit
//Projectiles are not units
//Structures are not units

import Server.Game;

public class Unit extends Entity {

    int health;
    int attackRange;

    public Unit(Game map, int x, int y, int size, double speed, int id) {
        super(map, x, y, size, speed, id);
        targetable = true;
    }
}
