package david.vaz.space.neon.refactored.resources;

public enum Image {
    BACKGROUND_1(Directory.GAME),
    BACKGROUND_2(Directory.GAME),
    BACKGROUND_3(Directory.GAME),
    BACKGROUND_4(Directory.GAME),
    BACKGROUND_5(Directory.GAME),
    PLAYER_1(Directory.GAME);

    private String path;

    Image(String path) {
        this.path = path;
    }

    public String path() {
        return path + name().toLowerCase() + ".png";
    }


}
