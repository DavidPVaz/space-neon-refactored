package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.game.Direction;

import java.util.LinkedList;
import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class FinalBoss extends Enemy {

    private int firingCooldownUpdatableValue;
    private int ragingValue;

    public FinalBoss(double x, double y) {
        super(x, y, Type.BOSS);
        this.firingCooldownUpdatableValue = FINAL_BOSS_INITIAL_FIRING_COOLDOWN;
        this.ragingValue = getHp() / 2;
        setFiringCooldown(this.firingCooldownUpdatableValue);
        setDirection(Math.random() > 0.5 ? Direction.EAST : Direction.WEST);
    }

    @Override
    public void move() {

        if (getMinX() + getDirection().getX() * getSpeed() < PADDING ||
                getMaxX() + getDirection().getX() * getSpeed() > SCREEN_WIDTH + PADDING) {

            setDirection(getDirection() == Direction.EAST ? Direction.WEST : Direction.EAST);
        }

        super.move();
    }

    @Override
    public void takeHit(int damage) {
        decrementHp(damage);

        if (getHp() <= ragingValue) {
            rage();
        }
    }

    @Override
    public List<Bullet> shoot() {

        decrementFiringCooldown();

        if (getFiringCooldown() > 0) {
            return null;
        }

        List<Bullet> bullets = getProjectiles();

        if (getFiringCooldown() <= 0) {
            setFiringCooldown(firingCooldownUpdatableValue);
        }

        return bullets;
    }

    @Override
    public List<Bullet> getProjectiles() {
        return Math.random() > 0.5 ? rainAttack() : maniacAttack();
    }

    private List<Bullet> rainAttack() {

        List<Bullet> bullets = new LinkedList<>();

        for (int i = 0; i < getPicture().getWidth(); i += 50) {
            bullets.add(new Bullet(getMinX() + i, getProjectilesYCoordinates(), getBulletType(), this));
        }

        return bullets;
    }

    private List<Bullet> maniacAttack() {

        List<Bullet> bullets = new LinkedList<>();

        for (int i = 0; i < getPicture().getWidth(); i += 50) {
            bullets.add(new Bullet(getMinX() + i, getProjectilesYCoordinates() + i / 5, getBulletType(), this));
        }

        return bullets;
    }

    private void rage() {
        firingCooldownUpdatableValue -= 20;
        ragingValue = getHp() / 2;
        incrementSpeed(2);
        getBulletType().incrementSpeed(2);
    }
}
