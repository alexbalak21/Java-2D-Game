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
    
    public Player (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        setDefaultValues();
        getPlayerImage();
    }

    void setDefaultValues() {
       x = 100;
       y = 100;
       speed = 4;
       direction = "down";
    }

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
            final int rows = 4;
            
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

    public void update() {
        if(keyH.upPressed){
            direction = "up";
            spriteNum++;
            y -= speed;
        }
        if(keyH.downPressed){
            direction = "down";
            spriteNum++;
            y += speed;
        }
        if(keyH.leftPressed){
            direction = "left";
            spriteNum++;
            x -= speed;
        }
        if(keyH.rightPressed){
            direction = "right";
            spriteNum++;
            x += speed;
        }
        if(spriteNum > 3){
            spriteNum = 0;
        }
    }
    
    public void draw(Graphics2D g2) {
      BufferedImage image = null;
      switch(direction){
        case "up":
          image = up[spriteNum];
          break;
        case "down":
          image = down[spriteNum];
          break;
        case "left":
          image = left[spriteNum];
          break;
        case "right":
          image = right[spriteNum];
          break;
      }

      g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
