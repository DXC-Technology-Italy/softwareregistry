package com.cm.dev.dao;

import com.cm.dev.domain.Project;
import com.mongodb.MongoException;

import java.util.List;

/**
 * Interface for Project Data Access Objects. 
 * The GenericDAO interface already exposes basic CRUD operations
 */
public interface ProjectDAO extends GenericDAO<Project> {

    List<Project> getByRepository(String repository) throws MongoException;
    Project getByName(String name) throws MongoException;
    List<Project> getAllProjectsWithoutDependencies() throws MongoException;
    List<String> getDistinctProjects() throws MongoException;
}
