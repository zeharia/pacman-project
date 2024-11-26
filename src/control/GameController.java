package control;

import model.*;
import view.GamePanel;
import view.ImageManager;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class GameController {
    private Player player;
    private Ghost ghost;
    private final List<Ghost> ghosts;
    private final List<Food> foods;
    private final List<BigPoint> bigPoints;
    private final List<Wall> walls;
    public final List<Fruit>fruits;

    private final GameMap gameMap;
    private final ImageManager imageManager;
    private final Timer gameLoop;
    private GamePanel gamePanel;
    private KeyHandler keyHandler;
    private GameDifficulty currentDifficulty = GameDifficulty.EASY;


    public GameController(GameMap gameMap, ImageManager imageManager) {
        this.ghosts = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.bigPoints = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.fruits=new ArrayList<>();
        this.gameMap = gameMap;
        this.imageManager = imageManager;
        this.keyHandler = new KeyHandler(null);

        gameLoop = new Timer(16, (ActionEvent e) -> {
            updateGamePlayer();
            updateGhosts();
            updateFruits();
            gamePanel.repaint();
        });
    }

    public void initializeGame() {
        ghosts.clear();
        foods.clear();
        bigPoints.clear();
        fruits.clear();
        String[] tileMap = gameMap.getTileMap();
        for (int row = 0; row < tileMap.length; row++) {
            for (int col = 0; col < tileMap[row].length(); col++) {
                char tile = tileMap[row].charAt(col);
                int x = col * 24;
                int y = row * 24;

                switch (tile) {
                    case 'P':
                        player = new Player(imageManager.pacmanRightImage, x, y, 24, 24, imageManager,gamePanel);
                        keyHandler.setPlayer(player);
                        break;
                    case 'X':
                        walls.add(new Wall(imageManager.getWallImage(), x, y, 24, 24));
                        break;
                    case 'b':
                        ghosts.add(new Ghost(imageManager.getBlueGhostImage(), x, y, 24, 24));
                        break;
                    case 'r':
                        ghosts.add(new Ghost(imageManager.getRedGhostImage(), x, y, 24, 24));
                        break;
                    case 'p':
                        ghosts.add(new Ghost(imageManager.getPinkGhostImage(), x, y, 24, 24));
                        break;
                    case 'o':
                        ghosts.add(new Ghost(imageManager.getOrangeGhostImage(), x, y, 24, 24));
                        break;
                    case 'A':
                        bigPoints.add(new BigPoint(null, x, y, 24, 24));
                        break;
                    case ' ':
                        foods.add(new Food(null, x, y, 24, 24));
                        break;
                }
            }
        }
        fruits.add(new Fruit(imageManager.getStrawberryImage(), -24, -24, 24, 24, 8000,gameMap));
        fruits.add(new Fruit(imageManager.getCherryImage(), -24, -24, 24, 24, 8000,gameMap));
        fruits.add(new Fruit(imageManager.getOrangeImage(), -24, -24, 24, 24, 8000,gameMap));

        for (Fruit fruit : fruits) {
            fruit.setGamePanel(gamePanel);
            fruit.setPlayer(player);
            fruit.setWalls(walls);
            fruit.setGhosts(ghosts);
            fruit.setFruits(fruits);
            fruit.setVisible(false);
            fruit.setDifficulty(currentDifficulty);
        }
    }
    public void updateGhosts() {
        for (Ghost ghost : ghosts) {
            if (currentDifficulty == GameDifficulty.HARD && !ghost.isScared()) {
                ghost.moveDFS(player.getX(), player.getY());
            } else {
                ghost.setSpeed(currentDifficulty.getGhostSpeed());
                ghost.moveRandom();
            }
            ghost.handleWallCollision(walls);
            ghost.updateScareMode();
        }
    }
    public void updateGamePlayer() {
        player.move(walls);
        player.secretPathPacman();
        player.handleWallCollision(walls);
        player.handleFoodCollision(foods);
        player.handleBigPointCollision(bigPoints, ghosts, ImageManager.ghostScaredImage);
        player.handleGhostCollision(ghosts);
        if (player.isGameOver()){resetGame();}
        if (foods.isEmpty())nextLevel();
    }
    public void updateFruits(){
        if (!fruits.isEmpty()){
            for (Fruit fruit:fruits){
                fruit.checkCollisionWithPlayer();
                fruit.isVisible();
                fruit.spawnFruit();
            }
        }

    }
    public void resetGame() {
        player.restorePosition();
        player.resetLives();
        for (Ghost ghost : new ArrayList<>(ghosts)) {
            ghost.restorePosition();
        }
        foods.clear();
        bigPoints.clear();
        initializeGame();
        player.score = 0;
    }
    public void nextLevel(){
        player.restorePosition();
        for (Ghost ghost:ghosts){
            ghost.restorePosition();
        }
        foods.clear();
        bigPoints.clear();
        fruits.clear();
        initializeGame();
    }
    public Player getPlayer() {
        return player;
    }
    public List<BigPoint> getBigPoints() {
        return bigPoints;
    }
    public List<Food> getFoods() {
        return foods;
    }
    public List<Ghost> getGhosts() {
        return ghosts;
    }
    public List<Wall> getWalls() {
        return walls;
    }
    public void startGame(){
        gameLoop.start();
    }
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(keyHandler);  // Ajout du KeyListener au panel
    }
    public void setDifficulty(GameDifficulty difficulty) {
        this.currentDifficulty = difficulty;
        for (Fruit fruit : fruits) {
            fruit.setDifficulty(difficulty);
        }
    }
}
