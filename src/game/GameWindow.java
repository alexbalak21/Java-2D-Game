// src/game/GameWindow.java
package game;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("My 2D Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        panel.requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }
}
