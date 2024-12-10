// package librarysystem;

// import dataaccess.DataAccessFacade;
// import dataaccess.TestData;
// import javax.swing.*;
// import java.awt.*;
// import java.io.File;

// /**
// * This class is used to test the AddBookCopyPanel.
// * It creates a storage directory if it doesn't exist, loads test data, and
// * displays the AddBookCopyPanel.
// */
// public class TestAddBookCopyPanel {
// public static void main(String[] args) {
// // Ensure storage directory exists
// createStorageDirectory();

// // Initialize test data
// TestData td = new TestData();
// td.bookData();
// td.userData();
// td.libraryMemberData();

// EventQueue.invokeLater(() -> {
// JFrame frame = new JFrame("Test Add Book Copy Panel");
// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// BackListener backListener = () -> System.out.println("Back button clicked");
// AddBookCopyPanel panel = new AddBookCopyPanel(backListener);

// panel.initialize();

// // Add a label with test instructions
// JPanel mainPanel = new JPanel(new BorderLayout());
// JLabel instructions = new JLabel(
// "<html>Test with these ISBNs:<br/>" +
// "23-11451 (The Big Fish)<br/>" +
// "28-12331 (Antartica)<br/>" +
// "99-22223 (Thinking Java)<br/>" +
// "48-56882 (Jimmy's First Day of School)</html>");
// instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
// mainPanel.add(instructions, BorderLayout.NORTH);
// mainPanel.add(panel, BorderLayout.CENTER);

// frame.add(mainPanel);
// frame.setSize(400, 400);
// frame.setLocationRelativeTo(null);
// frame.setVisible(true);
// });
// }

// private static void createStorageDirectory() {
// File directory = new File(DataAccessFacade.OUTPUT_DIR);
// if (!directory.exists()) {
// boolean created = directory.mkdirs();
// if (created) {
// System.out.println("Storage directory created at: " +
// directory.getAbsolutePath());
// } else {
// System.err.println("Failed to create storage directory at: " +
// directory.getAbsolutePath());
// }
// } else {
// System.out.println("Storage directory already exists at: " +
// directory.getAbsolutePath());
// }
// }
// }