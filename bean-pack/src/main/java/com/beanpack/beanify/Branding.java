package com.beanpack.beanify;

import com.beanpack.head;

/**
 * The {@code Branding} class contains branding-related ASCII art and identifiers for the application.
 * <p>
 * It includes a static representation of the application's logo as well as ASCII art for WizCrypt.
 * The logo dynamically includes the version from {@code head.version}.
 *
 * <p>Usage:
 * <pre>{@code
 *     System.out.println(Branding.logo);
 * }</pre>
 *
 * <p>Note: Ensure that {@code head.version} is initialized before accessing {@code logo}
 * to avoid null or incorrect version rendering.
 */
public class Branding {
    
    /**
     * The application banner logo, rendered in ASCII art with the dynamic version string appended.
     */
    public static String logo = 
        "_____________________   _____    _______  _________   ___ ___    _____  .___ _______\n" + 
        "\\______   \\_   _____/  /  _  \\   \\      \\ \\_   ___ \\ /   |   \\  /  _  \\ |   |\\      \\\n" +  
        " |    |  _/|    __)_  /  /_\\  \\  /   |   \\/    \\  \\//    ~    \\/  /_\\  \\|   |/   |   \\\n" + 
        " |    |   \\|        \\/    |    \\/    |    \\     \\___\\    Y    /    |    \\   /    |    \\\n" +
        " |______  /_______  /\\____|__  /\\____|__  /\\______  /\\___|_  /\\____|__  /___\\____|__  /\n" +
        "         \\/        \\/         \\/         \\/        \\/      \\/         \\/            \\/\n" +
        "                            B E A N C H A I N::" + head.version;    

    /**
     * The ASCII art banner for WizCrypt branding.
     */
    public static String wizCrypt = 
     """
    
        ▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌
        ▐ __      __              ____                           __      ▌
        ▐/\\ \\  __/\\ \\  __        /\\  _`\\                        /\\ \\__   ▌
        ▐\\ \\ \\/\\ \\ \\ \\/\\_\\  ____ \\ \\ \\/\\_\\  _ __   __  __  _____\\ \\ ,_\\  ▌
        ▐ \\ \\ \\ \\ \\ \\ \\/\\ \\/\\_ ,`\\\\ \\ \\/_/_/\\`'__\\/\\ \\/\\ \\/\\ '__`\\ \\ \\/  ▌
        ▐  \\ \\ \\_/ \\_\\ \\ \\ \\/_/  /_\\ \\ \\L\\ \\ \\ \\/ \\ \\ \\_\\ \\ \\ \\L\\ \\ \\ \\_ ▌
        ▐   \\ `\\___x___/\\ \\_\\/\\____\\\\ \\____/\\ \\_\\  \\/`____ \\ \\ ,__/\\ \\__\\▌
        ▐    '\\/__//__/  \\/_/\\/____/ \\/___/  \\/_/   `/___/> \\ \\ \\/  \\/__/▌
        ▐                                              /\\___/\\ \\_\\       ▌
        ▐                                              \\/__/  \\/_/       ▌
        ▐▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌
        """;
}
