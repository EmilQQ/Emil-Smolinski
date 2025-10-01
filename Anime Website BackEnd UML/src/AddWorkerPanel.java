import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AddWorkerPanel {
    private JFrame frame;
    private ManageWorkersPanel manageWorkersPanel;
    private JPanel mainPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField bestTraitField;
    private JTextField animatorExperienceField;
    private JTextField directorExperienceField;
    private JTextField editorExperienceField;
    private JButton addWorkerButton;
    private JButton cancelButton;
    private GridBagConstraints gbc;
    private JPanel specializationsPanel;
    private JPanel buttonPanel;

    public AddWorkerPanel(ManageWorkersPanel manageWorkersPanel) {
        this.manageWorkersPanel = manageWorkersPanel;
        frame = new JFrame();
        mainPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        specializationsPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        animatorExperienceField = new JTextField();
        directorExperienceField = new JTextField();
        editorExperienceField = new JTextField();
        addWorkerButton = new JButton("Dodaj pracownika");
        cancelButton = new JButton("Anuluj");
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        bestTraitField = new JTextField();

        frame.setTitle("Dodaj nowego pracownika – Anime World");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setMinimumSize(new Dimension(500, 350));
        frame.setLocationRelativeTo(null);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        addLabelAndField(mainPanel, gbc, "Imię:", firstNameField, y++);
        addLabelAndField(mainPanel, gbc, "Nazwisko:", lastNameField, y++);
        addLabelAndField(mainPanel, gbc, "Najbardziej ceniona cecha:", bestTraitField, y++);

        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Specjalizacje (lata doświadczenia):"), gbc);

        specializationsPanel.add(new JLabel("Animator:"));
        specializationsPanel.add(animatorExperienceField);

        specializationsPanel.add(new JLabel("Director:"));
        specializationsPanel.add(directorExperienceField);

        specializationsPanel.add(new JLabel("Editor:"));
        specializationsPanel.add(editorExperienceField);

        gbc.gridx = 1;
        gbc.gridy = y - 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(specializationsPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        cancelButton.addActionListener(e -> {
            frame.dispose();
        });

        addWorkerButton.addActionListener(e -> {
            try {
                String name = firstNameField.getText().trim();
                String surname = lastNameField.getText().trim();
                String bestTrait = bestTraitField.getText().trim();
                int animatorExp = Integer.parseInt(animatorExperienceField.getText().trim().isEmpty() ? "0" : animatorExperienceField.getText().trim());
                int directorExp = Integer.parseInt(directorExperienceField.getText().trim().isEmpty() ? "0" : directorExperienceField.getText().trim());
                int editorExp = Integer.parseInt(editorExperienceField.getText().trim().isEmpty() ? "0" : editorExperienceField.getText().trim());
                Map<WorkerSpeciality, Integer> specialities = new HashMap<>();
                if (animatorExp > 0){
                    specialities.put(WorkerSpeciality.Animator, animatorExp);
                }
                if (directorExp > 0){
                    specialities.put(WorkerSpeciality.Director, directorExp);
                }
                if (editorExp > 0){
                    specialities.put(WorkerSpeciality.Editor, editorExp);
                }

                Worker worker = new Worker(name, surname, bestTrait, specialities);

                ObjectPlus.saveExtent();

                JOptionPane.showMessageDialog(frame, "Udało się dodać pracownika " + name + " " + surname);

                manageWorkersPanel.refreshWorkerList();

                frame.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Błąd: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(addWorkerButton);

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
}
