package david.vaz.space.neon.refactored.screen.game;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.resources.Image;

import static david.vaz.space.neon.refactored.game.Constants.BAR_HEIGHT;
import static david.vaz.space.neon.refactored.game.Constants.PADDING;

public final class PauseScreenMock extends AbstractDrawable {

    public PauseScreenMock() {
        super(PADDING, BAR_HEIGHT, Image.PAUSE_SCREEN);
    }
}
