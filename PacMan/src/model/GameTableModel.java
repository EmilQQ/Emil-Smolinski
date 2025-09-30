package model;

import javax.swing.table.AbstractTableModel;

public class GameTableModel extends AbstractTableModel {
    private final Tile[][] board;

    public GameTableModel(int rows, int cols) {
        board = new Tile[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = new Tile(TileType.EMPTY, null);
            }
        }
    }

    @Override
    public int getRowCount() {
        return board.length;
    }

    @Override
    public int getColumnCount() {
        return board[0].length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return board[row][col];
    }

    public void setTileAt(int row, int col, Tile tile) {
        synchronized (this) {
            board[row][col] = tile;
            fireTableCellUpdated(row, col);
        }
    }

    public Tile getTileAt(int row, int col) {
        synchronized (this) {
            return board[row][col];
        }
    }

    public Tile[][] getBoard() {
        return board;
    }
}
