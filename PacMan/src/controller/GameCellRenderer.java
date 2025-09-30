package controller;

import model.Tile;
import model.TileType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class GameCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int col) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        label.setText("");

        int cellWidth = table.getColumnModel().getColumn(col).getWidth();
        int cellHeight = table.getRowHeight(row);

        if (value instanceof Tile tile && tile.getImage() != null) {
            if (tile.getType() == TileType.WALL){
                label.setIcon(new ImageIcon(tile.getImage().getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH)));
            } else {
                label.setIcon(new ImageIcon(tile.getImage()));
            }
        } else {
            label.setIcon(null);
        }

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);

        return label;
    }
}
