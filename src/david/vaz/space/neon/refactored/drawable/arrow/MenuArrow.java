package david.vaz.space.neon.refactored.drawable.arrow;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.menu.Option;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class MenuArrow extends AbstractDrawable {

    private Option selected;

    public MenuArrow(Option selected) {
        super(ARROW_X, ARROW_INITIAL_Y, Image.MENU_ARROW);
        this.selected = selected;
    }

    public Option getSelected() {
        return selected;
    }

    public void previous() {

        if (selected.previous() == selected) {
            return;
        }

        selected = selected.previous();
        moveInY(-OPTIONS_DISTANCE);
    }

    public void next() {

        if (selected.next() == selected) {
            return;
        }

        selected = selected.next();
        moveInY(OPTIONS_DISTANCE);
    }

    private void moveInY(double distance) {
        getPicture().translate(0, distance);
    }
}
