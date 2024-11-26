package model;

import view.GamePanel;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Fruit extends Entity {
    private int points;
    private GameMap gameMap;
    private long spawnTime;
    private boolean visible;
    private Random random;
    private GamePanel gamePanel;
    private Player player;
    public List<Fruit> fruits;
    public List<Wall> walls;
    public List<Ghost> ghosts;
    private GameDifficulty difficulty;

    public Fruit(Image image, int x, int y, int width, int height, long duration,GameMap gameMap) {
        super(image, x, y, width, height);
        this.visible = false;
        this.random = new Random();
        this.difficulty = GameDifficulty.EASY;
        this.gameMap=gameMap;
    }
    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }
    public boolean isVisible() {
        if (visible) {
            long duration;
            switch (difficulty) {
                case EASY: duration = 15000; break;
                case MEDIUM: duration = 10000; break;
                case HARD: duration = 5000; break;
                default: duration = 15000;
            }
            if (System.currentTimeMillis() - spawnTime > duration) {
                visible = false;
            }
        }
        return visible;
    }
    public void spawnFruit() {
        boolean anyFruitVisible = false;
        for (Fruit f : fruits) {
            if (f.visible) {
                anyFruitVisible = true;
                break;
            }
        }

        if (!anyFruitVisible && random.nextInt(100) < 2) {
            for (int attempts = 0; attempts < 10; attempts++) {
                int maxGridX = gameMap.getRow();
                int maxGridY = gameMap.getCol();

                int x = (random.nextInt(maxGridX + 1)) * 24;
                int y = (random.nextInt(maxGridY + 1)) * 24;

                if (isValidPosition(x, y, walls, ghosts, player)) {
                    Fruit randomFruit = fruits.get(random.nextInt(fruits.size()));
                    randomFruit.setX(x);
                    randomFruit.setY(y);
                    randomFruit.visible = true;
                    randomFruit.spawnTime = System.currentTimeMillis();
                    break;
                }
            }
        }
    }
    private static boolean isValidPosition(int x, int y, List<Wall> walls, List<Ghost> ghosts, Player player) {
        for (Wall wall : walls) {
            if (x == wall.getX() && y == wall.getY()) return false;
        }
        for (Ghost ghost : ghosts) {
            if (x == ghost.getX() && y == ghost.getY()) return false;
        }
        return !(x == player.getX() && y == player.getY());
    }
    public void checkCollisionWithPlayer() {
        if (visible && collision(this, player)) {
            gamePanel.incrementScore(300);
            visible = false;
        }
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (visible) {
            this.spawnTime = System.currentTimeMillis();
        }
    }
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }
    public void setGhosts(List<Ghost> ghosts) {
        this.ghosts = ghosts;
    }
    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }
    private void setX(int x) {
        this.x = x;
    }
    private void setY(int y) {
        this.y = y;
    }
    public int getPoints() {
        return points;
    }
}