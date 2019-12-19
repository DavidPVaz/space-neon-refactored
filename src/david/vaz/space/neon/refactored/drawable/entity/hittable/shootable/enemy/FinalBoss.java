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
        this.ragingValue = this.hp / 2;
        this.firingCooldown = this.firingCooldownUpdatableValue;
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
        hp -= damage;

        if (hp <= ragingValue) {
            rage();
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
            firingCooldown = firingCooldownUpdatableValue;
        }

        return bullets;
    }

    private List<Bullet> rainAttack() {

        List<Bullet> bullets = new LinkedList<>();

        for (int i = 0; i < getPicture().getWidth(); i += 50) {
            bullets.add(new Bullet(getMinX() + i, getProjectilesYCoordinates(), bulletType, this));
        }

        return bullets;
    }

    private List<Bullet> maniacAttack() {

        List<Bullet> bullets = new LinkedList<>();

        for (int i = 0; i < getPicture().getWidth(); i += 50) {
            bullets.add(new Bullet(getMinX() + i, getProjectilesYCoordinates() + i / 5, bulletType, this));
        }

        return bullets;
    }

    private void rage() {
        firingCooldownUpdatableValue -= 20;
        ragingValue = hp / 2;
        incrementSpeed(2);
        bulletType.incrementSpeed(2);
    }
}
