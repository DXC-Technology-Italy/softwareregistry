package com.cm.dev.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Project domain object
 * 
 */
@Document(collection = "Project")
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

    
    /** 
     * @return ProjectInfo
     */
    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    
    /** 
     * @param projectInfo
     */
    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    
    /** 
     * @return ObjectId
     */
    public ObjectId getId() {
        return id;
    }

    
    /** 
     * @param id
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getRepository() {
        return repository;
    }

    
    /** 
     * @param repository
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    
    /** 
     * @return String
     */
    public String getArea() {
        return area;
    }

    
    /** 
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    
    /** 
     * @return String
     */
    public String getVersion() {
        return version;
    }

    
    /** 
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    
    /** 
     * @return String
     */
    public String getUrl() {
        return url;
    }

    
    /** 
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    
    /** 
     * @return List<Dependency>
     */
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    
    /** 
     * @param dependencies
     */
    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        StringBuilder out = new StringBuilder("Project: [" + this.name + ", " + this.repository + ", " + this.version + ", Dependencies: \n");
        if (this.dependencies != null) {
            for (Dependency d : dependencies)
                out.append(d.getId()).append(" :: ").append(d.getGroupId()).append(":").append(d.getArtifactId()).append(":").append(d.getVersion()).append("\n");
        }
        out.append("]");
        return out.toString();
    }

}
