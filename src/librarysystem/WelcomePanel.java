package librarysystem;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends MainWindowPanel {
    private String message;

        JLabel welcomeLabel;


    public WelcomePanel(String message, BackListener backListener) {
        super(backListener);
        this.message = message;
    }

    @Override
    public void initialize() {
        removeAll();
        welcomeLabel = new JLabel(message);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(welcomeLabel, BorderLayout.CENTER);
    }

    @Override
    public void reset() {
        removeAll();
        initialize();
    }
}
