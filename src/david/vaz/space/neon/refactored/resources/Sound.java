package david.vaz.space.neon.refactored.resources;

public enum Sound {
    BULLET_WHIZZING(Directory.EFFECTS),
    POWER_UP(Directory.EFFECTS),
    TORPEDO_IMPACT(Directory.EFFECTS),
    BOMB_CARRIER_SHORT(Directory.BACKGROUND_MUSIC),
    COMBAT_PLAN_SHORT(Directory.BACKGROUND_MUSIC),
    FINAL_ACT_SHORT(Directory.BACKGROUND_MUSIC),
    FOLLOW_ME_SHORT(Directory.BACKGROUND_MUSIC),
    GALACTIC_CHASE_SHORT(Directory.BACKGROUND_MUSIC),
    GALACTIC_TREK_SHORT(Directory.BACKGROUND_MUSIC),
    GOLD_GRYPHONS_SHORT(Directory.BACKGROUND_MUSIC),
    KNOCK_OUT_SHORT(Directory.BACKGROUND_MUSIC),
    MAD_RUN_SHORT(Directory.BACKGROUND_MUSIC),
    NINJA_PANDA_SHORT(Directory.BACKGROUND_MUSIC),
    POWER_BATTLE_SHORT(Directory.BACKGROUND_MUSIC),
    SMILEY_ISLAND_SHORT(Directory.BACKGROUND_MUSIC),
    STAR_COMMANDO_SHORT(Directory.BACKGROUND_MUSIC),
    STAR_WAY_SHORT(Directory.BACKGROUND_MUSIC),
    TWIN_TURBO_SHORT(Directory.BACKGROUND_MUSIC),
    WACKY_RACE_SHORT(Directory.BACKGROUND_MUSIC);

    private String path;

    Sound(String path) {
        this.path = path;
    }

    public String path() {
        return path + name().toLowerCase() + ".wav";
    }
}
