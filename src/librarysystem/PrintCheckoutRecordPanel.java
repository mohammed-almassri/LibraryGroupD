package librarysystem;

import javax.swing.*;
import java.awt.*;

public class PrintCheckoutRecordPanel extends MainWindowPanel {
    public PrintCheckoutRecordPanel(BackListener backListener) {
        super(backListener);
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Print Checkout Record Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }

    @Override
    public void reset() {

    }
}
