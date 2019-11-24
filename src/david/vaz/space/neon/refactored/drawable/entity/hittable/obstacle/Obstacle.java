package david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.Collidable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

public class Obstacle extends AbstractEntity implements Hittable {

    private int hp;

    public Obstacle(double x, double y, Type type) {
        super(x, y, type.getImage(), type.getSpeed());
        this.hp = type.getHp();
        setDirection(Direction.SOUTH);
    }

    @Override
    public boolean collideWith(Collidable collidable) {
        return collidable instanceof Player && super.collideWith(collidable);
    }

    @Override
    public void takeHit(int damage) {
        this.hp -= damage;
    }

    @Override
    public boolean isDestroyed() {
        return hp <= 0;
    }

    public enum Type {
        SMALL(Image.SMALL_OBSTACLE, 6, 10),
        MEDIUM(Image.MEDIUM_OBSTACLE, 4, 20),
        BIG(Image.BIG_OBSTACLE, 2, 30);

        private Image image;
        private double speed;
        private int hp;

        Type(Image image, double speed, int hp) {
            this.image = image;
            this.speed = speed;
            this.hp = hp;
        }

        public Image getImage() {
            return image;
        }

        public double getSpeed() {
            return speed;
        }

        public int getHp() {
            return hp;
        }

        public Type random() {

            double random = Math.random();

            return random > 0.8 ? BIG : random < 0.45 ? SMALL : MEDIUM;
        }
    }

}
