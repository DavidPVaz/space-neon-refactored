package david.vaz.space.neon.refactored.screen.game;

import david.vaz.space.neon.refactored.drawable.bar.ScreenBar;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Player;
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
    private final PauseScreenMock pauseScreen;
    private final ScreenBar topBar;
    private final ScreenBar bottomBar;

    public GameScreen(Engine engine, PauseScreenMock pauseScreen) {
        super(Image.GAME_SCREEN, engine);
        this.pauseScreen = pauseScreen;
        topBar = new ScreenBar(TOP_BAR_X, TOP_BAR_Y, Image.TOP_BAR);
        bottomBar = new ScreenBar(BOTTOM_BAR_X, BOTTOM_BAR_Y, Image.BOTTOM_BAR);
    }

    @Override
    public void show() {
        setupPlayers();
        setupInputs();
        super.show();
        topBar.show();
        bottomBar.show();

        game = getEngine().getActiveState() == Engine.State.VERSUS ?
                new Versus(playerOne, playerTwo) :
                playerTwo == null ?
                        new SpaceNeon(playerOne) :
                        new SpaceNeon(playerOne, playerTwo);

        new Thread(() -> getEngine().play(game)).start();
    }

    @Override
    public void hide() {
        playerOne = null;
        playerTwo = null;
        clearInputs();
        topBar.hide();
        bottomBar.hide();
        super.hide();
    }

    private void setupPlayers() {

        Stack<LifeIcon> playerOneLives = buildPlayerLives(PLAYER_ONE_LIFE_X, PLAYER_ONE_LIVES_MARGIN, LifeIcon.Type.GREEN);
        playerOne = new Player(PLAYER_ONE_INITIAL_X, PLAYERS_INITIAL_Y, Image.PLAYER_GREEN, Bullet.Type.GREEN, playerOneLives, false);

        Engine.State activeState = getEngine().getActiveState();

        if (activeState == Engine.State.MULTI_PLAYER) {

            Stack<LifeIcon> playerTwoLives = buildPlayerLives(PLAYER_TWO_LIFE_X, PLAYER_TWO_LIVES_MARGIN, LifeIcon.Type.BLUE);
            playerTwo = new Player(PLAYER_TWO_INITIAL_X, PLAYERS_INITIAL_Y, Image.PLAYER_BLUE, Bullet.Type.BLUE, playerTwoLives, false);
            return;
        }

        if (activeState == Engine.State.VERSUS) {

            Stack<LifeIcon> playerTwoLives = buildPlayerLives(PLAYER_TWO_LIFE_X, PLAYER_TWO_LIVES_MARGIN, LifeIcon.Type.BLUE);
            playerTwo = new Player(PLAYER_TWO_INITIAL_X, PLAYER_TWO_REVERSED_INITIAL_Y, Image.PLAYER_BLUE_REVERSED, Bullet.Type.BLUE, playerTwoLives, true);
        }
    }

    private void setupInputs() {

        Engine engine = getEngine();

        addInputHandler(Key.ESC, Input.Type.KEY_RELEASE, () -> {

            if (engine.getActiveState() != Engine.State.PAUSED) {
                engine.setActiveState(Engine.State.PAUSED);
                pauseScreen.show();
                return;
            }

            Engine.State previousState = engine.getPreviousState();

            engine.setActiveState(previousState == Engine.State.MULTI_PLAYER ? Engine.State.MULTI_PLAYER :
                    previousState == Engine.State.SINGLE_PLAYER ? Engine.State.SINGLE_PLAYER :
                            Engine.State.VERSUS);
            pauseScreen.hide();
        });

        addInputHandler(Key.M, Input.Type.KEY_RELEASE, () -> {

            if (engine.getActiveState() == Engine.State.PAUSED) {
                pauseScreen.hide();
                dispose();
            }
        });

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

        if (playerTwo == null) {
            return;
        }

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

    private Stack<LifeIcon> buildPlayerLives(double lifeInitialX, double livesMargin, LifeIcon.Type type) {

        Stack<LifeIcon> livesStack = new Stack<>();

        for (int i = 0; i < PLAYERS_MAX_LIVES; i++) {
            livesStack.push(new LifeIcon(lifeInitialX + i * livesMargin, LIFE_ICON_Y, type));
        }

        return livesStack;
    }

    private void dispose() {
        game.dispose();
    }
}
