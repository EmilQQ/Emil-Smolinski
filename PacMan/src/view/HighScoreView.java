package view;

import controller.HighScoreController;
import model.Score;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoreView {
    private final JFrame frame;
    private final JList<String> scoreList;
    private final JScrollPane scrollPane;
    private final DefaultListModel<String> listModel;

    public HighScoreView(HighScoreController controller) {
        frame = new JFrame("PacMan - High Score");
        listModel = new DefaultListModel<>();
        scoreList = new JList<>(listModel);
        scrollPane = new JScrollPane(scoreList);

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(Color.BLACK);

        scoreList.setFont(new Font("Arial", Font.BOLD, 16));
        scoreList.setBackground(Color.BLACK);
        scoreList.setForeground(Color.WHITE);
        scoreList.setOpaque(true);

        List<Score> scores = controller.getAllScores();

        if (scores.isEmpty()) {
            listModel.addElement("No scores");
        } else {
            int i = 1;
            for (Score score : scores) {
                listModel.addElement(i + ". " + score.toString());
                i++;
            }
        }

        frame.add(scrollPane, BorderLayout.CENTER);

    }
}
