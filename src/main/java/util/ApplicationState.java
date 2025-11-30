package util;

import model.Admin;

import javax.swing.*;

public class ApplicationState {
    private static ApplicationState instance;
    private JPanel contentPanel;
    private Admin admin;

    private ApplicationState() {
        contentPanel = null;
        admin = null;
    }

    public static ApplicationState getInstance() {
        if (instance == null) {
            System.out.println("Create ApplicationState instance");

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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
