package com.cm.dev.util;

/**
 * Utility class for file operations
 *  
 */
public class FileUtils {

    private static final String SEPARATOR = "/";

    private FileUtils() {
        throw new IllegalStateException("Do not instantiate this class");
    }

    /** 
     * @param path
     * @return String
     */
    public static String basename(String path) {
        String[] pathSections = path.split(SEPARATOR);
        return pathSections[ pathSections.length - 1];
    }

    
    /** 
     * @param path
     * @return String
     */
    public static String basepath(String path) {
        String[] pathSections = path.split(SEPARATOR);
        StringBuilder outPath = new StringBuilder();

        for (int i = 0; i < pathSections.length - 1 ; i++) {
            outPath.append(pathSections[i]).append(SEPARATOR);
        }
        return outPath.toString();
    }
}
