package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameMap {
    private BufferedImage mapImage;
    private final int SCALE = 3;

    public GameMap() {
        try {
            mapImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/map.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Camera camera) {
        g.drawImage(mapImage,
                -camera.getX(),
                -camera.getY(),
                mapImage.getWidth() * SCALE,
                mapImage.getHeight() * SCALE,
                null);
    }
}
