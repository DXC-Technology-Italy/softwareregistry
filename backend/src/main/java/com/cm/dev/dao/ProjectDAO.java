package com.cm.dev.dao;

import com.cm.dev.domain.Project;

import java.util.List;

public interface ProjectDAO extends GenericDAO<Project> {

    public List<Project> getByRepository(String repository) throws Exception;
    public Project getByName(String name) throws Exception;
    public List<Project> getAllProjectsWithoutDependencies() throws Exception;
    public List<String> getDistinctProjects() throws Exception;
}
