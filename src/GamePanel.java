import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private Image backgroundImage;
    private Timer timer;
    private Ship ship;
    private boolean gameIsOn = true;
    private Laser laser;
    private List<Laser> lasers = new ArrayList<>();
    private List<Alien> aliens = new ArrayList<>();
    private List<Heart> hearts = new ArrayList<>();;
    private int originalSpeed = 1;
    private int speed = originalSpeed;
    private double alienProgress = 0.0;
    private int score = 0;
    private int highscore = 0;



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
        File f = new File("scores.txt");
        if(f.exists()){
            try {
                BufferedReader readStream = new BufferedReader(new FileReader("scores.txt"));
                highscore = Integer.parseInt(readStream.readLine());
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
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
        Font font = new Font("Verdana", Font.BOLD, 14);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Your score: " + score, 0, 715);
        g.drawString("High score: " + highscore, 425, 715);
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
            Font font = new Font("Verdana", Font.BOLD, 64);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("GAME OVER", 300, 350);
            saveToFile(score);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    protected void loop() throws IOException {
        update();
        repaint();
    }

    public void update() throws IOException {
        List<Laser> copyLasers = new ArrayList<>();
        boolean shipIsHit = false;
        if(hearts.size() == 0) {
            gameIsOn = false;
        }
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
                if (collidesWithLaser(laser, alien)) {
                    laser.removeSprite();
                    alien.removeSprite();
                    score += 10;
                }
            }
        }
        for(Alien alien : aliens){
            if(collidesWithShip(ship, alien)){
                hearts.remove(0);
                alien.removeSprite();
                shipIsHit = true;
                break;
            }
        }
        if(shipIsHit){
            aliens.clear();
        }
        checkFleet();
        moveFleet();
        if(aliens.isEmpty()){
            createFleet();
            originalSpeed += 1;
            speed = originalSpeed;
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
                speed = speed * (-1);
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

    private static boolean collidesWithLaser(Sprite s, Alien alien){
        // 61 i 116 to wymiary obrazka obcego
        if(s.getBounds(20, 60).intersects(alien.getBounds( 61, 116))){
                return true;
        } else {
            return false;
        }
    }

    private static boolean collidesWithShip(Sprite s, Alien alien){
        if(s.getBounds(96, 128).intersects(alien.getBounds( 61, 116))){
            return true;
        } else {
            return false;
        }
    }

    private static void saveToFile(int score){
        try {
            File scores = new File("scores.txt");
            Writer fileStream = new FileWriter("scores.txt");
            if(scores.createNewFile()){
                BufferedReader readStream = new BufferedReader(new FileReader("scores.txt"));
                int oldScore = Integer.parseInt(readStream.readLine());
                if(score > oldScore) {
                    fileStream.write(Integer.toString(score));
                }
                readStream.close();
            } else {
                fileStream.write(Integer.toString(score));
            }
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
