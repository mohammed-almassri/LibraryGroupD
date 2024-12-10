package librarysystem;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

public class AddBookCopyPanel extends MainWindowPanel {
    private JTextField isbnField;
    private JTextField copiesField;
    private JButton addButton;
    private JLabel messageLabel;
    private ControllerInterface ci = new SystemController();

    public AddBookCopyPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        // Set up the panel layout
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Create components
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(20);
        JLabel copiesLabel = new JLabel("Number of Copies:");
        copiesField = new JTextField(5);
        addButton = new JButton("Add Copies");
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(isbnLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(copiesLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(copiesField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(messageLabel, gbc);

        // Add back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> backListener.onBack());
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(backButton, gbc);

        // Add action listener for add button
        addButton.addActionListener(e -> addCopies());

        // Add the main panel
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addCopies() {
        try {
            String isbn = isbnField.getText().trim();
            int copies = Integer.parseInt(copiesField.getText().trim());

            if (isbn.isEmpty()) {
                messageLabel.setText("Please enter an ISBN");
                return;
            }

            ci.addBookCopy(isbn, copies);
            messageLabel.setForeground(Color.GREEN.darker());
            messageLabel.setText("Successfully added " + copies + " copies");

            // Clear the fields
            isbnField.setText("");
            copiesField.setText("");

        } catch (NumberFormatException e) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please enter a valid number of copies");
        } catch (LibrarySystemException e) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText(e.getMessage());
        }
    }
}
