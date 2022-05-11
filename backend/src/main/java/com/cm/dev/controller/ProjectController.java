package com.cm.dev.controller;

import com.cm.dev.bean.MatchedDependency;
import com.cm.dev.domain.Project;
import com.cm.dev.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Project Objects
 * 
 */

@RestController
@CrossOrigin
public class ProjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    /**
     * Returns all Project from the collection
     *
     * @return List<Project>
     */
    @RequestMapping(value = "/project")
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            projects = projectService.getAllProjects();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return projects;
    }

    /**
     * Returns all Project from the collection excluding the relation with dependencies collection
     *
     * @return List<Project>
     */

    @RequestMapping(value = "/project/withoutDependencies")
    public List<Project> getAllProjectsWithoutDependencies() {
        List<Project> projects = new ArrayList<>();
        try {
            projects = projectService.getAllProjectsWithoutDependencies();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return projects;
    }

    /**
     * Returns all Project from the collection excluding the relation with dependencies collection
     *
     * @return List<MatchedDependency>
     */

    @RequestMapping(value = "/project/{name}/checkDependencies")
    public List<MatchedDependency> checkDependencies(@PathVariable(value = "name") String name) {
        List<MatchedDependency> dependencies = new ArrayList<>();
        try {
            dependencies = projectService.checkDependencies(name);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return dependencies;
    }

    @RequestMapping("/project/reload")
    public void reload() {
        try {
            LOGGER.info("Reloading all projects");
            projectService.reload();
            LOGGER.info("End reloading all projects");

        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
    }

    
    /** 
     * @return List<String>
     */
    @RequestMapping("/distinctProject")
    public List<String> getDistinctProject() {
        List<String> projects = new ArrayList<>();
        try {
            projects = projectService.getDistinctProjects();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return projects;
    }

}
