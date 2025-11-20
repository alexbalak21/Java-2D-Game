package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entity.Player;

/**
 * GamePanel is the main game container that handles the game loop,
 * rendering, and input handling. It extends JPanel and implements Runnable
 * to run the game in a separate thread.
 */


public class GamePanel extends JPanel implements Runnable {
    // Original size of tiles in pixels (before scaling)
    public final int originalTileSize = 16;
    // Scale factor for all game elements
    public static final int SCALE = 3;

    // Actual size of tiles after scaling
    public final int tileSize = originalTileSize * SCALE; // 48x48 pixels

    // Grid dimensions in number of tiles
    final int gridWidth = 16;   // Number of tiles horizontally
    final int gridHeight = 12;  // Number of tiles vertically

    // Screen dimensions in pixels
    final int screenWidth = tileSize * gridWidth;   // 768 pixels
    final int screenHeight = tileSize * gridHeight; // 576 pixels

    // Input handler for keyboard controls
    KeyHandler keyHandler = new KeyHandler();
    // Game thread for running the game loop
    Thread gameThread;

    // Game entities
    Player player = new Player(this, keyHandler);
    MapManager mapManager = new MapManager(this);

    // Camera that follows the player
    private final Camera camera;

    //https://youtu.be/wT9uNGzMEM4?si=Um1deZEkYPkZAq9I&t=188


    /**
     * Constructs a new GamePanel and initializes its properties.
     * Sets up the panel size, background, and input handling.
     */
    public GamePanel() {
        // Set the preferred size of the game window
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        // Set black background
        this.setBackground(Color.black);
        // Enable double buffering for smoother rendering
        this.setDoubleBuffered(true);
        // Add key listener for player input
        this.addKeyListener(keyHandler);
        // Allow the panel to receive key events
        this.setFocusable(true);

        // Initialize camera
        camera = new Camera(screenWidth, screenHeight, 2000, 2000); // Adjust world size as needed
    }

    /**
     * Sets the camera position to center on the specified world coordinates.
     * Ensures the camera stays within the map boundaries.
     *
     * @param worldX The x-coordinate in world space to center the camera on
     * @param worldY The y-coordinate in world space to center the camera on
     */
    public void setCameraPosition(int worldX, int worldY) {
        // Calculate camera position to center on the target coordinates
        camera.setX(worldX - screenWidth / 2);
        camera.setY(worldY - screenHeight / 2);

        // Ensure camera stays within map bounds
        if (mapManager.mapImage != null) {
            // Prevent camera from going above or to the left of the map
            if (camera.getX() < 0) camera.setX(0);
            if (camera.getY() < 0) camera.setY(0);

            // Prevent camera from going below or to the right of the map
            if (camera.getX() > mapManager.mapWidth - screenWidth) {
                camera.setX(mapManager.mapWidth - screenWidth);
            }
            if (camera.getY() > mapManager.mapHeight - screenHeight) {
                camera.setY(mapManager.mapHeight - screenHeight);
            }
        }

        // Update player's position to match the camera's target
        player.x = worldX;
        player.y = worldY;
    }

    /**
     * Starts the game thread which runs the main game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();  // This will call the run() method in a new thread
    }

    /**
     * The main game loop running in a separate thread.
     * Handles game updates and rendering at a fixed frame rate.
     */
    @Override
    public void run() {
        // Target time per frame in nanoseconds (60 FPS)
        final double drawInterval = 1000000000/60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // Update game state at fixed intervals
            if (delta >= 1) {
                update();  // Update game logic
                repaint(); // Request a repaint
                delta--;
                drawCount++;
            }

            // Optional: Display FPS in console (uncomment to enable)
            if (timer >= 1000000000) {
                // System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Updates the game state for each frame.
     * Called in the game loop to update all game objects.
     */
    public void update() {
        // Update the player's position and state
        player.update();
        // Camera follows player (handled in player.update())
    }

    /**
     * Gets the camera instance.
     * @return The camera object
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Draws the game world and all game objects.
     * This method is called automatically by Swing's painting system.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw the game map with camera offset
        if (mapManager != null) {
            mapManager.draw(g2, camera.getX(), camera.getY());
        }

        // Draw the player (handles its own camera offset)
        if (player != null) {
            player.draw(g2);
        }

        g2.dispose();
    }
}