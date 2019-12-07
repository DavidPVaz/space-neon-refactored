package david.vaz.space.neon.refactored.drawable.lifes;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.resources.Image;

public final class LifeIcon extends AbstractDrawable {

    private Type type;

    public LifeIcon(double x, double y, Type type) {
        super(x, y, type.getImage());
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        BLUE(Image.BLUE_HEART),
        GREEN(Image.GREEN_HEART);

        private Image image;

        Type(Image image) {
            this.image = image;
        }

        private Image getImage() {
            return image;
        }
    }
}
