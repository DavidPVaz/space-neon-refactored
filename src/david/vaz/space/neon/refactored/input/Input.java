package david.vaz.space.neon.refactored.input;

import java.util.Objects;

public final class Input {

    private Key key;
    private Type type;

    public Input(Key key, Type type) {
        this.key = key;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Input)) {
            return false;
        }

        Input input = (Input) o;

        return key == input.key && type == input.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, type);
    }

    public enum Type {
        KEY_PRESS,
        KEY_RELEASE
    }
}
