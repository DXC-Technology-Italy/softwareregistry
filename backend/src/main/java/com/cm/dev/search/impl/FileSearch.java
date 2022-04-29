package com.cm.dev.search.impl;


import com.cm.dev.search.Searcher;
import com.cm.dev.exception.SearchException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Search will not use Lucene Index but performs full table scan with mapreduce
 * @author Sushil
 *
 */
@Component("fileSearcher")
public class FileSearch implements Searcher {

	@Override
	public List<String> searchKeywords(List<String> keyWords, boolean isFuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) {
		return null;
	}

	@Override
	public List<String> searchFilenames(String filename, String area, String repository, String kind, String limitResult) throws SearchException {
		return null;
	}


}
