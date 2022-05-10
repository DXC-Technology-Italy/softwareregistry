package com.cm.dev.search.impl;

import com.cm.dev.search.SearchSupported;
import com.cm.dev.search.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Factory class to return an instance based on search needed
 *
 */
@Component
public class SearchFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchFactory.class);

    @Autowired
    @Qualifier("fileSearcher")
    private Searcher fileSearch;

    @Autowired
    @Qualifier("indexSearcher")
    private Searcher indexSearch;

    
    /** 
     * @param searchType
     * @return Searcher
     */
    public Searcher getSearcher(SearchSupported searchType) {
        LOGGER.debug("getSearcher : Received Search Type - {}", searchType);
        if (searchType == SearchSupported.FILESEARCH) {
            return fileSearch;
        }
        return indexSearch;
    }
}
