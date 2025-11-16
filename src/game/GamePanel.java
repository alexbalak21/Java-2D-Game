// src/game/GamePanel.java
package game;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

class GamePanel extends JPanel {
    private int x = 50, y = 50;

    public GamePanel() {
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public void addNotify() {
        super.addNotify();
        // Start the game loop once the panel is realized
        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    private void update() {
        x += 1; // simple movement to the right
        // optional clamp so it doesn’t leave the screen
        x = Math.min(Math.max(x, 0), getWidth() - 40);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, 40, 40);
    }
}
