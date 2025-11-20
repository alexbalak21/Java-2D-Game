package game;

import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * GameWindow is the main application window that extends JFrame.
 * It serves as the top-level container for the game and manages
 * the game panel and window properties.
 */
public class GameWindow extends JFrame {
    
    /**
     * Constructs the main game window and initializes the game panel.
     * Sets up the window properties and starts the game loop.
     */
    public GameWindow() {
        // Set window title
        setTitle("My 2D Game");
        // Ensure the application exits when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Prevent window resizing to maintain consistent game dimensions
        setResizable(false);

        // Create and add the main game panel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        // Pack the window to fit the preferred size of its components
        pack();
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Make the window visible
        setVisible(true);
        // Ensure the game panel has focus to receive keyboard input
        gamePanel.requestFocusInWindow();

        // Start the game loop in a separate thread
        gamePanel.startGameThread();
    }

    /**
     * Returns the preferred size of the window.
     * This method is called by pack() to determine the window size.
     * 
     * @return Dimension representing the preferred size of the window
     */
    @Override
    public Dimension getPreferredSize() {
        // Delegate to the parent class's implementation
        // This ensures proper window sizing based on the GamePanel's preferred size
        return super.getPreferredSize();
    }
    
    public static void main(String[] args) {
        new GameWindow();
    }
}