package librarysystem;

import javax.swing.*;

abstract class MainWindowPanel extends JPanel {
    protected BackListener backListener;
    public abstract void initialize();
    public void reset() {
        removeAll();
        initialize();
    }
    public MainWindowPanel(BackListener backListener) {
        this.backListener = backListener;
    }
}
