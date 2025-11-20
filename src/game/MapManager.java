package game;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Manages the game map, including loading and rendering the map image.
 * Handles different loading strategies for the map image and provides
 * methods to draw the map with camera support.
 */
public class MapManager {
    
    // Reference to the main game panel
    GamePanel gp;
    
    // The map image that will be drawn
    public BufferedImage mapImage;
    
    // Dimensions of the map in pixels
    public int mapWidth, mapHeight;
    
    /**
     * Constructs a new MapManager and loads the map image.
     * 
     * @param gp The GamePanel instance this map manager belongs to
     */
    public MapManager(GamePanel gp) {
        this.gp = gp;
        loadMap(); // Load the map when the manager is created
    }
    
    /**
     * Loads the map image from the resources or file system.
     * Tries multiple locations to find the map image:
     * 1. /game/res/map.png (resource path)
     * 2. /res/map.png (alternative resource path)
     * 3. Assets/map.png (file system)
     */
    public void loadMap() {
        try {
            // First try loading from the classpath (JAR resources)
            java.net.URL imgUrl = getClass().getResource("/game/res/map.png");
            if (imgUrl == null) {
                // Try alternative resource path (for different project structures)
                imgUrl = getClass().getResource("/res/map.png");
            }
            
            // If not found in resources, try loading from file system
            if (imgUrl == null) {
                System.out.println("Map not found in resources, trying file system...");
                mapImage = ImageIO.read(new java.io.File("Assets/map.png"));
            } else {
                mapImage = ImageIO.read(imgUrl);
            }
            
            // If map was successfully loaded, store its dimensions
            if (mapImage != null) {
                mapWidth = mapImage.getWidth();
                mapHeight = mapImage.getHeight();
                System.out.println("Map loaded successfully: " + mapWidth + "x" + mapHeight);
            } else {
                System.err.println("Failed to load map.png - File not found or invalid image");
            }
            
        } catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Draws the map on the screen with camera offset.
     * The camera position determines which part of the map is visible.
     * 
     * @param g2 The Graphics2D context to draw on
     * @param cameraX The x-coordinate of the camera in world space
     * @param cameraY The y-coordinate of the camera in world space
     */
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (mapImage != null) {
            // Draw the map with negative camera offset to create scrolling effect
            // The camera position represents the top-left corner of the visible area
            g2.drawImage(mapImage, -cameraX, -cameraY, null);
        }
    }
}
