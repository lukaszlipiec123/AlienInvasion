import java.awt.event.*;

public class MainLoop implements ActionListener {

    private GamePanel gamePanel;

    public MainLoop(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void actionPerformed(ActionEvent a){
        this.gamePanel.loop();
    }
}
