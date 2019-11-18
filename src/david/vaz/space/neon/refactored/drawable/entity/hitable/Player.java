package david.vaz.space.neon.refactored.drawable.entity.hitable;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hitable.enemy.Enemy;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Player extends AbstractEntity implements Hittable {

    private final Queue<Bullet.Type> bullets;
    private final List<Direction> directions;

    public Player(double x, double y, Image image, double speed) {
        super(x, y, image, speed);
        this.bullets = new LinkedList<>();
        this.directions = new ArrayList<>();
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
        //will have a list of life icons and reduce that list when hit
    }

    public void addBullet(Bullet.Type type) {
        bullets.offer(type);
    }

    public Bullet shoot() {

        if (bullets.isEmpty()) {
            return null;
        }

        return new Bullet(getBulletXCoordinates(), getMinY(), bullets.poll(), this);
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
        return getMinX() + (getPicture().getWidth() / 2);
    }

}
