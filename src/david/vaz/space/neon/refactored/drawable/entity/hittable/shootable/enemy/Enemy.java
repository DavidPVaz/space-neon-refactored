package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Shootable;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.LinkedList;
import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.*;

public abstract class Enemy extends AbstractEntity implements Hittable, Shootable {

    private int hp;
    private final Bullet.Type bulletType;
    private int firingCooldown;

    protected Enemy(double x, double y, Type type) {
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
    public final boolean isDestroyed() {
        return hp <= 0;
    }

    @Override
    public List<Bullet> shoot() {

        firingCooldown--;

        if (firingCooldown > 0) {
            return null;
        }

        List<Bullet> bullets = getProjectiles();

        if (firingCooldown <= 0) {
            firingCooldown = ENEMIES_FIRING_COOLDOWN;
        }

        return bullets;
    }

    @Override
    public List<Bullet> getProjectiles() {

        List<Bullet> bullets = new LinkedList<>();

        bullets.add(new Bullet(getProjectilesXCoordinates(), getProjectilesYCoordinates(), bulletType, this));

        return bullets;
    }

    @Override
    public final double getProjectilesXCoordinates() {
        return getMinX() + (getPicture().getWidth() / 6.0);
    }

    @Override
    public final double getProjectilesYCoordinates() {
        return getMaxY() - 14;
    }

    public final void boostHp(int hpIncrement) {
        hp += hpIncrement;
    }

    protected final int getHp() {
        return hp;
    }

    protected final void decrementHp(int damage) {
        hp -= damage;
    }

    protected final int getFiringCooldown() {
        return firingCooldown;
    }

    protected final void setFiringCooldown(int firingCooldown) {
        this.firingCooldown = firingCooldown;
    }

    protected final void decrementFiringCooldown() {
        firingCooldown--;
    }

    protected final Bullet.Type getBulletType() {
        return bulletType;
    }

    public enum Type {
        REGULAR(Image.NORMAL_ENEMY, REGULAR_ENEMY_SPEED, ENEMY_HP),
        DIAMOND(Image.DIAMOND_ENEMY, DIAMOND_ENEMY_SPEED, ENEMY_HP),
        BOSS(Image.BOSS, BOSS_SPEED, BOSS_HP);

        private Image image;
        private double speed;
        private int hp;

        Type(Image image, double speed, int hp) {
            this.image = image;
            this.speed = speed;
            this.hp = hp;
        }

        public static Type random() {
            return Math.random() > 0.4 ? REGULAR : DIAMOND;
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
    }
}
