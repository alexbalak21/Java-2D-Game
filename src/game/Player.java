package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player {
    private int tileX;
    private int tileY;
    private final int TILE_SIZE = 16;
    private final int SCALE = 3; // scale factor
    private BufferedImage sprite;

    public Player(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int dx, int dy) {
        tileX += dx;
        tileY += dy;
    }

    public void draw(Graphics g, Camera camera) {
        int pixelX = tileX * Constants.TILE_SIZE * Constants.SCALE - camera.getX();
        int pixelY = tileY * Constants.TILE_SIZE * Constants.SCALE - camera.getY();

        g.drawImage(sprite, pixelX, pixelY,
                sprite.getWidth() * Constants.SCALE,
                sprite.getHeight() * Constants.SCALE,
                null);
    }

    public int getTileX() { return tileX; }
    public int getTileY() { return tileY; }
}
