package david.vaz.space.neon.refactored.game.factories;

import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.DiamondEnemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.FinalBoss;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.RegularEnemy;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class EnemyGenerator {

    private EnemyGenerator() {

    }

    public static Enemy generateEnemy(int score) {

        double chance = Math.random() * 20;
        double inFunctionOfScore = score / DIFFICULTY_VALUE;

        return score >= FINAL_BOSS_SPAWN_SCORE ?
                new FinalBoss(FINAL_BOSS_INITIAL_X, FINAL_BOSS_Y) :

                chance > (Math.max(inFunctionOfScore, MINIMUM_ENEMIES_SPAWN_CHANCE)) ?
                        null :
                        Enemy.Type.random() == Enemy.Type.REGULAR ?
                                new RegularEnemy(generateRandomX(), ENTITIES_STARTING_Y) :
                                new DiamondEnemy(generateRandomX(), ENTITIES_STARTING_Y);
    }

    private static double generateRandomX() {
        return (Math.random() * (ENEMIES_MAX_X_SPAWN - PADDING)) + PADDING;
    }
}
