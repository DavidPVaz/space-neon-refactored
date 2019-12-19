package david.vaz.space.neon.refactored.game.concreteGames;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Shootable;
import david.vaz.space.neon.refactored.game.Frames;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractGame implements Game {

    protected final List<Player> players;
    protected final List<Bullet> bullets;
    protected final Frames frames;
    protected boolean notDisposed;

    AbstractGame() {
        players = new LinkedList<>();
        bullets = new LinkedList<>();
        frames = new Frames();
        notDisposed = true;
    }

    @SafeVarargs
    final void shoot(List<? extends Shootable>... shootables) {

        for (List<? extends Shootable> listOfShootables : shootables) {

            for (Shootable shootable : listOfShootables) {

                List<Bullet> bullets = shootable.shoot();

                if (bullets == null) {
                    continue;
                }

                bullets.forEach(Bullet::show);
                this.bullets.addAll(bullets);
            }
        }
    }

    final void moveBullets() {

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

    abstract void movePlayers();

    public final void dispose() {
        notDisposed = false;
    }
}
