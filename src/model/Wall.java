package model;

import java.awt.*;

public class Wall extends Entity{
    public Wall(Image image,int x, int y, int width, int height) {
        super(image,x, y, width, height);
    }
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}
