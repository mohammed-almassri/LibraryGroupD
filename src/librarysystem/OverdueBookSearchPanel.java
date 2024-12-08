package librarysystem;

import javax.swing.*;
import java.awt.*;

public class OverdueBookSearchPanel extends MainWindowPanel {
    public OverdueBookSearchPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Overdue Book Search Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
