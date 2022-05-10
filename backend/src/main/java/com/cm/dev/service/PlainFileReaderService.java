package com.cm.dev.service;

import com.cm.dev.domain.Dependency;
import com.cm.dev.domain.Project;
import com.cm.dev.domain.Repository;
import com.cm.dev.exception.ServiceException;
import com.google.gson.JsonElement;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Interface that exposes methods to handle low level data files access business logic
 */
public interface PlainFileReaderService {

    void loadDataFromDependenciesFile() throws ServiceException, FileNotFoundException;

    List<Dependency> getDependencies() throws ServiceException;

    Map<Integer, String> getDependenciesFileLines() throws ServiceException;

    Map<String, Project> getProjects();
    Map<String, Repository> getRepositories();
    Map<String, JsonElement> getRepositoryMap();
}

