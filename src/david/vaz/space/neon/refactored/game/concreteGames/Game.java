package david.vaz.space.neon.refactored.game.concreteGames;

public interface Game {

    void init();

    void loop();

    boolean doesNotEnd();

    void end();

    void dispose();

}
