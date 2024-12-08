package librarysystem;

import javax.swing.*;
import java.awt.*;

public class AddMemberPanel extends MainWindowPanel {
    public AddMemberPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Add Member Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }

    @Override
    public void reset() {

    }
}
