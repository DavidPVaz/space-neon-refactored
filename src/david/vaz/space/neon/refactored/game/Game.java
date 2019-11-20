package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;

import java.util.*;

public final class Game {

    private List<Player> players;
    private List<Bullet> bullets;
    private boolean running;

    public Game(Player ...players) {
        this.players = Arrays.asList(players);
        this.bullets = new LinkedList<>();
        running = true;
    }

    public void init() {
        showContent();
    }

    public void loop() {
        players.forEach(Player::move);
        players.forEach(this::shoot);
        moveBullets();
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

    private void shoot(Player player) {

        Bullet bullet = player.shoot();

        if (bullet == null) {
            return;
        }

        bullet.show();
        bullets.add(bullet);
    }

    private void moveBullets() {

        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {

            Bullet bullet = bulletIterator.next();

            if (bullet.cantMove()) {
                bullet.hide();
                bulletIterator.remove();
                continue;
            }

            bullet.move();

        }
    }

}
