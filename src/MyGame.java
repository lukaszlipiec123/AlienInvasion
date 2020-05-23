import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MyGame extends JFrame {

    public MyGame() throws IOException {
        init();
    }

    private void init() throws IOException {
        add(new GamePanel());
        this.setTitle("AlienInvasion");
        pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024,768);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }


//    GamePanel gamePanel;
//
//    public MyGame(){
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        initComponents();
//
//        this.setSize(1024,768);
//        this.setVisible(true);
//
//        (new Timer(0, (ActionEvent a) -> {
//            this.gamePanel.repaint();
//        })).start();
//    }
//
//    private void initComponents() {
//
//        this.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                int move = 100;
//                int keyCode = e.getKeyCode();
//                switch (keyCode) {
//                    case KeyEvent.VK_LEFT:
//                        gamePanel.addMoveX(-move);
//                        break;
//                    case KeyEvent.VK_RIGHT:
//                        gamePanel.addMoveX(move);
//                        break;
//                    case KeyEvent.VK_UP:
//                        gamePanel.addMoveY(-move);
//                        break;
//                    case KeyEvent.VK_DOWN:
//                        gamePanel.addMoveY(move);
//                        break;
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//
//            }
//        });
//
//        gamePanel = new GamePanel();
//        this.add(gamePanel, BorderLayout.CENTER);
//    }

}
