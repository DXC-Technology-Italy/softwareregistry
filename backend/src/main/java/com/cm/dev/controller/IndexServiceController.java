package com.cm.dev.controller;


import com.cm.dev.bean.MatchedSearch;
import com.cm.dev.service.impl.IndexServiceImpl;
import com.cm.dev.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.cm.dev.util.ApplicationConstants.SEARCH_KEYWORD_SPLITTER;

@RestController
@CrossOrigin
public class IndexServiceController {

    @Autowired
    IndexServiceImpl service;

    /**
     * Search and return matching file names
     *
     * @param keywords - Words to search
     * @param isFuzzy  : Search is fuzzy or all words must be present in file, default is AND operation
     *                 <TR> TRUE : OR will be performed
     *                 <TR> FALSE : AND will be performed
     * @return
     */
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/getMatchingFiles", method = RequestMethod.POST)
    public List<MatchedSearch> getMatchingFiles(@RequestBody String keywords, @RequestParam(required = false, name = "isFuzzy", defaultValue = "false") boolean isFuzzy, @RequestParam(required = false, defaultValue = "", name = "area") String area, @RequestParam(required = false, defaultValue = "", name = "repository") String repository, @RequestParam(required = false, defaultValue = "", name = "project") String project, @RequestParam(required = false, defaultValue = "", name = "extension") String extension, @RequestParam(required = false, defaultValue = "", name = "kind") String kind, @RequestParam(required = false, defaultValue = "", name = "filename") String filename, @RequestParam(required = false, defaultValue = "100", name = "limitResult") String limitResult) {
        List<String> keyWordsList;
        if (keywords.contains(SEARCH_KEYWORD_SPLITTER)) {
            keyWordsList = Arrays.asList(keywords.split(SEARCH_KEYWORD_SPLITTER));
        } else {
            keyWordsList = Arrays.asList(keywords);
        }

        return service.searchMultipleWords(keyWordsList, isFuzzy, area, project, repository, extension, kind, filename, limitResult);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/getMatchingFileNames")
    public List<MatchedSearch> getMatchingFileNames(@RequestParam(name = "filename") String filename, @RequestParam(required = false, defaultValue = "", name = "area") String area, @RequestParam(required = false, defaultValue = "", name = "repository") String repository, @RequestParam(required = false, defaultValue = "", name = "kind") String kind, @RequestParam(required = false, defaultValue = "100", name = "limitResult") String limitResult) {

        return service.searchFileNames(filename, area, repository, kind, limitResult);
    }
    
}
