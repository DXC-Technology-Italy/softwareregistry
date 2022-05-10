package com.cm.dev.controller;

import com.cm.dev.domain.ProjectInfo;
import com.cm.dev.service.ProjectInfoService;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for ProjectInfo Objects
 * 
 */

@RestController
@CrossOrigin
public class ProjectInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInfoController.class);
    private static final String RESPONSE = "response";

    @Autowired
    private ProjectInfoService projectInfoService;

    
    /** 
     * @return List<ProjectInfo>
     */
    @RequestMapping("/projectInfo")
    public List<ProjectInfo> getAllProjectInfo() {
        List<ProjectInfo> projectInfos = new ArrayList<>();
        try {
            projectInfos = projectInfoService.getAllProjectInfos();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return projectInfos;
    }

    
    /** 
     * @param projectInfos
     * @return String
     */
    @PutMapping(value = "/projectInfo/saveChanges")
    public String saveChanges(@RequestBody List<ProjectInfo> projectInfos) {
        JsonObject out = new JsonObject();
        try {
            for (ProjectInfo pi : projectInfos) {
                projectInfoService.findAndModifyProjectInfo(pi);
            }
            out.addProperty(RESPONSE, "OK");
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
            out.addProperty(RESPONSE, "Error: " + e.getMessage( ));
        }
        return out.toString();
    }

    
    /** 
     * @param projectInfo
     * @return String
     */
    @PutMapping(value = "/projectInfo/changeMavenCertified")
    public String changeMavenCertified(@RequestBody ProjectInfo projectInfo) {
        JsonObject out = new JsonObject();
        try {

            projectInfoService.findAndModifyMavenCertified(projectInfo);
            out.addProperty(RESPONSE, "OK");

        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
            out.addProperty(RESPONSE, "Error: " + e.getMessage( ));
        }
        return out.toString();
    }

}

