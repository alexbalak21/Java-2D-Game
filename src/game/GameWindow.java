// src/game/GameWindow.java
// Main game window class that extends JFrame
// JFrame is the top-level container for Swing applications

package game;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GameWindow extends JFrame {
    public GameWindow() {
        // Set the title that appears in the window's title bar
        setTitle("My 2D Game");
        
        // Make the application exit when this window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Prevent the user from resizing the window (keeps game dimensions consistent)
        setResizable(false);

        // Create the game panel where all the game rendering happens
        GamePanel panel = new GamePanel();
        // Add the panel to the window's content area
        add(panel);

        // Automatically size the window to fit the preferred size of its contents
        // This uses the GamePanel's preferred size (800x600)
        pack();
        
        // Center the window on the screen
        setLocationRelativeTo(null);
        
        // Make the window visible
        setVisible(true);

        // Request focus for the game panel so it can receive keyboard input
        panel.requestFocusInWindow();
    }

    // Override the preferred size method
    // This method is called by pack() to determine the window size
    @Override
    public Dimension getPreferredSize() {
        // Return the parent class's preferred size
        // This is a pass-through - the actual size comes from the GamePanel
        return super.getPreferredSize();
    }
}
