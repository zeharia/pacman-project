import control.GameController;
import model.GameMap;
import view.GameFrame;
import view.GamePanel;
import view.ImageManager;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();
        ImageManager imageManager = new ImageManager();
        GameController gameController = new GameController(gameMap, imageManager);
        GamePanel gamePanel = new GamePanel(gameMap, gameController);
        gameController.setGamePanel(gamePanel);
        new GameFrame(gamePanel, gameController);
        gameController.initializeGame();
        gameController.startGame();
    }
}