package david.vaz.space.neon.refactored.screen;

import david.vaz.space.neon.refactored.drawable.Drawable;
import david.vaz.space.neon.refactored.input.Input;

public interface Screen extends Drawable {

    default void setup() {
        /*
        This method needs to be default.
        Because game screen work for single-player, multi-player and versus,
        this setup(), for game screen, can only be invoked on show(), to evaluate engine state at that time.
        Otherwise I would need a specific screen for each of the above states.
        It is overridden by menu and instructions screen, that can have its setup invoked when added to the engine.
        */
    }

    void process(Input input);
}
