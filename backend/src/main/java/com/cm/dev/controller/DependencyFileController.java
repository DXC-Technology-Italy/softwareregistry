package com.cm.dev.controller;

import com.cm.dev.service.DependencyFileService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Controller for Dependencies on resource files
 * 
 */

@RestController
@CrossOrigin()
public class DependencyFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyFileController.class);
    @Autowired
    private DependencyFileService dependencyFileService;

    /**
     * Return  a json of the line of maven dependecy tree of all project
     *
     * @return String
     */

    @GetMapping("/file/dependencies/lines")
    public String getAllLines() {
        Gson gson = new Gson();
        HashMap<Integer, String> lines = new HashMap<>();
        try {
            lines = dependencyFileService.getAllLines();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return gson.toJson(lines);
    }

}
