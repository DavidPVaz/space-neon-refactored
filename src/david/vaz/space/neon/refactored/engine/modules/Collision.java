package david.vaz.space.neon.refactored.engine.modules;

import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;

import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.COLLISION_DAMAGE;

public final class Collision {

    @SafeVarargs
    public static void checkIfBulletHitAnything(List<Bullet> bullets, List<? extends Collidable>... entitiesToCheck) {

        for (List<? extends Collidable> collidables : entitiesToCheck) {

            for (Collidable entity : collidables) {
                for (Bullet bullet : bullets) {

                    if (bullet.collideWith(entity)) {
                        bullet.hit((Hittable) entity);
                    }
                }
            }
        }
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
