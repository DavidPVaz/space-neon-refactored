package david.vaz.space.neon.refactored.drawable.entity.bullet;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Player;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class Bullet extends AbstractEntity {

    private final Type type;
    private final Hittable owner;
    private boolean hasCollided;

    public Bullet(double x, double y, Type type, Hittable owner) {
        super(x, y, type.getImage(), type.getSpeed());
        this.type = type;
        this.owner = owner;
        hasCollided = false;
        setDirection(owner instanceof Player && !((Player) owner).isReversed() ? Direction.NORTH : Direction.SOUTH);
    }

    @Override
    public boolean cantMove() {
        return getMinY() + getDirection().getY() * getSpeed() < PADDING + BAR_HEIGHT || super.cantMove();
    }

    @Override
    public boolean collideWith(Collidable collidable) {
        return collidable != owner && super.collideWith(collidable);
    }

    public void hit(Hittable hittable) {
        hittable.takeHit(type.getDamage());
        hasCollided = true;
    }

    public boolean hasCollided() {
        return hasCollided;
    }

    public enum Type {
        GREEN(BULLET_DAMAGE, Image.GREEN_BULLET, BULLET_SPEED),
        BLUE(BULLET_DAMAGE, Image.BLUE_BULLET, BULLET_SPEED),
        RED(BULLET_DAMAGE, Image.RED_BULLET, BULLET_RED_SPEED);

        private int damage;
        private Image image;
        private int speed;

        Type(int damage, Image image, int speed) {
            this.damage = damage;
            this.image = image;
            this.speed = speed;
        }

        public void incrementSpeed(int speed) {

            if (this.speed >= 16) {
                return;
            }

            this.speed += speed;
        }

        public void incrementDamage(int damage) {

            if (this.damage >= 10) {
                return;
            }

            this.damage += damage;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public void setDamage(int damage) {
            this.damage = damage;
        }

        private int getDamage() {
            return damage;
        }

        private int getSpeed() {
            return speed;
        }

        private Image getImage() {
            return image;
        }
    }
}
