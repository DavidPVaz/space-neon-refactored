package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle.Obstacle;
import david.vaz.space.neon.refactored.engine.modules.Collision;
import david.vaz.space.neon.refactored.game.factories.EnemyGenerator;
import david.vaz.space.neon.refactored.game.factories.ObstacleGenerator;
import david.vaz.space.neon.refactored.game.factories.PowerUpGenerator;

import java.util.*;

public final class Game {

    private final List<Player> players;
    private final List<Bullet> bullets;
    private final List<Enemy> enemies;
    private final List<Obstacle> obstacles;
    private final List<PowerUp> powerUps;
    private boolean running;

    public Game(Player ...players) {
        this.players = Arrays.asList(players);
        this.bullets = new LinkedList<>();
        this.enemies = new LinkedList<>();
        this.obstacles = new LinkedList<>();
        this.powerUps = new LinkedList<>();
        running = true;
    }

    public void init() {
        showContent();
    }

    public void loop() {

        generateEnemy();
        generateObstacle();
        generatePowerUp();

        Collision.checkIfAnyEnemyEntityHitPlayers(players, enemies, obstacles);
        Collision.checkIfBulletHitAnything(bullets, players, enemies, obstacles);

        players.forEach(Player::update);
        players.forEach(this::shoot);

        moveBullets();
        moveEnemies();
        moveObstacles();
        movePowerUps();
        players.forEach(Player::move);
    }

    public boolean doesNotEnd() {
        return running; //will return a match.all players are destroyed;
    }

    public void end() {
        //delete all game objects and clear lists
        running = false;
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

    private void generateObstacle() {

        Obstacle obstacle = ObstacleGenerator.generateObstacle();

        if (obstacle == null) {
            return;
        }

        obstacles.add(obstacle);
        obstacle.show();
    }

    private void generatePowerUp() {

        PowerUp powerUp = PowerUpGenerator.generatePowerUp();

        if (powerUp == null) {
            return;
        }

        powerUps.add(powerUp);
        powerUp.show();
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

    private void moveObstacles() {

        Iterator<Obstacle> obstacleIterator = obstacles.iterator();

        while (obstacleIterator.hasNext()) {

            Obstacle obstacle = obstacleIterator.next();

            if (obstacle.cantMove() || obstacle.isDestroyed()) {
                obstacle.hide();
                obstacleIterator.remove();
                continue;
            }

            obstacle.move();
        }
    }

    private void movePowerUps() {

        Iterator<PowerUp> powerUpIterator = powerUps.iterator();

        while (powerUpIterator.hasNext()) {

            PowerUp powerUp = powerUpIterator.next();

            if (powerUp.cantMove() || powerUp.hasBeenCollected()) {
                powerUp.hide();
                powerUpIterator.remove();
                continue;
            }

            powerUp.move();
        }
    }

}
