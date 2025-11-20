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
    MapManager mapManager = new MapManager(this);
    
    // Camera system
    public int cameraX = 0;
    public int cameraY = 0;

    //https://youtu.be/wT9uNGzMEM4?si=Um1deZEkYPkZAq9I&t=188


    public GamePanel() {
       this.setPreferredSize(new Dimension(screenWidth, screenHeight));
       this.setBackground(Color.black);   
       this.setDoubleBuffered(true);
       this.addKeyListener(keyHandler);
       this.setFocusable(true);
       
       // Set initial camera position (map position where player starts)
       setCameraPosition(616, 454); // Change these values to move the map
    }
    
    // Method to set camera position (where the map should be positioned)
    public void setCameraPosition(int worldX, int worldY) {
        // Position camera so player appears at worldX, worldY on the map
        cameraX = worldX - screenWidth / 2;
        cameraY = worldY - screenHeight / 2;
        
        // Ensure camera stays within map bounds
        if (mapManager.mapImage != null) {
            if (cameraX < 0) cameraX = 0;
            if (cameraY < 0) cameraY = 0;
            if (cameraX > mapManager.mapWidth - screenWidth) {
                cameraX = mapManager.mapWidth - screenWidth;
            }
            if (cameraY > mapManager.mapHeight - screenHeight) {
                cameraY = mapManager.mapHeight - screenHeight;
            }
        }
        
        // Update player's world position to match
        player.x = worldX;
        player.y = worldY;
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
        updateCamera();
    }
    
    public void updateCamera() {
        // Center camera on player
        cameraX = player.x - screenWidth / 2;
        cameraY = player.y - screenHeight / 2;
        
        // Prevent camera from going outside map bounds
        if (mapManager.mapImage != null) {
            if (cameraX < 0) cameraX = 0;
            if (cameraY < 0) cameraY = 0;
            if (cameraX > mapManager.mapWidth - screenWidth) {
                cameraX = mapManager.mapWidth - screenWidth;
            }
            if (cameraY > mapManager.mapHeight - screenHeight) {
                cameraY = mapManager.mapHeight - screenHeight;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        // Draw the map first (background)
        mapManager.draw(g2, cameraX, cameraY);
        
        // Draw the player with camera offset
        player.draw(g2, cameraX, cameraY);
        
        g2.dispose();
    }
}
