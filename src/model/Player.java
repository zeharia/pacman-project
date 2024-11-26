package model;

import view.GamePanel;
import view.ImageManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public int score;
    private int lives;
    private Direction direction;
    public final int moveSpeed = 4;
    private final int initialX;
    private final int initialY;
    private final ImageManager imageManager;
    private GamePanel gamePanel;
    private boolean isScared;
    private Direction nextDirection;
    private static final int TILE_SIZE = 24;

    public Player(Image image, int x, int y, int width, int height, ImageManager imageManager, GamePanel gamePanel) {
        super(image, x, y, width, height);
        this.lives = 3;
        this.score = 0;
        this.direction = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;
        this.initialX = x;
        this.initialY = y;
        this.imageManager = imageManager;
        this.gamePanel = gamePanel;
    }
    public void setNextDirection(Direction dir) {
        this.nextDirection = dir;
    }
    public void move(List<Wall> walls) {
        if (nextDirection != direction) {
            int testX = x;
            int testY = y;

            switch (nextDirection) {
                case UP -> testY -= moveSpeed;
                case DOWN -> testY += moveSpeed;
                case LEFT -> testX -= moveSpeed;
                case RIGHT -> testX += moveSpeed;
            }

            boolean canChangeDirection = true;
            Rectangle nextPos = new Rectangle(testX, testY, width, height);

            for (Wall wall : walls) {
                if (nextPos.intersects(wall.getBounds())) {
                    canChangeDirection = false;
                    break;
                }
            }
            if (canChangeDirection) {
                direction = nextDirection;
                // Changer l'image seulement quand la direction peut être changée
                setImageDirection(direction);
                x = testX;
                y = testY;
                return;
            }
        }

        // Le reste du code reste inchangé
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP -> newY -= moveSpeed;
            case DOWN -> newY += moveSpeed;
            case LEFT -> newX -= moveSpeed;
            case RIGHT -> newX += moveSpeed;
        }

        Rectangle newPos = new Rectangle(newX, newY, width, height);
        boolean canMove = true;

        for (Wall wall : walls) {
            if (newPos.intersects(wall.getBounds())) {
                canMove = false;
                break;
            }
            if (canMove) {
                x = newX;
                y = newY;
            }
        }
    }
    public void setImageDirection(Direction direction) {
        switch (direction) {
            case UP -> setImage(imageManager.pacmanUpImage);
            case DOWN -> setImage(imageManager.pacmanDownImage);
            case LEFT -> setImage(imageManager.pacmanleftImage);
            case RIGHT -> setImage(imageManager.pacmanRightImage);
        }
    }
    public void handleWallCollision(List<Wall> walls) {
        for (Wall wall : walls) {
            if (collision(this, wall)) {
                switch (direction) {
                    case UP -> y += moveSpeed;
                    case DOWN -> y -= moveSpeed;
                    case LEFT -> x += moveSpeed;
                    case RIGHT -> x -= moveSpeed;
                }
                return;
            }
        }
    }
    public void handleFoodCollision(List<Food> foods) {
        for (Food food :new ArrayList<>(foods)) {
            if (collision(this, food)) {
                gamePanel.incrementScore(food.getScore());
                foods.remove(food);
                break;
            }
        }
    }
    public void handleBigPointCollision(List<BigPoint> bigPoints, List<Ghost> ghosts, Image scaredGhostImage) {
        for (BigPoint bigPoint :new ArrayList<>(bigPoints)) {
            if (collision(this, bigPoint)) {
                gamePanel.incrementScore(bigPoint.getScore());
                bigPoints.remove(bigPoint);
                for (Ghost ghost : ghosts) {
                    ghost.startScareMode(scaredGhostImage);
                }
                break;
            }
        }
    }
    public void handleGhostCollision(List<Ghost> ghosts) {
        for (Ghost ghost : new ArrayList<>(ghosts)) {
            if (collision(this, ghost)) {
                if (ghost.isScared()) {
                    ghost.restorePosition();
                    gamePanel.incrementScore(200);
                } else {
                    this.restorePosition();
                    this.decrementLives();
                    gamePanel.decrementLives();
                }
            }
        }
    }
    public void decrementLives() {
        lives--;
    }
    public void restorePosition() {
        this.x = initialX;
        this.y = initialY;
        isScared = false;
    }
    public void resetLives() {
        this.lives = 3;
    }
    public boolean isGameOver() {
        return this.lives == 0;
    }
    public void secretPathPacman() {
        if (y == 9 * TILE_SIZE) {
            if (x >= (19 * TILE_SIZE)) {
                x = 0;
            } else if (x < 0) {
                x = 19 * TILE_SIZE;
            }
        }
    }
}

