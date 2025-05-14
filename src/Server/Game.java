package Server;

import Server.Entity.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Game {

    final int MAP_SIZE = 256;
    final int MAP_PARTITIONS = 16;
    final int COLLISION_BUFFER = 20; //size of largest minion radius
    Set<Entity>[][] entities = new HashSet[MAP_PARTITIONS][MAP_PARTITIONS];

    Champion player;

}
