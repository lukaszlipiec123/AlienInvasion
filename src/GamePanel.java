import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GamePanel extends JPanel {

    private Image backgroundImage;
    private Timer timer;
    private Ship ship;
    private boolean gameIsOn = true;
    private Laser laser;
    private List<Laser> lasers = new ArrayList<>();
    private List<Alien> aliens = new ArrayList<>();
    private List<Heart> hearts = new ArrayList<>();;
    private int speed = 1;
    private double alienProgress = 0.0;
    private int score = 0;



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
        createFleet();
        createHearts();
        this.timer = new Timer(10, new MainLoop(this));
        this.timer.start();
    }

    private void drawShip(Graphics g){
        g.drawImage(ship.getImage(), ship.getHorizontal(), ship.getVertical(), this);
    }

    private void drawLaser(Graphics g){
        for(Laser laser : lasers){
            g.drawImage(laser.getImage(), laser.getHorizontal(), laser.getVertical(), this);
        }
    }

    private void drawFleet(Graphics g){
        for(Alien alien : aliens){
            if(alien.isVisible())
                g.drawImage(alien.getImage(), alien.getHorizontal(), alien.getVertical(), this);
        }
    }

    private void drawHearts(Graphics g) {
        for (Heart heart : hearts) {
            g.drawImage(heart.getImage(), heart.getHorizontal(), heart.getVertical(), this);
        }
    }

    private void drawScores(Graphics g) {
//        super.paintComponent(g);
        Font font = new Font("Verdana", Font.BOLD, 14);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Your score: " + score, 0, 715);
        g.drawString("Top score: 0", 425, 715);
    }

    private void createFleet() throws IOException {
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 8; j++){
                this.aliens.add(new Alien(125 + 100 * j, 0 + 100 * i));
            }
        }
    }

    private void createHearts() throws IOException {
        for(int i = 0; i < 3; i++){
            this.hearts.add(new Heart(875 + 50 * i, 690));
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
            drawFleet(g);
            drawHearts(g);
            drawScores(g);
        } else {
            timer.stop();
        }

        Toolkit.getDefaultToolkit().sync();
    }

    protected void loop() throws IOException {
        update();
        repaint();
    }

    public void update() throws IOException {
        List<Laser> copyLasers = new ArrayList<>();

        this.ship.move();
        for(Laser laser : lasers){
            laser.move();
            if(laser.checkAlive()){
                copyLasers.add(laser);
            }
            lasers = copyLasers;
        }
        for(Laser laser : lasers){
            for(Alien alien : aliens) {
                if (collides(laser, alien)) {
                    laser.removeSprite();
                    alien.removeSprite();
                }
            }
        }
        checkFleet();
        moveFleet();
        if(aliens.isEmpty()){
            createFleet();
            score += 320;
        }
    }

    private void checkFleet(){
        List<Alien> copyAliens = new ArrayList<>();
        for(Alien alien : aliens){
            if(alien.checkAlive()){
                copyAliens.add(alien);
            }
        }
        aliens = copyAliens;
    }

    protected void moveFleet(){
        for(Alien alien : aliens) {
            alien.horizontal += speed;
            alien.vertical += alienProgress;
        }
        if(alienProgress != 0.0) {
            alienProgress = 0.0;
        }
        //Sprawdzenie kolizji
        for(Alien alien : aliens) {
            if(alien.horizontal >= 960 || alien.horizontal <= 0) {
                speed *= -1;
                alienProgress = 6;
                break;
            }
        }
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
            if(gameIsOn && lasers.size() < 3){
                lasers.add(new Laser(horizontal, vertical));
            }

        }
    }

    private static boolean collides(Sprite s, Alien alien){
        if(s.getBounds(20, 60).intersects(alien.getBounds( 61, 116))){
                return true;
        } else {
            return false;
        }
    }
}
