package david.vaz.space.neon.refactored.drawable.entity.hittable;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle.Obstacle;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.LinkedList;
import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.*;
import static david.vaz.space.neon.refactored.game.Constants.PADDING;

public class Player extends AbstractEntity implements Hittable {

    private final List<Direction> directions;
    private boolean firing;

    public Player(double x, double y, Image image) {
        super(x, y, image, PLAYERS_INITIAL_SPEED);
        this.directions = new LinkedList<>();
    }

    @Override
    public void move() {

        setDirection(getPressedDirection());

        if (cantMove()) {
            return;
        }

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
    public boolean willCollideWith(Collidable collidable) {
        return (collidable instanceof Enemy || collidable instanceof Obstacle) && super.willCollideWith(collidable);
    }

    @Override
    public void takeHit(int damage) {
        //will have a list of life icons and reduce that list when hit by a bullet or collide with an enemy
    }

    @Override
    public boolean isDestroyed() {
        return false; //will return the size of the life icons list
    }

    public void fire() {
        firing = true;
    }

    public void stopFiring() {
        firing = false;
    }

    public Bullet shoot() { //maybe turn this into shared method with enemies

        if (!firing) {
            return null;
        }

        return new Bullet(getBulletXCoordinates(), getBulletYCoordinates(), Bullet.Type.BLUE, this);
    }

    public void addDirection(Direction direction) {

        if (directions.contains(direction)) {
            return;
        }

        directions.add(direction);
    }

    public void removeDirection(Direction direction) {
        directions.remove(direction);
    }

    private Direction getPressedDirection() {

        if (directions.isEmpty()) {
            return null;
        }

        if (directions.size() == 2) {
            return Direction.resolveDiagonalDirection(directions.get(0), directions.get(1));
        }

        return directions.get(0);
    }

    private double getBulletXCoordinates() {
        return getMinX() + (getPicture().getWidth() / 6.0);
    }

    private double getBulletYCoordinates() {
        return getMinY() - 10;
    }

}
