package main;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class MapManager {
    public BufferedImage mapImage;
    public int mapWidth, mapHeight;
    private GamePanel gp;

    public MapManager(GamePanel gp) {
        this.gp = gp;
        loadMap();
    }

    private void loadMap() {
        try {
            mapImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map.png")));
            // Store original dimensions
            mapWidth = mapImage.getWidth();
            mapHeight = mapImage.getHeight();
            System.out.println("Map loaded: " + mapWidth + "x" + mapHeight);
            
            // Update camera's world bounds to match the scaled map size
            if (gp != null && gp.getCamera() != null) {
                gp.getCamera().setWorldBounds(
                    mapWidth * GamePanel.SCALE, 
                    mapHeight * GamePanel.SCALE
                );
            }
        } catch (Exception e) {
            System.err.println("Error loading tile map: " + e.getMessage());
        }
    }

    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (mapImage != null) {
            // Draw the map with scaling applied
            g2.drawImage(mapImage, 
                -cameraX, 
                -cameraY, 
                mapWidth * GamePanel.SCALE, 
                mapHeight * GamePanel.SCALE, 
                null);
        }
    }
}