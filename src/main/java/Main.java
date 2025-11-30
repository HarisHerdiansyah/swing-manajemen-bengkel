import util.ApplicationState;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        ApplicationState.getInstance();
        new MainFrame().setVisible(true);
    }
}
