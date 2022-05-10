package com.cm.dev.controller;


import com.cm.dev.bean.MatchedSearch;
import com.cm.dev.service.impl.IndexServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cm.dev.util.ApplicationConstants.SEARCH_KEYWORD_SPLITTER;

/**
 * Controller for Code Search
 * 
 */

@RestController
@CrossOrigin
public class IndexServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceController.class);

    @Autowired
    private IndexServiceImpl service;

    /**
     * Search on Lucene index and return matching file
     *
     * @param keywords - Words to search
     * @param isFuzzy  : Search is fuzzy or all words must be present in file, default is AND operation
     *                 <TR> TRUE : OR will be performed
     *                 <TR> FALSE : AND will be performed
     * @param area - area to search on
     * @param repository - repository to search on
     * @param filename - filename to search on
     * @param limitResult - limitResult to search on
     * @param project - project to search on
     * @param extension - extension to search on
     * @param kind - kind to search on
     *
     * @return List<MatchedSearch>
     */
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/getMatchingFiles")
    public List<MatchedSearch> getMatchingFiles(@RequestBody String keywords, @RequestParam(required = false, name = "isFuzzy", defaultValue = "false") boolean isFuzzy, @RequestParam(required = false, defaultValue = "", name = "area") String area, @RequestParam(required = false, defaultValue = "", name = "repository") String repository, @RequestParam(required = false, defaultValue = "", name = "project") String project, @RequestParam(required = false, defaultValue = "", name = "extension") String extension, @RequestParam(required = false, defaultValue = "", name = "kind") String kind, @RequestParam(required = false, defaultValue = "", name = "filename") String filename, @RequestParam(required = false, defaultValue = "100", name = "limitResult") String limitResult) {
        List<String> keyWordsList;
        List<MatchedSearch> matchedSearches = null;
        if (keywords.contains(SEARCH_KEYWORD_SPLITTER)) {
            keyWordsList = Arrays.asList(keywords.split(SEARCH_KEYWORD_SPLITTER));
        } else {
            keyWordsList = Collections.singletonList(keywords);
        }
        try {
            matchedSearches = service.searchMultipleWords(keyWordsList, isFuzzy, area, project, repository, extension, kind, filename, limitResult);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return matchedSearches;
    }

    /**
     * Search on Lucene index and return matching file
     *
     * @param area - area to search on
     * @param repository - repository to search on
     * @param filename - filename to search on
     * @param limitResult - limitResult to search on
     * @param kind - kind to search on
     *
     * @return List<MatchedSearch>
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/getMatchingFileNames")
    public List<MatchedSearch> getMatchingFileNames(@RequestParam(name = "filename") String filename, @RequestParam(required = false, defaultValue = "", name = "area") String area, @RequestParam(required = false, defaultValue = "", name = "repository") String repository, @RequestParam(required = false, defaultValue = "", name = "kind") String kind, @RequestParam(required = false, defaultValue = "100", name = "limitResult") String limitResult) {
        List<MatchedSearch> matchedSearches = null;
        try {
            matchedSearches = service.searchFileNames(filename, area, repository, kind, limitResult);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return matchedSearches;
    }

}
