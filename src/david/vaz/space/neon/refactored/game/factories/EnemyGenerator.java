package david.vaz.space.neon.refactored.game.factories;

import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.DiamondEnemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.FinalBoss;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.RegularEnemy;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class EnemyGenerator {

    public static Enemy generateEnemy(int score) {

        double chance = Math.random() * 18;

        return score >= 3000 ? new FinalBoss(FINAL_BOSS_INITIAL_X, FINAL_BOSS_Y) :

                chance > score / DIFFICULTY_VALUE ?
                        null :
                        Enemy.Type.random().equals(Enemy.Type.REGULAR) ?
                                new RegularEnemy(generateRandomX(), ENTITIES_STARTING_Y) :
                                new DiamondEnemy(generateRandomX(), ENTITIES_STARTING_Y);
    }

    private static double generateRandomX() {
        return (Math.random() * (ENEMIES_MAX_X_SPAWN - PADDING)) + PADDING;
    }
}
