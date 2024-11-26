package view;

import control.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int TILE_SIZE = 24;
    private GameMap gameMap;
    private GameController gameController;
    private ImageManager imageManager;
    private int lives = 3; // Nombre initial de vies
    private int score = 0; // Score actuel
    private int highScore = 0; // Meilleur score

    public GamePanel(GameMap gameMap, GameController gameController) {
        this.gameMap = gameMap;
        this.gameController = gameController;
        this.imageManager = new ImageManager();
        setBackground(Color.black);
        gameController.setGamePanel(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessins existants (murs, nourriture, fantômes, etc.)
        for (Wall wall : gameController.getWalls()) {
            g2d.drawImage(wall.getImage(), wall.getX(), wall.getY(), TILE_SIZE, TILE_SIZE, null);
        }
        for (Food food : gameController.getFoods()) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(food.getX() + 10, food.getY() + 10, 4, 4);
        }
        for (BigPoint bigPoint : gameController.getBigPoints()) {
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(bigPoint.getX() + 8, bigPoint.getY() + 8, 18, 18);
        }
        for (Ghost ghost : gameController.getGhosts()) {
            g2d.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), TILE_SIZE, TILE_SIZE, null);
        }
        Player player = gameController.getPlayer();
        g2d.drawImage(player.getImage(), player.getX(), player.getY(), TILE_SIZE, TILE_SIZE, null);

        for (Fruit fruit : gameController.fruits) {
            if (fruit.isVisible()) {
                g2d.drawImage(fruit.getImage(), fruit.getX(), fruit.getY(), fruit.getWidth(), fruit.getHeight(), this);
            }
        }

        // Police et couleurs pour le texte
        Font textFont = new Font("Arial", Font.BOLD, 20);
        g2d.setFont(textFont);
        g2d.setColor(Color.WHITE);

        // Affichage des vies et des scores en bas de l'écran
        int bottomMargin = getHeight() - 10; // 10 pixels du bas de l'écran
        int heartSize = 30;
        int heartSpacing = 20;
        int heartX = 10;

        // Affichage des cœurs
        for (int i = 0; i < lives; i++) {
            g2d.drawImage(imageManager.getHeartImage(), heartX, bottomMargin - heartSize, heartSize/2, heartSize/2, null);
            heartX += heartSpacing;
        }

        // Affichage du score
        String scoreText = "Score: " + score;
        String highScoreText = "High Score: " + highScore;

        // Calculer les positions centrées
        FontMetrics metrics = g2d.getFontMetrics(textFont);
        int scoreWidth = metrics.stringWidth(scoreText);
        int highScoreWidth = metrics.stringWidth(highScoreText);

        int centerX = getWidth() / 2;
        int leftScoreX = centerX - (scoreWidth + highScoreWidth + 50) / 2;
        int rightScoreX = leftScoreX + scoreWidth + 50;

        // Dessiner les textes de score
        g2d.drawString(scoreText, leftScoreX, bottomMargin);
        g2d.drawString(highScoreText, rightScoreX, bottomMargin);
    }

    @Override
    public Dimension getPreferredSize() {
        int width = gameMap.getRow()* TILE_SIZE;
        int height = gameMap.getCol()* TILE_SIZE + TILE_SIZE * 2;
        System.out.println(width);
        System.out.println(height);
        return new Dimension(width,height);
    }

    // Méthodes restantes inchangées
    public void decrementLives() {
        lives--;
        if (lives <= 0) {
            gameOver();
        }
        repaint();
    }

    public void incrementScore(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
        }
        repaint();
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score);
        resetGame();
    }

    private void resetGame() {
        lives = 3;
        score = 0;
        gameController.resetGame();
        repaint();
    }
}