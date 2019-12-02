package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.engine.modules.Collision;
import david.vaz.space.neon.refactored.game.factories.EnemyGenerator;

import java.util.*;

public final class Game {

    private List<Player> players;
    private List<Bullet> bullets;
    private List<Enemy> enemies;
    private boolean running;

    public Game(Player ...players) {
        this.players = Arrays.asList(players);
        this.bullets = new LinkedList<>();
        this.enemies = new LinkedList<>();
        running = true;
    }

    public void init() {
        showContent();
    }

    public void loop() {
        generateEnemy();

        Collision.checkIfAnyEnemyEntityHitPlayers(players, enemies);
        Collision.checkIfBulletHitAnything(bullets, players, enemies);

        players.forEach(Player::update);
        players.forEach(this::shoot);
        moveBullets();
        moveEnemies();
        players.forEach(Player::move);
    }

    public boolean doesNotEnd() {
        return running;
    }

    public void end() {
        running = false; //will return a match.all players are destroyed
    }

    private void showContent() {
        players.forEach(Player::show);
    }

    private void shoot(Player player) {

        //since booth players and enemies will shoot, i'll create a shootable interface
        //and use varargs again to ask them all to shoot
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

            if (bullet.cantMove() || bullet.hasCollided()) {
                bullet.hide();
                bulletIterator.remove();
                continue;
            }

            bullet.move();
        }
    }

    private void generateEnemy() {

        Enemy enemy = EnemyGenerator.generateEnemy();

        if (enemy == null) {
            return;
        }

        enemies.add(enemy);
        enemy.show();
    }

    private void moveEnemies() {

        Iterator<Enemy> enemyIterator = enemies.iterator();

        while (enemyIterator.hasNext()) {

            Enemy enemy = enemyIterator.next();

            if (enemy.cantMove() || enemy.isDestroyed()) {
                enemy.hide();
                enemyIterator.remove();
                continue;
            }

            enemy.move();
        }
    }

}
