package com.cm.dev.search;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * A generic "Searcher"
 */
public interface Searcher {

    List<String> searchKeywords(List<String> keyWords, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) throws IOException, ParseException;

    List<String> searchFilenames(String filename, String area, String repository, String kind, String limitResult) throws IOException, ParseException;
}
