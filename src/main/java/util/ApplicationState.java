/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package util;

import model.Admin;

import javax.swing.*;

public class ApplicationState {
    private static ApplicationState instance;
    private JPanel contentPanel;
    private Admin admin;
    private Object formObject;

    private ApplicationState() {
        contentPanel = null;
        admin = null;
        formObject = null;
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

    public Object getFormObject() {
        return formObject;
    }

    public void setFormObject(Object formObject) {
        this.formObject = formObject;
    }
}
