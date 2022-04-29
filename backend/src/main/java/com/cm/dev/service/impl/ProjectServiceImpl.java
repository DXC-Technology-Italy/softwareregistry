package com.cm.dev.service.impl;

import com.cm.dev.bean.MatchedDependency;
import com.cm.dev.dao.*;
import com.cm.dev.domain.*;
import com.cm.dev.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Project> getAllProjectsWithoutDependencies() throws Exception {
        return projectDAO.getAllProjectsWithoutDependencies();
    }

    public List<Project> getAllProjects() throws Exception {
        return projectDAO.getAll();
    }

    @Override
    public void reload() throws Exception {

        Map<String, Project> projects = PlainFileReaderServiceImpl.getInstance().getProjects();
        List<Dependency> dependencies = PlainFileReaderServiceImpl.getInstance().getDependencies();
        List<ProjectInfo> projectInfoEntities = new ArrayList<>(projectInfoDAO.getAll());
        HashMap<String, ProjectInfo> projectInfos = new HashMap<>();
        //List<Project> projectEntities = new ArrayList<>(projects.values());
        List<Project> projectEntities = new ArrayList<>();
        Area area = new Area();

        for( ProjectInfo pi: projectInfoEntities ) {
            projectInfos.put(pi.getName()+":"+pi.getRepository(), pi);
        }
        for( Project p: projects.values() ) {
            if(projectInfos.containsKey(p.getName()+":"+p.getRepository())){
                p.setProjectInfo(projectInfos.get(p.getName()+":"+p.getRepository()));
            }else{
                ProjectInfo pi = new ProjectInfo();
                pi.setName(p.getName());
                pi.setRepository(p.getRepository());
                p.setProjectInfo(projectInfoDAO.create(pi));
            }

            projectEntities.add(p);
        }

        dependencyDAO.deleteAll();
        dependencyDAO.createMany(dependencies);;

        projectDAO.deleteAll();
        projectDAO.createMany(projectEntities);

    }

    @Override
    public List<String> getDistinctProjects() throws Exception {
        return projectDAO.getDistinctProjects();
    }

    @Override
    public List<MatchedDependency> checkDependencies(String projectName) throws Exception {

        Project project = projectDAO.getByName(projectName);

        List<Dependency> dependencies = project.getDependencies();
        List<MatchedDependency> matchedDependencies = new ArrayList<>();

        for ( Dependency d: dependencies) {
            System.out.println(d);
            List<Dependency> dependenciesToCheck = dependencyDAO.getByNameAndGroupId(d.getArtifactId(), d.getGroupId());
            /* Checking all dependencies with same name and group Id */
            MatchedDependency dependency = MatchedDependency.fromDependency(d);
            String actualVersion = dependency.getVersion();
            dependency.setActualVersion(actualVersion);

            if (dependency.getGroupId().contains("templib")){
                matchedDependencies.add(dependency);
                continue;
            }
            for (Dependency dependencyToCheck: dependenciesToCheck) {
                if ( dependency.compareTo(dependencyToCheck) < 0 ) {
                    dependency = MatchedDependency.fromDependency(dependencyToCheck);
                    dependency.setActualVersion(actualVersion);
                }
            }
            matchedDependencies.add(dependency);
        }
        return matchedDependencies;
    }
}
