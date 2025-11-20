package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private final Player player;
    private final Camera camera;
    private final GameMap map;

    public GamePanel() {
        player = new Player(10, 5); // start at tile (10,5)
        camera = new Camera();
        map = new GameMap();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> player.move(0, -1);
                    case KeyEvent.VK_DOWN -> player.move(0, 1);
                    case KeyEvent.VK_LEFT -> player.move(-1, 0);
                    case KeyEvent.VK_RIGHT -> player.move(1, 0);
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Update camera to center on player
        camera.centerOn(player, getWidth(), getHeight());

        // Draw map
        map.draw(g, camera);

        // Draw player
        player.draw(g, camera);

        // Debug info
        g.setColor(Color.RED);
        g.drawString("Tile: (" + player.getTileX() + "," + player.getTileY() + ")", 10, 20);
    }
}
