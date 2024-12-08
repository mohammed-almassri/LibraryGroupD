package librarysystem;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;
import dataaccess.User;

import javax.swing.*;
import java.awt.BorderLayout;

public class MainWindow extends JFrame implements LibWindow {
    public static final MainWindow INSTANCE = new MainWindow();
    private static final long serialVersionUID = 1L;
    ControllerInterface ci = new SystemController();

    private boolean isInitialized = false;
    JPanel mainPanel;
    JMenuBar menuBar;

    // Menus
    private JMenu fileMenu;
    private JMenu memberMenu;     // Admin or Both
    private JMenu bookMenu;       // Admin or Both
    private JMenu checkoutMenu;   // Librarian or Both

    // File menu items
    private JMenuItem logoutItem;
    private JMenuItem exitItem;

    // Member menu items
    private JMenuItem addMemberItem;
    private JMenuItem editMemberItem;

    // Book menu items
    private JMenuItem addBookItem; // Optional use case
    private JMenuItem addBookCopyItem;

    // Checkout menu items
    private JMenuItem checkoutBookItem;
    private JMenuItem printCheckoutRecordItem;  // Optional
    private JMenuItem searchOverdueBooksItem;    // Optional

    private User loggedInUser; // Assumes a user is set before init is called

    private MainWindow() {}

    // Method to supply the logged-in user before init is called
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    @Override
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    @Override
    public void init() {
        createMenus();
        setupContent();
        setSize(660,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        isInitialized = true;
        setLoggedInUser(SystemController.currentUser);
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        addMenuItems();
        setJMenuBar(menuBar);
    }

    private void addMenuItems() {
        // File Menu
        fileMenu = new JMenu("File");
        logoutItem = new JMenuItem("Logout");
        exitItem = new JMenuItem("Exit");
        fileMenu.add(logoutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Add Action Listeners for File Menu
        exitItem.addActionListener(e -> System.exit(0));
        logoutItem.addActionListener(e -> {
            // Implement logout logic: close this window and show login window
            // For example:
            ci.logout();
            setVisible(false);
            LoginWindow.INSTANCE.setVisible(true);
        });

        // Based on role, show/hide menus
        // If User is ADMIN or BOTH
        if (loggedInUser != null && (loggedInUser.getAuthorization() == Auth.ADMIN || loggedInUser.getAuthorization() == Auth.BOTH)) {
            memberMenu = new JMenu("Member");
            addMemberItem = new JMenuItem("Add Member");
            editMemberItem = new JMenuItem("Edit Member Info");
            memberMenu.add(addMemberItem);
            memberMenu.add(editMemberItem);

            // Add action listeners for member items
            addMemberItem.addActionListener(e -> {
                // Show AddMemberWindow dialog
            });
            editMemberItem.addActionListener(e -> {
                // Show EditMemberWindow dialog or search member dialog
            });

            bookMenu = new JMenu("Book");
            addBookItem = new JMenuItem("Add Book"); // Optional use case
            addBookCopyItem = new JMenuItem("Add Book Copy");
            bookMenu.add(addBookItem);
            bookMenu.add(addBookCopyItem);

            // Add action listeners for book items
            addBookItem.addActionListener(e -> {
                // Show AddBookWindow
            });
            addBookCopyItem.addActionListener(e -> {
                // Show AddBookCopyWindow
            });

            menuBar.add(memberMenu);
            menuBar.add(bookMenu);
        }

        // If User is LIBRARIAN or BOTH
        if (loggedInUser != null && (loggedInUser.getAuthorization() == Auth.LIBRARIAN || loggedInUser.getAuthorization() == Auth.BOTH)) {
            checkoutMenu = new JMenu("Checkout");
            checkoutBookItem = new JMenuItem("Checkout Book");
            printCheckoutRecordItem = new JMenuItem("Print Checkout Record"); // optional
            searchOverdueBooksItem = new JMenuItem("Search Overdue Books");    // optional

            checkoutMenu.add(checkoutBookItem);
            checkoutMenu.add(printCheckoutRecordItem);
            checkoutMenu.add(searchOverdueBooksItem);

            // Add action listeners for checkout menu items
            checkoutBookItem.addActionListener(e -> {
                // Show CheckoutBookWindow
            });
            printCheckoutRecordItem.addActionListener(e -> {
                // Print record to console or show dialog
            });
            searchOverdueBooksItem.addActionListener(e -> {
                // Show OverdueBookSearchWindow
            });

            menuBar.add(checkoutMenu);
        }
    }

    private void setupContent() {
        mainPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel;
        if (loggedInUser != null) {
            welcomeLabel = new JLabel("Welcome, " + loggedInUser.getId() + "!");
        } else {
            welcomeLabel = new JLabel("Welcome!");
        }
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
    }
}
