import view.MenuView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuView menuView = new MenuView();
        });
    }
}
