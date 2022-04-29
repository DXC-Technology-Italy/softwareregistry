package com.cm.dev.service;

import com.cm.dev.bean.MatchedSearch;

import java.util.List;

public interface IndexService {

    public List<MatchedSearch> searchMultipleWords(List<String> words, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult);
    public List<MatchedSearch> searchFileNames(String filename, String area, String repository, String kind, String limitResult);
}
