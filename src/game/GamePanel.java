// src/game/GamePanel.java
package game;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private int x = 50, y = 50;
    private int vx = 0, vy = 0;
    private final int SPEED = 4;
    private final int SIZE = 40;

    public GamePanel() {
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));

        // Key bindings for arrow keys: press -> set velocity, release -> stop that axis
        bindKey("pressed LEFT",  true,  () -> vx = -SPEED);
        bindKey("released LEFT", false, () -> { if (vx < 0) vx = 0; });
        bindKey("pressed RIGHT", true,  () -> vx = SPEED);
        bindKey("released RIGHT", false, () -> { if (vx > 0) vx = 0; });
        bindKey("pressed UP",    true,  () -> vy = -SPEED);
        bindKey("released UP",   false, () -> { if (vy < 0) vy = 0; });
        bindKey("pressed DOWN",  true,  () -> vy = SPEED);
        bindKey("released DOWN", false, () -> { if (vy > 0) vy = 0; });

        // Start game loop when panel is realized
        addNotify();
    }

    private void bindKey(String strokeName, boolean whenPressed, Runnable action) {
        String key = strokeName.replace(' ', '_');
        KeyStroke ks = KeyStroke.getKeyStroke(strokeName);
        int condition = WHEN_IN_FOCUSED_WINDOW;
        getInputMap(condition).put(ks, key);
        getActionMap().put(key, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { action.run(); }
        });
    }

    // Start timer in addNotify to ensure component is displayable
    @Override
    public void addNotify() {
        super.addNotify();
        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    private void update() {
        x += vx;
        y += vy;
        // Clamp inside panel
        x = Math.max(0, Math.min(x, getWidth() - SIZE));
        y = Math.max(0, Math.min(y, getHeight() - SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, SIZE, SIZE);
    }
}
