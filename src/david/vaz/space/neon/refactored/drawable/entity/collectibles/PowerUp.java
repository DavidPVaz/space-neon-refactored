package david.vaz.space.neon.refactored.drawable.entity.collectibles;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import static david.vaz.space.neon.refactored.game.Constants.POWER_UP_SPEED;

public class PowerUp extends AbstractEntity {

    private boolean isCollected;
    private Type type;

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
        EXTRA_DAMAGE(POWER_UP_SPEED, Image.EXTRA_DAMAGE),
        DOUBLE_SHOOTING(POWER_UP_SPEED, Image.DOUBLE_SHOOTING),
        EXTRA_LIFE(POWER_UP_SPEED, Image.EXTRA_LIFE),
        INCREASE_PLAYER_SPEED(POWER_UP_SPEED, Image.FASTER_PLAYER),
        INCREASE_BULLET_SPEED(POWER_UP_SPEED, Image.FASTER_BULLET);

        private int speed;
        private Image image;

        Type(int speed, Image image) {
            this.speed = speed;
            this.image = image;
        }

        private int getSpeed() {
            return speed;
        }

        private Image getImage() {
            return image;
        }

        public static Type random() {

            int random = (int) (Math.random() * values().length);

            return values()[random];
        }
    }
}
