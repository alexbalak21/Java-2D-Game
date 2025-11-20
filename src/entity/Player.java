package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import GamePanel;
import KeyHandler;

/**
 * Player class represents the main player character in the game.
 * Handles player movement, animation, and rendering.
 * Extends the base Entity class for common entity functionality.
 */

public class Player extends Entity {
    // Reference to the main game panel for accessing game state and settings
    GamePanel gp;
    // Reference to the key handler for processing player input
    KeyHandler keyH;

    // Player scale factor (1 = normal size, 2 = double size, etc.)
    public int scale = 1;

    // Movement tracking variables
    private boolean isMoving = false;  // Flag to check if player is currently moving
    private int pixelsMoved = 0;       // Tracks how many pixels moved in current movement

    /**
     * Loads player image.
     * If loading fails, creates blank placeholder image to prevent crashes.
     */
    public void getPlayerImage() {
        try {
            // Try to load the player image
            java.net.URL imgUrl = getClass().getResource("/res/player/player.png");
            if (imgUrl == null) {
                throw new IOException("Could not find player.png. Tried:" +
                    "\n- /game/res/player/player.png" +
                    "\n- /res/player/player.png");
            }
            
            // Load the single player image
            BufferedImage image = ImageIO.read(imgUrl);
            System.out.println("Successfully loaded player image!");
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading player image: " + e.getMessage());
            // Create a blank placeholder image
            image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        }
    }

    /**
     * Player constructor.
     * @param gp Reference to the main GamePanel
     * @param keyH Reference to the KeyHandler for input processing
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();     // Initialize player's starting position and properties
        getPlayerImage();       // Load player sprites
    }

    /**
     * Sets the default values for the player character.
     * Called when the game starts or when player respawns.
     */
    void setDefaultValues() {
        x = 384;                // Starting X position (center of a 768x576 window)
        y = 288;                // Starting Y position
        speed = 2;              // Movement speed in pixels per frame
        direction = "down";     // Initial facing direction
        scale = 3;              // Scale factor for player size (3x original size)
    }

    /**
     * Updates player state each frame.
     * Handles movement input, animation, and position updates.
     */
    public void update() {
        // If not already moving, check for new input
        if (!isMoving) {
            if (keyH.upPressed) {
                direction = "up";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1; // show first step frame immediately
            } else if (keyH.downPressed) {
                direction = "down";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1;
            } else if (keyH.leftPressed) {
                direction = "left";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1;
            } else if (keyH.rightPressed) {
                direction = "right";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1;
            }
        }

        // If currently moving, advance position
        if (isMoving) {
            switch (direction) {
                case "up":    y -= speed; break;
                case "down":  y += speed; break;
                case "left":  x -= speed; break;
                case "right": x += speed; break;
            }

            // Print position every movement step
            System.out.println("World Position - X: " + x + ", Y: " + y);

            pixelsMoved += speed;

            // Animate while moving
            spriteCounter++;
            if (spriteCounter > 8) { // adjust for animation speed
                spriteNum = (spriteNum + 1) % 4;
                spriteCounter = 0;
            }

            // Stop when one tile is completed
            if (pixelsMoved >= gp.tileSize) {
                isMoving = false;
                spriteNum = 0; // back to idle frame
                spriteCounter = 0;
            }
        }
    }


    /**
     * Draws the player character on screen with camera offset.
     * @param g2 Graphics2D context for drawing
     * @param cameraX Camera's X position for viewport calculation
     * @param cameraY Camera's Y position for viewport calculation
     */
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (image == null) {
            return; // Don't draw if image failed to load
        }
        
        // Calculate scaled size using original tile size
        int scaledSize = gp.originalTileSize * scale;
        
        // Calculate position to keep player centered when scaled
        int offset = (scaledSize - gp.originalTileSize) / 2;
        int screenX = x - cameraX - offset;
        int screenY = y - cameraY - offset;
        
        // Draw player with camera offset and custom scale
        g2.drawImage(image, screenX, screenY, scaledSize, scaledSize, null);
    }
    
    /**
     * Overloaded draw method for backward compatibility.
     * Draws the player at screen coordinates (0,0) without camera offset.
     * @param g2 Graphics2D context for drawing
     */
    public void draw(Graphics2D g2) {
        draw(g2, 0, 0);
    }
}