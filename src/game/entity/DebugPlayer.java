package game.entity;

import game.GamePanel;
import game.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * DebugPlayer is a simplified version of the Player class for testing purposes.
 * It loads a single 16x16 player image and handles basic rendering.
 */
public class DebugPlayer extends Entity {
    // Reference to the main game panel for accessing game state and settings
    GamePanel gp;
    // Reference to the key handler for processing player input
    KeyHandler keyH;
    
    private BufferedImage playerImage;
    
    /**
     * Creates a new DebugPlayer instance.
     * 
     * @param gp The GamePanel instance this player belongs to
     * @param keyH The KeyHandler for processing player input
     */
    public DebugPlayer(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImage();
        setDefaultValues();
    }
    
    /**
     * Loads the player image from resources.
     * Tries multiple locations to find the image.
     */
    public void getPlayerImage() {
        try {
            // Try different possible locations for the player image
            String[] paths = {
                "/game/res/player.png",  // Standard location in resources
                "/res/player.png",        // Alternative resource path
                "player.png"              // Direct file path
            };
            
            for (String path : paths) {
                try {
                    java.net.URL imgUrl = getClass().getResource(path);
                    if (imgUrl != null) {
                        playerImage = ImageIO.read(imgUrl);
                        System.out.println("Debug player image loaded successfully from: " + path);
                        return;
                    }
                } catch (Exception e) {
                    System.err.println("Error loading player image from " + path + ": " + e.getMessage());
                }
            }
            
            // If we get here, no image was loaded successfully
            throw new IOException("Could not load player image from any location");
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading debug player image: " + e.getMessage());
            // Create a placeholder image if loading fails
            playerImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
            playerImage.getGraphics().fillRect(0, 0, 16, 16); // Fill with black
        }
    }
    
    /**
     * Sets the default values for the debug player.
     */
    public void setDefaultValues() {
        x = 100;  // Starting X position
        y = 100;  // Starting Y position
        speed = 3; // Movement speed
        direction = "down"; // Default direction
    }
    
    /**
     * Updates the player's state each frame.
     */
    public void update() {
        // Store old position for comparison
        int oldX = x;
        int oldY = y;
        
        // Debug print key states
        System.out.println(String.format("Keys - Up: %b, Down: %b, Left: %b, Right: %b", 
            keyH.upPressed, keyH.downPressed, keyH.leftPressed, keyH.rightPressed));
            
        // Simple movement logic (you can expand this as needed)
        if (keyH.upPressed) y -= speed;
        if (keyH.downPressed) y += speed;
        if (keyH.leftPressed) x -= speed;
        if (keyH.rightPressed) x += speed;
        
        // Check if position changed
        if (x != oldX || y != oldY) {
            System.out.println(String.format("Player moved from (%d, %d) to (%d, %d)", 
                oldX, oldY, x, y));
        } else {
            System.out.println("Player position unchanged");
        }
    }
    
    /**
     * Draws the player on screen.
     * 
     * @param g2 The Graphics2D context to draw on
     * @param cameraX The camera's X position
     * @param cameraY The camera's Y position
     */
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (playerImage != null) {
            int screenX = x - cameraX;
            int screenY = y - cameraY;
            
            // Draw the player image with scaling
            int scaledSize = 16 * 3; // 3x scale (16x16 -> 48x48)
            g2.drawImage(playerImage, screenX, screenY, scaledSize, scaledSize, null);
        }
    }
}