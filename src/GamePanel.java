import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private Image backgroundImage;
    private Timer timer;
    private Ship ship;
    private boolean gameIsOn = true;
    private Laser laser;
    private List<Laser> lasers = new ArrayList<Laser>();



    public void JPanelWithBackground() throws IOException {
        backgroundImage = ImageIO.read(new File("assets/background.jpg"));
    }

    public GamePanel() throws IOException {
        init();
    }

    private void init() throws IOException {
        addKeyListener(new EventListener(this));
        setFocusable(true);
        this.laser = new Laser();
        setPreferredSize(new Dimension(1024, 768));
        JPanelWithBackground();
        this.ship = new Ship();
        this.timer = new Timer(10, new MainLoop(this));
        this.timer.start();
    }

    private void drawShip(Graphics g){
        g.drawImage(ship.getImage(), ship.getHorizontal(), ship.getVertical(), this);
    }

    private void drawLaser(Graphics g){
        if(laser.checkAlive()){
            g.drawImage(laser.getImage(), laser.getHorizontal(), laser.getVertical(), this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);

        updateObjects(g);
    }

    private void updateObjects(Graphics g) {
        if(gameIsOn){
            drawShip(g);
            drawLaser(g);
        } else {
            timer.stop();
        }

        Toolkit.getDefaultToolkit().sync();
    }

    protected void loop(){
        update();
        repaint();
    }

    public void update(){
        this.ship.move();
        this.laser.move();
    }

    public void keyReleased(KeyEvent e) {
        this.ship.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) throws IOException {
        this.ship.keyPressed(e);

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_SPACE){
            int horizontal = this.ship.getHorizontal();
            int vertical = this.ship.getVertical();
//            if(gameIsOn && !laser.checkAlive()){
//                laser = new Laser(horizontal, vertical);
//            }

            if(gameIsOn){
                laser = new Laser(horizontal, vertical);
            }

        }
    }


}
