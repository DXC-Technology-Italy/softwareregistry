package com.cm.dev.service;

import com.cm.dev.bean.MatchedDependency;
import com.cm.dev.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectService {

    public List<Project> getAllProjects() throws Exception;
    public List<Project> getAllProjectsWithoutDependencies() throws Exception;
    public void reload() throws Exception;
    public List<String> getDistinctProjects() throws Exception;;

    List<MatchedDependency> checkDependencies(String projectName) throws Exception;
}
