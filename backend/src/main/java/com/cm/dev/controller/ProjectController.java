package com.cm.dev.controller;

import com.cm.dev.bean.MatchedDependency;
import com.cm.dev.domain.Project;
import com.cm.dev.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/project")
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            projects = projectService.getAllProjects();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
        } finally {
            return projects;
        }
    }

    @RequestMapping(value = "/project/withoutDependencies")
    public List<Project> getAllProjectsWithoutDependencies() {
        List<Project> projects = new ArrayList<>();
        try {
            projects = projectService.getAllProjectsWithoutDependencies();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
        } finally {
            return projects;
        }
    }

    @RequestMapping(value = "/project/report", produces="text/plain")
    public String report() {
        StringBuilder out = new StringBuilder();
        try {
            List<Project> projects = projectService.getAllProjectsWithoutDependencies();
           out.append("Certificato Maven;Nome Progetto; Versione; Repository; Tipologia; Url Git; Descrizione Progetto\n");
            for (Project p : projects) {
                ArrayList<String> projectType = new ArrayList<>();
                if (p.getProjectInfo().getProjectType() != null) {
                    for (String type : p.getProjectInfo().getProjectType()) {
                        switch (type) {
                            case "BATCH": projectType.add("Batch"); break;
                            case "LIBRARY": projectType.add("Libreria"); break;
                            case "ONLINE": projectType.add("Online"); break;
                            case "WEB_SERVICE": projectType.add("Servizio Web"); break;
                            case "CLIENT_LIBRARY": projectType.add("Libreria Client"); break;
                        }
                    }
                }

                out.append((p.getProjectInfo().getMavenCertified() != null && p.getProjectInfo().getMavenCertified()) ? "Si" : "No")
                    .append(";")
                    .append(p.getName())
                    .append(";")
                    .append(p.getVersion())
                    .append(";")
                    .append(p.getRepository())
                    .append(";")
                    .append(projectType.toString().replace("[","").replace("]",""))
                    .append(";")
                    .append(p.getUrl())
                    .append(";")
                    .append(p.getProjectInfo().getDescription() != null ? p.getProjectInfo().getDescription() : "")
                    .append("\n");
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
            e.printStackTrace();
        } finally {
            return out.toString();
        }
    }


    @RequestMapping(value = "/project/{name}/checkDependencies")
    public List<MatchedDependency> checkDependencies(@PathVariable(value = "name") String name) {
        List<MatchedDependency> dependencies = new ArrayList<>();
        try {
            dependencies = projectService.checkDependencies(name);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
            e.printStackTrace();
        } finally {
            return dependencies;
        }
    }

    @RequestMapping("/project/reload")
    public void reload(){
        try {
            System.out.println("Reloading all projects");
            projectService.reload();
            System.out.println("End reloading all projects");

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
            e.printStackTrace();
        }
    }

    @RequestMapping("/distinctProject")
    public List<String> getDistinctProject(){
        List<String> projects = new ArrayList<>();
        try {
            projects = projectService.getDistinctProjects();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
        } finally {
            return projects;
        }
    }

}
