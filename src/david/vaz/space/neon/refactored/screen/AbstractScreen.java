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

    private Map<Input, InputHandler> inputs;
    private Engine engine;

    public AbstractScreen(Image image, Engine engine) {
        super(PADDING, PADDING, image);
        this.inputs = new HashMap<>();
        this.engine = engine;
    }

    @Override
    public void process(Input input) {
        if (!inputs.keySet().contains(input)) {
            return;
        }

        inputs.get(input).handle();
    }

    protected void addInputHandler(Key key, Input.Type inputType, InputHandler inputHandler) {
        inputs.put(new Input(key,inputType), inputHandler);
    }

    protected Engine getEngine() {
        return engine;
    }
}
