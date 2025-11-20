package game;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * KeyHandler class manages keyboard input for the game.
 * Implements KeyListener to handle keyboard events and tracks the state of movement keys.
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    /**
     * Invoked when a key is typed (pressed and released).
     * Not used in this implementation but required by KeyListener interface.
     * @param e the KeyEvent containing information about the key typed
     */
    @Override
    public void keyTyped(KeyEvent e) {  
        // Not used in this implementation
    }

    /**
     * Handles key press events and updates the corresponding key state.
     * Sets the appropriate flag to true when a movement key is pressed.
     * @param e the KeyEvent containing information about the key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_UP) {    
            upPressed = true;
        }
        if(keyCode == KeyEvent.VK_DOWN) {    
            downPressed = true;
        }
        if(keyCode == KeyEvent.VK_LEFT) {    
            leftPressed = true;
        }
        if(keyCode == KeyEvent.VK_RIGHT) {    
            rightPressed = true;
        }
        
    }

    /**
     * Handles key release events and updates the corresponding key state.
     * Resets the appropriate flag when a movement key is released.
     * @param e the KeyEvent containing information about the key release
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_UP) {    
            upPressed = false;
        }
        if(keyCode == KeyEvent.VK_DOWN) {    
            downPressed = false;
        }
        if(keyCode == KeyEvent.VK_LEFT) {    
            leftPressed = false;
        }
        if(keyCode == KeyEvent.VK_RIGHT) {    
            rightPressed = false;
        }
    }

    
}