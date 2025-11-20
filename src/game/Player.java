package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    private int tileX;
    private int tileY;
    private final int TILE_SIZE = 16;
    private BufferedImage sprite;

    public Player(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        try {
            sprite = ImageIO.read(getClass().getResource("/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int dx, int dy) {
        tileX += dx;
        tileY += dy;
    }

    public void draw(Graphics g, Camera camera) {
        int pixelX = tileX * TILE_SIZE - camera.getX();
        int pixelY = tileY * TILE_SIZE - camera.getY();
        g.drawImage(sprite, pixelX, pixelY, null);
    }

    public int getTileX() { return tileX; }
    public int getTileY() { return tileY; }
}
