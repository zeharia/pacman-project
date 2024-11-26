package model;

public class GameMap {
    private int row=19;
    private int col =21;
    public String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXXXX",
            "XXA               AXX",
            "XX XX XXX X XXX XX XX",
            "XX                 XX",
            "XX XX X XXXXX X XX XX",
            "XX    X       X    XX",
            "XXXXX XXXX XXXX XXXXX",
            "OOOOX X       X XOOOO",
            "XXXXX X X r X X XXXXX",
            "OO      XbpoX      OO",
            "XXXXX X XXXXX X XXXXX",
            "OOOOX X       X XOOOX",
            "XXXXX X XXXXX X XXXXX",
            "XX        X        XX",
            "XX XX XXX X XXX XX XX",
            "XX  X     P     X  XX",
            "XXX X X XXXXX X X XXX",
            "XX    X   X   X    XX",
            "XX XXXXXX X XXXXXX XX",
            "XXA               AXX",
            "XXXXXXXXXXXXXXXXXXXXX"
    };
    public String[] getTileMap() {
        return tileMap;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

