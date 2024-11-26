package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel {
    private final Font titleFont;
    private final Font buttonFont;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final Color MENU_BACKGROUND = new Color(0, 0, 0);
    private final Color BUTTON_COLOR = new Color(25, 25, 112);
    private final Color TEXT_COLOR = Color.YELLOW;

    public GameMenu(ActionListener menuListener) {
        setLayout(new BorderLayout());
        setBackground(MENU_BACKGROUND);

        // Initialisation des polices
        titleFont = new Font("Arial", Font.BOLD, 50);
        buttonFont = new Font("Arial", Font.BOLD, 20);

        // Configuration du CardLayout pour switcher entre les menus
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(MENU_BACKGROUND);

        // Création des différents panels de menu
        JPanel mainMenu = createMainMenu(menuListener);
        JPanel difficultyMenu = createDifficultyMenu(menuListener);

        // Ajout des panels au CardLayout
        cardPanel.add(mainMenu, "MAIN");
        cardPanel.add(difficultyMenu, "DIFFICULTY");

        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createMainMenu(ActionListener listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(MENU_BACKGROUND);

        // Création du titre PACMAN
        JLabel titleLabel = new JLabel("PACMAN");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Création des boutons
        JButton playButton = createStyledButton("PLAY", "PLAY", listener);
        JButton instructionsButton = createStyledButton("INSTRUCTIONS", "INSTRUCTIONS", listener);

        // Ajout des composants avec espacement
        panel.add(Box.createVerticalStrut(100));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(50));
        panel.add(playButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(instructionsButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createDifficultyMenu(ActionListener listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(MENU_BACKGROUND);

        // Titre du menu de difficulté
        JLabel difficultyLabel = new JLabel("SELECT DIFFICULTY");
        difficultyLabel.setFont(titleFont);
        difficultyLabel.setForeground(TEXT_COLOR);
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Boutons de difficulté
        JButton easyButton = createStyledButton("EASY", "EASY", listener);
        JButton mediumButton = createStyledButton("MEDIUM", "MEDIUM", listener);
        JButton hardButton = createStyledButton("HARD", "HARD", listener);
        JButton backButton = createStyledButton("BACK", "BACK", listener);

        // Ajout des composants avec espacement
        panel.add(Box.createVerticalStrut(50));
        panel.add(difficultyLabel);
        panel.add(Box.createVerticalStrut(50));
        panel.add(easyButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(mediumButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(hardButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JButton createStyledButton(String text, String command, ActionListener listener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(BUTTON_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(BUTTON_COLOR.brighter());
                } else {
                    g2.setColor(BUTTON_COLOR);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                FontMetrics metrics = g2.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.setColor(TEXT_COLOR);
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };

        button.setFont(buttonFont);
        button.setActionCommand(command);
        button.addActionListener(listener);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));

        return button;
    }

    // Méthodes pour changer de menu
    public void showDifficultyMenu() {
        cardLayout.show(cardPanel, "DIFFICULTY");
    }

    public void showMainMenu() {
        cardLayout.show(cardPanel, "MAIN");
    }
}