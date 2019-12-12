package david.vaz.space.neon.refactored.screen.instructions;

import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.AbstractScreen;

public final class InstructionsScreen extends AbstractScreen {

    public InstructionsScreen(Engine engine) {
        super(Image.INSTRUCTIONS_SCREEN, engine);
    }

    @Override
    public void setup() {
        addInputHandler(Key.ESC, Input.Type.KEY_RELEASE, () -> getEngine().setActiveState(Engine.State.MENU));
    }
}
