package model;

import java.awt.*;

public class PacMan {
    public int row;
    public int col;
    public int dirRow = 0, dirCol = 1;
    public int requestedDirRow = 0, requestedDirCol = 1;
    public Image image;
    public int startRow, startCol;

    public PacMan(int row, int col, Image image) {
        this.row = row;
        this.col = col;
        this.image = image;
        this.startRow = row;
        this.startCol = col;
    }

    public void setDirection(int dirRow, int dirCol) {
        this.requestedDirRow = dirRow;
        this.requestedDirCol = dirCol;
    }

    public void moveTo(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}
