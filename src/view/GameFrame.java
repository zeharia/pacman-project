package view;

import control.GameController;
import model.GameDifficulty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame implements ActionListener {
    private final JFrame frame;
    private final GamePanel gamePanel;
    private final GameMenu gameMenu;
    private final CardLayout cardLayout;
    private final JPanel mainContainer;
    private final GameController gameController;

    public GameFrame(GamePanel gamePanel, GameController gameController) {
        this.gamePanel = gamePanel;
        this.gameController = gameController;

        frame = new JFrame("Pacman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        gameMenu = new GameMenu(this);

        mainContainer.add(gameMenu, "MENU");
        mainContainer.add(gamePanel, "GAME");

        frame.add(mainContainer);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(mainContainer, "MENU");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "PLAY":
                gameMenu.showDifficultyMenu();
                break;
            case "INSTRUCTIONS":
                JOptionPane.showMessageDialog(frame,
                        "Use arrow keys to move Pacman.\n" +
                                "Eat all dots to win.\n" +
                                "Avoid ghosts unless you eat a power pellet.\n" +
                                "Power pellets make ghosts vulnerable temporarily.",
                        "Instructions",
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            case "BACK":
                gameMenu.showMainMenu();
                break;
            case "EASY":
                startGame("EASY");
                break;
            case "MEDIUM":
                startGame("MEDIUM");
                break;
            case "HARD":
                startGame("HARD");
                break;
        }
    }
    private void startGame(String difficulty) {
        gameController.setDifficulty(GameDifficulty.fromString(difficulty));
        gameController.initializeGame();
        cardLayout.show(mainContainer, "GAME");
        gamePanel.requestFocusInWindow();
        gameController.startGame();
    }
}