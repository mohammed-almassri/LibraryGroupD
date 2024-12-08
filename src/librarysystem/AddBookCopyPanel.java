package librarysystem;

import javax.swing.*;
import java.awt.*;

public class AddBookCopyPanel extends MainWindowPanel {
    public AddBookCopyPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Add Book Copy Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
