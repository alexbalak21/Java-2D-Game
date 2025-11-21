package game;

public class Camera {
    private int x, y;

    public void centerOn(Player player, int screenWidth, int screenHeight) {
        int playerPixelX = player.getTileX() * Constants.TILE_SIZE * Constants.SCALE;
        int playerPixelY = player.getTileY() * Constants.TILE_SIZE * Constants.SCALE;

        x = playerPixelX - screenWidth / 2;
        y = playerPixelY - screenHeight / 2;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
