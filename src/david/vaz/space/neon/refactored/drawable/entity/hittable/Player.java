package david.vaz.space.neon.refactored.drawable.entity.hittable;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static david.vaz.space.neon.refactored.game.Constants.PLAYERS_INITIAL_SPEED;

public class Player extends AbstractEntity implements Hittable {

    private final Queue<Bullet.Type> bullets;
    private final List<Direction> directions;

    public Player(double x, double y, Image image) {
        super(x, y, image, PLAYERS_INITIAL_SPEED);
        this.bullets = new LinkedList<>();
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
    public boolean willCollideWith(Collidable collidable) {
        return collidable instanceof Enemy && super.willCollideWith(collidable);
    }

    @Override
    public void takeHit(int damage) {
        //will have a list of life icons and reduce that list when hit by a bullet or collide with an enemy
    }

    @Override
    public boolean isDestroyed() {
        return false; //will return the size of the life icons list
    }

    public void addBullet(Bullet.Type type) {
        bullets.offer(type);
    }

    public Bullet shoot() { //maybe turn this into shared method with enemies

        if (bullets.isEmpty()) {
            return null;
        }

        return new Bullet(getBulletXCoordinates(), getBulletYCoordinates(), bullets.poll(), this);
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
        return getMinX() + (getPicture().getWidth() / 6);
    }

    private double getBulletYCoordinates() {
        return getMinY() - 10;
    }

}
