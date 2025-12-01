import util.ApplicationState;
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        ApplicationState.getInstance();
        new LoginFrame().setVisible(true);
    }
}
