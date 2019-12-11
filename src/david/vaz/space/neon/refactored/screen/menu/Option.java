package david.vaz.space.neon.refactored.screen.menu;

public enum Option {
    SINGLE_PLAYER,
    MULTI_PLAYER,
    VERSUS,
    INSTRUCTIONS;

    public Option previous() {
        int ordinal = ordinal() - 1;
        System.out.println(ordinal());
        return ordinal == -1 ? this : values()[ordinal];
    }

    public Option next() {
        int ordinal = ordinal() + 1;
        return ordinal == values().length ? this : values()[ordinal];
    }
}
