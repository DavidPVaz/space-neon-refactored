package david.vaz.space.neon.refactored.resources;

public enum Sound {
    FIREBALL(Directory.SOUND),
    GAME(Directory.SOUND),
    MENU(Directory.SOUND);

    private String path;

    Sound(String path) {
        this.path = path;
    }

    public String path() {
        return path + name().toLowerCase() + ".wav";
    }
}
