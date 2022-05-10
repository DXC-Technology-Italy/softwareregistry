package com.cm.dev.dao;

import com.cm.dev.domain.Repository;
import com.mongodb.MongoException;

import java.util.List;

/**
 * Interface for Repository Data Access Objects. 
 * The GenericDAO interface already exposes basic CRUD operations
 */
public interface RepositoryDAO extends GenericDAO<Repository> {

    List<Repository> getRepositoriesFromArea(String code) throws MongoException;

    List<String> getRepositoriesByKind(String kind) throws MongoException;

    List<String> getDistinctRepositories() throws MongoException;

    List<Repository> getAllOrdered(List<String> orderFields) throws MongoException;
}
