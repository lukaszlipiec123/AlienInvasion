import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class MyGame extends JFrame {

    public MyGame() throws IOException {
        init();
    }

    private void init() throws IOException {
        add(new GamePanel());
        this.setTitle("AlienInvasion");
        try {
            BufferedImage image = ImageIO.read(new File("assets/alien.png"));
            this.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024,768);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
