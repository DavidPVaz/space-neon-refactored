package david.vaz.space.neon.refactored.drawable.entity;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import static david.vaz.space.neon.refactored.game.Constants.*;

public abstract class AbstractEntity extends AbstractDrawable implements Collidable {

    private double speed;
    private Direction direction;

    protected AbstractEntity(double x, double y, Image image, double speed) {
        super(x, y, image);
        this.speed = speed;
    }

    @Override
    public final int getMinX() {
        return getPicture().getX();
    }

    @Override
    public final int getMinY() {
        return getPicture().getY();
    }

    @Override
    public final int getMaxX() {
        return getPicture().getMaxX();
    }

    @Override
    public final int getMaxY() {
        return getPicture().getMaxY();
    }

    @Override
    public boolean collideWith(Collidable collidable) {

        if (cantMove()) {
            return !cantMove();
        }

        return getMinX() < collidable.getMaxX() &&
                getMaxX() > collidable.getMinX() &&
                getMinY() < collidable.getMaxY() &&
                getMaxY() > collidable.getMinY();
    }

    public void move() {
        getPicture().translate(getDirection().getX() * getSpeed(), getDirection().getY() * getSpeed());
    }

    public boolean cantMove() {
        return getMaxY() + getDirection().getY() * getSpeed() > SCREEN_HEIGHT - BAR_HEIGHT - PADDING;
    }

    protected final void setDirection(Direction direction) {
        this.direction = direction;
    }

    protected final Direction getDirection() {
        return direction;
    }

    protected final double getSpeed() {
        return speed;
    }

    protected final void setSpeed(double speed) {
        this.speed = speed;
    }

    protected final void incrementSpeed(int speed) {

        if (this.speed > 10) {
            return;
        }

        this.speed += speed;
    }
}
