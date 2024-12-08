package librarysystem;

import business.Book;
import business.BookCopy;
import business.Checkout;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

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

    private ActionListener handleCheckout() {
        return e -> {
            String memberId = memberIdField.getText().trim();
            String isbn = isbnField.getText().trim();

            // Validate input
            if (memberId.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Member ID and ISBN.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataAccessFacade da = new DataAccessFacade();

            LibraryMember m = da.readMemberMap().get(memberId);
            Book b = da.readBooksMap().get(isbn);
            Optional<BookCopy> first = Arrays.stream(b.getCopies()).filter(BookCopy::isAvailable).findFirst();
            BookCopy bc = first.orElse(null);
            boolean isMemberValid = m != null;
            boolean isBookAvailable = bc != null; // Check if the book is available for checkout

            if (!isMemberValid) {
                JOptionPane.showMessageDialog(this, "Member ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isBookAvailable) {
                JOptionPane.showMessageDialog(this, "Book not available.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Perform checkout process
                String bookTitle = bc.getBook().getTitle();
                String copyNumber = String.valueOf(bc.getCopyNum());
                LocalDate today = LocalDate.now();
                LocalDate dueDateObj = today.plusDays(bc.getBook().getMaxCheckoutLength());
                String checkoutDate = today.toString();
                String dueDate = dueDateObj.toString();

                bc.changeAvailability();
                da.updateBook(isbn, b);

                Checkout checkout = new Checkout(
                        m,
                        bc,
                        today,
                        dueDateObj
                );

                m.addCheckout(checkout);
                da.updateMember(m.getMemberId(), m);

                // Refresh the checkout table
                refreshCheckoutTable(m);

                // Display success message
                JOptionPane.showMessageDialog(this, "Checkout successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.reset();
                this.backListener.onBack();
            }
        };
    }

    /**
     * Refresh the JTable to display all checkouts for the given member.
     */
    private void refreshCheckoutTable(LibraryMember member) {
        // Clear the existing rows
        tableModel.setRowCount(0);

        // Populate the table with all checkouts
        for (Checkout checkout : member.getCheckouts()) {
            String bookTitle = checkout.getBookCopy().getBook().getTitle();
            String copyNumber = String.valueOf(checkout.getBookCopy().getCopyNum());
            String checkoutDate = checkout.getCheckoutDate().toString();
            String dueDate = checkout.getReturnDate().toString();

            tableModel.addRow(new Object[]{bookTitle, copyNumber, checkoutDate, dueDate});
        }
    }

}
