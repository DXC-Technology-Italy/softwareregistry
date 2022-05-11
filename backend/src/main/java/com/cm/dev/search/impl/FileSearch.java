package com.cm.dev.search.impl;


import com.cm.dev.search.Searcher;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * To be implemented
 */
@Component("fileSearcher")
public class FileSearch implements Searcher {

    
    /** 
     * @param keyWords
     * @param isFuzzy
     * @param area
     * @param project
     * @param repository
     * @param extension
     * @param kind
     * @param filename
     * @param limitResult
     * @return List<String>
     */
    @Override
    public List<String> searchKeywords(List<String> keyWords, boolean isFuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) {
        return Collections.emptyList();
    }

    
    /** 
     * @param filename
     * @param area
     * @param repository
     * @param kind
     * @param limitResult
     * @return List<String>
     */
    @Override
    public List<String> searchFilenames(String filename, String area, String repository, String kind, String limitResult) {
        return Collections.emptyList();
    }


}
