package david.vaz.space.neon.refactored.input;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;

import java.util.Arrays;

public enum Key {
    UP(KeyboardEvent.KEY_UP),
    LEFT(KeyboardEvent.KEY_LEFT),
    RIGHT(KeyboardEvent.KEY_RIGHT),
    SPACE(KeyboardEvent.KEY_SPACE);

    private int keyCode;

    Key(int keyCode) {
        this.keyCode = keyCode;
    }

    public static Key withCode(int keyCode) {
        return Arrays.stream(values()).filter(key -> key.keyCode == keyCode).findFirst().orElse(null);
    }

    public int getKeyCode() {
        return keyCode;
    }
}
