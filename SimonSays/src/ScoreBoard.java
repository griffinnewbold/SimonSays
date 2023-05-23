import java.util.Random;

/**
 * Represents a player's score board
 */
public class ScoreBoard {
    private int level;
    private int score;
    private Random random;
    private static final int LEVEL_SCORE_BONUS_RATIO = 1000;
    private static final int MINIMUM_SCORE_INCREASE = 50;

    /**
     * Constructor creates a new score board
     */
    public ScoreBoard() {
        level = 0;
        score = 0;
        random = new Random();
    }

    /**
     * Resets the score board
     */
    public void reset() {
        level = 0;
        score = 0;
    }

    /**
     * Increments a level and gives a level score bonus
     */
    public void nextLevel() {
        level++;
        if (level != 1)
            score += level * LEVEL_SCORE_BONUS_RATIO;
    }

    /**
     * Increase score based on the level
     */
    public void increaseScore() {
        score += level * (MINIMUM_SCORE_INCREASE + random.nextInt(10));
    }

    /**
     * @return  player's level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return  player's score
     */
    public int getScore() {
        return score;
    }
}