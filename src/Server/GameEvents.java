package Server;

public class GameEvents implements Runnable {

    Game myGame;

    public GameEvents(Game myGame) {
        this.myGame = myGame;
    }

    @Override
    public void run() {

        try {
            //Spawn champions and structures
            System.out.println("Game event 1...");
            Thread.sleep(1000);

            //Spawn minions
            System.out.println("Game event 2...");
            Thread.sleep(1000);

            //Spawn more minions?
            System.out.println("Game event 3...");
            Thread.sleep(1000);

            //not sure what else happens in a game of league,
            System.out.println("no more events...");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
