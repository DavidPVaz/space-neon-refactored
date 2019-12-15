package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy;

import david.vaz.space.neon.refactored.game.Direction;

import static david.vaz.space.neon.refactored.game.Constants.PADDING;
import static david.vaz.space.neon.refactored.game.Constants.SCREEN_WIDTH;

public class FinalBoss extends Enemy {

    public FinalBoss(double x, double y) {
        super(x, y, Type.BOSS);
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
}
