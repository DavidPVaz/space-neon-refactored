package david.vaz.space.neon.refactored.drawable.entity.hittable.enemy;

import david.vaz.space.neon.refactored.game.Direction;

import static david.vaz.space.neon.refactored.game.Constants.*;

public class DiamondEnemy extends Enemy {

    public DiamondEnemy(double x, double y) {
        super(x, y, Type.DIAMOND);
        setDirection(Math.random() > 0.5 ? Direction.SOUTHEAST : Direction.NORTHWEST);
    }

    @Override
    public void move() {

        if (getMinX() + getDirection().getX() * getSpeed() < PADDING ||
                getMaxX() + getDirection().getX() * getSpeed() > SCREEN_WIDTH + PADDING) {
            setDirection(getDirection().equals(Direction.SOUTHEAST) ? Direction.SOUTHWEST : Direction.SOUTHEAST);
        }

        super.move();
    }

}
