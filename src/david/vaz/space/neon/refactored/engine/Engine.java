package david.vaz.space.neon.refactored.engine;

import david.vaz.space.neon.refactored.engine.modules.AudioManager;
import david.vaz.space.neon.refactored.game.concreteGames.Game;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.screen.Screen;
import david.vaz.space.neon.refactored.screen.game.GameScreen;
import david.vaz.space.neon.refactored.screen.game.PauseScreenMock;
import david.vaz.space.neon.refactored.screen.instructions.InstructionsScreen;
import david.vaz.space.neon.refactored.screen.menu.MenuScreen;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class Engine implements KeyboardHandler {

    private static int currentFrames;

    private final long targetFrames;
    private final Map<State, Screen> screens;
    private final Map<Integer, Input> inputs;
    private final AudioManager manager;
    private State activeState = State.MENU;
    private State previousState;
    private Screen activeScreen;
    private boolean running;

    public Engine(long targetFrames) {
        this.targetFrames = targetFrames;
        screens = new HashMap<>();
        inputs = new LinkedHashMap<>();
        manager = AudioManager.getInstance();
    }

    public static int getFPS() {
        return currentFrames;
    }

    public void init() {

        manager.setup();

        addScreen(State.MENU, new MenuScreen(this));

        Screen gameScreen = new GameScreen(this, new PauseScreenMock());
        addScreen(State.SINGLE_PLAYER, gameScreen);
        addScreen(State.MULTI_PLAYER, gameScreen);
        addScreen(State.VERSUS, gameScreen);

        addScreen(State.INSTRUCTIONS, new InstructionsScreen(this));

        activeScreen = screens.get(activeState);

        Keyboard keyboard = new Keyboard(this);

        for (Key key : Key.values()) {
            addListener(keyboard, key.getKeyCode(), KeyboardEventType.KEY_PRESSED);
            addListener(keyboard, key.getKeyCode(), KeyboardEventType.KEY_RELEASED);
        }

        running = true;
    }

    public void start() {

        activeScreen.show();

        while (running) {

            checkActiveScreen();

            if (activeState == State.MENU || activeState == State.INSTRUCTIONS || activeState == State.PAUSED) {
                processAllPressedInputs();
            }
        }
    }

    public void play(Game game) {

        game.init();

        int oneSecond = 1;
        long milliSecondsPerFrame = TimeUnit.SECONDS.toMillis(oneSecond) / targetFrames;

        long lastSecond = 0;
        int howManyLoopsPerSecond = 0;

        while (game.doesNotEnd()) {

            if (activeState == State.PAUSED) {
                continue;
            }

            long startTime = System.currentTimeMillis();

            processAllPressedInputs();

            howManyLoopsPerSecond++;

            if (TimeUnit.MILLISECONDS.toSeconds(startTime) != lastSecond) {
                lastSecond = TimeUnit.MILLISECONDS.toSeconds(startTime);
                currentFrames = howManyLoopsPerSecond;
                howManyLoopsPerSecond = 0;
            }

            game.loop();

            long waitingValue = milliSecondsPerFrame - (System.currentTimeMillis() - startTime);

            sleep(waitingValue);
        }

        game.end();
        setActiveState(State.MENU);
    }

    public void setActiveState(State activeState) {
        previousState = this.activeState;
        this.activeState = activeState;
    }

    public State getActiveState() {
        return activeState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void quit() {
        manager.close();
        running = false;
        System.exit(0);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        Key key = Key.withCode(keyboardEvent.getKey());

        synchronized (inputs) {
            inputs.put(keyboardEvent.getKey(), new Input(key, Input.Type.KEY_PRESS));
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        Key key = Key.withCode(keyboardEvent.getKey());

        synchronized (inputs) {
            inputs.remove(keyboardEvent.getKey());
        }

        activeScreen.process(new Input(key, Input.Type.KEY_RELEASE));
    }

    public enum State {
        MENU,
        SINGLE_PLAYER,
        MULTI_PLAYER,
        VERSUS,
        INSTRUCTIONS,
        PAUSED
    }

    private void sleep(long waitingValue) {

        if (waitingValue <= 0) {
            return;
        }

        try {
            Thread.sleep(waitingValue);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private void addScreen(State state, Screen screen) {
        screens.put(state, screen);
        screen.setup();
    }

    private void addListener(Keyboard keyboard, int key, KeyboardEventType type) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(key);
        event.setKeyboardEventType(type);
        keyboard.addEventListener(event);
    }

    private void checkActiveScreen() {

        if (activeState == State.PAUSED) {
            return;
        }

        Screen screen = screens.get(activeState);

        if (screen != activeScreen) {
            activeScreen.hide();
            activeScreen = screen;
            activeScreen.show();
        }
    }

    private void processAllPressedInputs() {
        synchronized (inputs) {
            inputs.values().forEach(activeScreen::process);
        }
    }
}
