package david.vaz.space.neon.refactored.game.factories;

import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;

import static david.vaz.space.neon.refactored.game.Constants.*;

public final class PowerUpGenerator {

    public static PowerUp generatePowerUp(int score) {

        double chance = Math.random() * 300;

        return chance > score / DIFFICULTY_VALUE ? null : new PowerUp(generateRandomX(), ENTITIES_STARTING_Y, PowerUp.Type.random());
    }

    private static double generateRandomX() {
        return (Math.random() * (POWER_UP_MAX_X_SPAWN - PADDING)) + PADDING;
    }

}
