import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a color pad on the game's interface
 */
public class ColorPad {
    private Color color;
    private int SIZE_X;
    private int SIZE_Y;
    private int OFFSET_X;
    private int OFFSET_Y;
    private boolean flashing;
    private static boolean gameOver;
    private static Color gameOverColor = new Color(141, 43, 195);

    /**
     * Creates a color pad with given color, size, and position
     * @param col       color of the pad
     * @param x         width of the pad
     * @param y         height of the pad
     * @param xOffset   offset in the x-plane
     * @param yOffset   offset in the y-plane
     */
    public ColorPad(Color col, int x, int y, int xOffset, int yOffset) {
        color = col;
        SIZE_X = x;
        SIZE_Y = y;
        OFFSET_X = xOffset;
        OFFSET_Y = yOffset;
        flashing = false;
        gameOver = false;
    }

    /**
     * Draws the color pad on display
     * @param g     The context to draw on
     */
    public void drawColorPad(Graphics2D g) {
        if (gameOver)
            g.setColor(gameOverColor);
        else if (flashing)
            g.setColor(color);
        else
            g.setColor(color.darker().darker());
        g.fillOval(OFFSET_X, OFFSET_Y, SIZE_X, SIZE_Y);
    }

    /**
     * @param x     x coordinate
     * @param y     y coordinate
     * @return      true if ColorPad is within the given coordinates; false otherwise
     */
    public boolean isWithinCoords(int x, int y) {
        if (x < OFFSET_X || y < OFFSET_Y)
            return false;
        if (x < (OFFSET_X + SIZE_X) && y < (OFFSET_Y + SIZE_Y))
            return true;
        return false;
    }

    /**
     * @param bool      whether the pad should be flashing or not
     */
    public void setFlashing(boolean bool) {
        flashing = bool;
    }

    /**
     * @param bool      whether the pad should be in the gameOver state
     */
    public void setGameOver(boolean bool) {
        gameOver = bool;
    }

}