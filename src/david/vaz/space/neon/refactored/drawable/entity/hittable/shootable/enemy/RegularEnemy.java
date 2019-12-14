package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy;

import david.vaz.space.neon.refactored.game.Direction;

public final class RegularEnemy extends Enemy {

    public RegularEnemy(double x, double y) {
        super(x, y, Type.REGULAR);
        setDirection(Direction.SOUTH);
    }

}
