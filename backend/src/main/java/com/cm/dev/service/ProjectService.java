package com.cm.dev.service;

import com.cm.dev.bean.MatchedDependency;
import com.cm.dev.domain.Project;
import com.cm.dev.exception.ServiceException;

import java.io.IOException;
import java.util.List;

/**
 * Interface that exposes methods to handle Projects business logic
 */
public interface ProjectService {

    List<Project> getAllProjects() throws ServiceException;

    List<Project> getAllProjectsWithoutDependencies() throws ServiceException;

    void reload() throws ServiceException, IOException;

    List<String> getDistinctProjects() throws ServiceException;

    List<MatchedDependency> checkDependencies(String projectName) throws ServiceException;
}
