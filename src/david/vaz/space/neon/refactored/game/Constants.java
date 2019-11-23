package david.vaz.space.neon.refactored.game;

public final class Constants {

    public final static double PADDING = 10;
    public final static double SCREEN_WIDTH = 800;
    public final static double SCREEN_HEIGHT = 800;

    public final static double PLAYER_ONE_INITIAL_X = 400;
    public final static double PLAYER_ONE_INITIAL_Y = 600;
    public final static double PLAYERS_INITIAL_SPEED = 7;
    public final static int PLAYERS_FIRING_COOLDOWN = 5;

    public final static double ENEMIES_STARTING_Y = PADDING;
    private final static double ENEMIES_WIDTH = 25;
    public final static double ENEMIES_MAX_X_SPAWN = SCREEN_WIDTH - ENEMIES_WIDTH - PADDING;


    private Constants() {

    }
}
