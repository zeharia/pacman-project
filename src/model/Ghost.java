package model;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost extends Entity {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction getOpposite(Direction dir) {
            return switch (dir) {
                case UP -> DOWN;
                case DOWN -> UP;
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }
    }

    private Direction currentDirection;
    private final int initialX;
    private final int initialY;
    private int moveSpeed = 2;
    private boolean isScared;
    private long scareStartTime;
    private static final long SCARE_DURATION = 5000;
    private final Image normalImage;
    private List<Wall> walls;

    public Ghost(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
        this.currentDirection = Direction.UP;
        this.initialX = x;
        this.initialY = y;
        this.isScared = false;
        this.walls = new ArrayList<>();
        this.normalImage = image;
    }
    public void moveInDirection(Direction direction) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP -> newY -= moveSpeed;
            case DOWN -> newY += moveSpeed;
            case LEFT -> newX -= moveSpeed;
            case RIGHT -> newX += moveSpeed;
        }
        this.x = newX;
        this.y = newY;
    }
    public void moveRandom() {
        if (isValidGhostMove(currentDirection)) {
            moveInDirection(currentDirection);
        } else {
            List<Direction> validDirections = getValidGhostDirections();
            if (!validDirections.isEmpty()) {
                Random random = new Random();
                currentDirection = validDirections.get(random.nextInt(validDirections.size()));
                moveInDirection(currentDirection);
            }
        }
    }
    public boolean isValidGhostMove(Direction direction) {
        int nextX = this.x;
        int nextY = this.y;

        switch (direction) {
            case UP -> nextY -= moveSpeed;
            case DOWN -> nextY += moveSpeed;
            case LEFT -> nextX -= moveSpeed;
            case RIGHT -> nextX += moveSpeed;
        }

        Rectangle nextBounds = new Rectangle(nextX, nextY, this.getWidth(), this.getHeight());

        for (Wall wall : walls) {
            if (wall.getBounds().intersects(nextBounds)) {
                return false;
            }
        }
        return true;
    }
    public List<Direction> getValidGhostDirections() {
        List<Direction> validDirections = new ArrayList<>();
        Direction oppositeDirection = Direction.getOpposite(this.currentDirection);

        for (Direction dir : Direction.values()) {
            if (dir != oppositeDirection && isValidGhostMove(dir)) {
                validDirections.add(dir);
            }
        }
        return validDirections;
    }
    public void handleWallCollision(List<Wall> walls) {
        this.walls = walls;
    }
    public void moveDFS(int targetX, int targetY) {
        dfsMove(this.x, this.y, targetX, targetY, new ArrayList<>());
    }
    private boolean dfsMove(int currentX, int currentY, int targetX, int targetY, List<Ghost.Direction> visitedDirections) {
        if (currentX == targetX && currentY == targetY) {
            return true;
        }

        for (Ghost.Direction dir : getValidGhostDirections()) {
            if (!visitedDirections.contains(dir)) {
                visitedDirections.add(dir);
                int nextX = currentX, nextY = currentY;
                switch (dir) {
                    case UP -> nextY -= moveSpeed;
                    case DOWN -> nextY += moveSpeed;
                    case LEFT -> nextX -= moveSpeed;
                    case RIGHT -> nextX += moveSpeed;
                }

                if (dfsMove(nextX, nextY, targetX, targetY, visitedDirections)) {
                    moveInDirection(dir);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isScared() {
        return isScared;
    }
    public void startScareMode(Image scaredImage) {
        isScared = true;
        scareStartTime = System.currentTimeMillis();
        setImage(scaredImage);
    }
    public void updateScareMode() {
        if (isScared && System.currentTimeMillis() - scareStartTime > SCARE_DURATION) {
            isScared = false;
            setImage(normalImage);
        }
    }
    public void restorePosition() {
        this.x = initialX;
        this.y = initialY;
        this.currentDirection = Direction.UP;
        this.isScared = false;
        setImage(normalImage);
    }
    public void setSpeed(int speed) {
        this.moveSpeed = speed;
    }
}
