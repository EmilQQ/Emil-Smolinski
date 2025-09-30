package controller;

import model.*;
import repository.HighScoreModel;
import view.GameView;
import view.HighScoreView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class GameController {
    private final GameView view;
    private final int rows;
    private final int cols;
    private Image wallImg;
    private Image foodImg;
    private Image cherryImg;
    private Image pacmanUpImg;
    private Image pacmanDownImg;
    private Image pacmanLeftImg;
    private Image pacmanRightImg;
    private Image ghostBlueImg, ghostRedImg, ghostPinkImg, ghostOrangeImg;
    private Image powerPelletImg;
    private Image ghostScaredImg;
    private PacMan pacman;
    private final List<Ghost> ghosts = new ArrayList<>();
    private final Map<Point, Stack<TileType>> ghostMemory = new HashMap<>();
    private final Random random = new Random();
    private int score = 0;
    private int cherryRow = -1;
    private int cherryCol = -1;
    private boolean eaterPacMan = false;
    private int ghostsEaten = 0;
    private int lives = 3;
    private boolean hasUserMoved = false;
    private int time = 0;
    private static char[][] tileMapOfGame;
    private int round = 1;
    private int ghostSpeed = 500;
    private int pacmanSpeed = 200;
    private int highScore = SessionHighScore.getHighScore();
    private Image lifePowerImg, freezePowerImg, doublePointsPowerImg, invinciblePowerImg, speedPowerImg;
    private boolean ghostsStop = false;
    private boolean isDoublePointsActive = false;
    private boolean isInvincibleActive = false;

    public GameController(GameView view) {
        this.view = view;
        this.rows = view.getRows();
        this.cols = view.getCols();
        loadImages();
        generateMap(rows, cols);
        initMap();
        setupControls();
        movePacman();
        moveGhosts();
        cherryAppear();
        startGameTimer();
    }

    private void loadImages() {
        wallImg = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        foodImg = new ImageIcon(getClass().getResource("/food.png")).getImage();
        cherryImg = new ImageIcon(getClass().getResource("/cherry.png")).getImage();
        pacmanRightImg = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
        pacmanLeftImg = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();
        pacmanUpImg = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
        pacmanDownImg = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
        ghostBlueImg = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
        ghostRedImg = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();
        ghostPinkImg = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
        ghostOrangeImg = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
        ghostScaredImg = new ImageIcon(getClass().getResource("/scaredGhost.png")).getImage();
        powerPelletImg = new ImageIcon(getClass().getResource("/powerPellet.png")).getImage();
        lifePowerImg = new ImageIcon(getClass().getResource("/lifePOWER.png")).getImage();
        freezePowerImg = new ImageIcon(getClass().getResource("/freezePOWER.png")).getImage();
        speedPowerImg = new ImageIcon(getClass().getResource("/speedPOWER.png")).getImage();
        doublePointsPowerImg = new ImageIcon(getClass().getResource("/doublePointsPOWER.png")).getImage();
        invinciblePowerImg = new ImageIcon(getClass().getResource("/invincibilityPOWER.png")).getImage();
    }

    private void initMap() {
        GameTableModel model = view.getModel();

        for (int row = 0; row < tileMapOfGame.length; row++) {
            for (int col = 0; col < tileMapOfGame[row].length; col++) {
                char c = tileMapOfGame[row][col];
                switch (c) {
                    case 'X' -> model.setTileAt(row, col, new Tile(TileType.WALL, wallImg));
                    case ' ' -> model.setTileAt(row, col, new Tile(TileType.FOOD, foodImg));
                    case 'P' -> {
                        pacman = new PacMan(row, col, pacmanRightImg);
                        model.setTileAt(row, col, new Tile(TileType.PACMAN, pacman.image));
                    }
                    case 'b' -> {
                        ghosts.add(new Ghost(row, col, TileType.GHOST_BLUE, ghostBlueImg));
                        view.getModel().setTileAt(row, col, new Tile(TileType.GHOST_BLUE, ghostBlueImg));
                    }
                    case 'r' -> {
                        ghosts.add(new Ghost(row, col, TileType.GHOST_RED, ghostRedImg));
                        view.getModel().setTileAt(row, col, new Tile(TileType.GHOST_RED, ghostRedImg));
                    }
                    case 'p' -> {
                        ghosts.add(new Ghost(row, col, TileType.GHOST_PINK, ghostPinkImg));
                        view.getModel().setTileAt(row, col, new Tile(TileType.GHOST_PINK, ghostPinkImg));
                    }
                    case 'o' -> {
                        ghosts.add(new Ghost(row, col, TileType.GHOST_ORANGE, ghostOrangeImg));
                        view.getModel().setTileAt(row, col, new Tile(TileType.GHOST_ORANGE, ghostOrangeImg));
                    }
                    case 'S' -> model.setTileAt(row, col, new Tile(TileType.POWERUP, powerPelletImg));
                    default -> model.setTileAt(row, col, new Tile(TileType.EMPTY, null));
                }
            }
        }
    }

    private void setupControls() {
        view.getFrame().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                hasUserMoved = true;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> pacman.setDirection(-1, 0);
                    case KeyEvent.VK_DOWN -> pacman.setDirection(1, 0);
                    case KeyEvent.VK_LEFT -> pacman.setDirection(0, -1);
                    case KeyEvent.VK_RIGHT -> pacman.setDirection(0, 1);
                }
            }
        });
    }

    private void movePacman() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(pacmanSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (lives <= 0) {
                    String playerName = JOptionPane.showInputDialog(view.getFrame(), "Write your name:", "Save score", JOptionPane.PLAIN_MESSAGE);
                    if (playerName != null) {
                        new HighScoreController(new HighScoreModel()).addNewScore(new Score(playerName, score));
                    }
                    SessionHighScore.updateScore(score);
                    JOptionPane.showMessageDialog(view.getFrame(), "GG", "Game over", JOptionPane.INFORMATION_MESSAGE);
                    view.getFrame().setVisible(false);
                    break;
                }
                if (checkIfFoodHasBeenEaten()){
                    round++;
                    if (round == 256){
                        String playerName = JOptionPane.showInputDialog(view.getFrame(), "Write your name:", "Save score", JOptionPane.PLAIN_MESSAGE);
                        if (playerName != null) {
                            new HighScoreController(new HighScoreModel()).addNewScore(new Score(playerName, score));
                        }
                        SessionHighScore.updateScore(score);
                        JOptionPane.showMessageDialog(view.getFrame(), "GG", "Game over", JOptionPane.INFORMATION_MESSAGE);
                        view.getFrame().setVisible(false);
                        break;
                    }
                    ghosts.clear();
                    ghostSpeed = ghostSpeed - round;
                    generateMap(rows, cols);
                    initMap();
                    hasUserMoved = false;
                }

                if (!hasUserMoved) continue;

                GameTableModel model = view.getModel();

                int tryRow = pacman.row + pacman.requestedDirRow;
                int tryCol = pacman.col + pacman.requestedDirCol;
                if (isValidMove(model, tryRow, tryCol)) {
                    pacman.dirRow = pacman.requestedDirRow;
                    pacman.dirCol = pacman.requestedDirCol;
                }

                int newRow = pacman.row + pacman.dirRow;
                int newCol = pacman.col + pacman.dirCol;

                if (isValidMove(model, newRow, newCol)) {
                    model.setTileAt(pacman.row, pacman.col, new Tile(TileType.EMPTY, null));
                    pacman.moveTo(newRow, newCol);
                    pacman.image = switch (pacman.dirRow + "," + pacman.dirCol) {
                        case "-1,0" -> pacmanUpImg;
                        case "1,0" -> pacmanDownImg;
                        case "0,-1" -> pacmanLeftImg;
                        default -> pacmanRightImg;
                    };

                    for (Ghost ghost : ghosts){
                        colision(pacman, ghost.row, ghost.col, ghost);
                    }

                    Tile currentTile = model.getTileAt(pacman.row, pacman.col);
                    if (currentTile.getType() == TileType.FOOD) {
                        score += isDoublePointsActive? 20 : 10;
                    } else if (currentTile.getType() == TileType.CHERRY) {
                        score += isDoublePointsActive? 400 : 200;
                        cherryRow = -1;
                        cherryCol = -1;
                    } else if (currentTile.getType() == TileType.SPEEDPOWER) {
                        score += isDoublePointsActive? 50 : 25;
                        pacmanSpeed = pacmanSpeed - 50;
                        new Thread(() -> {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pacmanSpeed += 50;
                        }).start();
                    } else if (currentTile.getType() == TileType.LIFEPOWER) {
                        score += isDoublePointsActive? 50 : 25;
                        lives++;
                        view.getLivesLabel().setText("Lives: " + lives);
                    } else if (currentTile.getType() == TileType.FREEZEPOWER) {
                        score += isDoublePointsActive? 50 : 25;
                        ghostsStop = true;
                        new Thread(() -> {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ghostsStop = false;
                        }).start();
                    } else if (currentTile.getType() == TileType.DOUBLEPOINTSPOWER) {
                        score += 25;
                        isDoublePointsActive = true;
                        new Thread(() -> {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            isDoublePointsActive = false;
                        }).start();
                    } else if (currentTile.getType() == TileType.INVINCIBILITYPOWER) {
                        score += isDoublePointsActive? 50 : 25;
                        isInvincibleActive = true;
                        new Thread(() -> {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            isInvincibleActive = false;
                        }).start();
                    } else if (currentTile.getType() == TileType.POWERUP) {
                        score += isDoublePointsActive? 100 : 50;
                        eaterPacMan = true;
                        ghostsEaten = 0;

                        for (Ghost ghost : ghosts){
                            ghost.setImage(ghostScaredImg);
                        }

                        new Thread(() -> {
                            try{
                                Thread.sleep(6000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < 5; i++) {
                                boolean visible = i % 2 == 0;
                                for (Ghost ghost : ghosts) {
                                    ghost.setImage(visible ? ghost.startImage : ghostScaredImg);
                                    view.getModel().setTileAt(ghost.row, ghost.col, new Tile(ghost.type, ghost.image));   //jest po to aby aktualizowac widok
                                }
                                try {
                                    Thread.sleep(400);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            for (Ghost ghost : ghosts){
                                ghost.resetImage();
                            }
                            eaterPacMan = false;
                        }).start();
                    }
                    if (score > highScore) {
                        highScore = score;
                    }
                    view.getScoreLabel().setText("Score: " + score);
                    view.getHighScoreLabel().setText("High score: " + highScore);

                    model.setTileAt(pacman.row, pacman.col, new Tile(TileType.PACMAN, pacman.image));
                }
            }
        }).start();
    }

    private void moveGhosts() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(ghostSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!hasUserMoved) continue;
                if (ghostsStop) continue;
                GameTableModel model = view.getModel();

                for (Ghost ghost : ghosts) {
                    int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
                    int[] dir = directions[random.nextInt(4)];
                    int newRow = ghost.row + dir[0];
                    int newCol = ghost.col + dir[1];

                    if (isValidMove(model, newRow, newCol)) {
                        colision(pacman, newRow, newCol, ghost);

                        Point oldPos = new Point(ghost.row, ghost.col);
                        TileType restore = TileType.EMPTY;
                        if (ghostMemory.containsKey(oldPos) && !ghostMemory.get(oldPos).isEmpty()) {
                            restore = ghostMemory.get(oldPos).pop();
                            if (ghostMemory.get(oldPos).isEmpty()) ghostMemory.remove(oldPos);
                        }
                        Image restoreImg;
                        restoreImg = switch (restore) {
                            case FOOD -> foodImg;
                            case CHERRY -> cherryImg;
                            case POWERUP -> powerPelletImg;
                            case SPEEDPOWER -> speedPowerImg;
                            case FREEZEPOWER -> freezePowerImg;
                            case DOUBLEPOINTSPOWER -> doublePointsPowerImg;
                            case INVINCIBILITYPOWER -> invinciblePowerImg;
                            case LIFEPOWER -> lifePowerImg;
                            default -> {
                                int pick = random.nextInt(5);
                                 yield switch (pick) {
                                    case 0 -> speedPowerImg;
                                    case 1 -> freezePowerImg;
                                    case 2 -> doublePointsPowerImg;
                                    case 3 -> invinciblePowerImg;
                                    case 4 -> lifePowerImg;
                                    default -> speedPowerImg;
                                };
                            }
                        };
                        if (restore == TileType.EMPTY) {
                            TileType newTileType;
                            if (restoreImg == speedPowerImg) {
                                newTileType = TileType.SPEEDPOWER;
                            } else if (restoreImg == freezePowerImg) {
                                newTileType = TileType.FREEZEPOWER;
                            } else if (restoreImg == doublePointsPowerImg) {
                                newTileType = TileType.DOUBLEPOINTSPOWER;
                            } else if (restoreImg == invinciblePowerImg) {
                                newTileType = TileType.INVINCIBILITYPOWER;
                            } else if (restoreImg == lifePowerImg) {
                                newTileType = TileType.LIFEPOWER;
                            } else {
                                newTileType = null;
                            }
                            if (random.nextInt(4) == 0) {
                                model.setTileAt(oldPos.x, oldPos.y, new Tile(newTileType, restoreImg));
                            } else {
                                model.setTileAt(oldPos.x, oldPos.y, new Tile(restore, null));
                            }
                        } else {
                            model.setTileAt(oldPos.x, oldPos.y, new Tile(restore, restoreImg));
                        }
                        Point newPos = new Point(newRow, newCol);
                        ghostMemory.computeIfAbsent(newPos, k -> new Stack<>()).push(model.getTileAt(newRow, newCol).getType());

                        ghost.moveTo(newRow, newCol);
                        model.setTileAt(ghost.row, ghost.col, new Tile(ghost.type, ghost.image));
                    }
                }
            }
        }).start();
    }

    private void cherryAppear() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000 + random.nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!hasUserMoved) continue;
                if (cherryRow != -1 && cherryCol != -1) continue;

                int row, col;
                do {
                    row = random.nextInt(tileMapOfGame.length);
                    col = random.nextInt(tileMapOfGame[0].length);
                } while (!view.getModel().getTileAt(row, col).getType().equals(TileType.EMPTY));

                cherryRow = row;
                cherryCol = col;
                view.getModel().setTileAt(row, col, new Tile(TileType.CHERRY, cherryImg));

                new Thread(() -> {
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (cherryRow != -1 && cherryCol != -1 && view.getModel().getTileAt(cherryRow, cherryCol).getType() == TileType.CHERRY) {
                        view.getModel().setTileAt(cherryRow, cherryCol, new Tile(TileType.EMPTY, null));
                        cherryRow = -1;
                        cherryCol = -1;
                    }
                }).start();
            }
        }).start();
    }

    private void colision(PacMan pacman, int newRow, int newCol, Ghost ghost) {
        GameTableModel model = view.getModel();
        if (pacman.row == newRow && pacman.col == newCol) {
            if (eaterPacMan) {
                ghostsEaten++;
                score += (isDoublePointsActive? 400 : 200) * (int)Math.pow(2, ghostsEaten - 1);
                ghost.moveTo(ghost.startRow, ghost.startCol);
                model.setTileAt(ghost.row, ghost.col, new Tile(ghost.type, ghost.image));
            } else {
                if (!isInvincibleActive) {
                    lives--;
                    view.getLivesLabel().setText("Lives: " + lives);
                    resetPositions();
                }
            }
        }
    }

    private boolean isValidMove(GameTableModel model, int row, int col) {
        if (row < 0 || row >= model.getRowCount() || col < 0 || col >= model.getColumnCount()) {
            return false;
        }
        return model.getTileAt(row, col).getType() != TileType.WALL;
    }

    private void resetPositions() {
        GameTableModel model = view.getModel();
        model.setTileAt(pacman.row, pacman.col, new Tile(TileType.EMPTY, null));
        pacman.moveTo(pacman.startRow, pacman.startCol);
        model.setTileAt(pacman.row, pacman.col, new Tile(TileType.PACMAN, pacman.image));
        for (Ghost ghost : ghosts) {
            model.setTileAt(ghost.row, ghost.col, new Tile(TileType.EMPTY, null));
            ghost.moveTo(ghost.startRow, ghost.startCol);
            ghost.setImage(ghost.startImage);
            model.setTileAt(ghost.row, ghost.col, new Tile(ghost.type, ghost.image));
        }
        hasUserMoved = false;
    }

    private void startGameTimer() {
        new Thread(() -> {
            while (true) {
                if (!hasUserMoved) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time++;
                SwingUtilities.invokeLater(() -> view.getTimeLabel().setText("Time: " + time + "s"));
            }
        }).start();
    }

    public void pauseGame(){
        hasUserMoved = false;
    }

    private boolean checkIfFoodHasBeenEaten() {
        boolean dec = true;
        for (int row = 0; row < view.getModel().getBoard().length; row++) {
            for (int col = 0; col < view.getModel().getBoard()[0].length; col++) {
                if (view.getModel().getBoard()[row][col].getType() == TileType.FOOD) {
                    dec = false;
                }
            }
        }
        return dec;
    }

    private static void generateMap(int rows, int cols) {
        tileMapOfGame = new char[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                tileMapOfGame[row][col] = 'X';
            }
        }

        boolean[][] visited = new boolean[rows][cols];
        Random random = new Random();
        List<int[]> stack = new ArrayList<>();

        visited[1][1] = true;
        tileMapOfGame[1][1] = ' ';
        stack.add(new int[]{1, 1});

        while (!stack.isEmpty()) {
            int[] cell = stack.remove(stack.size() - 1);
            int r = cell[0];
            int c = cell[1];

            int[][] dirs = new int[][]{{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
            List<int[]> neighbors = new ArrayList<>();

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr > 0 && nc > 0 && nr < rows - 1 && nc < cols - 1 && !visited[nr][nc]) {
                    neighbors.add(new int[]{nr, nc});
                }
            }

            if (!neighbors.isEmpty()) {
                stack.add(cell);
                int[] next = neighbors.get(random.nextInt(neighbors.size()));
                int nr = next[0];
                int nc = next[1];
                int wallR = (r + nr) / 2;
                int wallC = (c + nc) / 2;

                tileMapOfGame[nr][nc] = ' ';
                tileMapOfGame[wallR][wallC] = ' ';
                visited[nr][nc] = true;
                stack.add(new int[]{nr, nc});
            }
        }

        int pacmanRow = rows - 3;
        int pacmanCol = cols / 2;
        tileMapOfGame[pacmanRow][pacmanCol] = 'P';

        int ghostRow = rows / 2;
        int ghostCol = cols / 2;
        tileMapOfGame[ghostRow][ghostCol] = 'b';
        tileMapOfGame[ghostRow - 1][ghostCol] = 'r';
        tileMapOfGame[ghostRow][ghostCol - 1] = 'p';
        tileMapOfGame[ghostRow][ghostCol + 1] = 'o';

        tileMapOfGame[1][1] = tileMapOfGame[1][cols - 2] = tileMapOfGame[rows - 2][1] = tileMapOfGame[rows - 2][cols - 2] = 'S';

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                if (tileMapOfGame[row][col] == ' ') {
                    int open = 0;
                    if (tileMapOfGame[row - 1][col] == ' ') open++;
                    if (tileMapOfGame[row + 1][col] == ' ') open++;
                    if (tileMapOfGame[row][col - 1] == ' ') open++;
                    if (tileMapOfGame[row][col + 1] == ' ') open++;
                    if (open < 2) {
                        if (tileMapOfGame[row - 1][col] == 'X') tileMapOfGame[row - 1][col] = ' ';
                        else if (tileMapOfGame[row + 1][col] == 'X') tileMapOfGame[row + 1][col] = ' ';
                        else if (tileMapOfGame[row][col - 1] == 'X') tileMapOfGame[row][col - 1] = ' ';
                        else if (tileMapOfGame[row][col + 1] == 'X') tileMapOfGame[row][col + 1] = ' ';
                    }
                }
            }
        }

        for (int row = 0; row < rows; row++) {
            tileMapOfGame[row][0] = tileMapOfGame[row][cols - 1] = 'X';
        }
        for (int col = 0; col < cols; col++) {
            tileMapOfGame[0][col] = tileMapOfGame[rows - 1][col] = 'X';
        }

        if (tileMapOfGame[2][1] == 'X' || tileMapOfGame[1][2] == 'X') {
            tileMapOfGame[2][1] = ' ';
            tileMapOfGame[1][2] = ' ';
            tileMapOfGame[1][4] = ' ';
        }
        if (tileMapOfGame[1][cols - 3] == 'X' || tileMapOfGame[2][cols - 2] == 'X') {
            tileMapOfGame[1][cols - 3] = ' ';
            tileMapOfGame[2][cols - 2] = ' ';
        }
        if (tileMapOfGame[rows - 3][1] == 'X' || tileMapOfGame[rows - 2][2] == 'X') {
            tileMapOfGame[rows - 2][2] = ' ';
            tileMapOfGame[rows - 3][1] = ' ';
        }
        if (tileMapOfGame[rows - 2][cols - 3] == 'X' || tileMapOfGame[rows - 3][cols - 2] == 'X') {
            tileMapOfGame[rows - 2][cols - 3] = ' ';
            tileMapOfGame[rows - 3][cols - 2] = ' ';
        }

    }

}

