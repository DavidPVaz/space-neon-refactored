package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy;

import david.vaz.space.neon.refactored.game.Direction;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class DiamondEnemy extends Enemy {

    public DiamondEnemy(double x, double y) {
        super(x, y, Type.DIAMOND);
        setDirection(Math.random() > 0.5 ? Direction.SOUTHEAST : Direction.SOUTHWEST);
    }

    @Override
    public void move() {

        if (getMinX() + getDirection().getX() * getSpeed() < PADDING ||
                getMaxX() + getDirection().getX() * getSpeed() > SCREEN_WIDTH + PADDING) {

            setDirection(getDirection() == Direction.SOUTHEAST ? Direction.SOUTHWEST : Direction.SOUTHEAST);
        }

        super.move();
    }
}
