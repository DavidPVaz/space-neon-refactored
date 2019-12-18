package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUpEnhancement;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.drawable.lifes.LifeIcon;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import java.util.*;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class Player extends AbstractEntity implements Hittable, Shootable {

    private Bullet.Type bulletType;
    private final Stack<LifeIcon> livesStack;
    private final List<Direction> directions;
    private final Map<PowerUp.Type, PowerUpEnhancement> powerUpAction;
    private Mode mode;
    private boolean firing;
    private int firingCooldown;
    private ShootingStrategy shootingStrategy;
    private boolean reversed;

    public Player(double x, double y, Image image, Bullet.Type bulletType, Stack<LifeIcon> livesStack, boolean reversed) {
        super(x, y, image, PLAYERS_INITIAL_SPEED);
        this.bulletType = bulletType;
        this.livesStack = livesStack;
        this.directions = new LinkedList<>();
        this.powerUpAction = setPowerUpAction();
        this.mode = Mode.VULNERABLE;
        this.firing = false;
        this.firingCooldown = PLAYERS_FIRING_COOLDOWN;
        this.shootingStrategy = ShootingStrategy.SINGLE_BULLET;
        this.reversed = reversed;
    }

    @Override
    public void show() {
        livesStack.forEach(LifeIcon::show);
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

        livesStack.pop().hide();
        resetEnhancements();
        mode = Mode.INVINCIBLE;
    }

    @Override
    public boolean isDestroyed() {
        return livesStack.empty();
    }

    @Override
    public List<Bullet> shoot() {

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

    @Override
    public List<Bullet> createBullets() {

        List<Bullet> bullets = new LinkedList<>();

        if (shootingStrategy.equals(ShootingStrategy.DOUBLE_BULLET)) {
            bullets.add(new Bullet(getBulletXCoordinates(), getBulletYCoordinates(), bulletType, this));
            bullets.add(new Bullet(getBulletXCoordinates() + DOUBLE_SHOOT_DISTANCE, getBulletYCoordinates(), bulletType, this));
            return bullets;
        }

        bullets.add(new Bullet(getBulletXCoordinates(), getBulletYCoordinates(), bulletType, this));
        return bullets;
    }

    @Override
    public double getBulletXCoordinates() {

        return shootingStrategy.equals(ShootingStrategy.SINGLE_BULLET) ?
                getMinX() + (getPicture().getWidth() / 6.0) :
                getMinX() - 5.0;
    }

    @Override
    public double getBulletYCoordinates() {
        return reversed ? getMaxY() - 14 :
                shootingStrategy.equals(ShootingStrategy.DOUBLE_BULLET) ? getMinY() : getMinY() - 15.0;
    }

    public boolean isAlive() {
        return !livesStack.empty();
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

    public void addDirection(Direction direction) {

        if (directions.contains(direction)) {
            return;
        }

        directions.add(direction);
    }

    public void removeDirection(Direction direction) {
        directions.remove(direction);
    }

    public boolean isReversed() {
        return reversed;
    }

    private void addExtraLife() {

        if (livesStack.size() == PLAYERS_MAX_LIVES) {
            return;
        }

        LifeIcon iconAtTheTopOfTheStack = livesStack.peek();

        LifeIcon newLife = new LifeIcon(
                iconAtTheTopOfTheStack.getPicture().getX() +
                        (iconAtTheTopOfTheStack.getType().equals(LifeIcon.Type.GREEN) ? PLAYER_ONE_LIVES_MARGIN : PLAYER_TWO_LIVES_MARGIN),
                LIFE_ICON_Y,
                iconAtTheTopOfTheStack.getType());

        livesStack.push(newLife);
        newLife.show();
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

    private Map<PowerUp.Type, PowerUpEnhancement> setPowerUpAction() {

        HashMap<PowerUp.Type, PowerUpEnhancement> powerUpAction = new HashMap<>();

        powerUpAction.put(PowerUp.Type.DOUBLE_SHOOTING, this::changeShootingStrategy);
        powerUpAction.put(PowerUp.Type.EXTRA_DAMAGE, () -> bulletType.incrementDamage(1));
        powerUpAction.put(PowerUp.Type.EXTRA_LIFE, this::addExtraLife);
        powerUpAction.put(PowerUp.Type.INCREASE_BULLET_SPEED, () -> bulletType.incrementSpeed(1));
        powerUpAction.put(PowerUp.Type.INCREASE_PLAYER_SPEED, () -> incrementSpeed(1));

        return powerUpAction;
    }

    private void resetEnhancements() {
        shootingStrategy = ShootingStrategy.SINGLE_BULLET;
        bulletType.setDamage(BULLET_DAMAGE);
        bulletType.setSpeed(BULLET_SPEED);
        setSpeed(PLAYERS_INITIAL_SPEED);
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
