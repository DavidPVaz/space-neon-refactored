package david.vaz.space.neon.refactored.screen.menu;

import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.AbstractScreen;

import java.util.HashMap;
import java.util.Map;

public class MenuScreen extends AbstractScreen {

    private Map<Option, Selection> options;

    public MenuScreen(Engine engine) {
        super(Image.MENU_SCREEN, engine);
        this.options = new HashMap<>();
    }

    @Override
    public void setup() {

    }

    private void setupOptions() {
        options.put(Option.SINGLE_PLAYER, () -> getEngine().setState(Engine.State.SINGLE_PLAYER_GAME));
        options.put(Option.MULTI_PLAYER, () -> getEngine().setState(Engine.State.MULTI_PLAYER_GAME));
        options.put(Option.VERSUS, () -> getEngine().setState(Engine.State.VERSUS));
        options.put(Option.INSTRUCTIONS, () -> getEngine().setState(Engine.State.INSTRUCTIONS));
    }
}
