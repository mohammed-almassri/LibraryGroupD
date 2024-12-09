package librarysystem;

import business.Checkout;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PrintCheckoutRecordPanel extends MainWindowPanel {
    private JTextField memberIdField;
    private JButton searchButton;
    private JTable printCheckoutTable;
    private DefaultTableModel tableModel;

    public PrintCheckoutRecordPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Print Checkout Record Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel memberIdLabel = new JLabel("Search Member ID:");
        memberIdField = new JTextField(15);

        searchButton = new JButton("Search");

        // Add components to formPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(memberIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(searchButton, gbc);

        JPanel tablePanel = new JPanel(new BorderLayout());
        JLabel tableLabel = new JLabel("Print Checkout Record");
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Setup JTable
        tableModel = new DefaultTableModel(new Object[]{"Book Title", "Copy Number", "Checkout Date", "Due Date"}, 0);
        printCheckoutTable = new JTable(tableModel);
        printCheckoutTable.setEnabled(false); // Read-only

        JScrollPane tableScrollPane = new JScrollPane(printCheckoutTable);
        tablePanel.add(tableLabel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add panels to main panel
        add(formPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        // Add action listener to the search button
        searchButton.addActionListener(handleSearch());
    }

    private ActionListener handleSearch() {
        return e -> {
            String memberId = memberIdField.getText().trim();

            // Validate input
            if (memberId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataAccessFacade da = new DataAccessFacade();

            LibraryMember m = da.readMemberMap().get(memberId);
            boolean isMemberValid = m != null;

            if (!isMemberValid) {
                JOptionPane.showMessageDialog(this, "Member ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                if (m.isEmptyCheckouts()) {
                    JOptionPane.showMessageDialog(this, "This member did not have any records.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                printCheckoutRecordsTable(m);
            }
        };
    }

    /**
     * Refresh the JTable to display all checkouts for the given member.
     */
    private void printCheckoutRecordsTable(LibraryMember member) {
        // Clear the existing rows
        tableModel.setRowCount(0);

        // Populate the table with all checkouts
        for (Checkout checkout : member.getCheckouts()) {
            String bookTitle = checkout.getBookCopy().getBook().getTitle();
            String copyNumber = String.valueOf(checkout.getBookCopy().getCopyNum());
            String checkoutDate = checkout.getCheckoutDate().toString();
            String dueDate = checkout.getReturnDate().toString();

            System.out.println("Title: " + bookTitle);
            System.out.println("Copy Number: " + copyNumber);
            System.out.println("Checkout Date: " + checkoutDate);
            System.out.println("Due Date: " + dueDate);
            System.out.println("=======================");

            tableModel.addRow(new Object[]{bookTitle, copyNumber, checkoutDate, dueDate});
        }
    }
}
