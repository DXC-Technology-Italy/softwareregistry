package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="Project")
public class Project {
    @Id
    private ObjectId id;
    private String name;
    private String repository;
    
    private String area;
    private String version;
    private String url;

    @DBRef
    private List<Dependency> dependencies;

    @DBRef
    private ProjectInfo projectInfo;

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public String toString() {
        String out = "Project: [" + this.name + ", " + this.repository + ", " + this.version + ", Dependencies: \n";
        if (this.dependencies != null) {
            for (Dependency d : dependencies)
                out += d.getId() + " :: " + d.getGroupId() + ":" + d.getArtifactId() + ":" + d.getVersion() + "\n";
        }
        out += "]";
        return out;
    }

}
