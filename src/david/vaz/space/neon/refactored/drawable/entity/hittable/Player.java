package david.vaz.space.neon.refactored.drawable.entity.hittable;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUpAction;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.*;

import static david.vaz.space.neon.refactored.game.Constants.*;
import static david.vaz.space.neon.refactored.game.Constants.PADDING;

public class Player extends AbstractEntity implements Hittable {

    private Bullet.Type bulletType;
    private Mode mode;
    private final List<Direction> directions;
    private final Map<PowerUp.Type, PowerUpAction> powerUpAction;
    private boolean firing;
    private int firingCooldown;
    private ShootingStrategy shootingStrategy;

    public Player(double x, double y, Image image, Bullet.Type bulletType) {
        super(x, y, image, PLAYERS_INITIAL_SPEED);
        this.bulletType = bulletType;
        this.mode = Mode.VULNERABLE;
        this.directions = new LinkedList<>();
        this.powerUpAction = new HashMap<>();
        this.firing = false;
        this.firingCooldown = PLAYERS_FIRING_COOLDOWN;
        this.shootingStrategy = ShootingStrategy.SINGLE_BULLET;
        setPowerUpAction();
    }

    @Override
    public void show() {
        //will display the lives icons
        super.show();
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
    public boolean cantMove() {

        if (getDirection() == null) {
            return true;
        }

        return getMinX() + getDirection().getX() * getSpeed() < PADDING ||
                getMaxX() + getDirection().getX() * getSpeed() > SCREEN_WIDTH + PADDING ||
                getMinY() + getDirection().getY() * getSpeed() < PADDING + BAR_HEIGHT ||
                super.cantMove();
    }

    @Override
    public void takeHit(int damage) {

        if (mode.equals(Mode.INVINCIBLE)) {
            return;
        }

        //will have a list of life icons and reduce that list when hit by a bullet or collide with an enemy
        mode = Mode.INVINCIBLE;
    }

    @Override
    public boolean isDestroyed() {
        return false; //will return the size of the life icons list
    }

    public void collect(PowerUp powerUp) {
        powerUpAction.get(powerUp.type()).enhance();
    }

    public void update() {

        if (mode.equals(Mode.VULNERABLE)) {
            super.show();
            return;
        }

        mode.cooldown--;

        if (mode.cooldown % 10 == 1) {
            super.show();
            return;
        }

        hide();

        if (mode.cooldown == 0) {
            mode.cooldown = INVINCIBILITY_COOLDOWN;
            mode = Mode.VULNERABLE;
        }

    }

    public void fire() {
        firing = true;
    }

    public void stopFiring() {
        firing = false;
    }

    public List<Bullet> shoot() { //maybe turn this into shared method with enemies

        firingCooldown--;

        if (!firing || firingCooldown > 0) {
            return null;
        }

        List<Bullet> bullets = createBullets();

        if (firingCooldown <= 0) {
            firingCooldown = PLAYERS_FIRING_COOLDOWN;
        }

        return bullets;
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

    private void changeShootingStrategy() {
        shootingStrategy = ShootingStrategy.DOUBLE_BULLET;
    }

    private Direction getPressedDirection() {

        if (directions.isEmpty()) {
            return null;
        }

        if (directions.size() == 2) {
            return Direction.resolveTwoPressedDirections(directions.get(0), directions.get(1));
        }

        return directions.get(0);
    }

    private List<Bullet> createBullets() {

        List<Bullet> toReturn = new LinkedList<>();

        if (shootingStrategy.equals(ShootingStrategy.DOUBLE_BULLET)) {
            toReturn.add(new Bullet(getBulletXCoordinates(), getBulletYCoordinates(), bulletType, this));
            toReturn.add(new Bullet(getBulletXCoordinates() + DOUBLE_SHOOT_DISTANCE, getBulletYCoordinates(), bulletType, this));
            return toReturn;
        }

        toReturn.add(new Bullet(getBulletXCoordinates(), getBulletYCoordinates(), bulletType, this));
        return toReturn;
    }

    private double getBulletXCoordinates() {

        if (shootingStrategy.equals(ShootingStrategy.SINGLE_BULLET)) {
            return getMinX() + (getPicture().getWidth() / 6.0);
        }

        return getMinX() - 5;
    }

    private double getBulletYCoordinates() {
        return shootingStrategy.equals(ShootingStrategy.DOUBLE_BULLET) ? getMinY() : getMinY() - 10;
    }

    private void setPowerUpAction() {
        powerUpAction.put(PowerUp.Type.DOUBLE_SHOOTING, this::changeShootingStrategy);
        powerUpAction.put(PowerUp.Type.EXTRA_DAMAGE, () -> bulletType.incrementDamage(2));
        //powerUpAction.put(PowerUp.Type.EXTRA_LIFE, this::addExtraLife);
        powerUpAction.put(PowerUp.Type.INCREASE_BULLET_SPEED, () -> bulletType.incrementSpeed(2));
        powerUpAction.put(PowerUp.Type.INCREASE_PLAYER_SPEED, () -> incrementSpeed(2));

    }

    private enum Mode {
        VULNERABLE,
        INVINCIBLE(INVINCIBILITY_COOLDOWN);

        private int cooldown;

        Mode() {
        }

        Mode(int cooldown) {
            this.cooldown = cooldown;
        }

    }

    private enum ShootingStrategy {
        SINGLE_BULLET,
        DOUBLE_BULLET
    }

}
