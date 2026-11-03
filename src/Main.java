
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends JFrame {

    private JTextField nameField;
    private JComboBox<String> roleBox;
    private JTextArea messageArea;
    private JTextArea activityLog;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Main() {
        setTitle("POS System - GitHub Co-Editing Test");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));

        // HEADER
        JLabel title = new JLabel("GitHub Co-Editing Test");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitle = new JLabel(
                "Test your team's collaboration environment"
        );

        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        header.setOpaque(false);
        header.add(title);
        header.add(subtitle);

        mainPanel.add(header, BorderLayout.NORTH);

        // INPUT FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        nameField = new JTextField(20);

        roleBox = new JComboBox<>(new String[]{
                "Administrator",
                "Developer",
                "Designer",
                "Tester",
                "Other"
        });

        messageArea = new JTextArea(5, 20);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Your Name:"), gbc);

        gbc.gridy++;
        formPanel.add(nameField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Your Role:"), gbc);

        gbc.gridy++;
        formPanel.add(roleBox, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Test Message:"), gbc);

        gbc.gridy++;
        formPanel.add(new JScrollPane(messageArea), gbc);

        // ACTIVITY LOG
        activityLog = new JTextArea(8, 30);
        activityLog.setEditable(false);
        activityLog.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel logPanel = new JPanel(new BorderLayout(5, 5));
        logPanel.setOpaque(false);
        logPanel.add(new JLabel("Activity Log"), BorderLayout.NORTH);
        logPanel.add(new JScrollPane(activityLog), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel);
        centerPanel.add(logPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // BUTTONS
        JButton submitButton = new JButton("Submit Test");
        JButton saveButton = new JButton("Save Log");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        submitButton.addActionListener(e -> submitTest());
        saveButton.addActionListener(e -> saveLog());
        clearButton.addActionListener(e -> clearForm());
        exitButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        buttonPanel.setOpaque(false);
        buttonPanel.add(submitButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        logActivity("Application started.");
    }

    private void submitTest() {

        String name = nameField.getText().trim();
        String role = (String) roleBox.getSelectedItem();
        String message = messageArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
            );
            nameField.requestFocus();
            return;
        }

        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a test message.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
            );
            messageArea.requestFocus();
            return;
        }

        logActivity("User: " + name);
        logActivity("Role: " + role);
        logActivity("Message: " + message);
        logActivity("--------------------------------");

        JOptionPane.showMessageDialog(
                this,
                "Test submitted successfully!\n\n" +
                        "Welcome, " + name + "!\n" +
                        "Role: " + role + "\n\n" +
                        "Your collaboration test is recorded locally.",
                "Test Successful",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void logActivity(String activity) {

        String timestamp = LocalDateTime.now().format(formatter);

        activityLog.append(
                "[" + timestamp + "] " + activity + "\n"
        );
    }

    private void saveLog() {

        try (FileWriter writer = new FileWriter("activity_log.txt")) {

            writer.write(activityLog.getText());

            JOptionPane.showMessageDialog(
                    this,
                    "Activity log saved successfully!\n" +
                            "File: activity_log.txt",
                    "Save Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save activity log:\n" + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {

        nameField.setText("");
        roleBox.setSelectedIndex(0);
        messageArea.setText("");

        logActivity("Form cleared.");

        nameField.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                System.err.println(
                        "Could not load system appearance."
                );
            }

            new Main().setVisible(true);
        });
    }
}
