package david.vaz.space.neon.refactored.screen;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.InputHandler;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.HashMap;
import java.util.Map;

import static david.vaz.space.neon.refactored.game.Constants.PADDING;

public abstract class AbstractScreen extends AbstractDrawable implements Screen {

    private Map<Input, InputHandler> validInputs;
    private Engine engine;

    protected AbstractScreen(Image image, Engine engine) {
        super(PADDING, PADDING, image);
        this.validInputs = new HashMap<>();
        this.engine = engine;
    }

    public final void process(Input input) {

        if (!validInputs.containsKey(input)) {
            return;
        }

        validInputs.get(input).handle();
    }

    protected final void addInputHandler(Key key, Input.Type inputType, InputHandler inputHandler) {
        validInputs.put(new Input(key, inputType), inputHandler);
    }

    protected final Engine getEngine() {
        return engine;
    }

    protected final void clearInputs() {
        validInputs.clear();
    }
}
