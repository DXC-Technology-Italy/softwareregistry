package com.ssj.search.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class RepositoryFileService {

    @Value("{search.index.directory}")
    private static String indexDirectory;
    private static String repositoryFile = "repositories.json";

    private String repositoryPath;

    private static RepositoryFileService INSTANCE;
    private HashMap<String, JsonElement> repositoryMap;

    
    /** 
     * @return HashMap<String, JsonElement>
     */
    public HashMap<String, JsonElement> getRepositoryMap() {
        return this.repositoryMap;
    }

    public RepositoryFileService() {
        this.repositoryMap  = new HashMap<>();
        this.repositoryPath = indexDirectory + "/" + repositoryFile;
        loadMapFromRepositoryFiles();
    }

    
    /** 
     * @return RepositoryFileService
     */
    public static RepositoryFileService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RepositoryFileService();
        }
        return INSTANCE;
    }

    public void loadMapFromRepositoryFiles() {
        JsonReader reader = null;
        try {
            JsonParser parser = new JsonParser();
            System.out.println("repositoryPath = " + repositoryPath);
            reader = new JsonReader(new FileReader(repositoryPath));
            JsonArray rootObj = parser.parse(reader).getAsJsonArray();
//            HashMap<String,JsonElement> repositoryMap = new HashMap<>();
            System.out.println("Parsing Json file");
            for (JsonElement element : rootObj) {
                JsonObject obj = element.getAsJsonObject();

                repositoryMap.put(obj.get("longName").getAsString(), element);
            }

            System.out.println("Done parsing Json file");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

