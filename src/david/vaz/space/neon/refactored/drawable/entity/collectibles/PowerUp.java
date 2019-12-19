package david.vaz.space.neon.refactored.drawable.entity.collectibles;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import static david.vaz.space.neon.refactored.game.Constants.POWER_UP_SPEED;

public final class PowerUp extends AbstractEntity {

    private final Type type;
    private boolean isCollected;

    public PowerUp(double x, double y, Type type) {
        super(x, y, type.getImage(), type.getSpeed());
        this.type = type;
        isCollected = false;
        setDirection(Direction.SOUTH);
    }

    public void setCollected() {
        isCollected = true;
    }

    public boolean hasBeenCollected() {
        return isCollected;
    }

    public Type type() {
        return type;
    }

    public enum Type {
        EXTRA_DAMAGE(Image.EXTRA_DAMAGE),
        DOUBLE_SHOOTING(Image.DOUBLE_SHOOTING),
        EXTRA_LIFE(Image.EXTRA_LIFE),
        INCREASE_PLAYER_SPEED(Image.FASTER_PLAYER),
        INCREASE_BULLET_SPEED(Image.FASTER_BULLET);

        private Image image;
        private double speed;

        Type(Image image) {
            this.image = image;
            speed = POWER_UP_SPEED;
        }

        public static Type random() {

            int random = (int) (Math.random() * values().length);

            return values()[random];
        }

        private double getSpeed() {
            return speed;
        }

        private Image getImage() {
            return image;
        }
    }
}
