package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BigPoint extends Entity{
    private final int score;
    public BigPoint(Image image,int x, int y, int width, int height) {
        super(image,x, y, width, height);
        this.score=50;
    }
    public int getScore() {
        return score;
    }
}
