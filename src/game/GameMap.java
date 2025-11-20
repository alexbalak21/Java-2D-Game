package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameMap {
    private BufferedImage mapImage;

    public GameMap() {
        try {
            mapImage = ImageIO.read(getClass().getResource("/map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Camera camera) {
        g.drawImage(mapImage, -camera.getX(), -camera.getY(), null);
    }
}
