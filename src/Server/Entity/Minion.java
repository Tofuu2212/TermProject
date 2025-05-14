package Server.Entity;

//A minion is a unit with an automatic update method
//and only one spell, auto attack

import Server.Game;

public class Minion extends Unit {
    public Minion(Game map, int x, int y, int size) {
        super(map, x, y, size);
        health = 100;
    }
}
