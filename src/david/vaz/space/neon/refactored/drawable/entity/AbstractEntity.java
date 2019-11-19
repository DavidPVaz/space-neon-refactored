package david.vaz.space.neon.refactored.drawable.entity;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import static david.vaz.space.neon.refactored.game.Constants.*;
import static david.vaz.space.neon.refactored.game.Constants.PADDING;

public abstract class AbstractEntity extends AbstractDrawable implements Movable, Collidable {

    private double speed;
    private Direction direction;

    public AbstractEntity(double x, double y, Image image, double speed) {
        super(x, y, image);
        this.speed = speed;
    }

    @Override
    public void move() {
        getPicture().translate(getDirection().getX() * getSpeed(), getDirection().getY() * getSpeed());
    }

    @Override
    public boolean cantMove() {

        if (getDirection() == null) {
            return true;
        }

        return getMinX() + getDirection().getX() * getSpeed() < PADDING ||
                getMaxX() + getDirection().getX() * getSpeed() > SCREEN_WIDTH + PADDING ||
                getMinY() + getDirection().getY() * getSpeed() < PADDING ||
                getMaxY() + getDirection().getY() * getSpeed() > SCREEN_HEIGHT + PADDING;
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
    public boolean willCollideWith(Collidable collidable) {

        if (cantMove()) {
            return !cantMove();
        }

        return getMinX() + getDirection().getX() * getSpeed() < collidable.getMaxX() &&
                getMaxX() + getDirection().getX() * getSpeed() > collidable.getMinX() &&
                getMinY() + getDirection().getY() * getSpeed() < collidable.getMaxY() &&
                getMaxY() + getDirection().getY() * getSpeed() > collidable.getMinY();
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

}