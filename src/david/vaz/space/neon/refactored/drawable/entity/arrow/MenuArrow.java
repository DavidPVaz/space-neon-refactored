package david.vaz.space.neon.refactored.drawable.entity.arrow;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.screen.menu.Option;

import static david.vaz.space.neon.refactored.game.Constants.ARROW_X;
import static david.vaz.space.neon.refactored.game.Constants.OPTIONS_DISTANCE;

public class MenuArrow extends AbstractDrawable {

    private Option selected;

    public MenuArrow(Option selected) {
        super(ARROW_X, 400, Image.MENU_ARROW);
        this.selected = selected;
    }

    public Option getSelected() {
        return selected;
    }

    public void previous() {
        System.out.println("on previous");

        if (selected.previous().equals(selected)) {
            System.out.println("they are equal");
            return;
        }

        System.out.println("moving up");
        selected = selected.previous();
        moveInY(-OPTIONS_DISTANCE);
    }

    public void next() {

        System.out.println("on next");

        if (selected.next().equals(selected)) {
            System.out.println("they are equal");
            return;
        }

        System.out.println("moving down");
        selected = selected.next();
        moveInY(OPTIONS_DISTANCE);
    }

    private void moveInY(double distance) {
        getPicture().translate(0, distance);
    }
}
