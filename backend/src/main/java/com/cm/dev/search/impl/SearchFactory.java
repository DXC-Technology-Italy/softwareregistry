package com.cm.dev.search.impl;

import com.cm.dev.search.SearchSupported;
import com.cm.dev.search.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Factory class to return instance based on search needed
 * @author Sushil
 *
 */
@Component
public class SearchFactory {

	private static final Logger logger = LoggerFactory.getLogger(SearchFactory.class);
	
	@Autowired
	@Qualifier("fileSearcher")
	private Searcher fileSearch;

	@Autowired
	@Qualifier("indexSearcher")
	private Searcher indexSearch;
	
	public Searcher getSearcher(SearchSupported searchType) {
		logger.debug("getSearcher : Received Search Type - {}",searchType);
		switch(searchType) {
			case FILESEARCH: return fileSearch; 
			case INDEXSEARCH: return indexSearch;
			default: return indexSearch;
		}
	}
}
