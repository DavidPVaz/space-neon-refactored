package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;

import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.COLLISION_DAMAGE;
import static david.vaz.space.neon.refactored.game.Constants.SCORE_PER_ENEMY;

public final class Collision {

    @SafeVarargs
    public static int checkIfBulletHitAnything(List<Bullet> bullets, List<? extends Collidable>... entitiesToCheck) {

        int score = 0;

        for (List<? extends Collidable> collidables : entitiesToCheck) {

            for (Collidable entity : collidables) {
                for (Bullet bullet : bullets) {

                    if (bullet.collideWith(entity)) {
                        bullet.hit((Hittable) entity);
                        score += entity instanceof Enemy && ((Enemy) entity).isDestroyed() ? SCORE_PER_ENEMY : score;
                    }
                }
            }
        }

        return score;
    }

    @SafeVarargs
    public static void checkIfAnyEnemyEntityHitPlayers(List<Player> players, List<? extends Collidable>... entitiesToCheck) {

        for (List<? extends Collidable> enemies : entitiesToCheck) {

            for (Collidable enemyEntity : enemies) {
                for (Player player : players) {

                    if (enemyEntity.collideWith(player)) {
                        ((Hittable) enemyEntity).takeHit(COLLISION_DAMAGE);
                        player.takeHit(COLLISION_DAMAGE);
                    }
                }
            }
        }
    }

    public static void checkIfAnyPlayerCatchAnyPowerUp(List<Player> players, List<PowerUp> powerUps) {

        players.forEach(player -> powerUps.forEach(powerUp -> {
            if (player.collideWith(powerUp) || powerUp.collideWith(player)) {
                player.collect(powerUp);
                powerUp.setCollected();
            }
        }));
    }

}
