package david.vaz.space.neon.refactored.drawable.entity;

public interface Collidable {

    int getMinX();

    int getMinY();

    int getMaxX();

    int getMaxY();

    boolean collideWith(Collidable collidable);
}
