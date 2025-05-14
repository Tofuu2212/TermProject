package Server.Entity;

//A champion is a unit with custom controls and many spells
//In contrast, a minion has an automatic update cycle and only an auto attack

import Server.Game;

public class Champion extends Unit {

    public Champion(Game map, int x, int y, int size) {
        super(map, x, y, size);
        health = 200;
    }
}
