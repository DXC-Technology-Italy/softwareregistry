package com.cm.dev.search;

import com.cm.dev.exception.SearchException;

import java.util.List;

public interface Searcher {

	public List<String> searchKeywords(List<String> keyWords, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) throws SearchException;
	public List<String> searchFilenames(String filename, String area, String repository, String kind, String limitResult) throws SearchException;
}
