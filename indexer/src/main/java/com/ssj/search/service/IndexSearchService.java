package com.ssj.search.service;

import com.ssj.search.contract.FileTypesSupported;
import com.ssj.search.contract.impl.IndexFactory;
import com.ssj.search.exception.SearchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ssj.search.util.ApplicationConstants.APP_CONST_ERROR_CODE_7002;


@Component
public class IndexSearchService {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexSearchService.class);

	@Autowired
	private IndexFactory indexFactory;

	
	/**
	 * Index all files available in given directory
	 * <BR>Only text files are supported for indexing, future formats to follow
	 * @param directoryPath - Directory to get indexed
	 * @return
	 */
	public String indexDirectory(String directoryPath) {
		try {
			return indexFactory.getIndexer(FileTypesSupported.TXT).indexDirectory(directoryPath, false);
		} catch (SearchException e) {
			logger.error("Unable to Index : ",e);
		}
		return APP_CONST_ERROR_CODE_7002;
	}

}
