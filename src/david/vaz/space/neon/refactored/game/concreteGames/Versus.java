package david.vaz.space.neon.refactored.game.concreteGames;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Player;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.game.Collision;

public final class Versus extends AbstractGame {

    public Versus(Player playerOne, Player playerTwo) {
        players.add(playerOne);
        players.add(playerTwo);
    }

    @Override
    public void init(Engine.State state) {
        manager.stopCurrentInLoopSound();
        players.forEach(Player::show);
        frames.show();
        manager.play(gameSounds.get(state), true);
    }

    @Override
    public void loop() {

        Collision.checkIfBulletHitAnything(bullets, players);

        players.forEach(Player::update);

        shoot(players);

        moveBullets();
        movePlayers();

        frames.update(Engine.getFPS());
    }

    @Override
    public boolean doesNotEnd() {
        return notDisposed && players.stream().allMatch(Player::isAlive);
    }

    @Override
    public void end() {
        players.forEach(Player::hide);
        players.clear();

        bullets.forEach(Bullet::hide);
        bullets.clear();

        frames.hide();
    }

    @Override
    void movePlayers() {
        players.forEach(Player::move);
    }
}
