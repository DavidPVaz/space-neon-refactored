package david.vaz.space.neon.refactored.screen.game;

import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.game.Game;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.AbstractScreen;

import static david.vaz.space.neon.refactored.game.Constants.PLAYER_ONE_INITIAL_X;
import static david.vaz.space.neon.refactored.game.Constants.PLAYER_ONE_INITIAL_Y;

public class GameScreen extends AbstractScreen {

    private Player playerOne;

    public GameScreen(Engine engine) {
        super(Image.GAME_SCREEN, engine);
    }

    @Override
    public void setup() {
        setupPlayers();
        setupInputs();
    }

    @Override
    public void show() {
        super.show();

       getEngine().play(new Game(playerOne));

    }

    private void setupPlayers() {
        playerOne = new Player(PLAYER_ONE_INITIAL_X, PLAYER_ONE_INITIAL_Y, Image.PLAYER_BLUE);
    }

    private void setupInputs() {

        addInputHandler(Key.RIGHT, Input.Type.KEY_PRESS, () -> playerOne.addDirection(Direction.EAST));
        addInputHandler(Key.LEFT, Input.Type.KEY_PRESS, () -> playerOne.addDirection(Direction.WEST));
        addInputHandler(Key.UP, Input.Type.KEY_PRESS, () -> playerOne.addDirection(Direction.NORTH));
        addInputHandler(Key.DOWN, Input.Type.KEY_PRESS, () -> playerOne.addDirection(Direction.SOUTH));

        addInputHandler(Key.RIGHT, Input.Type.KEY_RELEASE, () -> playerOne.removeDirection(Direction.EAST));
        addInputHandler(Key.LEFT, Input.Type.KEY_RELEASE, () -> playerOne.removeDirection(Direction.WEST));
        addInputHandler(Key.UP, Input.Type.KEY_RELEASE, () -> playerOne.removeDirection(Direction.NORTH));
        addInputHandler(Key.DOWN, Input.Type.KEY_RELEASE, () -> playerOne.removeDirection(Direction.SOUTH));

        //addInputHandler(Key.SPACE, Input.Type.KEY_RELEASE, () -> playerOne.addBullet());

    }
}
