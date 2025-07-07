package com.beanpack.beanify;

/**
 * The {@code Color} class defines ANSI escape codes for terminal text formatting.
 * <p>
 * These constants can be used to add color and style (e.g., bold, underline) to console output.
 * <p>
 * Example usage:
 * <pre>{@code
 *     System.out.println(Color.RED + "Error!" + Color.RESET);
 * }</pre>
 * 
 * <p>Note: These codes are primarily supported in UNIX-based terminals. 
 * Windows consoles may require ANSI support to be enabled.
 */
public class Color {

    /** Resets all attributes (color and style) to default. */
    public static final String RESET = "\u001B[0m";

    /** Red foreground color. Commonly used for errors or alerts. */
    public static final String RED = "\u001B[31m";

    /** Green foreground color. Often used to indicate success. */
    public static final String GREEN = "\u001B[32m";

    /** Yellow foreground color. Typically used for warnings or highlights. */
    public static final String YELLOW = "\u001B[33m";

    /** Blue foreground color. Used for informational or neutral messages. */
    public static final String BLUE = "\u001B[34m";

    /** Purple foreground color. Can be used for branding or special notes. */
    public static final String PURPLE = "\u001B[35m";

    /** Black foreground color. Rarely used due to poor visibility on dark backgrounds. */
    public static final String BLACK = "\u001B[30m";

    /** Bold text style. */
    public static final String BOLD = "\u001B[1m";

    /** Underlined text style. */
    public static final String UNDERLINE = "\u001B[4m";

    /** Purple background color. Can be combined with other foreground styles. */
    public static final String BG_PURPLE = "\u001B[45m";
}
