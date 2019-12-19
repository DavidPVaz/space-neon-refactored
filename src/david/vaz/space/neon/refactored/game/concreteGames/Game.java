package david.vaz.space.neon.refactored.game.concreteGames;

import david.vaz.space.neon.refactored.engine.Engine;

public interface Game {

    void init(Engine.State state);

    void loop();

    boolean doesNotEnd();

    void end();

    void dispose();
}
