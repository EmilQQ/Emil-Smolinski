package model;

import java.io.Serializable;

public class Score implements Serializable {
    private final String playerName;
    private final int points;

    public Score(String playerName, int points) {
        this.playerName = playerName;
        this.points = points;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return playerName + " - " + points;
    }
}
