import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.awt.*;
import java.util.Set;

public class ManageWorkersPanel {
    private JFrame frame;
    private JPanel mainPanel;
    private Anime anime;
    private JLabel selectedAnimeLabel;
    private JList<Worker> assignedWorkersList;
    private JList<Worker> freeWorkersList;
    private JButton addButton;
    private JButton removeButton;
    private JButton createWorkerButton;
    private JButton backToAnimeListButton;
    private JButton confirmAddWorkersButton;
    private JPanel centerPanel;
    private DefaultListModel<Worker> assignedModel;
    private DefaultListModel<Worker> freeModel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel buttonPanel;
    private JPanel bottomPanel;
    private JPanel leftBottomPanel;
    private JPanel rightBottomPanel;
    private JPanel centerBottomPanel;

    public ManageWorkersPanel(Anime anime) {
        this.anime = anime;
        frame = new JFrame();
        mainPanel = new JPanel(new BorderLayout(10, 10));
        selectedAnimeLabel = new JLabel("Twoje wybrane anime: " + anime.getTitle());
        centerPanel = new JPanel(new BorderLayout(10, 0));
        assignedModel = new DefaultListModel<>();
        freeModel = new DefaultListModel<>();
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        addButton = new JButton("+");
        removeButton = new JButton("-");
        bottomPanel = new JPanel(new BorderLayout());
        leftBottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightBottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        centerBottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backToAnimeListButton = new JButton("Wróć do listy anime");
        createWorkerButton = new JButton("Stwórz pracownika");
        confirmAddWorkersButton = new JButton("Zapisz");
        assignedWorkersList = new JList<>();
        freeWorkersList = new JList<>();

        frame.setTitle("Manage Workers – Anime World");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setMinimumSize(new Dimension(700, 450));
        frame.setLocationRelativeTo(null);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        selectedAnimeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        assignedWorkersList.setBorder(BorderFactory.createTitledBorder("Pracownicy przypisani"));
        assignedWorkersList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        assignedWorkersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        freeWorkersList.setBorder(BorderFactory.createTitledBorder("Wolni pracownicy"));
        freeWorkersList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        freeWorkersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        leftPanel.add(new JScrollPane(assignedWorkersList), BorderLayout.CENTER);

        rightPanel.add(new JScrollPane(freeWorkersList), BorderLayout.CENTER);

        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(50, 40));
        addButton.setToolTipText("Dodaj pracownika do anime");
        addButton.addActionListener(e -> {
            int selectedIndex = freeWorkersList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Worker selectedWorker = freeModel.getElementAt(selectedIndex);
                try {
                    anime.addWorker(selectedWorker);
                    ObjectPlus.saveExtent();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                refreshWorkerList();
            } else {
                JOptionPane.showMessageDialog(frame, "Wybierz pracownika z listy wolnych osób");
            }
        });

        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.setMaximumSize(new Dimension(50, 40));
        removeButton.setToolTipText("Usuń pracownika z anime");
        removeButton.addActionListener(e -> {
            int selectedIndex = assignedWorkersList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Worker selectedWorker = assignedModel.getElementAt(selectedIndex);
                try {
                    anime.removeWorker(selectedWorker);
                    ObjectPlus.saveExtent();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                refreshWorkerList();
            } else {
                JOptionPane.showMessageDialog(frame, "Wybierz pracownika z listy zatrudnionych osób");
            }
        });

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createVerticalGlue());

        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(rightPanel, BorderLayout.EAST);

        leftPanel.setPreferredSize(new Dimension(280, 300));
        rightPanel.setPreferredSize(new Dimension(280, 300));
        buttonPanel.setPreferredSize(new Dimension(60, 300));

        backToAnimeListButton.addActionListener(e -> {
            frame.dispose();
        });

        createWorkerButton.addActionListener(e -> {
            new AddWorkerPanel(this);
        });

        confirmAddWorkersButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Zmiany zostały zapisane");
            frame.dispose();
        });

        leftBottomPanel.add(backToAnimeListButton);
        centerBottomPanel.add(createWorkerButton);
        rightBottomPanel.add(confirmAddWorkersButton);

        bottomPanel.add(leftBottomPanel, BorderLayout.WEST);
        bottomPanel.add(centerBottomPanel, BorderLayout.CENTER);
        bottomPanel.add(rightBottomPanel, BorderLayout.EAST);

        mainPanel.add(selectedAnimeLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Worker worker) {
                    setText(worker.getName() + " " + worker.getSurname());
                }
                return this;
            }
        };

        freeWorkersList.setModel(freeModel);
        freeWorkersList.setCellRenderer(defaultListCellRenderer);

        assignedWorkersList.setModel(assignedModel);
        assignedWorkersList.setCellRenderer(defaultListCellRenderer);

        refreshWorkerList();

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public void refreshWorkerList() {
        assignedModel.clear();
        freeModel.clear();

        List<Contract> listOfContracts = anime.getWorkersList();
        Set<Worker> assignedWorkers = new HashSet<>();
        for (Contract contract : listOfContracts) {
            assignedWorkers.add(contract.getWorker());
            assignedModel.addElement(contract.getWorker());
        }

        List<Worker> allWorkers = ObjectPlus.getExtentFromClass(Worker.class);
        for (Worker worker : allWorkers) {
            if (!assignedWorkers.contains(worker)) {
                freeModel.addElement(worker);
            }
        }
    }
}
