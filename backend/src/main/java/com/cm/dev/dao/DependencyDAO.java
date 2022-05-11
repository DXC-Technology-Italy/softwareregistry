package com.cm.dev.dao;

import com.cm.dev.domain.Dependency;
import com.mongodb.MongoException;

import java.util.List;

/**
 * Interface for Dependency Data Access Objects. 
 * The GenericDAO interface already exposes basic CRUD operations
 */
public interface DependencyDAO extends GenericDAO<Dependency> {

    List<Dependency> getByName(String name) throws MongoException;
    List<Dependency> getByNameAndGroupId(String name, String groupId) throws MongoException;

}