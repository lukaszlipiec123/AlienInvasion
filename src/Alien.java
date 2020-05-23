import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Alien extends Sprite{

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible = true;

    public Alien(int horizontal, int vertical) throws IOException {
        this.horizontal = horizontal;
        this.vertical = vertical;
        init();
    }

    public void init() throws IOException {
        Image alienImage = ImageIO.read(new File("assets/alien.png"));
        setImage(alienImage);
    }

    @Override
    public void move(){
    }

    public void move(int direction){
        this.horizontal += direction;
    }

}
