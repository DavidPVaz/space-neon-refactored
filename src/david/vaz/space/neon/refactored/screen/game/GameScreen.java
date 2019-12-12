package david.vaz.space.neon.refactored.screen.game;

import david.vaz.space.neon.refactored.drawable.bar.ScreenBar;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.drawable.lifes.LifeIcon;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.game.concreteGames.Game;
import david.vaz.space.neon.refactored.game.concreteGames.SpaceNeon;
import david.vaz.space.neon.refactored.game.concreteGames.Versus;
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
        // Input setup need to be invoked before show,
        // since this screen work for both single and multi-player,
        // I need the engine state to build just one, or two players.
        // Otherwise I would need a specific screen for single, and another for multi.
    }

    @Override
    public void show() {
        setupInputs();
        super.show();
        topBar.show();
        bottomBar.show();

        setupPlayers();

        if (getEngine().getActiveState().equals(Engine.State.VERSUS)) {
            game = new Versus(playerOne, playerTwo);
        } else if (playerTwo == null) {
            game = new SpaceNeon(playerOne);
        } else {
            game = new SpaceNeon(playerOne, playerTwo);
        }

        new Thread(() -> getEngine().play(game)).start();
    }

    private void setupPlayers() {

        Stack<LifeIcon> playerOneLives = buildPlayerLives(PLAYER_ONE_LIFE_X, PLAYER_ONE_LIVES_MARGIN, LifeIcon.Type.BLUE);
        playerOne = new Player(PLAYER_ONE_INITIAL_X, PLAYERS_INITIAL_Y, Image.PLAYER_BLUE, Bullet.Type.BLUE, playerOneLives);

        Engine.State activeState = getEngine().getActiveState();

        if (activeState.equals(Engine.State.MULTI_PLAYER_GAME) || activeState.equals(Engine.State.VERSUS)) {

            Stack<LifeIcon> playerTwoLives = buildPlayerLives(PLAYER_TWO_LIFE_X, PLAYER_TWO_LIVES_MARGIN, LifeIcon.Type.GREEN);
            playerTwo = new Player(PLAYER_TWO_INITIAL_X, PLAYERS_INITIAL_Y, Image.PLAYER_GREEN, Bullet.Type.GREEN, playerTwoLives);
        }
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

        Engine.State activeState = getEngine().getActiveState();

        if (activeState.equals(Engine.State.MULTI_PLAYER_GAME) || activeState.equals(Engine.State.VERSUS)) {

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

    private Stack<LifeIcon> buildPlayerLives(double lifeInitialX, double livesMargin, LifeIcon.Type type) {

        Stack<LifeIcon> livesStack = new Stack<>();

        for (int i = 0; i < PLAYERS_MAX_LIVES; i++) {
            livesStack.push(new LifeIcon(lifeInitialX + i * livesMargin, LIFE_ICON_Y, type));
        }

        return livesStack;
    }
}
