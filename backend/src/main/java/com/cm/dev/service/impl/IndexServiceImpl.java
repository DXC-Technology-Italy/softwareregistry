package com.cm.dev.service.impl;

import com.cm.dev.bean.MatchedSearch;
import com.cm.dev.search.SearchSupported;
import com.cm.dev.search.impl.SearchFactory;
import com.cm.dev.exception.SearchException;
import com.cm.dev.service.IndexService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cm.dev.util.ApplicationConstants.APP_CONST_ERROR_CODE_8001;


/**
 * Service class for indexing and searching
 * @author Sushil
 *
 */
@Component
public class IndexServiceImpl implements IndexService {


	@Autowired
	private SearchFactory searchFactory;

	/**
	 * Search the given words
	 * @param words : Words to be searched
	 * @param fuzzy : Search is fuzzy or all words must be present in file
	 * <TR> TRUE : OR will be performed
	 * <TR> FALSE : AND will be performed
	 * @return
	 */
	public List<MatchedSearch> searchMultipleWords(List<String> words, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) {
		List<MatchedSearch> searchResults = new ArrayList<>();
		try {
			List<String> results = searchFactory.getSearcher(SearchSupported.INDEXSEARCH).searchKeywords(words, fuzzy, area, project, repository, extension, kind, filename, limitResult);
			Pattern ptrnPath      = Pattern.compile("(area[0-9]{4})(\\/[^\\/]+)(\\/[^\\/]+)");
			HashMap<String, JsonElement> repositoryMap = PlainFileReaderServiceImpl.getInstance().getRepositoryMap();
			for (String result : results) {
				MatchedSearch matchedSearch = new MatchedSearch();
				matchedSearch.setFilepath(result);
				Matcher matcherPath = ptrnPath.matcher(result);
				if (matcherPath.find())
				{
					matchedSearch.setArea(matcherPath.group(1));
					matchedSearch.setRepository(matcherPath.group(2).replace("\\",""));
					matchedSearch.setProject(matcherPath.group(3).replace("\\",""));
					JsonObject obj = repositoryMap.get(matchedSearch.getArea() + matchedSearch.getRepository()).getAsJsonObject();
					matchedSearch.setKind(obj.get("kind").getAsString());
				}


				searchResults.add(matchedSearch);
			}
			return searchResults;
//			return searchFactory.getSearcher(SearchSupported.INDEXSEARCH).searchKeywords(words, fuzzy, area, project, repository, extension);
		} catch (SearchException e) {
			e.getMessage();
		}
		MatchedSearch matchedSearch = new MatchedSearch();
		matchedSearch.setFilepath(APP_CONST_ERROR_CODE_8001);
		searchResults.add(matchedSearch);
		return searchResults;
	}

	@Override
	public List<MatchedSearch> searchFileNames(String filename, String area, String repository, String kind, String limitResult) {
		List<MatchedSearch> searchResults = new ArrayList<>();
		try {
			List<String> results = searchFactory.getSearcher(SearchSupported.INDEXSEARCH).searchFilenames(filename, area, repository, kind, limitResult);
			Pattern ptrnPath      = Pattern.compile("(area[0-9]{4})(\\/[^\\/]+)(\\/[^\\/]+)");
			HashMap<String, JsonElement> repositoryMap = PlainFileReaderServiceImpl.getInstance().getRepositoryMap();
			for (String result : results) {
				MatchedSearch matchedSearch = new MatchedSearch();
				matchedSearch.setFilepath(result);
				Matcher matcherPath = ptrnPath.matcher(result);
				if (matcherPath.find())
				{
					matchedSearch.setArea(matcherPath.group(1));
					matchedSearch.setRepository(matcherPath.group(2).replace("\\",""));
					matchedSearch.setProject(matcherPath.group(3).replace("\\",""));
					JsonObject obj = repositoryMap.get(matchedSearch.getArea() + matchedSearch.getRepository()).getAsJsonObject();
					matchedSearch.setKind(obj.get("kind").getAsString());
				}


				searchResults.add(matchedSearch);
			}
		}catch( Exception e){

		}
		return searchResults;
	}
}
