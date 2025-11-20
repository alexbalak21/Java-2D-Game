package game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.GamePanel;
import game.KeyHandler;


public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    // Track movement progress
    private boolean isMoving = false;
    private int pixelsMoved = 0;

        public void getPlayerImage() {
        try {
            // Try to load the sprite sheet using different methods
            java.net.URL imgUrl = getClass().getResource("/game/res/player/player_sheet.png");
            if (imgUrl == null) {
                // If not found, try alternative path
                imgUrl = getClass().getResource("/res/player/player_sheet.png");
            }
            
            if (imgUrl == null) {
                throw new IOException("Could not find player_sheet.png. Tried:" +
                    "\n- /game/res/player/player_sheet.png" +
                    "\n- /res/player/player_sheet.png");
            }
            
            BufferedImage spriteSheet = ImageIO.read(imgUrl);

            final int columns = 4;
            
            // Use original tile size (16x16 pixels)
            int frameSize = gp.originalTileSize; // Original tile size
            
            // Initialize arrays if they're null
            if (up == null) up = new BufferedImage[columns];
            if (down == null) down = new BufferedImage[columns];
            if (left == null) left = new BufferedImage[columns];
            if (right == null) right = new BufferedImage[columns];
            
            // Extract up frames (first row)
            for (int i = 0; i < columns; i++) {
                up[i] = spriteSheet.getSubimage(i * frameSize, 0, frameSize, frameSize);
            }
            
            // Extract down frames (second row)
            for (int i = 0; i < columns; i++) {
                down[i] = spriteSheet.getSubimage(i * frameSize, frameSize, frameSize, frameSize);
            }
            
            // Extract left frames (third row)
            for (int i = 0; i < columns; i++) {
                left[i] = spriteSheet.getSubimage(i * frameSize, frameSize * 2, frameSize, frameSize);
            }
            
            // Extract right frames (fourth row)
            for (int i = 0; i < columns; i++) {
                right[i] = spriteSheet.getSubimage(i * frameSize, frameSize * 3, frameSize, frameSize);
            }
            
            System.out.println("Successfully loaded player sprites!");
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading player sprites: " + e.getMessage());
            // Initialize empty images to prevent NullPointerException
            BufferedImage emptyImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            up = new BufferedImage[]{emptyImage, emptyImage, emptyImage};
            down = new BufferedImage[]{emptyImage, emptyImage, emptyImage};
            left = new BufferedImage[]{emptyImage, emptyImage, emptyImage};
            right = new BufferedImage[]{emptyImage, emptyImage, emptyImage};
        }
    }

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    void setDefaultValues() {
        x = 384;
        y = 288;
        speed = 2;
        direction = "down";
    }

    public void update() {
        // If not already moving, check for new input
        if (!isMoving) {
            if (keyH.upPressed) {
                direction = "up";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1; // show first step frame immediately
            } else if (keyH.downPressed) {
                direction = "down";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1;
            } else if (keyH.leftPressed) {
                direction = "left";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1;
            } else if (keyH.rightPressed) {
                direction = "right";
                isMoving = true;
                pixelsMoved = 0;
                spriteNum = 1;
            }
        }

        // If currently moving, advance position
        if (isMoving) {
            switch (direction) {
                case "up":    y -= speed; break;
                case "down":  y += speed; break;
                case "left":  x -= speed; break;
                case "right": x += speed; break;
            }

            // Print position every movement step
            System.out.println("World Position - X: " + x + ", Y: " + y);

            pixelsMoved += speed;

            // Animate while moving
            spriteCounter++;
            if (spriteCounter > 8) { // adjust for animation speed
                spriteNum = (spriteNum + 1) % 4;
                spriteCounter = 0;
            }

            // Stop when one tile is completed
            if (pixelsMoved >= gp.tileSize) {
                isMoving = false;
                spriteNum = 0; // back to idle frame
                spriteCounter = 0;
            }
        }
    }


    //Draw player
    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        BufferedImage image = null;
        switch (direction) {
            case "up":    image = up[spriteNum]; break;
            case "down":  image = down[spriteNum]; break;
            case "left":  image = left[spriteNum]; break;
            case "right": image = right[spriteNum]; break;
        }
        // Draw player with camera offset (screen position = world position - camera position)
        int screenX = x - cameraX;
        int screenY = y - cameraY;
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
    
    // Keep the old draw method for backward compatibility
    public void draw(Graphics2D g2) {
        draw(g2, 0, 0);
    }
}
