package david.vaz.space.neon.refactored.screen.menu;

import david.vaz.space.neon.refactored.drawable.entity.arrow.MenuArrow;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.input.Input;
import david.vaz.space.neon.refactored.input.Key;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.AbstractScreen;

import java.util.HashMap;
import java.util.Map;

public class MenuScreen extends AbstractScreen {

    private Map<Option, Selection> options;
    private MenuArrow arrow;

    public MenuScreen(Engine engine) {
        super(Image.MENU_SCREEN, engine);
        this.options = new HashMap<>();
        this.arrow = new MenuArrow(Option.SINGLE_PLAYER);
    }

    @Override
    public void show() {
        System.out.println("showing");
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
        options.put(Option.SINGLE_PLAYER, () -> getEngine().setState(Engine.State.SINGLE_PLAYER_GAME));
        options.put(Option.MULTI_PLAYER, () -> getEngine().setState(Engine.State.MULTI_PLAYER_GAME));
        options.put(Option.VERSUS, () -> getEngine().setState(Engine.State.VERSUS));
        options.put(Option.INSTRUCTIONS, () -> getEngine().setState(Engine.State.INSTRUCTIONS));
    }

    private void setupInputs() {
        System.out.println("setting up inputs");
        addInputHandler(Key.UP, Input.Type.KEY_PRESS, () -> {
            System.out.println("pressing up");
            arrow.previous();
        });
        addInputHandler(Key.DOWN, Input.Type.KEY_PRESS, () -> {
            System.out.println("pressing down");
            arrow.next();
        });

        addInputHandler(Key.ENTER, Input.Type.KEY_PRESS, () -> {
            System.out.println("pressing enter");
            options.get(arrow.getSelected()).select();
        });
    }
}
