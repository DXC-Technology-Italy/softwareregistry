package com.cm.dev.service.impl;

import com.cm.dev.bean.MatchedSearch;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.search.SearchSupported;
import com.cm.dev.search.impl.SearchFactory;
import com.cm.dev.service.IndexService;
import com.cm.dev.service.PlainFileReaderService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Business Logic for Indexing and Searching
 * 
 */

@Component
public class IndexServiceImpl implements IndexService {


    @Autowired
    private SearchFactory searchFactory;

    @Autowired
    PlainFileReaderService plainFileReaderService;

    /**
     * Search the given words
     *
     * @param words : Words to be searched
     * @param fuzzy : Search is fuzzy or all words must be present in file
     *              <TR> TRUE : OR will be performed
     *              <TR> FALSE : AND will be performed
     * @return
     */
    public List<MatchedSearch> searchMultipleWords(List<String> words, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) throws IOException, ParseException, ServiceException {
        List<MatchedSearch> searchResults = new ArrayList<>();

        List<String> results = searchFactory.getSearcher(SearchSupported.INDEXSEARCH).searchKeywords(words, fuzzy, area, project, repository, extension, kind, filename, limitResult);
        Pattern ptrnPath = Pattern.compile("(area[0-9]{4})(/[^/]+)(/[^/]+)");
        Map<String, JsonElement> repositoryMap = plainFileReaderService.getRepositoryMap();
        for (String result : results) {
            MatchedSearch matchedSearch = new MatchedSearch();
            matchedSearch.setFilepath(result);
            Matcher matcherPath = ptrnPath.matcher(result);
            if (matcherPath.find()) {
                matchedSearch.setArea(matcherPath.group(1));
                matchedSearch.setRepository(matcherPath.group(2).replace("\\", ""));
                matchedSearch.setProject(matcherPath.group(3).replace("\\", ""));
                JsonObject obj = repositoryMap.get(matchedSearch.getArea() + matchedSearch.getRepository()).getAsJsonObject();
                matchedSearch.setKind(obj.get("kind").getAsString());
            }
            searchResults.add(matchedSearch);
        }
        return searchResults;
    }

    
    /** 
     * @param filename
     * @param area
     * @param repository
     * @param kind
     * @param limitResult
     * @return List<MatchedSearch>
     * @throws IOException
     * @throws ParseException
     * @throws ServiceException
     */
    @Override
    public List<MatchedSearch> searchFileNames(String filename, String area, String repository, String kind, String limitResult) throws IOException, ParseException, ServiceException {
        List<MatchedSearch> searchResults = new ArrayList<>();
        List<String> results = searchFactory.getSearcher(SearchSupported.INDEXSEARCH).searchFilenames(filename, area, repository, kind, limitResult);
        Pattern ptrnPath = Pattern.compile("(area[0-9]{4})(/[^/]+)(/[^/]+)");
        Map<String, JsonElement> repositoryMap = plainFileReaderService.getRepositoryMap();
        for (String result : results) {
            MatchedSearch matchedSearch = new MatchedSearch();
            matchedSearch.setFilepath(result);
            Matcher matcherPath = ptrnPath.matcher(result);
            if (matcherPath.find()) {
                matchedSearch.setArea(matcherPath.group(1));
                matchedSearch.setRepository(matcherPath.group(2).replace("\\", ""));
                matchedSearch.setProject(matcherPath.group(3).replace("\\", ""));
                JsonObject obj = repositoryMap.get(matchedSearch.getArea() + matchedSearch.getRepository()).getAsJsonObject();
                matchedSearch.setKind(obj.get("kind").getAsString());
            }

            searchResults.add(matchedSearch);
        }

        return searchResults;
    }
}
