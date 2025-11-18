// src/game/GamePanel.java
// Main game panel where all game logic and rendering happens
// Extends JPanel which is a general-purpose container for Swing components

package game;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import game.entity.Player;


public class GamePanel extends JPanel implements Runnable {
    public final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48

    final int gridWidth = 16;
    final int gridHeight = 12;

    final int screenWidth = tileSize * gridWidth; // 768
    final int screenHeight = tileSize * gridHeight; // 576

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    Player player = new Player(this, keyHandler);

    //https://youtu.be/wT9uNGzMEM4?si=Um1deZEkYPkZAq9I&t=188


    public GamePanel() {
       this.setPreferredSize(new Dimension(screenWidth, screenHeight));
       this.setBackground(Color.black);   
       this.setDoubleBuffered(true);
       this.addKeyListener(keyHandler);
       this.setFocusable(true);
    }





    //Start the game
    public void startGameThread() {
        gameThread = new Thread(this);
        //Start the thread
        gameThread.start();
    }

    //GameLoop
    @Override
    public void run() {
        while (gameThread != null) {
            //Update the game logic & entities
            update();

            //Call to paintComponent method
            repaint();
            

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();  
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
