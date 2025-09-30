package model;

public class SessionHighScore {
    private static int highScore = 0;

    public static int getHighScore() {
        return highScore;
    }

    public static void updateScore(int score) {
        if (score > highScore) {
            highScore = score;
        }
    }
}
