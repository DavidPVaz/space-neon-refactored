package david.vaz.space.neon.refactored.game.concreteGames;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Shootable;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.engine.modules.AudioManager;
import david.vaz.space.neon.refactored.game.Frames;
import david.vaz.space.neon.refactored.resources.Sound;

import java.util.*;

abstract class AbstractGame implements Game {

    protected final List<Player> players;
    protected final List<Bullet> bullets;
    protected final Frames frames;
    protected boolean notDisposed;
    protected final AudioManager manager;
    protected final Map<Engine.State, Sound> gameSounds;

    AbstractGame() {
        players = new LinkedList<>();
        bullets = new LinkedList<>();
        frames = new Frames();
        notDisposed = true;
        manager = AudioManager.getInstance();
        gameSounds = possibleSounds();
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

    @Override
    public final void dispose() {
        notDisposed = false;
    }

    private Map<Engine.State, Sound> possibleSounds() {

        Map<Engine.State, Sound> map = new HashMap<>();

        map.put(Engine.State.SINGLE_PLAYER, Sound.GOLD_GRYPHONS_SHORT);
        map.put(Engine.State.MULTI_PLAYER, Sound.NINJA_PANDA_SHORT);
        map.put(Engine.State.VERSUS, Sound.MAD_RUN_SHORT);

        return map;
    }
}
