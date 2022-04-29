package com.cm.dev.controller;

import com.cm.dev.domain.Dependency;
import com.cm.dev.service.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import java.util.List;

@RestController
@CrossOrigin
public class DependencyController {

    @Autowired
    private DependencyService dependencyService;

    @GetMapping("/dependency/{name}")
    public List<Dependency> getDependenciesByName(@PathVariable(value = "name") String name) {
        List<Dependency> dependencies = new ArrayList<>();
        try {
            dependencies = dependencyService.getByName(name);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
        } finally {
            return dependencies;
        }
    }

}
