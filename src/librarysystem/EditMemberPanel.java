package librarysystem;

import javax.swing.*;
import java.awt.*;

public class EditMemberPanel extends MainWindowPanel {
    public EditMemberPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Edit Member Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }

    @Override
    public void reset() {

    }
}
