import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Heart extends Sprite {

    public void move(){}

    public Heart(int horizontal, int vertical) throws IOException {
        this.horizontal = horizontal;
        this.vertical = vertical;
        init();
    }

    public void init() throws IOException {
        Image heartImage = ImageIO.read(new File("assets/heart.png"));
        setImage(heartImage);
    }
}
