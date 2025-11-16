// src/game/GamePanel.java
// Main game panel where all game logic and rendering happens
// Extends JPanel which is a general-purpose container for Swing components

package game;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48

    final int gridWidth = 16;
    final int gridHeight = 12;

    final int screenWidth = tileSize * gridWidth; // 768
    final int screenHeight = tileSize * gridHeight; // 576

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;


    public GamePanel() {
       this.setPreferredSize(new Dimension(screenWidth, screenHeight));
       this.setBackground(Color.black);   
       this.setDoubleBuffered(true);
       this.addKeyListener(keyHandler);
       this.setFocusable(true);

       //NEED TO IMPLEMENT MOVEMENT
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
            
            
        }
    }

    public void update() {
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(100, 100, tileSize, tileSize);

        g2.dispose();
    }
}
