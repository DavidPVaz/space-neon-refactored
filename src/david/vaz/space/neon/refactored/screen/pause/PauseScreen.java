package david.vaz.space.neon.refactored.screen.pause;

import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.AbstractScreen;

public class PauseScreen extends AbstractScreen {

    public PauseScreen(Engine engine) {
        super(Image.PAUSE_SCREEN, engine);
    }

    @Override
    public void setup() {
        addInputHandler(Key.ESC, Input.Type.KEY_RELEASE, () -> {

            Engine engine = getEngine();

            if (engine.getActiveState() != Engine.State.PAUSED) {
                return;
            }

            Engine.State previousState = engine.getPreviousState();

            engine.setActiveState(previousState == Engine.State.MULTI_PLAYER ? Engine.State.MULTI_PLAYER :
                    previousState == Engine.State.SINGLE_PLAYER ? Engine.State.SINGLE_PLAYER :
                            Engine.State.VERSUS);
        });

        addInputHandler(Key.M, Input.Type.KEY_PRESS, () -> {

            Engine engine = getEngine();

            if (engine.getActiveState() != Engine.State.PAUSED) {
                return;
            }

            //gameScreen.dispose();
            getEngine().setActiveState(Engine.State.MENU);
        });
    }
}
