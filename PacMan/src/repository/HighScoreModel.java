package repository;

import model.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HighScoreModel {
    private static final String FILE_NAME = "highscores.ser";

    public void saveScore(Score score) {
        List<Score> scores = loadAllScores();
        scores.add(score);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Score> loadAllScores() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Score> scoreList;
            scoreList = (List<Score>) ois.readObject();
            return scoreList.stream().sorted(Comparator.comparing(Score::getPoints).reversed()).collect(Collectors.toList());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
