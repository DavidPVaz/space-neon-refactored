package david.vaz.space.neon.refactored.engine.modules;

import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import java.util.List;

public class Collision {

    @SafeVarargs
    public static boolean detectsThatBulletHitAnything(Bullet bullet, List<? extends Collidable>... entitiesToCheck) {

        for (List<? extends Collidable> collidables : entitiesToCheck) {
            for (Collidable collidable : collidables) {
                if(bullet.willCollideWith(collidable)) {
                    bullet.hit((Hittable) collidable);
                    return true;
                }
            }
        }

        return false;
    }

}
