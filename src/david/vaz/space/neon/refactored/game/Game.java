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

        players.forEach(Player::move);
        players.forEach(this::shoot);
        moveEnemies();
        moveBullets();
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

            if (bullet.cantMove()) {
                bullet.hide();
                bulletIterator.remove();
                continue;
            }

            bullet.move();

            if (Collision.detectsThatBulletHitAnything(bullet, players, enemies)) {
                bullet.hide();
                bulletIterator.remove();
            }
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

            //enemies will check if they collide with the player
            //also need another for moving the obstacles and check if obstacles collide with enemy
        }
    }

}
