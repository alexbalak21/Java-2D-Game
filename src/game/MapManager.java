package game;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapManager {
    
    GamePanel gp;
    public BufferedImage mapImage;
    public int mapWidth, mapHeight;
    
    public MapManager(GamePanel gp) {
        this.gp = gp;
        loadMap();
    }
    
    public void loadMap() {
        try {
            // Try to load map.png from Assets folder
            java.net.URL imgUrl = getClass().getResource("/game/res/map.png");
            if (imgUrl == null) {
                // Try alternative path
                imgUrl = getClass().getResource("/res/map.png");
            }
            
            if (imgUrl == null) {
                // Try loading from file system directly
                mapImage = ImageIO.read(new java.io.File("Assets/map.png"));
            } else {
                mapImage = ImageIO.read(imgUrl);
            }
            
            if (mapImage != null) {
                mapWidth = mapImage.getWidth();
                mapHeight = mapImage.getHeight();
                System.out.println("Map loaded successfully: " + mapWidth + "x" + mapHeight);
            } else {
                System.out.println("Failed to load map.png");
            }
            
        } catch (IOException e) {
            System.out.println("Error loading map: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (mapImage != null) {
            // Draw the map with camera offset
            g2.drawImage(mapImage, -cameraX, -cameraY, null);
        }
    }
}
