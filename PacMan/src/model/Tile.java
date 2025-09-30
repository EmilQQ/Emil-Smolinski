package model;

import java.awt.*;

public class Tile {
    private TileType type;
    private Image image;

    public Tile(TileType type, Image image) {
        this.type = type;
        this.image = image;
    }

    public TileType getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }

    public void setType(TileType type, Image image) {
        this.type = type;
        this.image = image;
    }
}
