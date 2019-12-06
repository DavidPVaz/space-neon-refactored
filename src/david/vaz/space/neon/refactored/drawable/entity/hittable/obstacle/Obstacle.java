package david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle;

import david.vaz.space.neon.refactored.drawable.entity.AbstractEntity;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Hittable;
import david.vaz.space.neon.refactored.game.Direction;
import david.vaz.space.neon.refactored.resources.Image;

import static david.vaz.space.neon.refactored.game.Constants.*;

public class Obstacle extends AbstractEntity implements Hittable {

    private int hp;

    public Obstacle(double x, double y, Type type) {
        super(x, y, type.getImage(), type.getSpeed());
        this.hp = type.getHp();
        setDirection(Direction.SOUTH);
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
        SMALL(Image.SMALL_OBSTACLE, SMALL_OBSTACLE_SPEED, SMALL_OBSTACLE_HP),
        MEDIUM(Image.MEDIUM_OBSTACLE, MEDIUM_OBSTACLE_SPEED, MEDIUM_OBSTACLE_HP),
        BIG(Image.BIG_OBSTACLE, BIG_OBSTACLE_SPEED, BIG_OBSTACLE_HP);

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

        public static Type random() {

            double random = Math.random();

            return random > 0.8 ?
                    BIG :
                    random < 0.5 ?
                            SMALL :
                            MEDIUM;
        }
    }

}
