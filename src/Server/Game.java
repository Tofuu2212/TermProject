package Server;

import Server.Entity.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import Message.*;

public class Game implements Runnable {

    public static final int MAP_SIZE = 512;
    int numEntities = 0;

    //Map Paritioning
    public final int MAP_PARTITIONS = 16;
    public final int COLLISION_BUFFER = 20; //size of largest minion radius
    public Set<Entity>[][] entities = new HashSet[MAP_PARTITIONS][MAP_PARTITIONS];

    //Terrain Map
    public static final int TERRAIN_MAP_SIZE = 64;
    public static int[][] terrainMap = new int[TERRAIN_MAP_SIZE][TERRAIN_MAP_SIZE];
    String terrainFile = "src/Server/terrain.txt";

    GameEvents gameEvents;

    public Champion player;
    ClientHandler playerClient;

    public Game(ClientHandler playerClient) throws FileNotFoundException {
        this.playerClient = playerClient;
        //MapPartition constructor:
        for (int i = 0; i < MAP_PARTITIONS; i++) {
            for (int j = 0; j < MAP_PARTITIONS; j++) {
                entities[i][j] = new HashSet<Entity>();
            }
        }
        //TerrainMap constructor: loadMap()
        java.io.File file = new java.io.File(terrainFile);
        Scanner input = new Scanner(file);
        for (int i = 0; i < TERRAIN_MAP_SIZE; i++) {
            for (int j = 0; j < TERRAIN_MAP_SIZE; j++) {
                terrainMap[i][j] = input.nextInt();
            }
        }

        player = spawnPlayer(40, 40);

        gameEvents = new GameEvents(this);
    }


    public synchronized Champion spawnPlayer(int x, int y) {
        Champion c = new Champion(this, x, y, numEntities);
        entities[y * MAP_PARTITIONS / MAP_SIZE][x * MAP_PARTITIONS / MAP_SIZE].add(c);
        numEntities++;
        return c;
    }

    //y is row, x is col
    //does not check that minion is spawned "in bounds" (0 to MAP_SIZE - 1)
    public synchronized Minion spawnMinion(int x, int y) {
        Minion m = new Minion(this, x, y, numEntities);
        entities[y * MAP_PARTITIONS / MAP_SIZE][x * MAP_PARTITIONS / MAP_SIZE].add(m);
        numEntities++;
        return m;
    }

    public synchronized void removeMinion(Minion m) {
        entities[m.y * MAP_PARTITIONS / MAP_SIZE][m.x * MAP_PARTITIONS / MAP_SIZE].remove(m);
    }

    public static int intSqrt(int s) {
        //source:
        //https://en.wikipedia.org/wiki/Integer_square_root#Example_implementation_in_C
        if (s <= 1)
            return s;
        int x0 = s / 2;
        int x1 = (x0 + s / x0) / 2;
        while (x1 < x0) {
            x0 = x1;
            x1 = (x0 + s / x0) / 2;
        }
        return x0;
    }

    public int intDist(Entity a, Entity b) {
        int temp = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
        if (temp < 0) temp *= -1;
        return intSqrt(temp);
    }

    public synchronized ArrayList<Entity> collisions(Entity a, int searchRadius) {
        ArrayList<Entity> hit = new ArrayList<>();

        //how close are we to a boundary?... do we have to check surrounding cells?
        //this could break if the circle is more than 2x the size of the map lol
        int xMinCell = (a.x - a.size - COLLISION_BUFFER) * MAP_PARTITIONS / MAP_SIZE;
        xMinCell = (xMinCell < 0) ? 0 : xMinCell;
        int xMaxCell = (a.x + a.size + COLLISION_BUFFER) * MAP_PARTITIONS / MAP_SIZE;
        xMaxCell = (xMaxCell > MAP_PARTITIONS - 1) ? MAP_PARTITIONS - 1 : xMaxCell;
        int yMinCell = (a.y - a.size - COLLISION_BUFFER) * MAP_PARTITIONS / MAP_SIZE;
        yMinCell = (yMinCell < 0) ? 0 : yMinCell;
        int yMaxCell = (a.y + a.size + COLLISION_BUFFER) * MAP_PARTITIONS / MAP_SIZE;
        yMaxCell = (yMaxCell > MAP_PARTITIONS - 1) ? MAP_PARTITIONS - 1 : yMaxCell;

        //check in this range of cells
        for (int i = xMinCell; i <= xMaxCell; i++) {
            for (int j = yMinCell; j <= yMaxCell; j++) {
                for (Entity e : entities[j][i]) { //j is y, i is x
                    if (intDist(a, e) < searchRadius + e.size && a != e) {
                        hit.add(e);
                    }
                }
            }
        }
        return hit;
    }

    @Override
    public void run() {
        //some kind of update loop
        Thread eventThread = new Thread(gameEvents);
        eventThread.start();

        while (true) {
            try {
                update();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void update() {
        for (int i = 0; i < MAP_PARTITIONS; i++) {
            for (int j = 0; j < MAP_PARTITIONS; j++) {
                for (Entity e : entities[j][i]) { //j is y, i is x
                    e.update();
                    System.out.println("on entity: " + e.toString() + ", " + e.type);
                    Message m = new Message(e.id, false, e.x, e.y, e.type, e.type);
                    playerClient.send(m);
                }
            }
        }
    }

    public synchronized void readMessages() {
        for (Message m : playerClient.messageQueue) {
            ServerMessageParser.parse(m, this);
        }
    }
}
