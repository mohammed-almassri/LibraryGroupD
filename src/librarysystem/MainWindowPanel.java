package librarysystem;

import javax.swing.*;

abstract class MainWindowPanel extends JPanel {
    protected BackListener backListener;
    public abstract void initialize();
    public abstract void reset();
    
    public MainWindowPanel(BackListener backListener) {
        this.backListener = backListener;
    }
}
