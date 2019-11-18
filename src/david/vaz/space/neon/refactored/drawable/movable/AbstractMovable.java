package david.vaz.space.neon.refactored.drawable.movable;

import david.vaz.space.neon.refactored.drawable.AbstractDrawable;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import static david.vaz.space.neon.refactored.game.Constants.*;

public abstract class AbstractMovable extends AbstractDrawable implements Movable {

    private double speed;
    private Direction direction;

    public AbstractMovable(double x, double y, Image image, double speed) {
        super(x, y, image);
        this.speed = speed;
        this.direction = null;
    }

    //needs to be implemented in subclasses
    @Override
    public void update() {

        if (cantMove()) {
            return;
        }

        getPicture().translate(direction.getX() * speed, direction.getY() * speed);
    }

    @Override
    public boolean cantMove() {

        if (direction == null) {
            return true;
        }

        return getPicture().getX() + direction.getX() * speed < PADDING ||
                getPicture().getMaxX() + direction.getX() * speed > SCREEN_WIDTH + PADDING ||
                getPicture().getY() + direction.getY() * speed < PADDING ||
                getPicture().getMaxY() + direction.getY() * speed > SCREEN_HEIGHT + PADDING;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    protected double getSpeed() {
        return speed;
    }
}
