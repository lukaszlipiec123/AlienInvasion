import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;


public class Ship extends Sprite {


    public Ship() throws IOException {
        init();
    }

    private void init() throws IOException {
        Image shipImage = ImageIO.read(new File("assets/ship.png"));
        setImage(shipImage);

        int startHorizontal = 454;
        int startVertical = 568;

        setHorizontal(startHorizontal);
        setVertical(startVertical);
    }

    @Override
    public void move(){
        horizontal += dHorizontal;

        if(horizontal < 0){
            horizontal = 0;
        }

        if(horizontal >= 1024 - 100){
            horizontal = 1024 - 100;
        }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_LEFT){
            dHorizontal = -2;
        };

        if(key == KeyEvent.VK_RIGHT){
            dHorizontal = 2;
        };

    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_LEFT){
            dHorizontal = 0;
        };

        if(key == KeyEvent.VK_RIGHT){
            dHorizontal = 0;
        };
    };
}
