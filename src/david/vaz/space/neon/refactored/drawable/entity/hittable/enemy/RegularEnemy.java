package david.vaz.space.neon.refactored.drawable.entity.hittable.enemy;

import david.vaz.space.neon.refactored.game.Direction;

public class RegularEnemy extends Enemy {

    public RegularEnemy(double x, double y) {
        super(x, y, Type.REGULAR);
        setDirection(Direction.SOUTH);
    }
}
