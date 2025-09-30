package view;

import controller.GameCellRenderer;
import model.GameTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameView {
    private final JFrame frame;
    private final JTable table;
    private final GameTableModel model;
    private final JScrollPane scrollPane;
    private final JLabel scoreLabel;
    private final JLabel livesLabel;
    private final JLabel timeLabel;
    private final JPanel topPanel;
    private final int rows;
    private final int cols;
    private final JLabel highScoreLabel;

    public GameView(int rows, int cols, int tileSize) {
        this.rows = rows;
        this.cols = cols;
        frame = new JFrame("PacMan");
        model = new GameTableModel(rows, cols);
        table = new JTable(model);
        scoreLabel = new JLabel();
        livesLabel = new JLabel();
        timeLabel = new JLabel();
        topPanel = new JPanel(new GridLayout(1, 3));
        scrollPane = new JScrollPane(table);
        highScoreLabel = new JLabel("High Score = 0");

        table.setRowHeight(tileSize);
        table.setDefaultRenderer(Object.class, new GameCellRenderer());
        table.setTableHeader(null);
        table.setCellSelectionEnabled(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(livesLabel, BorderLayout.SOUTH);

        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setOpaque(true);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setText("Score: 0");

        livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setBackground(Color.BLACK);
        livesLabel.setOpaque(true);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        livesLabel.setText("Lives: 3");

        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setOpaque(true);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setText("Time: 0");

        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreLabel.setForeground(Color.YELLOW);
        highScoreLabel.setBackground(Color.BLACK);
        highScoreLabel.setOpaque(true);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        topPanel.add(scoreLabel);
        topPanel.add(highScoreLabel);
        topPanel.add(timeLabel);
        topPanel.setBackground(Color.BLACK);
        topPanel.setOpaque(true);

        int frameWidth = tileSize * cols + frame.getInsets().left + frame.getInsets().right;
        int frameHeight = tileSize * rows + frame.getInsets().top + frame.getInsets().bottom + topPanel.getHeight() + livesLabel.getHeight() - 8;
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newHeight = frame.getHeight() - topPanel.getHeight() - livesLabel.getHeight() - frame.getInsets().top - frame.getInsets().bottom;
                int newWidth = frame.getWidth() - frame.getInsets().left - frame.getInsets().right;

                int newRowHeight = Math.max(newHeight / rows, 10);
                int newColumnWidth = Math.max(newWidth / rows, 10);

                table.setRowHeight(newRowHeight);
                for (int i = 0; i < cols; i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(newColumnWidth);
                    table.getColumnModel().getColumn(i).setMinWidth(10);
                }
            }
        }); //SZCZERZE NIE WIEM Z CZEGO JEST TEN BIALY PASEK
    }

    public GameTableModel getModel() {
        return model;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTable getTable() {
        return table;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public JLabel getLivesLabel() {
        return livesLabel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public JLabel getHighScoreLabel() {
        return highScoreLabel;
    }
}
