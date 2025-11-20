package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

/**
 * Player class with camera-relative drawing and smooth movement.
 */
public class Player {
    // Game references
    private final GamePanel gp;
    private final KeyHandler keyH;
    
    // Position and movement
    public int x, y;  // World position (in pixels)
    public int screenX, screenY;  // Screen position (for drawing)
    private final int originalTileSize = 16; // Original size of each tile in pixels
    private int tileSize; // Scaled tile size (original * scale)
    private long lastMoveTime = 0;
    private final long moveDelay = 200; // Delay between moves in milliseconds
    private BufferedImage image;
    
    // Player dimensions (in original pixels)
    public final int originalWidth = 16;
    public final int originalHeight = 16;
    // Scaled dimensions
    public int width;
    public int height;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        // Calculate scaled dimensions
        this.tileSize = originalTileSize * GamePanel.SCALE;
        this.width = originalWidth * GamePanel.SCALE;
        this.height = originalHeight * GamePanel.SCALE;
        loadPlayerImage();
        setDefaultPosition();
    }

    private void loadPlayerImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player.png")));
            if (image == null) {
                throw new IOException("Player image not found");
            }
        } catch (IOException e) {
            System.err.println("Error loading player image: " + e.getMessage());
            // Create a placeholder
            // Create placeholder with original size, will be scaled when drawn
            image = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            g2.setColor(java.awt.Color.RED);
            g2.fillRect(0, 0, width, height);
            g2.dispose();
        }
    }

    private void setDefaultPosition() {
        // Start at a position that's aligned with the grid
        // 480 / 16 = 30 tiles, then multiply by scaled tile size
        int startX = (48 / originalTileSize) * tileSize;
        int startY = (48 / originalTileSize) * tileSize;
        x = startX;
        y = startY;
        updateScreenPosition();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        
        // Only process movement if enough time has passed since last move
        if (currentTime - lastMoveTime >= moveDelay) {
            int newX = x;
            int newY = y;
            
            // Calculate target position based on input (one tile at a time)
            if (keyH.upPressed) newY -= tileSize;
            else if (keyH.downPressed) newY += tileSize;
            else if (keyH.leftPressed) newX -= tileSize;
            else if (keyH.rightPressed) newX += tileSize;
            
            // If we have valid movement, snap to the new position
            if (newX != x || newY != y) {
                x = newX;
                y = newY;
                lastMoveTime = currentTime; // Update last move time
            }
        }
        
        // Update screen position for drawing
        updateScreenPosition();
        
        // Update camera to follow player
        if (gp != null && gp.getCamera() != null) {
            gp.getCamera().centerOnPlayer(this);
        }
    }
    
    private void updateScreenPosition() {
        if (gp != null && gp.getCamera() != null) {
            // Convert world coordinates to screen coordinates
            screenX = x - gp.getCamera().getX();
            screenY = y - gp.getCamera().getY();
        } else {
            // Fallback if no camera is available
            screenX = x;
            screenY = y;
        }
    }

    public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }
    
    // Getters for camera calculations
    public int getCenterX() {
        return x + width / 2;
    }
    
    public int getCenterY() {
        return y + height / 2;
    }
    
    // Helper method to get the scaled tile size
    public int getTileSize() {
        return tileSize;
    }
}