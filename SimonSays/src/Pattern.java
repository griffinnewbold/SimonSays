import java.util.Random;

/**
 * Represents a random pattern of integers
 */
public class Pattern {
    private int length;
    private int[] pattern;
    private Random random;

    /**
     * Creates a new pattern with random integers and given length
     * @param len   length of the pattern
     */
    public Pattern(int len) {
        length = len;
        pattern = new int[length];
        random = new Random();
        createPattern();
    }

    /**
     * Create a random pattern
     */
    private void createPattern() {
        for (int i=0; i<length; i++)
            pattern[i] = random.nextInt(4);
    }

    /**
     * Get the element of the pattern at specified index
     * @param index     index in the pattern
     * @return  element of pattern in the given index. If index is invalid, returns -1
     */
    public int getAtIndex(int index) {
        if (index < 0 || index >= length)
            return -1;
        return pattern[index];
    }

    /**
     * @return  length of the pattern
     */
    public int getLength() {
        return length;
    }
}