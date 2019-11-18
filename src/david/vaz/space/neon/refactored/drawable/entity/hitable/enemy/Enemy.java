package david.vaz.space.neon.refactored.drawable.entity.hitable.enemy;

import david.vaz.space.neon.refactored.drawable.entity.hitable.Hittable;
import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.resources.Image;

public abstract class Enemy extends AbstractEntity implements Hittable {

    private int hp;

    public Enemy(double x, double y, Image image, double speed) {
        super(x, y, image, speed);

    }

    @Override
    public void takeHit(int damage) {
        hp -= damage;
    }
}
