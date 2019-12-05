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
    public static void checkIfBulletHitAnything(List<? extends Collidable> bullets, List<? extends Collidable>... entitiesToCheck) {

        for (List<? extends Collidable> collidables : entitiesToCheck) {

            for (Collidable entity : collidables) {
                for (Collidable bullet : bullets) {

                    if (bullet.collideWith(entity)) {
                        Bullet toUse = (Bullet) bullet;
                        toUse.hit((Hittable) entity);
                    }
                }
            }
        }
    }

    @SafeVarargs
    public static void checkIfAnyEnemyEntityHitPlayers(List<? extends Collidable> players, List<? extends Collidable>... entitiesToCheck) {

        for (List<? extends Collidable> enemies : entitiesToCheck) {

            for (Collidable enemyEntity : enemies) {
                for (Collidable player : players) {

                    if (enemyEntity.collideWith(player)) {
                        ((Hittable) enemyEntity).takeHit(COLLISION_DAMAGE);
                        ((Hittable) player).takeHit(COLLISION_DAMAGE);
                    }
                }
            }
        }
    }

    public static void checkIfAnyPlayerCatchAnyPowerUp(List<? extends Collidable> players, List<PowerUp> powerUps) {

        players.forEach(player -> powerUps.forEach(powerUp -> {
            if (player.collideWith(powerUp) || powerUp.collideWith(player)) {
                ((Player) player).collect(powerUp);
                powerUp.setCollected();
            }
        }));

    }

}
