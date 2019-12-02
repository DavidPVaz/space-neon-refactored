package david.vaz.space.neon.refactored.game;

public final class Constants {

    public final static double PADDING = 10;
    public final static double SCREEN_WIDTH = 800;
    public final static double SCREEN_HEIGHT = 800;

    public final static double PLAYER_ONE_INITIAL_X = 400;
    public final static double PLAYER_ONE_INITIAL_Y = 600;
    public final static double PLAYERS_INITIAL_SPEED = 7;
    public final static int PLAYERS_FIRING_COOLDOWN = 5;
    public final static int INVINCIBILITY_COOLDOWN = 120;

    public final static int COLLISION_DAMAGE = 10;

    public final static double ENEMIES_STARTING_Y = PADDING;
    private final static double ENEMIES_WIDTH = 25;
    private final static double SMALL_OBSTACLE_WIDTH = 80;
    private final static double MEDIUM_OBSTACLE_WIDTH = 100;
    private final static double BIG_OBSTACLE_WIDTH = 140;
    public final static double ENEMIES_MAX_X_SPAWN = SCREEN_WIDTH - ENEMIES_WIDTH - PADDING;
    public final static double SMALL_OBSTACLE_MAX_X_SPAWN = SCREEN_WIDTH - SMALL_OBSTACLE_WIDTH - PADDING;
    public final static double MEDIUM_OBSTACLE_MAX_X_SPAWN = SCREEN_WIDTH - MEDIUM_OBSTACLE_WIDTH - PADDING;
    public final static double BIG_OBSTACLE_MAX_X_SPAWN = SCREEN_WIDTH - BIG_OBSTACLE_WIDTH - PADDING;


    private Constants() {

    }
}
