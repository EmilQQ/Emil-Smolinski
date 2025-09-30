package model;

import java.awt.*;

public class Ghost {
    public int row;
    public int col;
    public TileType type;
    public Image image;
    public int startRow;
    public int startCol;
    public Image startImage;

    public Ghost(int row, int col, TileType type, Image image) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.image = image;
        this.startRow = row;
        this.startCol = col;
        this.startImage = image;
    }

    public void moveTo(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void resetImage() {
        this.image = startImage;
    }
}
