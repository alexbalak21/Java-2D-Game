package main;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures GUI code runs on the Event Dispatch Thread (EDT)
        // This is required for thread safety in Swing applications
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
