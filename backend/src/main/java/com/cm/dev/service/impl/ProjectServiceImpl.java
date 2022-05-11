package com.cm.dev.service.impl;

import com.cm.dev.bean.MatchedDependency;
import com.cm.dev.dao.AreaDAO;
import com.cm.dev.dao.DependencyDAO;
import com.cm.dev.dao.ProjectDAO;
import com.cm.dev.dao.ProjectInfoDAO;
import com.cm.dev.domain.Dependency;
import com.cm.dev.domain.Project;
import com.cm.dev.domain.ProjectInfo;
import com.cm.dev.exception.ServiceException;
import com.cm.dev.service.PlainFileReaderService;
import com.cm.dev.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Business Logic for Project Objects
 * 
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private DependencyDAO dependencyDAO;

    @Autowired
    private ProjectInfoDAO projectInfoDAO;

    @Autowired
    private AreaDAO areaDAO;

    @Autowired
    PlainFileReaderService plainFileReaderService;

    
    /** 
     * @return List<Project>
     * @throws ServiceException
     */
    public List<Project> getAllProjectsWithoutDependencies() throws ServiceException {
        return projectDAO.getAllProjectsWithoutDependencies();
    }

    
    /** 
     * @return List<Project>
     * @throws ServiceException
     */
    public List<Project> getAllProjects() throws ServiceException {
        return projectDAO.getAll();
    }

    
    /** 
     * @throws ServiceException
     * @throws IOException
     */
    @Override
    public void reload() throws ServiceException, IOException {

        Map<String, Project> projects = plainFileReaderService.getProjects();
        List<Dependency> dependencies = plainFileReaderService.getDependencies();
        List<ProjectInfo> projectInfoEntities = new ArrayList<>(projectInfoDAO.getAll());
        HashMap<String, ProjectInfo> projectInfos = new HashMap<>();
        List<Project> projectEntities = new ArrayList<>();

        for (ProjectInfo pi : projectInfoEntities) {
            projectInfos.put(pi.getName() + ":" + pi.getRepository(), pi);
        }
        for (Project p : projects.values()) {
            if (projectInfos.containsKey(p.getName() + ":" + p.getRepository())) {
                p.setProjectInfo(projectInfos.get(p.getName() + ":" + p.getRepository()));
            } else {
                ProjectInfo pi = new ProjectInfo();
                pi.setName(p.getName());
                pi.setRepository(p.getRepository());
                p.setProjectInfo(projectInfoDAO.create(pi));
            }

            projectEntities.add(p);
        }

        dependencyDAO.deleteAll();
        dependencyDAO.createMany(dependencies);

        projectDAO.deleteAll();
        projectDAO.createMany(projectEntities);

    }

    
    /** 
     * @return List<String>
     * @throws ServiceException
     */
    @Override
    public List<String> getDistinctProjects() throws ServiceException {
        return projectDAO.getDistinctProjects();
    }

    
    /** 
     * @param projectName
     * @return List<MatchedDependency>
     * @throws ServiceException
     */
    @Override
    public List<MatchedDependency> checkDependencies(String projectName) throws ServiceException {

        Project project = projectDAO.getByName(projectName);

        List<Dependency> dependencies = project.getDependencies();
        List<MatchedDependency> matchedDependencies = new ArrayList<>();

        for (Dependency d : dependencies) {
            List<Dependency> dependenciesToCheck = dependencyDAO.getByNameAndGroupId(d.getArtifactId(), d.getGroupId());
            /* Checking all dependencies with same name and group Id */
            MatchedDependency dependency = MatchedDependency.fromDependency(d);
            String actualVersion = dependency.getVersion();
            dependency.setActualVersion(actualVersion);

            if (dependency.getGroupId().contains("templib")) {
                matchedDependencies.add(dependency);
                continue;
            }
            for (Dependency dependencyToCheck : dependenciesToCheck) {
                if (dependency.compareTo(dependencyToCheck) < 0) {
                    dependency = MatchedDependency.fromDependency(dependencyToCheck);
                    dependency.setActualVersion(actualVersion);
                }
            }
            matchedDependencies.add(dependency);
        }
        return matchedDependencies;
    }
}
