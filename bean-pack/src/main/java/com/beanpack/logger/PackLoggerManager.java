package com.beanpack.logger;

import org.tinylog.Logger;

public class PackLoggerManager {
    public static boolean devMode = false;

    
    /**
     * takes 'log' message 
     * prints to console in DevMode 
     * logs as 'INFO' in log file
     * @param log
     */
    public static void PackLogger(String log) {
        if (devMode) {
            System.out.println(log);
        }
        Logger.info(log);
    }

    /**
     * takes 'log' message 
     * Force prints to console ignores devMode settings
     * logs as 'INFO' in log file
     * @param log
     */
    public static void PackLoggerFPrint(String log) {
        System.out.println(log);
        Logger.info(log);
    }

    /**
     * takes 'log' message 
     * prints to console in DevMode 
     * NO LOG to FILE
     * @param log
     */
    public static void PackPrinter(String log) {
        if (devMode) {
            System.out.println(log);
        }
    }

    /**
     * takes 'log' message 
     * prints to console in DevMode 
     * logs as 'ERROR' in log file
     * @param log
     */
    public static void PackLoggerError(String log) {
        if (devMode) {
            System.out.println(log);
        }
        Logger.info(log);
    }

}
