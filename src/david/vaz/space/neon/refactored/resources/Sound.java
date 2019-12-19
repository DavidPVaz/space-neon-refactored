package david.vaz.space.neon.refactored.resources;

public enum Sound {
    BULLET_WHIZZING(Directory.EFFECTS),
    POWER_UP(Directory.EFFECTS),
    TORPEDO_IMPACT(Directory.EFFECTS),
    FINAL_ACT_SHORT(Directory.BACKGROUND_MUSIC),
    GOLD_GRYPHONS_SHORT(Directory.BACKGROUND_MUSIC),
    MAD_RUN_SHORT(Directory.BACKGROUND_MUSIC),
    NINJA_PANDA_SHORT(Directory.BACKGROUND_MUSIC),
    WACKY_RACE_SHORT(Directory.BACKGROUND_MUSIC);

    private String path;

    Sound(String path) {
        this.path = path;
    }

    public String path() {
        return path + name().toLowerCase() + ".wav";
    }
}
