package librarysystem;

import business.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OverdueBookSearchPanel extends MainWindowPanel {
    private JTextField isbnField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JLabel messageLabel;
    private ControllerInterface ci = new SystemController();

    public OverdueBookSearchPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("ISBN:"));
        isbnField = new JTextField(20);
        searchPanel.add(isbnField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBook());
        searchPanel.add(searchButton);

        // Create table
        String[] columns = { "ISBN", "Title", "Copy Number", "Member", "Checkout Date", "Due Date", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Message label for feedback
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Add back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> backListener.onBack());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void searchBook() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            showMessage("Please enter an ISBN", true);
            return;
        }

        try {
            List<Checkout> overdueCheckouts = ci.searchOverdueBookCopies(isbn);
            displayCheckouts(isbn, overdueCheckouts);
        } catch (LibrarySystemException e) {
            showMessage(e.getMessage(), true);
            clearTable();
        }
    }

    private void displayCheckouts(String isbn, List<Checkout> checkouts) {
        clearTable();

        if (checkouts.isEmpty()) {
            showMessage("No overdue copies found", false);
            return;
        }

        for (Checkout checkout : checkouts) {
            BookCopy copy = checkout.getBookCopy();
            Book book = copy.getBook();
            LibraryMember member = checkout.getMember();

            Object[] rowData = new Object[7];
            rowData[0] = book.getIsbn();
            rowData[1] = book.getTitle();
            rowData[2] = copy.getCopyNum();
            rowData[3] = member.getFirstName() + " " + member.getLastName();
            rowData[4] = checkout.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            rowData[5] = checkout.getReturnDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            rowData[6] = "OVERDUE";

            tableModel.addRow(rowData);
        }

        showMessage("Found " + checkouts.size() + " overdue copies", false);
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setForeground(isError ? Color.RED : new Color(0, 100, 0));
    }
}
