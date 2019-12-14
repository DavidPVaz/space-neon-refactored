package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Shootable;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.LinkedList;
import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.*;

public abstract class Enemy extends AbstractEntity implements Hittable, Shootable {

    private int hp;
    private Bullet.Type bulletType;
    private int firingCooldown;

    public Enemy(double x, double y, Type type) {
        super(x, y, type.getImage(), type.getSpeed());
        this.hp = type.getHp();
        this.bulletType = Bullet.Type.RED;
        this.firingCooldown = ENEMIES_FIRING_COOLDOWN;
    }

    @Override
    public void takeHit(int damage) {
        hp -= damage;
    }

    @Override
    public boolean isDestroyed() {
        return hp <= 0;
    }

    @Override
    public List<Bullet> shoot() {

        firingCooldown--;

        if (firingCooldown > 0) {
            return null;
        }

        List<Bullet> bullets = createBullets();

        if (firingCooldown <= 0) {
            firingCooldown = ENEMIES_FIRING_COOLDOWN;
        }

        return bullets;
    }

    @Override
    public List<Bullet> createBullets() {

        List<Bullet> bullets = new LinkedList<>();

        bullets.add(new Bullet(getBulletXCoordinates(), getBulletYCoordinates(), bulletType, this));

        return bullets;
    }

    @Override
    public double getBulletXCoordinates() {
        return getMinX() + (getPicture().getWidth() / 6.0);
    }

    @Override
    public double getBulletYCoordinates() {
        return getMaxY() - 14;
    }

    public void boostHp(int hpIncrement) {
        this.hp += hpIncrement;
    }

    public enum Type {
        REGULAR(Image.NORMAL_ENEMY, REGULAR_ENEMY_SPEED, ENEMY_HP),
        DIAMOND(Image.DIAMOND_ENEMY, DIAMOND_ENEMY_SPEED, ENEMY_HP);

        private Image image;
        private double speed;
        private int hp;

        Type(Image image, double speed, int hp) {
            this.image = image;
            this.speed = speed;
            this.hp = hp;
        }

        private Image getImage() {
            return image;
        }

        private double getSpeed() {
            return speed;
        }

        private int getHp() {
            return hp;
        }

        public static Type random() {
            return Math.random() > 0.4 ? REGULAR : DIAMOND;
        }
    }
}
