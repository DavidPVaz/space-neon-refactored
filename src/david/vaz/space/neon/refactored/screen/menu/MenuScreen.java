package david.vaz.space.neon.refactored.screen.menu;

import david.vaz.space.neon.refactored.drawable.arrow.MenuArrow;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.AbstractScreen;

import java.util.HashMap;
import java.util.Map;

public final class MenuScreen extends AbstractScreen {

    private Map<Option, Selection> options;
    private MenuArrow arrow;

    public MenuScreen(Engine engine) {
        super(Image.MENU_SCREEN, engine);
        this.options = new HashMap<>();
        this.arrow = new MenuArrow(Option.SINGLE_PLAYER);
    }

    @Override
    public void show() {
        super.show();
        arrow.show();
    }

    @Override
    public void hide() {
        arrow.hide();
        super.hide();
    }

    @Override
    public void setup() {
        setupOptions();
        setupInputs();
    }

    private void setupOptions() {
        options.put(Option.SINGLE_PLAYER, () -> getEngine().setActiveState(Engine.State.SINGLE_PLAYER));
        options.put(Option.MULTI_PLAYER, () -> getEngine().setActiveState(Engine.State.MULTI_PLAYER));
        options.put(Option.VERSUS, () -> getEngine().setActiveState(Engine.State.VERSUS));
        options.put(Option.INSTRUCTIONS, () -> getEngine().setActiveState(Engine.State.INSTRUCTIONS));
    }

    private void setupInputs() {
        addInputHandler(Key.UP, Input.Type.KEY_RELEASE, () -> arrow.previous());
        addInputHandler(Key.DOWN, Input.Type.KEY_RELEASE, () -> arrow.next());
        addInputHandler(Key.ENTER, Input.Type.KEY_PRESS, () -> options.get(arrow.getSelected()).select());
        addInputHandler(Key.Q, Input.Type.KEY_PRESS, () -> getEngine().quit());
    }
}
