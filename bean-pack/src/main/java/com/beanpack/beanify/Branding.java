package com.beanpack.beanify;

import com.beanpack.head;

/**
 * The {@code Branding} class contains branding-related information for the application.
 * It includes a static representation of the application's logo in ASCII art format.
 * 
 * <p>The logo also incorporates the application's version, which is dynamically appended
 * from the {@code head.version} property.
 * 
 * <p>Usage:
 * <pre>
 *     System.out.println(Branding.logo);
 * </pre>
 * 
 * <p>Note: Ensure that the {@code head.version} property is properly initialized
 * before accessing the {@code logo} field to avoid runtime issues.
 */
public class Branding {
    
    public static String logo = 
        "_____________________   _____    _______  _________   ___ ___    _____  .___ _______\n" + 
        "\\______   \\_   _____/  /  _  \\   \\      \\ \\_   ___ \\ /   |   \\  /  _  \\ |   |\\      \\\n" +  
        " |    |  _/|    __)_  /  /_\\  \\  /   |   \\/    \\  \\//    ~    \\/  /_\\  \\|   |/   |   \\\n" + 
        " |    |   \\|        \\/    |    \\/    |    \\     \\___\\    Y    /    |    \\   /    |    \\\n" +
        " |______  /_______  /\\____|__  /\\____|__  /\\______  /\\___|_  /\\____|__  /___\\____|__  /\n" +
        "         \\/        \\/         \\/         \\/        \\/      \\/         \\/            \\/\n" +
        "                            B E A N C H A I N::" + head.version;    

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
