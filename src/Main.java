import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            try {
                new MyGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
