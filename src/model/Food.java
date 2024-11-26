package model;

import java.awt.*;

public class Food extends Entity{
    private final int score;
    public Food(Image image,int x, int y, int width, int height) {
        super(image,x, y, width, height);
        this.score=10;
    }
    public int getScore() {
        return score;
    }
}
