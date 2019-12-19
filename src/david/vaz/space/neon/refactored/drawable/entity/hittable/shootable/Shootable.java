package david.vaz.space.neon.refactored.drawable.entity.hittable.shootable;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;

import java.util.List;

public interface Shootable {

    List<Bullet> shoot();

    List<Bullet> getProjectiles();

    double getProjectilesXCoordinates();

    double getProjectilesYCoordinates();

}
