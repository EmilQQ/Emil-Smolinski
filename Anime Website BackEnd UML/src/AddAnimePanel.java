import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddAnimePanel {
    private JFrame frame;
    private ManageAnimePanel manageAnimePanel;
    private JPanel mainPanel;
    private JButton addAnimeButton;
    private JButton cancelButton;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField idField;
    private JTextField originalTitleField;
    private JTextField originalAuthorField;
    private JTextField releaseDateField;
    private JTextField chaptersCountField;
    private JComboBox<String> statusComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JComboBox<String> ageRangeComboBox;
    private JComboBox<String> targetGroupComboBox;
    private GridBagConstraints gbc;
    private JPanel buttonPanel;

    public AddAnimePanel(ManageAnimePanel manageAnimePanel) {
        this.manageAnimePanel = manageAnimePanel;
        frame = new JFrame();
        mainPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        titleField = new JTextField();
        authorField = new JTextField();
        idField = new JTextField();
        originalTitleField = new JTextField();
        originalAuthorField = new JTextField();
        releaseDateField = new JTextField();
        chaptersCountField = new JTextField();
        String[] statuses = {"planned", "onGoing", "finished", "stopped"};
        statusComboBox = new JComboBox<>(statuses);
        startDateField = new JTextField();
        endDateField = new JTextField();
        String[] ageRanges = {"6+", "13+", "18+"};
        ageRangeComboBox = new JComboBox<>(ageRanges);
        String[] targetGroups = {"Dzieci", "Młodzież", "Dorośli"};
        targetGroupComboBox = new JComboBox<>(targetGroups);
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addAnimeButton = new JButton("Dodaj anime");
        cancelButton = new JButton("Anuluj");

        frame.setTitle("Dodaj nowe anime – Anime World");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 550);
        frame.setMinimumSize(new Dimension(600, 550));
        frame.setLocationRelativeTo(null);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        addLabelAndField(mainPanel, gbc, "Tytuł:", titleField, y++);
        addLabelAndField(mainPanel, gbc, "Autor:", authorField, y++);
        addLabelAndField(mainPanel, gbc, "ID:", idField, y++);
        addLabelAndField(mainPanel, gbc, "Oryginalny tytuł:", originalTitleField, y++);
        addLabelAndField(mainPanel, gbc, "Oryginalny autor:", originalAuthorField, y++);
        addLabelAndField(mainPanel, gbc, "Data wydania (yyyy-MM-dd):", releaseDateField, y++);
        addLabelAndField(mainPanel, gbc, "Liczba chapterów:", chaptersCountField, y++);
        addLabelAndField(mainPanel, gbc, "Status:", statusComboBox, y++);
        addLabelAndField(mainPanel, gbc, "Data rozpoczęcia emisji (yyyy-MM-dd):", startDateField, y++);
        addLabelAndField(mainPanel, gbc, "Data zakończenia emisji (yyyy-MM-dd):", endDateField, y++);
        addLabelAndField(mainPanel, gbc, "Przedział wiekowy:", ageRangeComboBox, y++);
        addLabelAndField(mainPanel, gbc, "Grupa docelowa:", targetGroupComboBox, y++);

        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;

        idField.setEditable(false);
        setNextAnimeId();

        addAnimeButton.addActionListener(e -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                int id = Integer.parseInt(idField.getText().trim());
                String originalTitle = originalTitleField.getText().trim();
                String originalAuthor = originalAuthorField.getText().trim();
                LocalDate releaseDate = LocalDate.parse(releaseDateField.getText().trim(), formatter);
                int chaptersCount = Integer.parseInt(chaptersCountField.getText().trim());
                String status = (String) statusComboBox.getSelectedItem();
                LocalDate startDate = LocalDate.parse(startDateField.getText().trim(), formatter);
                LocalDate endDate = LocalDate.parse(endDateField.getText().trim(), formatter);
                String ageRange = (String) ageRangeComboBox.getSelectedItem();
                String targetGroup = (String) targetGroupComboBox.getSelectedItem();

                Anime anime = new Anime(title, author, id, new Light_Novel(originalTitle, originalAuthor, releaseDate, chaptersCount), new EmissionInformation(status, startDate, endDate, ageRange), targetGroup);

                ObjectPlus.saveExtent();

                JOptionPane.showMessageDialog(frame, "Udało się dodać anime " + title);

                manageAnimePanel.refreshAnimeList();

                frame.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Błąd: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> {
            frame.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(addAnimeButton);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(buttonPanel, gbc);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(field, gbc);
    }

    private void setNextAnimeId() {
        int nextId = 1;
        for (Anime anime : ObjectPlus.getExtentFromClass(Anime.class)) {
            int currentId = anime.getId();
            if (currentId >= nextId) {
                nextId = currentId + 1;
            }
        }
        idField.setText(String.valueOf(nextId));
    }
}
