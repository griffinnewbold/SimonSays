import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents the game's flow and interface
 */
public class Simon extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static Simon simon;
    private ScoreBoard scoreBoard;
    private Pattern pattern;
    private JButton button;
    private ColorPad[] colorPads;
    private Timer timer;

    // CONSTANTS:
    private static final String GAME_NAME = "Simon Says!";
    private static final int WIDTH   = 800;
    private static final int HEIGHT  = 800;
    private static final int PADS_SPACE_WIDTH = 470;
    private static final int PADS_SPACE_HEIGHT = 470;
    private static final int PADS_SPACE_OFFSET = (HEIGHT - PADS_SPACE_HEIGHT) / 2;
    private static final int EXTRA_SPACE_Y = 75;
    private static final int PAD_SPACING = 25;
    private static final int PAD_WIDTH = PADS_SPACE_WIDTH / 2 - PAD_SPACING *2;
    private static final int PAD_HEIGHT = PADS_SPACE_HEIGHT / 2 - PAD_SPACING *2;
    private static final int NUM_PADS = 4;
    private static final int MAIN_BUTTON_WIDTH = 120;
    private static final int MAIN_BUTTON_HEIGHT = 50;
    private static final int TIMER_DELAY = 20;
    private static final Color BACKGROUND_COLOR = new Color(0,0,0);
    private static final Color SIMON_MAGENTA = new Color(141, 43, 195);

    // LOGO:
    private static final int LOGO_Y_LOCATION = 20;
    private static final String LOGO_PATH = "/Users/griffin/Downloads/simon.png";
    private BufferedImage logo;

    // GAME FLOW:
    private boolean gameRunning;
    private boolean gameOver;
    private boolean advancingLevel;
    private boolean displayingPattern;
    private int ticks;
    private int playerPatternIndex;
    private int gamePatternIndex;


    /**
     * Constructor creates the the frame and draws the game's graphics.
     */
    public Simon() {
        createFrame();
        createMainButton();

        try {
            logo = ImageIO.read(new File(LOGO_PATH));
        } catch (IOException e) {
            logo = null;
        }

        scoreBoard = new ScoreBoard();
        colorPads = new ColorPad[NUM_PADS];
        timer = new Timer(TIMER_DELAY, this);
        gameRunning = false;
        gameOver = false;
        advancingLevel = false;
        displayingPattern = false;
        ticks = 0;
        initializeColorPads();
        repaint();
    }

    /**
     * Creates frame
     */
    private void createFrame() {
        JFrame frame = new JFrame(GAME_NAME);
        frame.setSize(WIDTH, HEIGHT);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setVisible(true);
        frame.add(this);
        frame.addMouseListener(this);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Creates the main button
     */
    private void createMainButton() {
        button = new JButton("PLAY!");
        button.setBackground(Color.WHITE);
        button.setForeground(BACKGROUND_COLOR);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        int offset_x = WIDTH/2 - MAIN_BUTTON_WIDTH/2;
        int offset_y = HEIGHT - 110;
        button.setBounds(offset_x, offset_y, MAIN_BUTTON_WIDTH, MAIN_BUTTON_HEIGHT);
        button.addActionListener(new MainButtonListener());
        setLayout(null);
        add(button);
    }

    /**
     * Initializes color pads
     */
    private void initializeColorPads() {
        int firstColumnOffsetX = WIDTH/2 - PAD_SPACING - PAD_WIDTH;
        int firstRowOffsetY = PAD_SPACING + PADS_SPACE_OFFSET + EXTRA_SPACE_Y;
        int secondColumnOffsetX = WIDTH/2 + PAD_SPACING;
        int secondRowOffsetY = PAD_HEIGHT + PAD_SPACING *2 + PADS_SPACE_OFFSET + EXTRA_SPACE_Y;
        colorPads[0] = new ColorPad(Color.GREEN, PAD_WIDTH, PAD_HEIGHT, firstColumnOffsetX, firstRowOffsetY);
        colorPads[1] = new ColorPad(Color.RED, PAD_WIDTH, PAD_HEIGHT, secondColumnOffsetX, firstRowOffsetY);
        colorPads[2] = new ColorPad(Color.YELLOW, PAD_WIDTH, PAD_HEIGHT, firstColumnOffsetX, secondRowOffsetY);
        colorPads[3] = new ColorPad(Color.BLUE, PAD_WIDTH, PAD_HEIGHT, secondColumnOffsetX, secondRowOffsetY);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (simon != null)
            paint((Graphics2D) g);
    }

    /**
     * Paint the game's graphics
     * @param g     2D graphics context
     */
    private void paint(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (logo == null) { // If logo image not found:
            g.setColor(SIMON_MAGENTA);
            g.setFont(new Font("Arial", Font.BOLD, 55));
            g.drawString(GAME_NAME, (WIDTH/2) - 160,  LOGO_Y_LOCATION + 100);
        }
        else { // Logo image found:
            g.drawImage(logo, null, WIDTH / 2 - logo.getWidth() / 2, LOGO_Y_LOCATION);
        }

        // Draw color pads:
        for (int i = 0; i< NUM_PADS; i++) {
            colorPads[i].drawColorPad(g);
        }

        // Set main button text:
        if (gameRunning && !gameOver)
            button.setText("PLAYING..");
        else if (!gameRunning && gameOver)
            button.setText("TRY AGAIN");
        else
            button.setText("PLAY");

        // Draw Score board:
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Level:  " + scoreBoard.getLevel(), (WIDTH/2) - 36,  PAD_SPACING + PADS_SPACE_OFFSET + 30);
        g.drawString("Score:  " + scoreBoard.getScore(), (WIDTH/2) - 36,  PAD_SPACING + PADS_SPACE_OFFSET + 52);

        // Game state text
        g.setFont(new Font("Arial", Font.BOLD, 22));
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over", (WIDTH/2) - 60,  (HEIGHT/2 + EXTRA_SPACE_Y) - 6);
        }
        else if (gameRunning && !displayingPattern && !advancingLevel) {
            g.setColor(SIMON_MAGENTA);
            g.drawString("Your Turn!", (WIDTH/2) - 56,  (HEIGHT/2 + EXTRA_SPACE_Y) - 6);
        }
        else if (gameRunning && advancingLevel) {
            g.setColor(Color.WHITE);
            g.drawString("Level " + scoreBoard.getLevel(), (WIDTH/2) - 41,  (HEIGHT/2 + EXTRA_SPACE_Y) - 6);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameRunning)
            return;

        if (gameOver) {
            gameOver();
            repaint();
            return;
        }

        ticks++;
        if (displayingPattern) {
            if (ticks % 40 == 0) {
                advancePattern();
                ticks = 0;
            }
            else if (ticks % 20 == 0)
                triggerAllFlashing(false);
        }

        else if (advancingLevel) {
            triggerAllFlashing(true);
            if (ticks % 60 == 0) {
                triggerAllFlashing(false);
                startNextLevel();
                ticks = 0;
            }
        }
        else { // Player's Turn
            if (ticks % 20 == 0) {
                triggerAllFlashing(false);
                ticks = 0;
            }
        }
        repaint();
    }


    /**
     * Start the game by starting the timer, resetting the score board and starting a pattern
     */
    private void startGame() {
        timer.start();
        triggerAllFlashing(false);
        gameRunning = true;
        gameOver = false;
        scoreBoard.reset();
        scoreBoard.nextLevel();
        startPattern();
    }

    /**
     * Start a pattern with length equals to player's level
     */
    private void startPattern() {
        pattern = new Pattern(scoreBoard.getLevel());
        gamePatternIndex = 0;
        playerPatternIndex = 0;
        displayingPattern = true;
    }

    /**
     * advance to the next element in the pattern
     */
    private void advancePattern() {
        if (gamePatternIndex >= pattern.getLength()) {
            displayingPattern = false;
            return;
        }
        colorPads[pattern.getAtIndex(gamePatternIndex)].setFlashing(true);
        gamePatternIndex++;
    }

    /**
     * go up a level and update scoreboard
     */
    private void advanceLevel() {
        advancingLevel = true;
        scoreBoard.nextLevel();
    }

    /**
     * Starts a new level
     */
    private void startNextLevel() {
        advancingLevel = false;
        pattern = null;
        startPattern();
    }

    /**
     * Game over, stop timer but keep score board
     */
    private void gameOver() {
        alertGameOver(true);
        gameRunning = false;
        gameOver = true;
        timer.stop();
    }

    /**
     * Alert all ColorPads of a change in the game's state
     * @param bool  true if game is over, false otherwise
     */
    private void alertGameOver(boolean bool) {
        for (int i = 0; i< NUM_PADS; i++)
            colorPads[i].setGameOver(bool);
    }


    /**
     *  Change the flashing state of all ColorPads
     * @param bool  true if ColorPads should be flashing, false otherwise
     */
    private void triggerAllFlashing(boolean bool) {
        for (int i = 0; i< NUM_PADS; i++)
            colorPads[i].setFlashing(bool);
    }


    /**
     * Listener class to the Main button
     */
    private class MainButtonListener implements ActionListener {
        private MainButtonListener() {}
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameRunning) {
                alertGameOver(false);
                startGame();
            }
        }
    }


    // Mouse Listeners:
    @Override
    public void mousePressed(MouseEvent e) {
        if (gameRunning && !displayingPattern && !advancingLevel) {
            int indexOfColorPadPressed = colorPadWithinCoords(e.getX(), e.getY());
            if (indexOfColorPadPressed == -1)
                return;
            colorPads[indexOfColorPadPressed].setFlashing(true);
            repaint();
            ticks = 0;
            if (indexOfColorPadPressed == pattern.getAtIndex(playerPatternIndex)) {
                scoreBoard.increaseScore();
                playerPatternIndex++;
                if (playerPatternIndex >= pattern.getLength())
                    advanceLevel();
            }
            else
                gameOver = true;

        }
    }

    /**
     * Gets index of colorPad within the given coordinates
     * @param x     x coordinate
     * @param y     y coordinate
     * @return      index of colorPad within coordinates; -1 if no colorPad is within the coordinates
     */
    private int colorPadWithinCoords(int x, int y) {
        for (int i = 0; i< NUM_PADS; i++) {
            if (colorPads[i].isWithinCoords(x, y))
                return i;
        }
        return -1;
    }

    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}

    // Run Game:
    public static void main(String[] args) {
        simon = new Simon();
    }
}