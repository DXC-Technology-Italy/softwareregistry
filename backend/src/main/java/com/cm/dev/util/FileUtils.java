package com.cm.dev.util;

public class FileUtils {

    private static final String SEPARATOR = "/";

    public static String basename(String path) {
        String[] pathSections = path.split(SEPARATOR);
        return pathSections[ pathSections.length - 1];
    }

    public static String basepath(String path) {
        String[] pathSections = path.split(SEPARATOR);
        String outPath = "";

        for (int i = 0; i < pathSections.length - 1 ; i++) {
            outPath += pathSections[i] + SEPARATOR;
        }
        return outPath;
    }
}
