package view;

import controller.GameController;
import controller.HighScoreController;
import repository.HighScoreModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuView {
    private JFrame menuFrame;
    private JPanel menuPanel;
    private JButton startGameButton;
    private JButton exitButton;
    private JButton showRankingButton;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private GridBagConstraints constraints;
    private GameController gameController;

    public MenuView() {
        menuFrame = new JFrame("PacMan - Menu");
        menuPanel = new JPanel(new GridBagLayout());
        startGameButton = new JButton("New Game");
        exitButton = new JButton("Exit");
        showRankingButton = new JButton("High Scores");
        titleLabel = new JLabel("PacMan");
        mainPanel = new JPanel(new BorderLayout());
        constraints = new GridBagConstraints();

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(800, 600);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(true);

        menuPanel.setBackground(Color.BLACK);
        menuPanel.setOpaque(true);

        startGameButton.addActionListener(e -> {
            JTextField rowField = new JTextField("");
            JTextField colField = new JTextField("");

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Number of rows (10–100):"));
            panel.add(rowField);
            panel.add(new JLabel("Number of cols (10–100):"));
            panel.add(colField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Board Size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int rows = Integer.parseInt(rowField.getText());
                    int cols = Integer.parseInt(colField.getText());

                    if (rows >= 10 && rows <= 100 && cols >= 10 && cols <= 100) {
                        GameView view = new GameView(rows, cols, 32);
                        gameController = new GameController(view);
                    } else {
                        JOptionPane.showMessageDialog(null, "Size needs to be 10–100");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Write suited numbers only");
                }
            }
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        showRankingButton.addActionListener(e -> {
            HighScoreModel model = new HighScoreModel();
            HighScoreController highScoreController = new HighScoreController(model);
            new HighScoreView(highScoreController);
        });

        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;

        menuPanel.add(startGameButton, constraints);
        constraints.gridy++;
        menuPanel.add(showRankingButton, constraints);
        constraints.gridy++;
        menuPanel.add(exitButton, constraints);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        menuFrame.add(mainPanel);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                if (gameController != null) {
                    gameController.pauseGame();
                }
                menuFrame.setVisible(true);
                return true;
            }
            return false;
        });
    }
}
