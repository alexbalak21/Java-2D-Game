// src/game/GameWindow.java
// Main game window class that extends JFrame
// JFrame is the top-level container for Swing applications

package game;

import javax.swing.JFrame;
import java.awt.Dimension;

// GameWindow class extends JFrame to create the main game window
public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("My 2D Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        gamePanel.requestFocusInWindow();

        gamePanel.startGameThread();
    }

    // Override the preferred size method
    // This method is called by pack() to determine the window size
    @Override
    public Dimension getPreferredSize() {

        return super.getPreferredSize();
    }
}
