package com.cm.dev.service;

import com.cm.dev.domain.Repository;
import com.cm.dev.exception.ServiceException;

import java.io.IOException;
import java.util.List;

/**
 * Interface that exposes methods to handle Repositories business logic
 */
public interface RepositoryService {

    List<Repository> getAllRepositories() throws ServiceException;

    List<Repository> getRepositoriesFromArea(String code) throws ServiceException;

    List<String> getRepositoriesByKind(String kind) throws ServiceException;

    void reload() throws ServiceException, IOException;

    List<String> getDistinctRepositories() throws ServiceException;

    List<Repository> getAllOrdered(List<String> orderFields) throws ServiceException;
}
