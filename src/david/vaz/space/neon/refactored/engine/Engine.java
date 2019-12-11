package david.vaz.space.neon.refactored.engine;

import david.vaz.space.neon.refactored.game.Game;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.screen.Screen;
import david.vaz.space.neon.refactored.screen.game.GameScreen;
import david.vaz.space.neon.refactored.screen.menu.MenuScreen;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.util.*;
import java.util.concurrent.TimeUnit;

public final class Engine implements KeyboardHandler {

    private final Map<State, Screen> screens = new HashMap<>();
    private final Map<Integer, Input> inputs = new LinkedHashMap<>();
    private final long targetFrames;
    private State state = State.MENU;
    private Screen screen;
    private boolean running;
    private static int currentFrames;

    public Engine(long targetFrames) {
        this.targetFrames = targetFrames;
    }

    public static int getFPS() {
        return currentFrames;
    }

    public void init() {

        addScreen(State.MENU, new MenuScreen(this));
        addScreen(State.SINGLE_PLAYER_GAME, new GameScreen(this));

        screen = screens.get(state);

        Keyboard keyboard = new Keyboard(this);

        for (Key key : Key.values()) {
            addListener(keyboard, key.getKeyCode(), KeyboardEventType.KEY_PRESSED);
            addListener(keyboard, key.getKeyCode(), KeyboardEventType.KEY_RELEASED);
        }

        running = true;
    }

    public void start() {

        screen.show();

        while (running) {
            checkActiveScreen();
            processAllPressedInputs();
        }
    }

    public void play(Game game) {

        game.init();

        int oneSecond = 1;
        long milliSecondsPerFrame = TimeUnit.SECONDS.toMillis(oneSecond) / targetFrames;

        long lastSecond = 0;
        int howManyLoopsPerSecond = 0;

        while (game.doesNotEnd()) {

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
    }

    public void setState(State state) {
        this.state = state;
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

        Screen screen = screens.get(state);

        if (screen != this.screen) {
            this.screen.hide();
            this.screen = screen;
            this.screen.show();
        }
    }

    private void processAllPressedInputs() {
        synchronized (inputs) {
            inputs.values().forEach(screen::process);
        }
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

        screen.process(new Input(key, Input.Type.KEY_RELEASE));
    }

    public enum State {
        MENU,
        SINGLE_PLAYER_GAME,
        MULTI_PLAYER_GAME,
        VERSUS,
        INSTRUCTIONS
    }
}
