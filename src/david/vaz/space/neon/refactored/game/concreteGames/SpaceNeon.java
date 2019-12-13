package david.vaz.space.neon.refactored.game.concreteGames;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.hittable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle.Obstacle;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.game.Collision;
import david.vaz.space.neon.refactored.game.Score;
import david.vaz.space.neon.refactored.game.factories.EnemyGenerator;
import david.vaz.space.neon.refactored.game.factories.ObstacleGenerator;
import david.vaz.space.neon.refactored.game.factories.PowerUpGenerator;

import java.util.*;

public final class SpaceNeon extends AbstractGame {

    private final List<Enemy> enemies;
    private final List<Obstacle> obstacles;
    private final List<PowerUp> powerUps;
    private final Score score;

    public SpaceNeon(Player... players) {
        this.players.addAll(Arrays.asList(players));
        this.enemies = new LinkedList<>();
        this.obstacles = new LinkedList<>();
        this.powerUps = new LinkedList<>();
        this.score = new Score();
    }

    @Override
    public void init() {
        players.forEach(Player::show);
        score.show();
        frames.show();
    }

    @Override
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

        movePlayers();
        moveBullets();
        moveEnemies();
        moveObstacles();
        movePowerUps();

        frames.update(Engine.getFPS());
    }

    @Override
    public boolean doesNotEnd() {
        return players.stream().anyMatch(Player::isAlive);
    }

    @Override
    public void end() {
        players.forEach(Player::hide);
        players.clear();

        bullets.forEach(Bullet::hide);
        bullets.clear();

        enemies.forEach(Enemy::hide);
        enemies.clear();

        obstacles.forEach(Obstacle::hide);
        obstacles.clear();

        powerUps.forEach(PowerUp::hide);
        powerUps.clear();

        score.hide();
        frames.hide();
    }

    @Override
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
