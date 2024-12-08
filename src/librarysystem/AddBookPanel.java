package librarysystem;

import javax.swing.*;
import java.awt.*;

public class AddBookPanel extends MainWindowPanel {
    public AddBookPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Add Book Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }

    @Override
    public void reset() {

    }
}
