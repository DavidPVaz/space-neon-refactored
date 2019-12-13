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
    public int getMinX() {
        return getPicture().getX();
    }

    @Override
    public int getMinY() {
        return getPicture().getY();
    }

    @Override
    public int getMaxX() {
        return getPicture().getMaxX();
    }

    @Override
    public int getMaxY() {
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

    protected void setDirection(Direction direction) {
        this.direction = direction;
    }

    protected Direction getDirection() {
        return direction;
    }

    protected double getSpeed() {
        return speed;
    }

    protected void incrementSpeed(int speed) {

        if (this.speed > 10) {
            return;
        }

        this.speed += speed;
    }

}
