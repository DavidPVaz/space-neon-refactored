package david.vaz.space.neon.refactored.screen;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.InputHandler;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static david.vaz.space.neon.refactored.game.Constants.PADDING;

public abstract class AbstractScreen extends AbstractDrawable implements Screen {

    private Map<Input, InputHandler> validInputs;
    private Queue<Input> inputsToHandle;
    private Engine engine;

    public AbstractScreen(Image image, Engine engine) {
        super(PADDING, PADDING, image);
        this.validInputs = new HashMap<>();
        this.inputsToHandle = new LinkedList<>();
        this.engine = engine;
    }

    @Override
    public void processAllValidInputs() {

        Input input;

        while ((input = inputsToHandle.poll()) != null) {

            if (validInputs.containsKey(input)) {
                validInputs.get(input).handle();
            }

        }

    }

    public void submit(Input input) {
        inputsToHandle.offer(input);
    }

    protected void addInputHandler(Key key, Input.Type inputType, InputHandler inputHandler) {
        validInputs.put(new Input(key, inputType), inputHandler);
    }

    protected Engine getEngine() {
        return engine;
    }
}
