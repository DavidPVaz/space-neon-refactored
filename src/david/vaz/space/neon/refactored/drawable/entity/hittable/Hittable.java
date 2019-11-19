package david.vaz.space.neon.refactored.drawable.entity.hittable;

public interface Hittable {

    void takeHit(int damage);

    boolean isDestroyed();
}
