package david.vaz.space.neon.refactored.engine.modules;

import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;

import java.util.List;

public final class Collision {

    @SafeVarargs
    public static boolean detectsThatBulletHitAnything(Bullet bullet, List<? extends Collidable> ...entitiesToCheck) {

        for (List<? extends Collidable> collidables : entitiesToCheck) {
            for (Collidable entity : collidables) {
                if(bullet.collideWith(entity)) {
                    bullet.hit((Hittable) entity);
                    return true;
                }
            }
        }

        return false;
    }

}
