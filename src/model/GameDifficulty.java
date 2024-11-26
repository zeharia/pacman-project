package model;

public enum GameDifficulty {
    EASY(2),
    MEDIUM(3),
    HARD(4);

    private final int ghostSpeed;
    GameDifficulty(int ghostSpeed) {
        this.ghostSpeed = ghostSpeed;
    }
    public int getGhostSpeed() {
        return ghostSpeed;
    }
    public static GameDifficulty fromString(String difficulty) {
        return valueOf(difficulty.toUpperCase());
    }
}