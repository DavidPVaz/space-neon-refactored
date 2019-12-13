package david.vaz.space.neon.refactored.resources;

public enum Image {
    BLUE_BULLET(Directory.BULLET),
    GREEN_BULLET(Directory.BULLET),
    RED_BULLET(Directory.BULLET),
    DIAMOND_ENEMY(Directory.ENEMY),
    NORMAL_ENEMY(Directory.ENEMY),
    BIG_OBSTACLE(Directory.ENEMY),
    MEDIUM_OBSTACLE(Directory.ENEMY),
    SMALL_OBSTACLE(Directory.ENEMY),
    BLUE_HEART(Directory.HEART),
    GREEN_HEART(Directory.HEART),
    PLAYER_BLUE(Directory.PLAYER),
    PLAYER_GREEN(Directory.PLAYER),
    PLAYER_BLUE_REVERSED(Directory.PLAYER),
    DOUBLE_SHOOTING(Directory.POWER_UP),
    EXTRA_DAMAGE(Directory.POWER_UP),
    EXTRA_LIFE(Directory.POWER_UP),
    FASTER_BULLET(Directory.POWER_UP),
    FASTER_PLAYER(Directory.POWER_UP),
    GAME_SCREEN(Directory.SCREEN),
    INSTRUCTIONS_SCREEN(Directory.SCREEN),
    MENU_SCREEN(Directory.SCREEN),
    PAUSE_SCREEN(Directory.SCREEN),
    MENU_ARROW(Directory.SCREEN),
    BOTTOM_BAR(Directory.BAR),
    TOP_BAR(Directory.BAR);

    private String path;

    Image(String path) {
        this.path = path;
    }

    public String path() {
        return path + name().toLowerCase() + ".png";
    }


}
