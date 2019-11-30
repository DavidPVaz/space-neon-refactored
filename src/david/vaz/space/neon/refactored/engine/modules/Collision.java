package david.vaz.space.neon.refactored.engine.modules;

import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;

import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.COLLISION_DAMAGE;

public final class Collision {

    @SafeVarargs
    public static boolean detectsThatBulletHitAnything(Bullet bullet, List<? extends Collidable>... entitiesToCheck) {

        for (List<? extends Collidable> collidables : entitiesToCheck) {
            for (Collidable entity : collidables) {
                if (bullet.collideWith(entity)) {
                    bullet.hit((Hittable) entity);
                    return true;
                }
            }
        }

        return false;
    }

    @SafeVarargs
    public static void checkIfAnyEnemyEntityHitPlayers(List<? extends Collidable> players, List<? extends Collidable>... entitiesToCheck) {

        for (List<? extends Collidable> enemies : entitiesToCheck) {

            for (Collidable enemyEntity : enemies) {
                for (Collidable player : players) {

                    if (enemyEntity.collideWith(player)) {

                        Hittable enemyToHit = (Hittable) enemyEntity;
                        Hittable playerToHit = (Hittable) player;
                        enemyToHit.takeHit(COLLISION_DAMAGE);
                        playerToHit.takeHit(COLLISION_DAMAGE);
                    }
                }
            }
        }
    }

}
