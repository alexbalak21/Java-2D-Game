package game;

public class Camera {
    private int x, y;

    public void centerOn(Player player, int screenWidth, int screenHeight) {
        int playerPixelX = player.getTileX() * 16;
        int playerPixelY = player.getTileY() * 16;

        x = playerPixelX - screenWidth / 2;
        y = playerPixelY - screenHeight / 2;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
