import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
            mapImage = ImageIO.read(getClass().getResource("/res/tile_map.png"));
            mapWidth = mapImage.getWidth();
            mapHeight = mapImage.getHeight();
            System.out.println("Map loaded: " + mapWidth + "x" + mapHeight);
        } catch (Exception e) {
            System.err.println("Error loading tile map: " + e.getMessage());
        }
    }
    
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (mapImage != null) {
            g2.drawImage(mapImage, -cameraX, -cameraY, null);
        }
    }
}