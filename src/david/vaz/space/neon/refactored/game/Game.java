package david.vaz.space.neon.refactored.game;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle.Obstacle;
import david.vaz.space.neon.refactored.engine.Engine;
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
    private final Score score;
    private final Frames frames;

    public Game(Player... players) {
        this.players = new LinkedList<>();
        this.players.addAll(Arrays.asList(players));
        this.bullets = new LinkedList<>();
        this.enemies = new LinkedList<>();
        this.obstacles = new LinkedList<>();
        this.powerUps = new LinkedList<>();
        this.score = new Score();
        this.frames = new Frames();
    }

    public void init() {
        players.forEach(Player::show);
        score.show();
        frames.show();
    }

    public void loop() {

        generateEnemy();
        generateObstacle();
        generatePowerUp();

        int toUpdate = Collision.checkIfBulletHitAnything(bullets, players, enemies, obstacles);
        score.update(toUpdate);

        Collision.checkIfAnyEnemyEntityHitPlayers(players, enemies, obstacles);
        Collision.checkIfAnyPlayerCatchAnyPowerUp(players, powerUps);

        players.forEach(Player::update);
        players.forEach(this::shoot);

        moveBullets();
        moveEnemies();
        moveObstacles();
        movePowerUps();
        movePlayers();

        frames.update(Engine.getFPS());
    }

    public boolean doesNotEnd() {
        return players.stream().anyMatch(Player::isAlive);
    }

    public void end() {
        //delete all game objects and clear lists
    }

    private void shoot(Player player) {

        //since booth players and enemies will shoot, i'll create a shootable interface
        //and use varargs again to ask them all to shoot
        List<Bullet> bullets = player.shoot();

        if (bullets == null) {
            return;
        }

        bullets.forEach(Bullet::show);
        this.bullets.addAll(bullets);
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

    private void movePlayers() {

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
