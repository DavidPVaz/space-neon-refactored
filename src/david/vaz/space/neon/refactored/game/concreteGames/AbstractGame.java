package david.vaz.space.neon.refactored.game.concreteGames;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.game.Frames;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractGame implements Game {

    protected final List<Player> players;
    protected final List<Bullet> bullets;
    protected final Frames frames;

    AbstractGame() {
        this.players = new LinkedList<>();
        this.bullets = new LinkedList<>();
        this.frames = new Frames();
    }

    void shoot(Player player) {

        //since booth players and enemies will shoot, i'll create a shootable interface
        //and use varargs again to ask them all to shoot
        List<Bullet> bullets = player.shoot();

        if (bullets == null) {
            return;
        }

        bullets.forEach(Bullet::show);
        this.bullets.addAll(bullets);
    }

    void moveBullets() {

        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {

            Bullet bullet = bulletIterator.next();

            if (bullet.cantMove() || bullet.hasCollided()) {
                bullet.hide();
                bulletIterator.remove();
                continue;
            }

            bullet.move();
        }
    }

    void movePlayers() {

        Iterator<Player> playerIterator = players.iterator();

        while (playerIterator.hasNext()) {

            Player player = playerIterator.next();

            if (player.isDestroyed()) {
                player.hide();
                playerIterator.remove();
                continue;
            }

            player.move();
        }
    };
}
