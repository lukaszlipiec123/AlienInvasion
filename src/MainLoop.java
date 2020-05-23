import java.awt.event.*;
import java.io.IOException;

public class MainLoop implements ActionListener {

    private GamePanel gamePanel;

    public MainLoop(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void actionPerformed(ActionEvent a){
        try {
            this.gamePanel.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
