package com.cm.dev.controller;

import com.cm.dev.domain.ProjectInfo;
import com.cm.dev.service.ProjectInfoService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ProjectInfoController {
    @Autowired
    ProjectInfoService projectInfoService;

    @RequestMapping("/projectInfo")
    public List<ProjectInfo> getAllProjectInfo() {
        List<ProjectInfo> projectInfos = new ArrayList<>();
        try {
            projectInfos = projectInfoService.getAllProjectInfos();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            return projectInfos;
        }
    }

    @RequestMapping(value = "/projectInfo/saveChanges", method = RequestMethod.PUT)
    public String saveChanges(@RequestBody List<ProjectInfo> projectInfos) {
        JsonObject out = new JsonObject();
        try {
            for (ProjectInfo pi : projectInfos) {
                projectInfoService.findAndModifyProjectInfo(pi);
            }
            out.addProperty("response", "OK");
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            out.addProperty("response", "Error: " + e.getMessage());
        } finally {
            return out.toString();
        }
    }

    @RequestMapping(value = "/projectInfo/changeMavenCertified", method = RequestMethod.PUT)
    public String ChangeMavenCertified(@RequestBody ProjectInfo projectInfo) {
        JsonObject out = new JsonObject();
        try {

            projectInfoService.findAndModifyMavenCertified(projectInfo);
            out.addProperty("response", "OK");

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            out.addProperty("response", "Error: " + e.getMessage());
        } finally {
            return out.toString();
        }


    }

}

