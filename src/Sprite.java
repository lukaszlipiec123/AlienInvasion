import java.awt.*;


public abstract class Sprite {
    private Image image;
    private boolean isAlive;

    protected int horizontal;
    protected int vertical;
    protected int dHorizontal;

    public abstract void move();

    protected void setImage(Image image){
        this.image = image;
    }

    public Image getImage(){
        return this.image;
    }

    protected void setHorizontal(int value){
        this.horizontal = value;
    }

    protected void setVertical(int value){
        this.vertical = value;
    }

    protected int getHorizontal(){
        return this.horizontal;
    }

    protected int getVertical(){
        return this.vertical;
    }

    public Sprite(){
        this.isAlive = true;
    }

    public void removeSprite(){
        this.isAlive = false;
    }

    protected boolean checkAlive(){
        return this.isAlive;
    }

    public Rectangle getBounds(int x, int y){
        return new Rectangle(horizontal, vertical, x, y);
    }
}
