package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GamePanel extends JPanel {

    private BufferedImage mapImage;
    private BufferedImage playerImage;

    // Player position in tiles
    private int playerTileX = 10;
    private int playerTileY = 5;

    private final int TILE_SIZE = 16;

    // Camera offset
    private int cameraX;
    private int cameraY;

    public GamePanel() {
        try {
            mapImage = ImageIO.read(getClass().getResource("/map.png"));
            playerImage = ImageIO.read(getClass().getResource("/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Keyboard input: move by tiles
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP -> playerTileY--;
                    case java.awt.event.KeyEvent.VK_DOWN -> playerTileY++;
                    case java.awt.event.KeyEvent.VK_LEFT -> playerTileX--;
                    case java.awt.event.KeyEvent.VK_RIGHT -> playerTileX++;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Convert tile coords to pixel coords
        int playerPixelX = playerTileX * TILE_SIZE;
        int playerPixelY = playerTileY * TILE_SIZE;

        // Center camera on player
        cameraX = playerPixelX - getWidth() / 2;
        cameraY = playerPixelY - getHeight() / 2;

        // Draw map with camera offset
        g.drawImage(mapImage, -cameraX, -cameraY, null);

        // Draw player with camera offset
        g.drawImage(playerImage, playerPixelX - cameraX, playerPixelY - cameraY, null);

        // Debug info
        g.setColor(Color.RED);
        g.drawString("Tile: (" + playerTileX + "," + playerTileY + ")", 10, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tile-based Camera Game");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        frame.add(panel);
        frame.setVisible(true);
    }
}
