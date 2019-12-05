package david.vaz.space.neon.refactored.drawable.entity.bullet;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;

import static david.vaz.space.neon.refactored.game.Constants.*;

public class Bullet extends AbstractEntity {

    private final Type type;
    private final Hittable owner;
    private boolean hasCollided;

    public Bullet(double x, double y, Type type, Hittable owner) {
        super(x, y, type.getImage(), type.getSpeed());
        this.type = type;
        this.owner = owner;
        hasCollided = false;
        setDirection(owner instanceof Player ? Direction.NORTH : Direction.SOUTH);
    }

    @Override
    public boolean cantMove() {
        return getMinY() + getDirection().getY() * getSpeed() < PADDING + BAR_HEIGHT || super.cantMove();
    }

    @Override
    public boolean collideWith(Collidable collidable) {
        return !collidable.equals(owner) && super.collideWith(collidable);
    }

    public void hit(Hittable hittable) {
        hittable.takeHit(type.getDamage());
        hasCollided = true;
    }

    public boolean hasCollided() {
        return hasCollided;
    }

    public enum Type {
        GREEN(2, Image.GREEN_BULLET, BULLET_SPEED),
        BLUE(2, Image.BLUE_BULLET, BULLET_SPEED),
        RED(2, Image.RED_BULLET, BULLET_SPEED);

        private int damage;
        private Image image;
        private int speed;

        Type(int damage, Image image, int speed) {
            this.damage = damage;
            this.image = image;
            this.speed = speed;
        }

        public int getDamage() {
            return damage;
        }

        public int getSpeed() {
            return speed;
        }

        public Image getImage() {
            return image;
        }
    }
}
