package Server.Pathing;

import Server.Game;

public class Point {

    public double x = 0;
    public double y = 0;

    public Point(double x, double y) {
        this.x = x < 0 ? 0 : (x >= Game.MAP_SIZE ? Game.MAP_SIZE - 1 : x);
        this.y = y < 0 ? 0 : (y >= Game.MAP_SIZE ? Game.MAP_SIZE - 1 : y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Boolean inWall() {
        return Game.terrainMap[(int) (y * Game.TERRAIN_MAP_SIZE / Game.MAP_SIZE)]
                [(int) (x * Game.TERRAIN_MAP_SIZE / Game.MAP_SIZE)] == 1;
    }

}
