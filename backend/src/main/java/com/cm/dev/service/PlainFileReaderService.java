package com.cm.dev.service;

import com.cm.dev.bean.MatchedSearch;
import com.cm.dev.domain.Dependency;

import java.util.HashMap;
import java.util.List;

public interface PlainFileReaderService {

    public void loadDataFromDependenciesFile();

    public List<Dependency> getDependencies();

    public HashMap<Integer, String> getDependenciesFileLines();

}

