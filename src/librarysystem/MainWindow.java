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
    MainWindowPanel currentPanel;
    MainWindowPanel welcomePanel;
    MainWindowPanel addMemberPanel;
    MainWindowPanel editMemberPanel;
    MainWindowPanel addBookPanel;
    MainWindowPanel addBookCopyPanel;
    MainWindowPanel checkoutBookPanel;
    MainWindowPanel printCheckoutRecordPanel;
    MainWindowPanel searchOverdueBooksPanel;
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

    // Panel keys


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
        User user = (new SystemController()).getCurrentUser();
        setLoggedInUser(user);
        System.out.println(user);
        createMenus();
        setupContent();
        setSize(660,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        isInitialized = true;
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        addMenuItems();
        setJMenuBar(menuBar);
    }

    private void addMenuItems() {
        // File menu is always present
        fileMenu = createFileMenu();
        menuBar.add(fileMenu);

        if (isAdminOrBoth()) {
            memberMenu = createMemberMenu();
            bookMenu = createBookMenu();
            menuBar.add(memberMenu);
            menuBar.add(bookMenu);
        }

        if (isLibrarianOrBoth()) {
            checkoutMenu = createCheckoutMenu();
            menuBar.add(checkoutMenu);
        }
    }

    private JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
        logoutItem = new JMenuItem("Logout");
        exitItem = new JMenuItem("Exit");

        logoutItem.addActionListener(e -> {
            // Implement logout logic
        });
        exitItem.addActionListener(e -> System.exit(0));

        menu.add(logoutItem);
        menu.addSeparator();
        menu.add(exitItem);

        return menu;
    }

    private JMenu createMemberMenu() {
        JMenu menu = new JMenu("Member");
        addMemberItem = new JMenuItem("Add Member");
//        editMemberItem = new JMenuItem("Edit Member Info");

        addMemberItem.addActionListener(e -> {
            showPanel(addMemberPanel);
        });
//        editMemberItem.addActionListener(e -> {
//            showPanel(editMemberPanel);
//        });

        menu.add(addMemberItem);
//        menu.add(editMemberItem);
        return menu;
    }

    private JMenu createBookMenu() {
        JMenu menu = new JMenu("Book");
        addBookItem = new JMenuItem("Add Book");
        addBookCopyItem = new JMenuItem("Add Book Copy");

        addBookItem.addActionListener(e -> {
            showPanel(addBookPanel);
        });
        addBookCopyItem.addActionListener(e -> {
            showPanel(addBookCopyPanel);
        });

        menu.add(addBookItem);
        menu.add(addBookCopyItem);
        return menu;
    }

    private JMenu createCheckoutMenu() {
        JMenu menu = new JMenu("Checkout");
        checkoutBookItem = new JMenuItem("Checkout Book");
        printCheckoutRecordItem = new JMenuItem("Print Checkout Record");
        searchOverdueBooksItem = new JMenuItem("Search Overdue Books");

        checkoutBookItem.addActionListener(e -> {
            showPanel(checkoutBookPanel);
        });
        printCheckoutRecordItem.addActionListener(e -> {
            showPanel(printCheckoutRecordPanel);
        });
        searchOverdueBooksItem.addActionListener(e -> {
            showPanel(searchOverdueBooksPanel);
        });

        menu.add(checkoutBookItem);
        menu.add(printCheckoutRecordItem);
        menu.add(searchOverdueBooksItem);

        return menu;
    }

    private boolean isAdminOrBoth() {
        return loggedInUser != null && (loggedInUser.getAuthorization() == Auth.ADMIN || loggedInUser.getAuthorization() == Auth.BOTH);
    }

    private boolean isLibrarianOrBoth() {
        return loggedInUser != null && (loggedInUser.getAuthorization() == Auth.LIBRARIAN || loggedInUser.getAuthorization() == Auth.BOTH);
    }
    private void setupContent() {

        mainPanel = new JPanel();
        BackListener backListener = ()->{
            this.showPanel(welcomePanel);
        };
        welcomePanel = new WelcomePanel("welcome, "+loggedInUser.getId(),backListener);
        addMemberPanel = new AddMemberPanel(backListener);
        editMemberPanel = new EditMemberPanel(backListener);
        addBookPanel = new AddBookPanel(backListener);
        addBookCopyPanel = new AddBookCopyPanel(backListener);
        checkoutBookPanel = new CheckoutBookPanel(backListener);
        printCheckoutRecordPanel = new PrintCheckoutRecordPanel(backListener);
        searchOverdueBooksPanel = new OverdueBookSearchPanel(backListener);

        getContentPane().add(mainPanel);
        showPanel(welcomePanel);
    }

    private void showPanel(MainWindowPanel panel) {
        mainPanel.removeAll();
        if(currentPanel!=null){
            currentPanel.reset();
        }
        panel.initialize();
        currentPanel = panel;
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
