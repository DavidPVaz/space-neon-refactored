package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.game.Direction;

import java.util.LinkedList;
import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.*;

public class FinalBoss extends Enemy {

    private int FIRING_COOLDOWN_VALUE;
    private int LIFE_UPGRADE_BOUNDARY;

    public FinalBoss(double x, double y) {
        super(x, y, Type.BOSS);
        this.FIRING_COOLDOWN_VALUE = 130;
        this.LIFE_UPGRADE_BOUNDARY = hp / 2;
        this.firingCooldown = FIRING_COOLDOWN_VALUE;
        setDirection(Math.random() > 0.5 ? Direction.EAST : Direction.WEST);

    }

    @Override
    public void move() {

        if (getMinX() + getDirection().getX() * getSpeed() < PADDING ||
                getMaxX() + getDirection().getX() * getSpeed() > SCREEN_WIDTH + PADDING) {

            setDirection(getDirection().equals(Direction.EAST) ? Direction.WEST : Direction.EAST);
        }

        super.move();
    }

    @Override
    public void takeHit(int damage) {
        hp -= damage;

        if (hp <= LIFE_UPGRADE_BOUNDARY) {
            FIRING_COOLDOWN_VALUE -= 20;
            incrementSpeed(2);
            LIFE_UPGRADE_BOUNDARY = hp / 2;
        }
    }

    @Override
    public List<Bullet> shoot() {

        firingCooldown--;

        if (firingCooldown > 0) {
            return null;
        }

        List<Bullet> bullets = Math.random() > 0.5 ? rainAttack() : maniacAttack();

        if (firingCooldown <= 0) {
            firingCooldown = FIRING_COOLDOWN_VALUE;
        }

        return bullets;
    }

    private List<Bullet> rainAttack() {

        List<Bullet> bullets = new LinkedList<>();

        for (int i = 0; i < getPicture().getWidth(); i += 50) {
            bullets.add(new Bullet(getMinX() + i, getBulletYCoordinates(), bulletType, this));
        }

        return bullets;
    }

    private List<Bullet> maniacAttack() {

        List<Bullet> bullets = new LinkedList<>();

        for (int i = 0; i < getPicture().getWidth(); i += 50) {
            bullets.add(new Bullet(getMinX() + i, getBulletYCoordinates() + i/5, bulletType, this));
        }

        return bullets;
    }
}
