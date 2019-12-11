package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;

public final class Constants {

    public final static double PADDING = 10;
    public final static double SCREEN_WIDTH = 800;
    public final static double SCREEN_HEIGHT = 800;

    public final static double BAR_HEIGHT = 40;
    public final static double TOP_BAR_X = PADDING;
    public final static double TOP_BAR_Y = PADDING;
    public final static double BOTTOM_BAR_X = PADDING;
    public final static double BOTTOM_BAR_Y = SCREEN_HEIGHT - BAR_HEIGHT - PADDING;

    public final static double PLAYER_ONE_INITIAL_X = SCREEN_WIDTH - 100;
    public final static double PLAYER_ONE_INITIAL_Y = SCREEN_HEIGHT - 100;
    public final static double PLAYERS_INITIAL_SPEED = 4;
    public final static int PLAYERS_FIRING_COOLDOWN = 8;
    public final static int INVINCIBILITY_COOLDOWN = 120;

    public final static double PLAYERS_MAX_LIVES = 3;
    public final static double LIFE_ICON_SIDE = 13;
    public final static double LIFE_ICON_DISTANCE = 20;
    public final static double LIFE_ICON_Y = SCREEN_HEIGHT - BAR_HEIGHT + PADDING + PADDING;
    public final static double PLAYER_ONE_LIFE_X = SCREEN_WIDTH - PADDING - LIFE_ICON_SIDE;
    public final static double PLAYER_ONE_LIVES_MARGIN = -LIFE_ICON_SIDE - LIFE_ICON_DISTANCE;

    public final static double REGULAR_ENEMY_SPEED = 3;
    public final static double DIAMOND_ENEMY_SPEED = 4;
    public final static int ENEMY_HP = 6;

    public final static double SMALL_OBSTACLE_SPEED = 5;
    public final static double MEDIUM_OBSTACLE_SPEED = 4;
    public final static double BIG_OBSTACLE_SPEED = 2;
    public final static int SMALL_OBSTACLE_HP = 10;
    public final static int MEDIUM_OBSTACLE_HP = 20;
    public final static int BIG_OBSTACLE_HP = 30;

    public final static int COLLISION_DAMAGE = 10;
    public final static int POWER_UP_SPEED = 3;
    public final static int BULLET_SPEED = 8;
    public final static int BULLET_DAMAGE = 2;
    public final static double DOUBLE_SHOOT_DISTANCE = 20;

    public final static double SCORE_PER_ENEMY = 5;
    public final static double SCORE_X = SCREEN_WIDTH / 2;
    public final static double SCORE_Y = SCREEN_HEIGHT - BAR_HEIGHT + PADDING + PADDING;

    public final static double FPS_X = PADDING + PADDING;
    public final static double FPS_Y = PADDING + PADDING;

    public final static double ENTITIES_STARTING_Y = PADDING + BAR_HEIGHT;
    private final static double ENEMIES_WIDTH = 25;
    private final static double SMALL_OBSTACLE_WIDTH = 80;
    private final static double MEDIUM_OBSTACLE_WIDTH = 100;
    private final static double BIG_OBSTACLE_WIDTH = 140;
    private final static double POWER_UP_WIDTH = 25;
    public final static double ENEMIES_MAX_X_SPAWN = SCREEN_WIDTH - ENEMIES_WIDTH - PADDING;
    public final static double SMALL_OBSTACLE_MAX_X_SPAWN = SCREEN_WIDTH - SMALL_OBSTACLE_WIDTH - PADDING;
    public final static double MEDIUM_OBSTACLE_MAX_X_SPAWN = SCREEN_WIDTH - MEDIUM_OBSTACLE_WIDTH - PADDING;
    public final static double BIG_OBSTACLE_MAX_X_SPAWN = SCREEN_WIDTH - BIG_OBSTACLE_WIDTH - PADDING;
    public final static double POWER_UP_MAX_X_SPAWN = SCREEN_WIDTH - POWER_UP_WIDTH - PADDING;

    public final static double ARROW_X = 680;
    public final static double ARROW_INITIAL_Y = 400;
    public final static double OPTIONS_DISTANCE = 95;

    private Constants() {

    }
}
