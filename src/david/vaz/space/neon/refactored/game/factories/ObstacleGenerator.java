package david.vaz.space.neon.refactored.game.factories;

import david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle.Obstacle;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class ObstacleGenerator {

    private ObstacleGenerator() {

    }

    public static Obstacle generateObstacle() {

        double chance = Math.random() * 20;

        Obstacle.Type type = Obstacle.Type.random();

        return chance > 0.1 ? null : new Obstacle(generateRandomX(type), ENTITIES_STARTING_Y, type);
    }

    private static double generateRandomX(Obstacle.Type type) {

        double xMaxSpawn = type == Obstacle.Type.SMALL ?
                SMALL_OBSTACLE_MAX_X_SPAWN :
                type == Obstacle.Type.MEDIUM ?
                        MEDIUM_OBSTACLE_MAX_X_SPAWN :
                        BIG_OBSTACLE_MAX_X_SPAWN;

        return (Math.random() * (xMaxSpawn - PADDING)) + PADDING;
    }
}
