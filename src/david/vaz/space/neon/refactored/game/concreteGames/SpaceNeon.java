package david.vaz.space.neon.refactored.game.concreteGames;

import david.vaz.space.neon.refactored.drawable.entity.bullet.Bullet;
import david.vaz.space.neon.refactored.drawable.entity.collectibles.PowerUp;
import david.vaz.space.neon.refactored.drawable.entity.hittable.obstacle.Obstacle;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.Player;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.Enemy;
import david.vaz.space.neon.refactored.drawable.entity.hittable.shootable.enemy.FinalBoss;
import david.vaz.space.neon.refactored.engine.Engine;
import david.vaz.space.neon.refactored.game.Collision;
import david.vaz.space.neon.refactored.game.Score;
import david.vaz.space.neon.refactored.game.factories.EnemyGenerator;
import david.vaz.space.neon.refactored.game.factories.ObstacleGenerator;
import david.vaz.space.neon.refactored.game.factories.PowerUpGenerator;
import david.vaz.space.neon.refactored.resources.Sound;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static david.vaz.space.neon.refactored.game.Constants.SCORE_INCREMENT;

public final class SpaceNeon extends AbstractGame {

    private final List<Enemy> enemies;
    private final List<Obstacle> obstacles;
    private final List<PowerUp> powerUps;
    private final Score score;
    private boolean bossHasBeenSpawned;
    private boolean bossHasNotBeenDefeated;

    public SpaceNeon(Player... players) {
        this.players.addAll(Arrays.asList(players));
        enemies = new LinkedList<>();
        obstacles = new LinkedList<>();
        powerUps = new LinkedList<>();
        score = new Score();
        bossHasBeenSpawned = false;
        bossHasNotBeenDefeated = true;
    }

    @Override
    public void init(Engine.State state) {
        manager.stopCurrentInLoopSound();
        players.forEach(Player::show);
        score.show();
        frames.show();
        manager.play(gameSounds.get(state), true);
    }

    @Override
    public void loop() {

        generateEnemy();
        generateObstacle();
        generatePowerUp();

        movePlayers();
        moveBullets();
        moveEnemies();
        moveObstacles();
        movePowerUps();

        players.forEach(Player::update);

        shoot(players, enemies);

        int toUpdate = Collision.checkIfBulletHitAnything(bullets, players, enemies, obstacles);
        score.update(toUpdate);

        Collision.checkIfAnyEnemyEntityHitPlayers(players, enemies, obstacles);
        Collision.checkIfAnyPlayerCatchAnyPowerUp(players, powerUps);

        frames.update(Engine.getFPS());
    }

    @Override
    public boolean doesNotEnd() {
        return notDisposed && players.stream().anyMatch(Player::isAlive) && bossHasNotBeenDefeated;
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

        if (bossHasBeenSpawned) {
            return;
        }

        Enemy enemy = EnemyGenerator.generateEnemy(score.value());

        if (enemy == null) {
            return;
        }

        if (enemy instanceof FinalBoss) {
            manager.stopCurrentInLoopSound();
            manager.play(Sound.FINAL_ACT_SHORT, true);
            bossHasBeenSpawned = true;
        }

        if (score.value() % SCORE_INCREMENT == 0) {
            enemy.boostHp(15);
        }

        enemies.add(enemy);
        enemy.show();
    }

    private void generateObstacle() {

        if (bossHasBeenSpawned) {
            return;
        }

        Obstacle obstacle = ObstacleGenerator.generateObstacle();

        if (obstacle == null) {
            return;
        }

        obstacles.add(obstacle);
        obstacle.show();
    }

    private void generatePowerUp() {

        if (bossHasBeenSpawned) {
            return;
        }

        PowerUp powerUp = PowerUpGenerator.generatePowerUp(score.value());

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
                bossHasNotBeenDefeated = !(enemy instanceof FinalBoss);
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
