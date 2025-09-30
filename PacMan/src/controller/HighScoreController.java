package controller;

import model.Score;
import repository.HighScoreModel;

import java.util.List;

public class HighScoreController {
    private final HighScoreModel model;

    public HighScoreController(HighScoreModel model) {
        this.model = model;
    }

    public void addNewScore(Score score) {
        model.saveScore(score);
    }

    public List<Score> getAllScores() {
        return model.loadAllScores();
    }
}
