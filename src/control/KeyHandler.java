package control;

import model.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private Player player;

    public KeyHandler(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP ->
                    player.setNextDirection(Player.Direction.UP);
            case KeyEvent.VK_DOWN ->
                    player.setNextDirection(Player.Direction.DOWN);
            case KeyEvent.VK_LEFT ->
                    player.setNextDirection(Player.Direction.LEFT);
            case KeyEvent.VK_RIGHT ->
                    player.setNextDirection(Player.Direction.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void setPlayer(Player player) {
        this.player = player;
    }
}