package david.vaz.space.neon.refactored.screen.game;

import david.vaz.space.neon.refactored.drawable.bar.ScreenBar;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.drawable.lifes.LifeIcon;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.game.Game;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.AbstractScreen;

import java.util.Stack;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class GameScreen extends AbstractScreen {

    private Game game;
    private Player playerOne;
    private Player playerTwo;
    private final ScreenBar topBar;
    private final ScreenBar bottomBar;

    public GameScreen(Engine engine) {
        super(Image.GAME_SCREEN, engine);
        topBar = new ScreenBar(TOP_BAR_X, TOP_BAR_Y, Image.TOP_BAR);
        bottomBar = new ScreenBar(BOTTOM_BAR_X, BOTTOM_BAR_Y, Image.BOTTOM_BAR);
    }

    @Override
    public void setup() {
        setupInputs();
    }

    @Override
    public void show() {
        super.show();
        topBar.show();
        bottomBar.show();

        setupPlayers();

        if (playerTwo == null) {
            game = new Game(playerOne);
        } else {
            game = new Game(playerOne, playerTwo);
        }

        new Thread(() -> getEngine().play(game)).start();
    }

    private void setupPlayers() {

        Stack<LifeIcon> playerOneLifeList = new Stack<>();

        for (int i = 0; i < PLAYERS_MAX_LIVES; i++) {
            playerOneLifeList.push(new LifeIcon(PLAYER_ONE_LIFE_X + i * PLAYER_ONE_LIVES_MARGIN, LIFE_ICON_Y, LifeIcon.Type.BLUE));
        }

        playerOne = new Player(PLAYER_ONE_INITIAL_X, PLAYERS_INITIAL_Y, Image.PLAYER_BLUE, Bullet.Type.BLUE, playerOneLifeList);

        if (!getEngine().getActiveState().equals(Engine.State.MULTI_PLAYER_GAME)) {
            return;
        }

        Stack<LifeIcon> playerTwoLifeList = new Stack<>();

        for (int i = 0; i < PLAYERS_MAX_LIVES; i++) {
            playerTwoLifeList.push(new LifeIcon(PLAYER_TWO_LIFE_X + i * PLAYER_TWO_LIVES_MARGIN, LIFE_ICON_Y, LifeIcon.Type.GREEN));
        }

        playerTwo = new Player(PLAYER_TWO_INITIAL_X, PLAYERS_INITIAL_Y, Image.PLAYER_GREEN, Bullet.Type.GREEN, playerTwoLifeList);
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

        addInputHandler(Key.SPACE, Input.Type.KEY_PRESS, () -> playerOne.fire());
        addInputHandler(Key.SPACE, Input.Type.KEY_RELEASE, () -> playerOne.stopFiring());

        addInputHandler(Key.D, Input.Type.KEY_PRESS, () -> playerTwo.addDirection(Direction.EAST));
        addInputHandler(Key.A, Input.Type.KEY_PRESS, () -> playerTwo.addDirection(Direction.WEST));
        addInputHandler(Key.W, Input.Type.KEY_PRESS, () -> playerTwo.addDirection(Direction.NORTH));
        addInputHandler(Key.S, Input.Type.KEY_PRESS, () -> playerTwo.addDirection(Direction.SOUTH));

        addInputHandler(Key.D, Input.Type.KEY_RELEASE, () -> playerTwo.removeDirection(Direction.EAST));
        addInputHandler(Key.A, Input.Type.KEY_RELEASE, () -> playerTwo.removeDirection(Direction.WEST));
        addInputHandler(Key.W, Input.Type.KEY_RELEASE, () -> playerTwo.removeDirection(Direction.NORTH));
        addInputHandler(Key.S, Input.Type.KEY_RELEASE, () -> playerTwo.removeDirection(Direction.SOUTH));

        addInputHandler(Key.T, Input.Type.KEY_PRESS, () -> playerTwo.fire());
        addInputHandler(Key.T, Input.Type.KEY_RELEASE, () -> playerTwo.stopFiring());

    }
}
