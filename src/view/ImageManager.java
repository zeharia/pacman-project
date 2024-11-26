package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageManager {
    public Image wallImage;

    public Image blueGhostImage;
    public Image orangeGhostImage;
    public Image pinkGhostImage;
    public Image redGhostImage;
    public static Image ghostScaredImage;

    public Image pacmanUpImage;
    public Image pacmanDownImage;
    public Image pacmanleftImage;
    public Image pacmanRightImage;

    public Image cherryImage;
    public Image strawberryImage;
    public Image orangeImage;
    public Image heartImage;

    public ImageManager(){
        loadImages();
    }
    private void loadImages() {
        try {
            wallImage = loadImage("/images/tile/wall.png");
            blueGhostImage = loadImage("/images/ghosts/CyanGhost.png");
            orangeGhostImage = loadImage("/images/ghosts/OrangeGhost.png");
            pinkGhostImage = loadImage("/images/ghosts/PinkGhost.png");
            redGhostImage = loadImage("/images/ghosts/RedGhost.png");
            pacmanUpImage = loadImage("/images/pacman/pacmanUp.png");
            pacmanDownImage = loadImage("/images/pacman/pacmanDown.png");
            pacmanleftImage = loadImage("/images/pacman/pacmanLeft.png");
            pacmanRightImage = loadImage("/images/pacman/pacmanRight.png");
            ghostScaredImage = loadImage("/images/ghosts/scarredGhost.png");
            cherryImage = loadImage("/images/fruits/cherry.png");
            strawberryImage = loadImage("/images/fruits/strawberry.png");
            orangeImage = loadImage("/images/fruits/orange.png");
            heartImage = loadImage("/images/pacman/pacmanRight.png");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Image loadImage(String path) throws IOException {
        try (var stream = getClass().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("folder not found: " + path);
            }
            return ImageIO.read(stream);
        }
    }
    public Image getWallImage() {
        return wallImage;
    }
    public Image getRedGhostImage() {
        return redGhostImage;
    }
    public Image getBlueGhostImage() {
        return blueGhostImage;
    }
    public Image getPinkGhostImage() {
        return pinkGhostImage;
    }
    public Image getOrangeGhostImage() {
        return orangeGhostImage;
    }
    public Image getCherryImage() {
        return cherryImage;
    }
    public Image getStrawberryImage() {
        return strawberryImage;
    }
    public Image getOrangeImage() {
        return orangeImage;
    }
    public Image getHeartImage() {
        return heartImage;
    }
}
