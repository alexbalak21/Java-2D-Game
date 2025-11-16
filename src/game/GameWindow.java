// src/game/GameWindow.java
package game;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("My 2D Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);

        pack();                      // sizes the frame to the panel’s preferred size
        setLocationRelativeTo(null); // center on screen
        setVisible(true);

        panel.requestFocusInWindow(); // ready for input later
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
