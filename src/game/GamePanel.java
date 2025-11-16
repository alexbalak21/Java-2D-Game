// src/game/GamePanel.java
// Main game panel where all game logic and rendering happens
// Extends JPanel which is a general-purpose container for Swing components

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
    // Player position coordinates (top-left corner of the player square)
    private int x = 50, y = 50;
    
    // Player velocity (pixels per frame) - positive = right/down, negative = left/up
    private int vx = 0, vy = 0;
    
    // Game constants
    private final int SPEED = 4;        // How fast the player moves
    private final int SIZE = 40;        // Size of the player square (40x40 pixels)

    public GamePanel() {
        // Set the background color to black
        setBackground(Color.BLACK);
        
        // Enable double buffering for smoother graphics (reduces flickering)
        setDoubleBuffered(true);
        
        // Allow this panel to receive keyboard focus (needed for input)
        setFocusable(true);
        
        // Set the preferred size that the window will use when packing
        setPreferredSize(new Dimension(800, 600));

        // Set up keyboard controls for arrow keys
        // When key is pressed: set velocity in that direction
        // When key is released: stop movement in that direction (only if moving that way)
        bindKey("pressed LEFT",  true,  () -> vx = -SPEED);   // Move left
        bindKey("released LEFT", false, () -> { if (vx < 0) vx = 0; });  // Stop left movement
        bindKey("pressed RIGHT", true,  () -> vx = SPEED);    // Move right
        bindKey("released RIGHT", false, () -> { if (vx > 0) vx = 0; });  // Stop right movement
        bindKey("pressed UP",    true,  () -> vy = -SPEED);   // Move up
        bindKey("released UP",   false, () -> { if (vy < 0) vy = 0; });  // Stop up movement
        bindKey("pressed DOWN",  true,  () -> vy = SPEED);    // Move down
        bindKey("released DOWN", false, () -> { if (vy > 0) vy = 0; });  // Stop down movement

        // Start the game loop when the panel is added to a window
        // addNotify() is called when the component becomes displayable
        addNotify();
    }

    // Helper method to bind keyboard actions to keys
    private void bindKey(String strokeName, boolean whenPressed, Runnable action) {
        // Create a unique key name by replacing spaces with underscores
        String key = strokeName.replace(' ', '_');
        
        // Convert the string description to a KeyStroke object
        KeyStroke ks = KeyStroke.getKeyStroke(strokeName);
        
        // Set when this key binding is active (when window has focus)
        int condition = WHEN_IN_FOCUSED_WINDOW;
        
        // Map the key stroke to our unique key name in the input map
        getInputMap(condition).put(ks, key);
        
        // Map the key name to an action that runs our code
        getActionMap().put(key, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { action.run(); }
        });
    }

    // Override addNotify to start the game loop
    // addNotify() is called when the component is added to a container and becomes displayable
    @Override
    public void addNotify() {
        // Call the parent implementation first
        super.addNotify();
        
        // Create a timer for the game loop
        // 16ms delay = approximately 60 FPS (1000ms / 16ms ≈ 62.5 FPS)
        Timer timer = new Timer(16, e -> {
            update();     // Update game state (player position)
            repaint();    // Redraw the screen
        });
        
        // Start the game loop timer
        timer.start();
    }

    // Update game logic (called every frame)
    private void update() {
        // Update player position based on current velocity
        x += vx;  // Move horizontally
        y += vy;  // Move vertically
        
        // Keep player inside the panel boundaries (collision detection with walls)
        // Math.max ensures position is at least 0 (left/top edge)
        // Math.min ensures position doesn't exceed panel width/height minus player size
        x = Math.max(0, Math.min(x, getWidth() - SIZE));
        y = Math.max(0, Math.min(y, getHeight() - SIZE));
    }

    // Override paintComponent to render the game
    // This method is called automatically when repaint() is called
    @Override
    protected void paintComponent(Graphics g) {
        // Call parent implementation to clear the panel with background color
        super.paintComponent(g);
        
        // Set the drawing color to red for the player
        g.setColor(Color.RED);
        
        // Draw the player as a filled rectangle at position (x, y) with size (SIZE x SIZE)
        g.fillRect(x, y, SIZE, SIZE);
    }
}
