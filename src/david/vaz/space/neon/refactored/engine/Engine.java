package david.vaz.space.neon.refactored.engine;

import david.vaz.space.neon.refactored.game.Game;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.screen.AbstractScreen;
import david.vaz.space.neon.refactored.screen.Screen;
import david.vaz.space.neon.refactored.screen.game.GameScreen;
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
    private State state = State.GAME;
    private Screen screen;
    private boolean running;
    private int currentFrames;
    private double lag;

    public Engine(long targetFrames) {
        this.targetFrames = targetFrames;
    }

    public void init() {

        addScreen(State.GAME, new GameScreen(this));

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
        }
    }

    public int getFPS() {
        return currentFrames;
    }

    public void play(Game game) {

        game.init();

        int oneSecond = 1;
        long milliSecondsPerFrame = TimeUnit.SECONDS.toMillis(oneSecond) / targetFrames;

        long lastSecond = 0;
        int howManyLoopsPerSecond = 0;

        while (game.doesNotEnd()) {

            long startTime = System.currentTimeMillis();

            processAllInputs();

            howManyLoopsPerSecond++;

            if (TimeUnit.MILLISECONDS.toSeconds(startTime) != lastSecond) {
                lastSecond = TimeUnit.MILLISECONDS.toSeconds(startTime);
                currentFrames = howManyLoopsPerSecond;
                howManyLoopsPerSecond = 0;
            }

            game.loop();

            long waitingValue = milliSecondsPerFrame - (System.currentTimeMillis() - startTime);

            sleep(waitingValue);
            System.out.println(currentFrames + "FPS");
            System.out.println(lag > 0 ? "LAG: " + lag + "ms" : "Running smoothly");
        }

        game.end();
    }

    private void sleep(long waitingValue) {

        if (waitingValue <= 0) {
            lag = Math.abs(waitingValue);
            return;
        }

        try {
            Thread.sleep(waitingValue);
        } catch (InterruptedException e) { // exceptions should not be silenced unless explicitly so.
            System.err.println(e.getMessage());
            // Thread woke up early
        }
    }

    private void addScreen(State state, AbstractScreen screen) {
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

    private void processAllInputs() {
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

    private enum State {
        GAME
    }
}
