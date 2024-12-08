package librarysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class CheckoutBookPanel extends MainWindowPanel {

    private JTextField memberIdField;
    private JTextField isbnField;
    private JButton checkoutButton;
    private JTable checkoutTable;
    private DefaultTableModel tableModel;

    public CheckoutBookPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());

        // Create top form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdField = new JTextField(15);

        JLabel isbnLabel = new JLabel("Book ISBN:");
        isbnField = new JTextField(15);

        checkoutButton = new JButton("Checkout");

        // Add components to formPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(memberIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(isbnLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(isbnField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(checkoutButton, gbc);

        // Create bottom table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel tableLabel = new JLabel("Checkout Record");
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Setup JTable
        tableModel = new DefaultTableModel(new Object[]{"Book Title", "Copy Number", "Checkout Date", "Due Date"}, 0);
        checkoutTable = new JTable(tableModel);
        checkoutTable.setEnabled(false); // Read-only

        JScrollPane tableScrollPane = new JScrollPane(checkoutTable);
        tablePanel.add(tableLabel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add panels to main panel
        add(formPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        // Add action listener to the checkout button
        checkoutButton.addActionListener(handleCheckout());
    }

    @Override
    public void reset() {
        removeAll();
        initialize();
    }

    private ActionListener handleCheckout() {
        return e -> {
            String memberId = memberIdField.getText().trim();
            String isbn = isbnField.getText().trim();

            // Validate input
            if (memberId.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Member ID and ISBN.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Placeholder logic: Replace with actual system logic
            boolean isMemberValid = true; // Check if member exists in the system
            boolean isBookAvailable = true; // Check if the book is available for checkout

            if (!isMemberValid) {
                JOptionPane.showMessageDialog(this, "Member ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isBookAvailable) {
                JOptionPane.showMessageDialog(this, "Book not available.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Simulate checkout process
                String bookTitle = "Sample Book Title"; // Fetch from book details
                String copyNumber = "1"; // Fetch the next available copy
                String checkoutDate = "2024-12-08"; // Use current date
                String dueDate = "2024-12-29"; // Calculate based on max checkout period

                // Add record to table
                tableModel.addRow(new Object[]{bookTitle, copyNumber, checkoutDate, dueDate});

                // Display success message
                JOptionPane.showMessageDialog(this, "Checkout successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.reset();
                this.backListener.onBack();
            }
        };
    }

}
