import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ManageAnimePanel {
    private JFrame frame;
    private JPanel mainPanel;
    private JList listAnimes;
    private JButton addWorkersButton;
    private JButton addNewAnimeButton;
    private JLabel header;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private DefaultListModel<Anime> animeListModel;

    public ManageAnimePanel() {
        frame = new JFrame();
        mainPanel = new JPanel(new BorderLayout(10, 10));
        header = new JLabel("Twoja lista anime:");
        listAnimes = new JList<>();
        scrollPane = new JScrollPane(listAnimes);
        buttonPanel = new JPanel();
        addNewAnimeButton = new JButton("Dodaj nowe anime");
        addWorkersButton = new JButton("Zarządzaj pracownikami");
        animeListModel = new DefaultListModel<>();

        frame.setTitle("Manage Anime – Anime World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setMinimumSize(new Dimension(600, 450));
        frame.setLocationRelativeTo(null);

        header.setFont(new Font("SansSerif", Font.BOLD, 16));

        listAnimes.setFont(new Font("Monospaced", Font.PLAIN, 14));
        listAnimes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAnimes.setVisibleRowCount(10);
        listAnimes.setFixedCellHeight(24);
        listAnimes.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addNewAnimeButton.setToolTipText("Stwórz nowe anime w systemie");
        addNewAnimeButton.addActionListener(e -> {
            new AddAnimePanel(this);
        });

        addWorkersButton.setToolTipText("Dodaj pracowników dla anime");
        addWorkersButton.addActionListener(e -> {
            int selectedIndex = listAnimes.getSelectedIndex();
            if (selectedIndex >= 0) {
                Anime selectedAnime = animeListModel.getElementAt(selectedIndex);
                new ManageWorkersPanel(selectedAnime);
            } else {
                JOptionPane.showMessageDialog(frame, "Wybierz anime z listy");
            }
        });

        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addNewAnimeButton);
        buttonPanel.add(addWorkersButton);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        listAnimes.setModel(animeListModel);
        listAnimes.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Anime anime) {
                    setText(anime.getTitle());
                }
                return this;
            }
        });

        refreshAnimeList();

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public void refreshAnimeList() {
        try {
            ObjectPlus.loadExtent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        animeListModel.clear();
        for (Anime anime : ObjectPlus.getExtentFromClass(Anime.class)) {
            animeListModel.addElement(anime);
        }
    }
}
