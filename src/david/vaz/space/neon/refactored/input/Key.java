package david.vaz.space.neon.refactored.input;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;

import java.util.Arrays;

public enum Key {
    W(KeyboardEvent.KEY_W),
    A(KeyboardEvent.KEY_A),
    D(KeyboardEvent.KEY_D),
    S(KeyboardEvent.KEY_S),
    T(KeyboardEvent.KEY_T),
    UP(KeyboardEvent.KEY_UP),
    LEFT(KeyboardEvent.KEY_LEFT),
    RIGHT(KeyboardEvent.KEY_RIGHT),
    DOWN(KeyboardEvent.KEY_DOWN),
    SPACE(KeyboardEvent.KEY_SPACE),
    ENTER(KeyboardEvent.KEY_ENTER),
    ESC(KeyboardEvent.KEY_ESC),
    Q(KeyboardEvent.KEY_Q),
    M(KeyboardEvent.KEY_M);

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
