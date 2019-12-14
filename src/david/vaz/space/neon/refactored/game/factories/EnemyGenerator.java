package david.vaz.space.neon.refactored.game.factories;

import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.DiamondEnemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.RegularEnemy;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class EnemyGenerator {

    public static Enemy generateEnemy(int score) {

        double chance = Math.random() * 20;

        return chance > score / DIFFICULTY_VALUE ?
                null :
                Enemy.Type.random().equals(Enemy.Type.REGULAR) ?
                        new RegularEnemy(generateRandomX(), ENTITIES_STARTING_Y) :
                        new DiamondEnemy(generateRandomX(), ENTITIES_STARTING_Y);
    }

    private static double generateRandomX() {
        return (Math.random() * (ENEMIES_MAX_X_SPAWN - PADDING)) + PADDING;
    }
}
