package com.cm.dev.service;

import com.cm.dev.bean.MatchedSearch;
import com.cm.dev.exception.ServiceException;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Interface that exposes methods to handle Searches business logic
 */
public interface IndexService {

    List<MatchedSearch> searchMultipleWords(List<String> words, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) throws ServiceException, IOException, ParseException;

    List<MatchedSearch> searchFileNames(String filename, String area, String repository, String kind, String limitResult) throws ServiceException, IOException, ParseException;
}
