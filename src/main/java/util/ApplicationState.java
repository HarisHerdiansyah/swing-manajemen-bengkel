package util;

import javax.swing.*;

public class ApplicationState {
    private static ApplicationState instance;
    private JPanel contentPanel;

    private ApplicationState() {
        contentPanel = null;
    }

    public static ApplicationState getInstance() {
        if (instance == null) {
            instance = new ApplicationState();
        }
        return instance;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }
}
