package david.vaz.space.neon.refactored.drawable.entity.hittable.enemy;

import david.vaz.space.neon.refactored.game.Direction;

public class DiamondEnemy extends Enemy {

    public DiamondEnemy(double x, double y) {
        super(x, y, Type.DIAMOND);
        setDirection(Math.random() > 0.5 ? Direction.SOUTHEAST : Direction.NORTHWEST);
    }

    @Override
    public void move() {
        super.move();//this one will move in diagonals and switch to the inverse diagonal when side has been reached
    }

    @Override
    public boolean cantMove() {
        return super.cantMove();
    }
}
