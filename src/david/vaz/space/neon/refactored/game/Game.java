package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.hitable.Player;

import java.util.*;

public final class Game {

    private List<Player> players;
    private boolean running;

    public Game(Player ...players) {
        this.players = Arrays.asList(players);
        running = true;
    }

    public void init() {
        showContent();
    }

    public void loop() {
        players.forEach(Player::move);
    }

    public boolean doesNotEnd() {
        return running;
    }

    public void end() {
        running = false;
    }

    private void showContent() {
        players.forEach(Player::show);
    }

}
