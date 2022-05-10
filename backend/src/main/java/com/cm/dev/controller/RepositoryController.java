package com.cm.dev.controller;

import com.cm.dev.domain.Repository;
import com.cm.dev.service.AreaService;
import com.cm.dev.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Controller for Repository Objects
 * 
 */

@RestController
@CrossOrigin
public class RepositoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryController.class);

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private AreaService areaService;

    
    /** 
     * @param orderby
     * @return List<Repository>
     */
    @RequestMapping("/repository")
    public List<Repository> getAllRepositories(@RequestParam(required = false) String orderby) {
        List<Repository> repositories = new ArrayList<>();
        try {
            if (orderby != null) {
                List<String> orderFields = Arrays.asList(orderby.split(","));
                repositories = repositoryService.getAllOrdered(orderFields);
            } else {
                repositories = repositoryService.getAllRepositories();
            }
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return repositories;
    }


    
    /** 
     * @param code
     * @return List<Repository>
     */
    @RequestMapping("/repository/area/{code}")
    public List<Repository> getRepositoriesFromArea(@PathVariable(value = "code") String code) {
        List<Repository> repositories = new ArrayList<>();
        try {
            repositories = repositoryService.getRepositoriesFromArea(code);

        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return repositories;
    }

    
    /** 
     * @param kind
     * @return List<String>
     */
    @RequestMapping("/repository/kind/{kind}")
    public List<String> getRepositoriesByKind(@PathVariable(value = "kind") String kind) {
        List<String> repositories = new ArrayList<>();
        try {
            repositories = repositoryService.getRepositoriesByKind(kind);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return repositories;
    }

    @RequestMapping("/repository/reload")
    public void reload() {
        try {
            LOGGER.info("Reloading all repositories");
            repositoryService.reload();
            LOGGER.info("End reloading all repositories");
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
    }

    
    /** 
     * @return List<String>
     */
    @RequestMapping("/distinctRepositories")
    public List<String> getDistinctRepositories() {
        List<String> repositoryNames = new ArrayList<>();
        try {
            repositoryNames = repositoryService.getDistinctRepositories();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return repositoryNames;
    }

    
    /** 
     * @param orderby
     * @return ResponseEntity<String>
     */
    @RequestMapping("/repository/csv")
    public ResponseEntity<String> getRepositoryCSV(@RequestParam(required = false) String orderby) {
        StringBuilder builder = new StringBuilder();
        List<Repository> repositories;
        try {
            if (orderby != null) {
                List<String> orderFields = Arrays.asList(orderby.split(","));
                repositories = repositoryService.getAllOrdered(orderFields);
            } else {
                repositories = repositoryService.getAllRepositories();
            }
            builder.append("Nome;Area;Tipologia;UrlGit")
                    .append("\n");
            for (Repository r : repositories) {
                builder.append(r.getName())
                        .append(";")
                        .append(r.getArea())
                        .append(";")
                        .append(r.getKind())
                        .append(";")
                        .append(r.getUrl())
                        .append("\n");
            }
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(builder.toString());
    }

}
