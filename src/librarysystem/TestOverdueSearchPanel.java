// package librarysystem;

// import javax.swing.*;

// import dataaccess.TestData;

// import java.awt.*;

// public class TestOverdueSearchPanel {
// public static void main(String[] args) {
// // First, run the TestData to initialize the data
// // TestData.main(new String[] {}); // Ensure test data is initialized
// TestData.main(args);
// EventQueue.invokeLater(() -> {
// JFrame frame = new JFrame("Test Overdue Book Search");
// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// BackListener backListener = () -> System.out.println("Back button clicked");
// OverdueBookSearchPanel panel = new OverdueBookSearchPanel(backListener);

// panel.initialize();

// // Add instructions
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
// frame.setSize(800, 600);
// frame.setLocationRelativeTo(null);
// frame.setVisible(true);
// });
// }
// }