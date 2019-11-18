package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.hitable.Player;

public final class Game {

    private Player player;
    private boolean running;

    public Game(Player player) {
        this.player = player;
        running = true;
    }

    public void init() {
        showContent();
    }

    public void loop() {
        player.move();
    }

    public boolean doesNotEnd() {
        return running;
    }

    public void end() {
        running = false;
    }

    private void showContent() {
        player.show();
    }

}
