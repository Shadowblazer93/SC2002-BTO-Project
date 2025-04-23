package enums;

/**
 * Defines ANSI escape codes for coloring console output.
 * Example usage:
 * {@code System.out.println(defColor.RED + "Error: Invalid input!" + defColor.RESET);}
 */
public class defColor {
    
    /**
     * Resets console colour to default
     */
    public static final String RESET = "\u001B[0m";

    /**
     * Set console colour to black
     */
    public static final String BLACK = "\u001B[30m";

    /**
     * Set console colour to red
     */
    public static final String RED = "\u001B[31m";

    /**
     * Set console colour to yellow
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * Set console colour to blue
     */
    public static final String BLUE = "\u001B[34m";

    /**
     * Set console colour to black
     */
    public static final String PURPLE = "\u001B[35m";

}
