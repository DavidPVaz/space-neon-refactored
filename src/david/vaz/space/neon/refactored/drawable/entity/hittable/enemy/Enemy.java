package david.vaz.space.neon.refactored.drawable.entity.hittable.enemy;

import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.resources.Image;

public abstract class Enemy extends AbstractEntity implements Hittable {

    private int hp;

    Enemy(double x, double y, Type type) {
        super(x, y, type.getImage(), type.getSpeed());
        this.hp = type.getHp();
    }

    @Override
    public boolean collideWith(Collidable collidable) {
        return collidable instanceof Player && super.collideWith(collidable);
    }

    @Override
    public void takeHit(int damage) {
        hp -= damage;
    }

    @Override
    public boolean isDestroyed() {
        return hp <= 0;
    }

    public enum Type {
        REGULAR(Image.NORMAL_ENEMY, 3, 6),
        DIAMOND(Image.DIAMOND_ENEMY, 5, 6);

        private Image image;
        private double speed;
        private int hp;

        Type(Image image, double speed, int hp) {
            this.image = image;
            this.speed = speed;
            this.hp = hp;
        }

        public Image getImage() {
            return image;
        }

        public double getSpeed() {
            return speed;
        }

        public int getHp() {
            return hp;
        }

        public static Type random() {
            return Math.random() > 0.4 ? REGULAR : DIAMOND;
        }
    }
}
