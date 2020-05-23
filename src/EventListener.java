import java.awt.event.*;
import java.io.IOException;

public class EventListener extends KeyAdapter {

    private GamePanel board;

    public EventListener(GamePanel board){
        this.board = board;
    }

    @Override
    public void keyReleased(KeyEvent e){
        this.board.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e){
        try {
            this.board.keyPressed(e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
