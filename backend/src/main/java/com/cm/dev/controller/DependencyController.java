package com.cm.dev.controller;

import com.cm.dev.domain.Dependency;
import com.cm.dev.service.DependencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Dependency Objects
 * 
 */

@RestController
@CrossOrigin
public class DependencyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyController.class);

    @Autowired
    private DependencyService dependencyService;

    /**
     * Return a list of dependencies by searching the collection if the given name is contained in the artifact_id
     *
     *  @param name - artifact_id to search
     *
     * @return List<Dependency>
     */
    @GetMapping("/dependency/{name}")
    public List<Dependency> getDependenciesByName(@PathVariable(value = "name") String name) {
        List<Dependency> dependencies = new ArrayList<>();
        try {
            dependencies = dependencyService.getByName(name);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return dependencies;
    }

}
