package com.cm.dev.service;

import com.cm.dev.domain.Repository;

import java.util.List;

public interface RepositoryService {

    public List<Repository> getAllRepositories() throws Exception;
    public List<Repository> getRepositoriesFromArea(String code) throws Exception;
    public List<String> getRepositoriesByKind(String kind) throws Exception;

    public void reload() throws Exception;
    public List<String> getDistinctRepositories() throws Exception;
    public List<Repository> getAllOrdered(List<String> orderFields) throws Exception;
}
