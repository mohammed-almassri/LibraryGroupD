package librarysystem;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.TestData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddBookPanel extends MainWindowPanel {

    private JTextField isbnField;
    private JTextField titleField;
    private JTextField maximumCheckoutField;
    private JTextField numberOfCopiesField;
    private JList<Author> authorList;
    private List<Author> selectedAuthors = new ArrayList<>();
    private final ControllerInterface ci;

    public AddBookPanel(BackListener backListener) {
        super(backListener);
        this.ci = new SystemController();
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Add Book Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Helper method to add components with consistent constraints
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Isbn:"), gbc);
        isbnField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(isbnField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Title:"), gbc);
        titleField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Maximum Checkout:"), gbc);
        maximumCheckoutField = new JTextField();
        gbc.gridx = 1;
        formPanel.add(maximumCheckoutField, gbc);


        TestData t = new TestData();

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Select Authors:"), gbc);
        authorList = new JList<>(t.allAuthors.toArray(new Author[0]));
        authorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(authorList);
        scrollPane.setPreferredSize(new Dimension(200, 100)); // Consistent height with text fields
        gbc.gridx = 1;
        formPanel.add(scrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new AddBookPanel.SubmitButtonListener());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String isbn = isbnField.getText();
            String title = titleField.getText();
            String maximum = maximumCheckoutField.getText();
            selectedAuthors = authorList.getSelectedValuesList();

            // Basic validation
            if (isbn.isEmpty() || title.isEmpty() || maximum.isEmpty() || selectedAuthors.isEmpty()
            ) {
                JOptionPane.showMessageDialog(AddBookPanel.this,
                        "ISBN, Title, Maximum Of checkout, Number of copies, author fields are required.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Persist data
            try {
                ci.addNewBook(new Book(isbn, title, Integer.parseInt(maximum), selectedAuthors));
                JOptionPane.showMessageDialog(AddBookPanel.this, "Book was added successfully!");
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddBookPanel.this, "Error saving member: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        isbnField.setText("");
        titleField.setText("");
        maximumCheckoutField.setText("");
        authorList.clearSelection();
    }
}
