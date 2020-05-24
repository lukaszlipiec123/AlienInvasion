import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Laser extends Sprite {

    public Laser(int horizontal, int vertical) throws IOException {
        this.horizontal = horizontal;
        this.vertical = vertical;
        init();
    }

    public Laser(){}

    private void init() throws IOException {
        Image laserImage = ImageIO.read(new File("assets/laser.png"));
        setImage(laserImage);

        setHorizontal(horizontal + 48 - 10);
        setVertical(vertical);

    }

    @Override
    public void move(){
        this.vertical -= 5;

        if(this.vertical < 0){
            this.removeSprite();
        }
    }

}
