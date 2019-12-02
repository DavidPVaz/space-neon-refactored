package david.vaz.space.neon.refactored.game.factories;

import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.DiamondEnemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.RegularEnemy;

import static david.vaz.space.neon.refactored.game.Constants.*;

public class EnemyGenerator {

    public static Enemy generateEnemy() {

        double chance = Math.random() * 10;

        return chance > 0.5 ? null :
                Enemy.Type.random().equals(Enemy.Type.REGULAR) ?
                        new RegularEnemy(generateRandomX(), ENEMIES_STARTING_Y) :
                        new DiamondEnemy(generateRandomX(), ENEMIES_STARTING_Y);
    }

    private static double generateRandomX() {
        //make a recursive call if the x generated was the same as the previous call
        return (Math.random() * (ENEMIES_MAX_X_SPAWN - PADDING)) + PADDING;
    }
}
