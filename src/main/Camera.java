package main;

import entity.Player;

/**
 * Camera class that follows the player and manages the viewport.
 * Handles the game's viewport positioning and smooth following of the player.
 */
public class Camera {
    // Camera position (top-left corner)
    private int x, y;
    
    // Camera dimensions (matches screen size)
    private final int width, height;
    
    // World boundaries
    private int worldWidth, worldHeight;
    
    // Screen dimensions
    private final int screenWidth, screenHeight;
    
    /**
     * Controls the smoothness of camera movement.
     * Lower values = smoother, more gradual movement
     * Higher values = snappier, more immediate movement
     * A value of 1.0f means instant movement (no smoothing)
     */
    private final float lerpFactor = 0.3f; // Increased for more responsive movement
    
    /**
     * Creates a new Camera instance.
     * 
     * @param screenWidth Width of the game screen in pixels
     * @param screenHeight Height of the game screen in pixels
     * @param worldWidth Total width of the game world in pixels
     * @param worldHeight Total height of the game world in pixels
     */
    public Camera(int screenWidth, int screenHeight, int worldWidth, int worldHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.width = screenWidth;
        this.height = screenHeight;
    }
    
    /**
     * Updates the camera's position to follow the player smoothly.
     * The camera will keep the player centered while staying within world bounds.
     * 
     * @param player The Player instance to follow
     */
    public void centerOnPlayer(Player player) {
        if (player == null) return;
        
        // Calculate target position (centered on player)
        int targetX = player.getCenterX() - screenWidth / 2;
        int targetY = player.getCenterY() - screenHeight / 2;
        
        // Apply smoothing using linear interpolation
        x += (targetX - x) * lerpFactor;
        y += (targetY - y) * lerpFactor;
        
        // Keep camera within world bounds to prevent showing areas outside the world
        x = Math.max(0, Math.min(x, worldWidth - screenWidth));
        y = Math.max(0, Math.min(y, worldHeight - screenHeight));
    }
    
    // Getters for camera position
    public int getX() { return x; }
    public int getY() { return y; }
    
    // Setters for direct camera position control
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    /**
     * Updates the world boundaries for the camera.
     * 
     * @param width New world width in pixels
     * @param height New world height in pixels
     */
    public void setWorldBounds(int width, int height) {
        this.worldWidth = width;
        this.worldHeight = height;
    }
}
