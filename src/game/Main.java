// src/game/Main.java
// Entry point of the game application
// This is where the program starts execution

package game;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures GUI code runs on the Event Dispatch Thread (EDT)
        // This is required for thread safety in Swing applications
        SwingUtilities.invokeLater(() -> {
            // Create and show the game window
            new GameWindow();
        });
    }
}
