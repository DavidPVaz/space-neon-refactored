package david.vaz.space.neon.refactored.drawable.entity.bullet;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;

public class Bullet extends AbstractEntity {

    private final Type type;
    private final Hittable owner;

    public Bullet(double x, double y, Type type, Hittable owner) {
        super(x, y, type.getImage(), type.getSpeed());
        this.type = type;
        this.owner = owner;
        setDirection(owner instanceof Player ? Direction.NORTH : Direction.SOUTH);
    }

    @Override
    public boolean willCollideWith(Collidable collidable) {
        return !collidable.equals(owner) && super.willCollideWith(collidable);
    }

    public void hit(Hittable hittable) {
        hittable.takeHit(type.getDamage());
    }

    public enum Type {
        GREEN(2, 7, Image.GREEN_BULLET),
        BLUE(2, 4, Image.BLUE_BULLET),
        RED(2, 4, Image.RED_BULLET);

        private int damage;
        private int speed;
        private Image image;

        Type(int damage, int speed, Image image) {
            this.damage = damage;
            this.speed = speed;
            this.image = image;
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